package com.onthebrink.world.feature.trunkplacers;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import com.onthebrink.misc.MiscRegistry;
import dev.architectury.platform.Platform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class WoodsCycadTrunkPlacer extends TrunkPlacer {
    public static final Codec<WoodsCycadTrunkPlacer> CODEC = RecordCodecBuilder.create(
            instance -> trunkPlacerParts(instance).apply(instance, WoodsCycadTrunkPlacer::new)
    );

    public WoodsCycadTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        if(Platform.isFabric()){
            return MiscRegistry.WOODS_CYCAD_TRUNK_PLACER;
        }
        return TrunkPlacerType.STRAIGHT_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(
            LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, Random random, int freeTreeHeight, BlockPos pos, TreeConfiguration config
    ) {
        setDirtAt(level, blockSetter, random, pos.below(), config);

        // Place the trunk blocks
        for (int i = 0; i < freeTreeHeight; i++) {
            placeLog(level, blockSetter, random, pos.above(i), config);
        }

        float CONE_GENERATION_CHANCE = 0.5f;

        // Place the "onthebrink:woods_cycad_cone" block on top of the trunk
        if (random.nextFloat() < CONE_GENERATION_CHANCE) {
            BlockState cycadCone = Registry.BLOCK.getOptional(new ResourceLocation("onthebrink", "woods_cycad_cone"))
                    .map(Block::defaultBlockState)
                    .orElseThrow(() -> new IllegalStateException("Cycad cone block not found in registry"));

            blockSetter.accept(pos.above(freeTreeHeight), cycadCone);
        }

        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pos.above(freeTreeHeight), 0, false));
    }
}
