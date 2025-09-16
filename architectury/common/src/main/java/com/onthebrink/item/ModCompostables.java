package com.onthebrink.item;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.platform.Platform;
import net.minecraft.world.level.block.ComposterBlock;

public class ModCompostables {
    public static void load() {
        ComposterBlock.COMPOSTABLES.put(ModItems.EMPTY_COCONUT.get(), 0.2f);
    }

}
