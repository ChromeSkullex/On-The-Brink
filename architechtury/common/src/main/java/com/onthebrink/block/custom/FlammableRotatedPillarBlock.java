package com.onthebrink.block.custom;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static com.onthebrink.block.ModBlocks.*;

public class FlammableRotatedPillarBlock {
    @ExpectPlatform
    public static void registerStripped(Block base, Block stripped) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static RotatedPillarBlock get(BlockBehaviour.Properties properties) {
        throw new AssertionError();
    }

    public static void registerAllStripped() {
        STRIPPED_WOODS_CYCAD_LOG.listen(stripped -> WOODS_CYCAD_LOG.listen(base -> registerStripped(base, stripped)));
        STRIPPED_WOODS_CYCAD_WOOD.listen(stripped -> WOODS_CYCAD_WOOD.listen(base -> registerStripped(base, stripped)));
    }
}