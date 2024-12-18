package com.onthebrink.entity.animal;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.level.Level;

public class PenguinTemp extends Chicken {
    public PenguinTemp(EntityType<? extends Chicken> entityType, Level level) {
        super(entityType, level);
    }
}
