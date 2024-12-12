package com.onthebrink.entity.client;


import com.onthebrink.OnTheBrink;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/**
 * Base Animal Model is responsible for holding all the common Geo Animated Entities
 * functions and abstraction.
 * Ensure that the name follows a convention.
 */
public abstract class BaseAnimalModel<T extends IAnimatable> extends AnimatedGeoModel<T> {
   private final String entityName;

    public BaseAnimalModel(String entityName) {
        this.entityName = entityName;
    }
    @Override
    public Identifier getModelLocation(T entity) {
        return new Identifier(OnTheBrink.MOD_ID, "geo/" + entityName + ".geo.json");
    }

    @Override
    public Identifier getTextureLocation(T entity) {
        return new Identifier(OnTheBrink.MOD_ID, "textures/entity/" + entityName + ".png");
    }

    @Override
    public Identifier getAnimationFileLocation(T entity) {
        return new Identifier(OnTheBrink.MOD_ID, "animations/" + entityName + ".animation.json");
    }

}
