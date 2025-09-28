package com.onthebrink.client.renderer;

import com.onthebrink.client.model.AnimalGeoModel;
import com.onthebrink.entity.animal.base.AnimalBase;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class AnimalGeoRenderer<T extends AnimalBase> extends  FixedGeoEntityRenderer<T>{


    public AnimalGeoRenderer(EntityRendererProvider.Context renderManager, String modelName) {
        super(renderManager, new AnimalGeoModel<>(modelName));
    }




}
