package com.onthebrink.forge;

import com.onthebrink.client.ClientInit;
import com.onthebrink.client.OnTheBrinkClient;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.onthebrink.OnTheBrink;

@Mod(OnTheBrink.MOD_ID)
public final class OnTheBrinkForge {
    public OnTheBrinkForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
//        EventBuses.registerModEventBus(OnTheBrink.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
        EventBuses.registerModEventBus(OnTheBrink.MOD_ID, modEventBus);
        // Run our common setup.
        OnTheBrink.init();

        // Client
        modEventBus.addListener(this::onClient);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientInit::immediate);


    }

    public void onClient(FMLClientSetupEvent event) {

        ClientInit.later();
    }
}
