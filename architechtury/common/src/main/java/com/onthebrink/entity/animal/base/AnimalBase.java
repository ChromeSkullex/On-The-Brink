package com.onthebrink.entity.animal.base;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;
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

}