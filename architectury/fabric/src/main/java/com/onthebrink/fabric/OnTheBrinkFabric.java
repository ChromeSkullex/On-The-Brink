package com.onthebrink.fabric;

import com.onthebrink.client.ClientInit;
import com.onthebrink.misc.MiscRegistryFabric;
import com.onthebrink.world.feature.ModPlacedFeatures;
import com.onthebrink.world.feature.configuration.ModConfiguredFeatures;
import dev.architectury.platform.Mod;
import net.fabricmc.api.ModInitializer;

import com.onthebrink.OnTheBrink;
import net.minecraft.data.BuiltinRegistries;

public final class OnTheBrinkFabric implements ModInitializer {
    private static boolean initialized = false;

    @Override
    public void onInitialize() {
        if (initialized) {
            return;
        }
        initialized = true;

        MiscRegistryFabric.registerTreePlacerTypes();

        // Run our common setup.
        OnTheBrink.init();

        ModPlacedFeatures.register();

        ClientInit.later();
    }
}
