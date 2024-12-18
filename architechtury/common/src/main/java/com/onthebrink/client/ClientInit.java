package com.onthebrink.client;

import com.onthebrink.block.ModBlocks;
import com.onthebrink.client.renderer.AnimalGeoRenderer;
import com.onthebrink.entity.ModEntities;
import com.onthebrink.world.gen.ModTreeGeneration;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.minecraft.client.renderer.RenderType;

public class ClientInit {

    public static void later(){
        registerBlockRenderers();
        ModTreeGeneration.generateTrees();
    }

    public static void immediate(){
        registerEntityRenderer();

    }
    private static void registerEntityRenderer(){
        EntityRendererRegistry.register(ModEntities.PENGUIN_TEMP, context -> new AnimalGeoRenderer<>(context, "penguin_temp"));

    }


    public static void registerBlockRenderers(){
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.WOODS_CYCAD_SPROUT.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.CHOCOLATE_COSMOS.get());
    }
}
