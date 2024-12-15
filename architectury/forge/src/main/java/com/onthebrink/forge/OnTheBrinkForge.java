package com.onthebrink.forge;

import com.onthebrink.block.ModBlocks;
import dev.architectury.platform.forge.EventBuses;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.onthebrink.OnTheBrink;

@Mod(OnTheBrink.MOD_ID)
public final class OnTheBrinkForge {
    public OnTheBrinkForge() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(OnTheBrink.MOD_ID, modEventBus);

        modEventBus.addListener(this::clientSetup);

        // Run our common setup.
        OnTheBrink.init();
    }
    private void clientSetup (final FMLClientSetupEvent event) {
        setBlockRenderTypes();
    }

    public void setBlockRenderTypes(){
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WOODS_CYCAD_SPROUT.get(), RenderType.cutout());
    }
}
