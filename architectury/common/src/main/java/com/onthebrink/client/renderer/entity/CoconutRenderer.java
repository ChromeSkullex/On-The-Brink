package com.onthebrink.client.renderer.entity;

import com.onthebrink.entity.projectiles.CoconutEntity;
import com.onthebrink.item.ModItems;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.item.ItemStack;

public class CoconutRenderer extends ThrownItemRenderer<CoconutEntity> {
    public CoconutRenderer(EntityRendererProvider.Context context) {
        // Use the coconut item as the rendered item
        super(context);
    }

}
