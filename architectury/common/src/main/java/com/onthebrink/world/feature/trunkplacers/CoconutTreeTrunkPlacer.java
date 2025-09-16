package com.onthebrink.world.feature.trunkplacers;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.onthebrink.block.custom.CoconutFruitBlock;
import com.onthebrink.misc.MiscRegistry;
import dev.architectury.platform.Platform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class CoconutTreeTrunkPlacer extends TrunkPlacer {
    public static final Codec<CoconutTreeTrunkPlacer> CODEC = RecordCodecBuilder.create(
            instance -> trunkPlacerParts(instance).apply(instance, CoconutTreeTrunkPlacer::new)
    );

    public CoconutTreeTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        if(Platform.isFabric()){
            return MiscRegistry.COCONUT_TREE_TRUCK_PLACER;
        }
        return TrunkPlacerType.STRAIGHT_TRUNK_PLACER;
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(
            LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, Random random, int freeTreeHeight, BlockPos pos, TreeConfiguration config
    ) {
        // Place the trunk blocks
        for (int i = 0; i < freeTreeHeight; i++) {
            placeLog(level, blockSetter, random, pos.above(i), config);
        }

        BlockState coconutCrown = Registry.BLOCK.getOptional(new ResourceLocation("onthebrink", "coconut_tree_crown"))
                .map(Block::defaultBlockState)
                .orElseThrow(() -> new IllegalStateException("Coconut tree crown block not found in registry"));

        BlockPos topPos = pos.above(freeTreeHeight);
        blockSetter.accept(topPos, coconutCrown);
        blockSetter.accept(topPos.above(1), coconutCrown);

        // add coconuts to the crown
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (random.nextFloat() < 0.8F) { // 80% chance per direction
                Direction direction2 = direction.getOpposite();
                BlockPos blockPos2 = topPos.offset(direction2.getStepX(), 0, direction2.getStepZ());
                if (Feature.isAir(level, blockPos2)) {
                    BlockState coconutBlock = Registry.BLOCK.getOptional(new ResourceLocation("onthebrink", "coconut_fruit"))
                            .map(Block::defaultBlockState)
                            .map(state -> state
                                    .setValue(CoconutFruitBlock.FACING, direction)
                                    .setValue(CoconutFruitBlock.AGE, random.nextInt(3)) // random age like cocoa
                            )
                            .orElseThrow(() -> new IllegalStateException("Coconut block not found in registry"));
                    blockSetter.accept(blockPos2, coconutBlock);
                }
            }
        }

        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pos.above(freeTreeHeight+1), 0, false));
    }
}
