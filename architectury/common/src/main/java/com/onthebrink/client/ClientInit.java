package com.onthebrink.client;

import com.onthebrink.OnTheBrink;
import com.onthebrink.block.ModBlocks;
import com.onthebrink.client.renderer.entity.CoconutRenderer;
import com.onthebrink.entity.ModEntities;
import com.onthebrink.event.BlowgunZoomEvent;
import com.onthebrink.item.ModItems;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.item.ItemPropertiesRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ClientInit {

    public static void later(){
        registerBlockRenderers();

        registerItemPredicates();
    }

    public static void immediate(){
        registerEntityRenderers();
    }

    public static void registerItemPredicates(){

        // this is to make the advanced blowgun item invisible when we are zooming
        // in the model file I added a predicate to change it to an invisible model (zero scale)
        ItemPropertiesRegistry.register(ModItems.ADVANCED_BLOWGUN.get(), OnTheBrink.location("zooming"),  (stack, world, entity, seed) -> {
            // return 1 when we want the invisible blowgun model
            if (entity == Minecraft.getInstance().player && BlowgunZoomEvent.runZoomMechanic) return 1.0F;
            return 0.0F;
        });

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

        ColorHandlerRegistry.registerBlockColors(new BlockColor() {
            @Override
            public int getColor(BlockState blockState, @Nullable BlockAndTintGetter blockAndTintGetter, @Nullable BlockPos blockPos, int i) {
                return 0xECEFB8;
            }
        }, ModBlocks.TRANQUILIZER_CAULDRON);
    }
}
