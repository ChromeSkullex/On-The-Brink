package com.onthebrink.client.renderer;

import com.onthebrink.client.model.AnimalGeoModel;
import com.onthebrink.entity.animal.base.AnimalAnimatable;
import com.onthebrink.entity.animal.base.AnimalBase;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.function.Function;

public class AnimalGeoRenderer<T extends AnimalBase> extends  FixedGeoEntityRenderer<T>{

//    private final Function<ResourceLocation, RenderType> renderType;

    public AnimalGeoRenderer(EntityRendererProvider.Context renderManager, String modelName) {
        super(renderManager, new AnimalGeoModel<>(modelName));
//        this.renderType = renderType;
    }


}
