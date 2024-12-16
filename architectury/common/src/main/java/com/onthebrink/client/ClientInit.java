package com.onthebrink.client;

import com.onthebrink.block.ModBlocks;
import com.onthebrink.world.gen.ModTreeGeneration;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.minecraft.client.renderer.RenderType;

public class ClientInit {

    public static void later(){
        registerBlockRenderers();
        ModTreeGeneration.generateTrees();
    }

    public static void registerBlockRenderers(){
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.WOODS_CYCAD_SPROUT.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.CHOCOLATE_COSMOS.get());
    }
}
