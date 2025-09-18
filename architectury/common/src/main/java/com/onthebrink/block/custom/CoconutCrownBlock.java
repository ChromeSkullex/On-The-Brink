package com.onthebrink.block.custom;

import com.onthebrink.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.Random;

public class CoconutCrownBlock extends RotatedPillarBlock {
    public CoconutCrownBlock(Properties properties) {
        super(properties);
    }


    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        if (random.nextInt(2) != 0) return; // 50% chance per tick

        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        Direction opposite = direction.getOpposite();

        BlockPos targetPos = pos.offset(opposite.getStepX(), 0, opposite.getStepZ());
        BlockState targetState = level.getBlockState(targetPos);

        Optional<Block> coconutFruitOpt = Registry.BLOCK.getOptional(new ResourceLocation("onthebrink", "coconut_fruit"));

        if (coconutFruitOpt.isEmpty()) return; // block not registered


        if (targetState.isAir()) {

            // check if there are at least 3 logs below
            for (int i = 1; i <= 3; i++) {
                BlockState below = level.getBlockState(pos.below(i));
                if (!below.is(ModBlocks.COCONUT_TREE_LOG.get())) {
                    return;
                }
            }

            BlockPos abovePos = pos.above();

            // check if there are 4 leaves in this shape + over the crown
            for (Direction horizontal : Direction.Plane.HORIZONTAL) {
                BlockPos neighborPos = abovePos.relative(horizontal);
                BlockState neighbor = level.getBlockState(neighborPos);
                if (!neighbor.is(ModBlocks.COCONUT_TREE_LEAVES.get())) return;
            }

            // after all this, is it even worth it lol I'm not sure if limiting
            // creativity in this way is a good choice.
            // the idea is to encourage people to at least shape their coconut farms
            // into something that resembles a palm tree.

            BlockState newFruit = coconutFruitOpt.get().defaultBlockState()
                    .setValue(CoconutFruitBlock.FACING, direction)
                    .setValue(CoconutFruitBlock.AGE, 0);
            level.setBlockAndUpdate(targetPos, newFruit);
        }
    }
}
