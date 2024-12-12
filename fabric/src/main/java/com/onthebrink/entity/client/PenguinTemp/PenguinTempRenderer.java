package com.onthebrink.entity.client.PenguinTemp;

import com.onthebrink.entity.penguinTemp.PenguinTempEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PenguinTempRenderer extends GeoEntityRenderer<PenguinTempEntity> {

    public PenguinTempRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new PenguinTempModel());
    }

}
