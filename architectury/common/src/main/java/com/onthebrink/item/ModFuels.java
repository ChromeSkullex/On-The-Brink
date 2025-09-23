package com.onthebrink.item;

import dev.architectury.registry.fuel.FuelRegistry;

public class ModFuels {
    public static void register()
    {
        final int oneItem = 200; // 200 ticks to smelt one item

        FuelRegistry.register(oneItem * 10, ModItems.BURNT_RUBBER.get()); // i.e. 10 items

        FuelRegistry.register(oneItem * 2, ModItems.EMPTY_COCONUT.get());
    }
}
