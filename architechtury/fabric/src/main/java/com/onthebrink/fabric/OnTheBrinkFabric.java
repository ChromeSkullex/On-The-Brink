package com.onthebrink.fabric;

import com.onthebrink.client.ClientInit;
import com.onthebrink.misc.MiscRegistryFabric;
import net.fabricmc.api.ModInitializer;

import com.onthebrink.OnTheBrink;

public final class OnTheBrinkFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // This is necessary because fabric doesnt have an api for dealing with TrunkPlacers and FoliagePlacers
        // So we need to use mixins
        MiscRegistryFabric.registerTreePlacerTypes();

        // Run our common setup.
        OnTheBrink.init();

        ClientInit.later();
    }
}
