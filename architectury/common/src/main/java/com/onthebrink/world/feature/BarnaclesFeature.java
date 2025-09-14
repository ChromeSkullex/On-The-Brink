package com.onthebrink.world.feature;

import com.google.common.collect.Lists;
import com.onthebrink.block.ModBlocks;
import com.onthebrink.world.feature.configuration.BarnaclesFeatureConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class BarnaclesFeature extends Feature<BarnaclesFeatureConfiguration> {
    public BarnaclesFeature(){
        super(BarnaclesFeatureConfiguration.CODEC);
    }

    public static List<Direction> getShuffledDirections(BarnaclesFeatureConfiguration config, Random random) {
        List<Direction> list = Lists.<Direction>newArrayList(BarnaclesFeatureConfiguration.validDirections);
        Collections.shuffle(list, random);
        return list;
    }

    public static List<Direction> getShuffledDirectionsExcept(BarnaclesFeatureConfiguration config, Random random, Direction excludedDirection) {
        List<Direction> list = (List<Direction>) BarnaclesFeatureConfiguration.validDirections.stream().filter(direction2 -> direction2 != excludedDirection).collect(Collectors.toList());
        Collections.shuffle(list, random);
        return list;
    }

    private static boolean isAirOrWater(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER);
    }

    //@Override
    public boolean place_test(FeaturePlaceContext<BarnaclesFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();


        // Place a glowstone block
        world.setBlock(pos, Blocks.GLOWSTONE.defaultBlockState(), 3);
        world.getChunk(pos).markPosForPostprocessing(pos);

        return true;
    }

    @Override
    public boolean place(FeaturePlaceContext<BarnaclesFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos origin = context.origin();
        Random random = context.random();
        BarnaclesFeatureConfiguration config = context.config();

        List<Direction> directions = BarnaclesFeatureConfiguration.validDirections;
        if (directions.isEmpty()) {
            directions = List.of(Direction.UP, Direction.DOWN,
                    Direction.NORTH, Direction.SOUTH,
                    Direction.EAST, Direction.WEST);
        }

        // Try placing at the origin
        if (tryPlaceBarnacle(world, origin, random, config, directions)) {
            return true;
        }

        // Search outward up to searchRange
        for (Direction dir : directions) {
            BlockPos.MutableBlockPos mutablePos = origin.mutable();
            for (int i = 1; i <= config.searchRange(); i++) {
                mutablePos.move(dir);

                if (tryPlaceBarnacle(world, mutablePos, random, config, directions)) {
                    return true;
                }

                BlockState current = world.getBlockState(mutablePos);
                if (!current.isAir() && !current.is(Blocks.WATER)) {
                    break; // stop if blocked
                }
            }
        }

        return false;
    }
    private boolean tryPlaceBarnacle(WorldGenLevel world, BlockPos pos, Random random,
                                     BarnaclesFeatureConfiguration config, List<Direction> directions) {
        BlockState current = world.getBlockState(pos);

        boolean replacingWater = current.is(Blocks.WATER);
        if (!current.isAir() && !replacingWater) return false;

        GlowLichenBlock barnaclesBlock = (GlowLichenBlock) ModBlocks.BARNACLES.get();

        // Make a mutable copy to shuffle directions
        List<Direction> shuffled = new ArrayList<>(directions);
        Collections.shuffle(shuffled, random);

        for (Direction attachDir : shuffled) {
            BlockState adjacent = world.getBlockState(pos.relative(attachDir));

            // ✅ Only attach to valid blocks
            boolean canAttach = BarnaclesFeatureConfiguration.canBePlacedOn.stream()
                    .anyMatch(holder -> holder.value() == adjacent.getBlock());

            if (canAttach) {
                BlockState barnacleState = barnaclesBlock.defaultBlockState()
                        .setValue(GlowLichenBlock.getFaceProperty(attachDir), true);

                // ✅ Waterlog if replacing water
                if (replacingWater && barnacleState.hasProperty(BlockStateProperties.WATERLOGGED)) {
                    barnacleState = barnacleState.setValue(BlockStateProperties.WATERLOGGED, true);
                }

                world.setBlock(pos, barnacleState, 3);
                world.getChunk(pos).markPosForPostprocessing(pos);

                if (random.nextFloat() < config.chanceOfSpreading()) {
                    barnaclesBlock.spreadFromFaceTowardRandomDirection(barnacleState, world, pos, attachDir, random, true);
                }

                return true;
            }
        }

        return false; // no valid block found
    }

}
