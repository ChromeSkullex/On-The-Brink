package com.onthebrink;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.logging.LogUtils;
import com.onthebrink.block.ModBlocks;
import com.onthebrink.entity.ModEntities;
import com.onthebrink.item.ModCompostables;
import com.onthebrink.item.ModFuels;
import com.onthebrink.item.ModItems;
import com.onthebrink.util.GroundCoverBiomeConfig;
import com.onthebrink.world.feature.ModFeatures;
import com.onthebrink.world.feature.configuration.ModConfiguredFeatures;
import com.onthebrink.world.gen.ModFlowerGeneration;
import com.onthebrink.world.gen.ModMiscGroundCoverGeneration;
import com.onthebrink.world.gen.ModTreeGeneration;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.registry.fuel.FuelRegistry;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.block.Blocks;
import org.slf4j.Logger;

public final class OnTheBrink  {
    public static final String MOD_ID = "onthebrink";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        LifecycleEvent.SERVER_BEFORE_START.register(OnTheBrink::onServerStart);

        GroundCoverBiomeConfig.load();

        ModFeatures.register();

        ModBlocks.register(); // must be before items
        ModItems.register();

        ModEntities.register();

        ModTreeGeneration.generateTrees();
        ModFlowerGeneration.generateFlowers();
        ModMiscGroundCoverGeneration.generateMiscGroundCover();

        runAtSetupEvent();
    }

    private static void runAtSetupEvent()
    {
        LifecycleEvent.SETUP.register(() -> {
            // things that need to use the SETUP lifecycle event so that they work on forge
            ModCompostables.load();
            ModFuels.register();
        });
    }

    private static void onServerStart(MinecraftServer server) {
        //LOGGER.info("Server started!");
        // this is not needed yet, but it'll likely be at some point, so I'm leaving it here for convenience
    }

    public static ResourceLocation location(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
