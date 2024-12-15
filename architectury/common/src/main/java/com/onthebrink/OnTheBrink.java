package com.onthebrink;

import com.mojang.logging.LogUtils;
import com.onthebrink.block.ModBlocks;
import com.onthebrink.item.ModItems;
import com.onthebrink.world.gen.ModTreeGeneration;
import net.minecraft.client.renderer.RenderType;
import org.slf4j.Logger;

public final class OnTheBrink  {
    public static final String MOD_ID = "onthebrink";
    public static final Logger LOGGER = LogUtils.getLogger();


    public static void init() {
        ModBlocks.register();
        ModItems.register();
//        LOGGER.info("Initializing On The Brink");

        // Uncomment to generate placeholder trees in the plains biome
        //ModTreeGeneration.generateTrees();

    }
}
