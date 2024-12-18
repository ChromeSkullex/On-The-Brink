package com.onthebrink;

import com.mojang.logging.LogUtils;
import com.onthebrink.block.ModBlocks;
import com.onthebrink.item.ModItems;
import com.onthebrink.world.gen.ModTreeGeneration;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.minecraft.client.renderer.RenderType;
import com.onthebrink.entity.ModEntities;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public final class OnTheBrink  {
    public static final String MOD_ID = "onthebrink";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static ResourceLocation location(String path) {
        return new ResourceLocation(MOD_ID, path);
    }


    public static void init() {
        ModBlocks.register();
        ModItems.register();        
        ModEntities.register();
    }
}
