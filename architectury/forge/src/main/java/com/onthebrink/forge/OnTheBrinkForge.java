package com.onthebrink.forge;

import com.onthebrink.client.ClientInit;
import com.onthebrink.item.ModItems;
import com.onthebrink.world.feature.ModPlacedFeatures;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.onthebrink.OnTheBrink;

@Mod(OnTheBrink.MOD_ID)
public final class OnTheBrinkForge {
    public OnTheBrinkForge() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(OnTheBrink.MOD_ID, modEventBus);

        modEventBus.addListener(this::clientSetup);

        modEventBus.addListener(this::annoyingRegistries);

        // Run our common setup.
        OnTheBrink.init();

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientInit::immediate);
    }
    private void clientSetup (final FMLClientSetupEvent event) {
        ClientInit.later();
    }

    private void annoyingRegistries(FMLCommonSetupEvent event) {
        // safe place to deal with registries that forge is too stupid to load at the right order
        event.enqueueWork(ModPlacedFeatures::register);
        event.enqueueWork(ModItems::registerDispenserBehaviors);
    }
}
