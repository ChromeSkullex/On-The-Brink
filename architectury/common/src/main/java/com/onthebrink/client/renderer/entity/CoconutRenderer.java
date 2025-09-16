package com.onthebrink.client.renderer.entity;

import com.onthebrink.entity.projectiles.CoconutEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class CoconutRenderer extends ThrownItemRenderer<CoconutEntity> {
    public CoconutRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

}
