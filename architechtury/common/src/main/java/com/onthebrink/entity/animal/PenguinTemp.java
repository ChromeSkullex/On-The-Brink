package com.onthebrink.entity.animal;

import com.onthebrink.entity.ai.AnimalPanicGoal;
import com.onthebrink.entity.ai.AnimalWanderGoal;
import com.onthebrink.entity.animal.base.AnimalBase;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.level.Level;


/**
 * <h1>TODO</h1>
 * 1. Create goals
 * 2.
 * */
public class PenguinTemp extends AnimalBase  {
    public PenguinTemp(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(3, new AnimalWanderGoal(this, .5));
        goalSelector.addGoal(5, new AnimalPanicGoal(this, 0.8));
    }


}