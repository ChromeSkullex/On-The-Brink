package com.onthebrink.client;

import com.onthebrink.block.ModBlocks;
import com.onthebrink.client.renderer.entity.CoconutRenderer;
import com.onthebrink.entity.ModEntities;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ClientInit {

    public static void later(){
        registerBlockRenderers();
    }

    public static void immediate(){
        registerEntityRenderers();
    }

    public static void registerEntityRenderers(){
        // Misc
        EntityRendererRegistry.register(ModEntities.COCONUT_ENTITY, CoconutRenderer::new);
    }

    public static void registerBlockRenderers(){
        // Plants
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.WOODS_CYCAD_SPROUT.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.CHOCOLATE_COSMOS.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.AFRICAN_VIOLET.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.COCONUT_FRUIT.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.PINK_SAND_VERBENA.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.SLEEP_POPPY.get());

        // Animals (blocks)
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.BARNACLES.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.GLASS_SPONGE.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.WET_GLASS_SPONGE.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.SEASHELLS.get());

        // Building Blocks
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.WOODS_CYCAD_DOOR.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.WOODS_CYCAD_TRAPDOOR.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.CHAIN_LINK.get());

        registerBlockColors();
    }

    private static void registerBlockColors(){
        ColorHandlerRegistry.registerBlockColors(new BlockColor() {
            @Override
            public int getColor(BlockState blockState, @Nullable BlockAndTintGetter blockAndTintGetter, @Nullable BlockPos blockPos, int i) {
                return 0x96856C;
            }
        }, ModBlocks.POPPY_TEA_CAULDRON);
    }
}
