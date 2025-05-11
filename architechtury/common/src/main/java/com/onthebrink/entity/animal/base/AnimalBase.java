package com.onthebrink.entity.animal.base;

import com.onthebrink.OnTheBrink;
import com.onthebrink.entity.util.Gender;
import dev.architectury.extensions.network.EntitySpawnExtension;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class AnimalBase extends TamableAnimal implements AnimalAnimatable<AnimalBase>{

    // Variables
    private static final EntityDataAccessor<Integer> HUNGER = SynchedEntityData.defineId(AnimalBase.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> HAPPINESS = SynchedEntityData.defineId(AnimalBase.class, EntityDataSerializers.INT);
//    private Gender gender = Gender.random(random);
    private static final EntityDataAccessor<Byte> GENDER = SynchedEntityData.defineId(AnimalBase.class, EntityDataSerializers.BYTE);


    /**
     * <h1>***** Data Save/load *****</h1>
     * **/
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(HUNGER, getMaxHunger());
        entityData.define(HAPPINESS, 0);
        entityData.define(GENDER, (byte)Gender.random(random).ordinal());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setHappiness(compound.getInt("Happiness"));
        this.setHunger(compound.getInt("Hunger"));
        this.setGender(Gender.values()[compound.getInt("Gender")]);

    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Hunger", getHunger());
        compound.putInt("Happiness", getHappiness());
        compound.putByte("Gender", (byte) getGender().ordinal());

    }

    /**
     * <h1>***** ANIMATIONS *****</h1>
    * **/
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);


    protected AnimalBase(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }


    @Override
    // From IAnimatable
    public void registerControllers(AnimationData data) {
    }

    @Override
    // From IAnimatable
    public AnimationFactory getFactory() {
        return factory;
    }


    /**
     * <h1>***** Data Setters/Getters *****</h1>
     * **/
    // Hunger
    public int getHunger(){
        return this.entityData.get(HUNGER);
    }

    // Change this to info
    private int getMaxHunger(){
        return 100;
    }
    public void setHunger(int hunger){
        entityData.set(HUNGER, Math.min(hunger, getMaxHunger()));
    }

    // Happiness
    public int getHappiness(){
        return this.entityData.get(HAPPINESS);
    }
    public void setHappiness(int happiness){
        entityData.set(HAPPINESS, happiness);
    }

    // Gender
    public void setGender(@NotNull Gender gender) {
        this.entityData.set(GENDER, (byte) gender.ordinal());
    }

    public Gender getGender() {
        return Gender.values()[this.entityData.get(GENDER)];
    }

    // ALL OTHER FUNCTIONS

    @Override
    public void tick(){
        super.tick();
        if (!level.isClientSide()) {
            // Ticking down hunger
            if (tickCount % 1200 == 0 && getHunger() > 0){
                setHunger(getHunger() - 1);
            }
        }
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (heldItem.isEmpty()){
            return InteractionResult.PASS;
        }
        if (heldItem.is(Items.STICK)){
            String hungerText = "Hunger: " + getHunger();
            String happinessText = " Happiness: " + getHappiness();
            String genderText = "Gender: " + getGender();
            player.displayClientMessage(new TranslatableComponent(hungerText + happinessText + genderText), true);

            return InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }




}

