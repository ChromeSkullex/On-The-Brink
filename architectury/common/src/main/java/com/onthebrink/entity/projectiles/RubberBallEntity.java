package com.onthebrink.entity.projectiles;

import com.onthebrink.OnTheBrink;
import com.onthebrink.entity.ModEntities;
import com.onthebrink.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RubberBallEntity extends ThrowableItemProjectile {

    public RubberBallEntity(EntityType<? extends RubberBallEntity> type, Level world) {
        super(type, world);
        this.setNoGravity(true);
    }

    public RubberBallEntity(Level world, LivingEntity shooter) {
        super(ModEntities.RUBBER_BALL_ENTITY.get(), shooter, world);
        this.setNoGravity(true);
    }

    public RubberBallEntity(Level world, double x, double y, double z) {
        super(ModEntities.RUBBER_BALL_ENTITY.get(), x, y, z, world);
        this.setNoGravity(true);
    }

    @Override
    public void tick() {
        super.tick();

        // only apply our custom gravity on the server to prevent desync (or else it looks like the ball keeps phasing through the ground)
        if (!this.level.isClientSide()) {
            Vec3 velocity = this.getDeltaMovement();

            if (!this.onGround) {
                this.setDeltaMovement(velocity.x, velocity.y - 0.04D, velocity.z);
            }

            final double checkDistance = 0.05D;
            Vec3 start = this.position();
            Vec3 end = start.add(0.0D, -checkDistance, 0.0D);

            HitResult clip = this.level.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
            if (clip.getType() == HitResult.Type.BLOCK && clip instanceof BlockHitResult) {

                final double normalVelocity = 0.01;
                final double friction = 0.98;

                Vec3 curr = this.getDeltaMovement();
                Vec3 groundedVel = new Vec3(curr.x * friction, normalVelocity, curr.z * friction);
                this.setDeltaMovement(groundedVel);

                this.hasImpulse = true;
            }

            BlockPos pos = this.blockPosition();
            BlockState state = this.level.getBlockState(pos);
            VoxelShape shape = state.getCollisionShape(this.level, pos);
            if (!shape.isEmpty() && shape.bounds().contains(this.getX() - pos.getX(), this.getY() - pos.getY(), this.getZ() - pos.getZ())) {
                Vec3 center = new Vec3(pos.getX() + 0.5, this.getY(), pos.getZ() + 0.5);
                Vec3 escapeDir = this.position().subtract(center.scale(-1)).normalize().scale(0.1); // small horizontal push
                this.setDeltaMovement(escapeDir.x, velocity.y, escapeDir.z);
            }

            this.move(MoverType.SELF, Vec3.ZERO);
        }
    }

    @Override
    protected Item getDefaultItem() {
        Item registered = Registry.ITEM.get(new ResourceLocation(OnTheBrink.MOD_ID, "rubber_ball"));
        return (registered == null) ? Items.SNOWBALL : registered;
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if (pResult.getEntity() instanceof RubberBallEntity) {
            return;
        }

        super.onHitEntity(pResult);
        pResult.getEntity().hurt(DamageSource.thrown(this, this.getOwner()), 0.0F);
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);

        if (this.level.isClientSide()) {
            return;
        }

        final double bounceFactor = 0.75D;

        Vec3 currentVelocity = this.getDeltaMovement();


        if (Math.abs(currentVelocity.y) > 0.05) {
            this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.SLIME_HURT_SMALL, SoundSource.NEUTRAL, 0.4F, 1.5F);

            if (pResult instanceof BlockHitResult blockHitResult) {
                Direction direction = blockHitResult.getDirection();
                Vec3 normal = new Vec3(direction.getStepX(), direction.getStepY(), direction.getStepZ());
                // The reflection formula is: v' = v - 2 * (v . n) * n
                Vec3 reflection = currentVelocity.subtract(normal.scale(2 * currentVelocity.dot(normal)));
                this.setDeltaMovement(reflection.scale(bounceFactor));
            } else {
                this.setDeltaMovement(currentVelocity.scale(-bounceFactor));
            }
        }
        else{
            if (pResult instanceof BlockHitResult blockHitResult) {
                Direction direction = blockHitResult.getDirection();
                Vec3 normal = new Vec3(direction.getStepX(), direction.getStepY(), direction.getStepZ());

                final double normalVelocity = 0.01; // like a normal force, but we are directly setting the opposing vel

                if (normal.y > 0) {
                    // smaller is more friction. 1 is no friction, I think just the air resistance is enough
                    final double friction = 1.0;

                    Vec3 groundedVel = new Vec3(currentVelocity.x, normalVelocity, currentVelocity.z).scale(friction);
                    this.setDeltaMovement(groundedVel);
                }
                else {
                    if(Math.abs(currentVelocity.x) > 0.2 || Math.abs(currentVelocity.z) > 0.2)
                        this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.SLIME_HURT_SMALL, SoundSource.NEUTRAL, 0.2F, 1.3F);

                    Vec3 reflection = currentVelocity.subtract(normal.scale(2 * currentVelocity.dot(normal)));

                    reflection = reflection.scale(bounceFactor);

                    reflection = new Vec3(reflection.x, normalVelocity, reflection.z);

                    this.setDeltaMovement(reflection);
                }
            }
        }
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        if (!this.level.isClientSide) {
            this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.CHICKEN_EGG, SoundSource.NEUTRAL, 0.7F, 1.0F);

            ItemEntity itemDrop = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(ModItems.RUBBER_BALL.get()));
            this.level.addFreshEntity(itemDrop);

            this.discard();
        }
        return InteractionResult.sidedSuccess(this.level.isClientSide);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (!this.level.isClientSide && pSource.getEntity() instanceof Player player) {
            Vec3 lookAngle = player.getLookAngle();
            this.setDeltaMovement(lookAngle.scale(1.2D).add(0, 0.2D, 0));

            this.hasImpulse = true;

            this.level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.SNOW_HIT, SoundSource.NEUTRAL, 0.7F, 1.0F);

            // return true to indicate the hit was handled, but don't call super.hurt() to prevent the entity from taking damage.
            return true;
        }
        // default behavior for other damage sources
        return super.hurt(pSource, pAmount);
    }

    @Override
    public boolean isPickable() {
        return true; // allows right click
    }

    @Override
    public boolean isAttackable() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

}