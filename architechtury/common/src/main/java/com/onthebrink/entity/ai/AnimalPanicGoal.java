package com.onthebrink.entity.ai;

import com.onthebrink.entity.animal.base.AnimalBase;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.PanicGoal;

public class AnimalPanicGoal extends PanicGoal {
    public AnimalPanicGoal(AnimalBase mob, double speedModifier) {
        super(mob, speedModifier);
    }
}
