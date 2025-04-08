package com.onthebrink.client.model;

import com.onthebrink.OnTheBrink;
import com.onthebrink.entity.animal.base.AnimalAnimatable;
import com.onthebrink.entity.animal.base.AnimalBase;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AnimalGeoModel<T extends AnimalBase > extends AnimatedGeoModel<T> {

    private final ResourceLocation modelLocation;
    private final ResourceLocation animationLocation;
    private final ResourceLocation textureLocation;

    public AnimalGeoModel(String modelName) {
        this.modelLocation = OnTheBrink.location("geo/entities/"+modelName+".geo.json");
        this.animationLocation = OnTheBrink.location("animations/"+modelName+".animation.json");
        this.textureLocation = OnTheBrink.location("textures/entities/"+modelName+".png");
    }


    @Override
    public ResourceLocation getAnimationFileLocation(T animatable) {
        return animationLocation;
    }

    @Override
    public ResourceLocation getModelLocation(T object) {
        return modelLocation;
    }

    @Override
    public ResourceLocation getTextureLocation(T object) {
        return textureLocation;
    }
}
