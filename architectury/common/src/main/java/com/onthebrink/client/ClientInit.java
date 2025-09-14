package com.onthebrink.client;

import com.onthebrink.block.ModBlocks;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.minecraft.client.renderer.RenderType;

public class ClientInit {

    public static void later(){
        registerBlockRenderers();
    }

    public static void registerBlockRenderers(){
        // Plants
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.WOODS_CYCAD_SPROUT.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.CHOCOLATE_COSMOS.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.AFRICAN_VIOLET.get());

        // Animals (blocks)
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.BARNACLES.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.GLASS_SPONGE.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.WET_GLASS_SPONGE.get());

        // Building Blocks
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.WOODS_CYCAD_DOOR.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.WOODS_CYCAD_TRAPDOOR.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.CHAIN_LINK.get());
    }
}
