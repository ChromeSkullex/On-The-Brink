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
        if (random.nextInt(2) == 0) { // 50% chance per tick
            Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
            Direction opposite = direction.getOpposite();

            // Match trunk placer logic: offset outwards
            BlockPos targetPos = pos.offset(opposite.getStepX(), 0, opposite.getStepZ());
            BlockState targetState = level.getBlockState(targetPos);

            Optional<Block> coconutFruitOpt = Registry.BLOCK.getOptional(new ResourceLocation("onthebrink", "coconut_fruit"));

            if (coconutFruitOpt.isEmpty()) {
                return; // block not registered
            }

            if (targetState.isAir()) {
                BlockState newFruit = coconutFruitOpt.get().defaultBlockState()
                        .setValue(CoconutFruitBlock.FACING, direction) // facing same as trunk placer
                        .setValue(CoconutFruitBlock.AGE, 0);
                level.setBlockAndUpdate(targetPos, newFruit);
            }
        }
    }
}
