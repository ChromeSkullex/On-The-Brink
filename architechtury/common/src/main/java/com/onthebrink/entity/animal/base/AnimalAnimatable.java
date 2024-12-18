package com.onthebrink.entity.animal.base;

import net.minecraft.world.entity.Mob;
import software.bernie.geckolib3.core.IAnimatable;

public interface AnimalAnimatable<T extends Mob & AnimalAnimatable<T>> extends IAnimatable {
    // this is for animations



}
