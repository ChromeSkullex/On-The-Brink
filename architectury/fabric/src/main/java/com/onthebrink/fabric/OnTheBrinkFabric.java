package com.onthebrink.fabric;

import com.onthebrink.block.ModBlocks;
import net.fabricmc.api.ModInitializer;

import com.onthebrink.OnTheBrink;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public final class OnTheBrinkFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        OnTheBrink.init();

        setBlockRenderTypes();
    }
    public void setBlockRenderTypes(){
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WOODS_CYCAD_SPROUT.get(), RenderType.cutout());
    }

}
