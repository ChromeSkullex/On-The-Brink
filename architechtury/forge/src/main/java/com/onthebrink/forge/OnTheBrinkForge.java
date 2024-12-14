package com.onthebrink.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.onthebrink.OnTheBrink;

@Mod(OnTheBrink.MOD_ID)
public final class OnTheBrinkForge {
    public OnTheBrinkForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(OnTheBrink.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        OnTheBrink.init();
    }
}
