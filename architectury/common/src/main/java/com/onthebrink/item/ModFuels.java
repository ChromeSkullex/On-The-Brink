package com.onthebrink.item;

import com.onthebrink.block.ModBlocks;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.registry.fuel.FuelRegistry;
import net.minecraft.world.level.block.ComposterBlock;

public class ModFuels {
    public static void register()
    {
        FuelRegistry.register(20, ModItems.EMPTY_COCONUT.get()); // 20 ticks. takes 10 ticks to smelt one item, so 20 smelts two
    }
}
