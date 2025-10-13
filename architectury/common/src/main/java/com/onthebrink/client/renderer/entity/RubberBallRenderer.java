package com.onthebrink.client.renderer.entity;

import com.onthebrink.entity.projectiles.CoconutEntity;
import com.onthebrink.entity.projectiles.RubberBallEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class RubberBallRenderer extends ThrownItemRenderer<RubberBallEntity> {
    public RubberBallRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.25F;
    }
}
