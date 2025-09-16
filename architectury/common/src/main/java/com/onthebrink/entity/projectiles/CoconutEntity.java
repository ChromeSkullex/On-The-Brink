package com.onthebrink.entity.projectiles;

import com.onthebrink.OnTheBrink;
import com.onthebrink.entity.ModEntities;
import com.onthebrink.item.ModItems;
import net.minecraft.core.Direction; // For manual bounce calculation
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource; // <-- Changed Import
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class CoconutEntity extends ThrowableItemProjectile {

    private static final EntityDataAccessor<Integer> BOUNCES =
            SynchedEntityData.defineId(CoconutEntity.class, EntityDataSerializers.INT);

    public CoconutEntity(EntityType<? extends CoconutEntity> type, Level world) {
        super(type, world);
    }

    public CoconutEntity(Level world, LivingEntity shooter) {
        super(ModEntities.COCONUT_ENTITY.get(), shooter, world);
    }

    public CoconutEntity(Level world, double x, double y, double z) {
        super(ModEntities.COCONUT_ENTITY.get(), x, y, z, world);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BOUNCES, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Bounces", this.entityData.get(BOUNCES));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(BOUNCES, pCompound.getInt("Bounces"));
    }

    @Override
    protected Item getDefaultItem() {
        Item registered = Registry.ITEM.get(new ResourceLocation(OnTheBrink.MOD_ID, "coconut"));
        return (registered == null) ? Items.SNOWBALL : registered;
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        // ▼▼▼ FIXED: Using static DamageSource.thrown instead of damageSources() ▼▼▼
        pResult.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 1.0F);
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);

        // ▼▼▼ FIXED: Using this.level (direct field) instead of this.level() (method) ▼▼▼
        if (this.level.isClientSide()) {
            return;
        }

        int currentBounces = this.entityData.get(BOUNCES);

        if (currentBounces < 1) {
            this.entityData.set(BOUNCES, currentBounces + 1);

            Vec3 currentVelocity = this.getDeltaMovement();
            double bounceFactor = 0.4D;

            if (pResult instanceof BlockHitResult blockHitResult) {
                // ▼▼▼ FIXED: Manually calculating the bounce vector ▼▼▼
                Direction direction = blockHitResult.getDirection();
                Vec3 normal = new Vec3(direction.getStepX(), direction.getStepY(), direction.getStepZ());
                // The reflection formula is: v' = v - 2 * (v . n) * n
                Vec3 reflection = currentVelocity.subtract(normal.scale(2 * currentVelocity.dot(normal)));
                this.setDeltaMovement(reflection.scale(bounceFactor));
            } else {
                this.setDeltaMovement(currentVelocity.scale(-bounceFactor));
            }

            this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.SNOW_HIT, SoundSource.NEUTRAL, 0.4F, 1.5F);

        } else {
            if (this.level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        new ItemParticleOption(ParticleTypes.ITEM, this.getItem()),
                        this.getX(), this.getY(), this.getZ(),
                        10, (this.getBbWidth() / 2.0D), (this.getBbHeight() / 2.0D), (this.getBbWidth() / 2.0D), 0.05D
                );
            }

            this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.WOOD_BREAK, SoundSource.NEUTRAL, 0.7F, 1.0F);

            // ▼▼▼ CHANGE THIS LINE TO YOUR DESIRED ITEM ▼▼▼
            ItemEntity itemDrop = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(ModItems.OPENED_COCONUT.get()));
            this.level.addFreshEntity(itemDrop);

            this.discard();
        }
    }
}