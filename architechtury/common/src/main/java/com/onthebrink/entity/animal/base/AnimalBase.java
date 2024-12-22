package com.onthebrink.entity.animal.base;

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
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;



/**
 * <h1>TODO</h1>
 * 1. Create Attribute function
 * 2. Create shared goals
 * 3. Create Attributes: hunger, gender, traits, etc.
 * */
public class AnimalBase extends TamableAnimal implements AnimalAnimatable<AnimalBase> {

    // Data Accessors
    private static final EntityDataAccessor<Integer> HUNGER = SynchedEntityData.defineId(AnimalBase.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> HAPPINESS = SynchedEntityData.defineId(AnimalBase.class, EntityDataSerializers.INT);


    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(HUNGER, getMaxHunger());
        entityData.define(HAPPINESS, 0);
    }

    // ANIMATIONS
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);


    protected AnimalBase(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }



    // SETTERS AND GETTERS HERE

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
            player.displayClientMessage(new TranslatableComponent(hungerText + happinessText), true);

            return InteractionResult.PASS;
        }
        return InteractionResult.PASS;
    }
}

