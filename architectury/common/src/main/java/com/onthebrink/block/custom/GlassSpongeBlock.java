package com.onthebrink.block.custom;

import com.google.common.collect.Lists;
import com.onthebrink.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;

import java.util.Queue;

public class GlassSpongeBlock extends SpongeBlock {

    public GlassSpongeBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected void tryAbsorbWater(Level level, BlockPos pos) {
        if (this.removeWaterBreadthFirstSearch(level, pos)) {
            level.setBlock(pos, ModBlocks.WET_GLASS_SPONGE.get().defaultBlockState(), 2);
            level.levelEvent(2001, pos, Block.getId(Blocks.WATER.defaultBlockState()));
        }
    }

    // I needed to copy it from the SpongeBlock class because it's unfortunately private
    private boolean removeWaterBreadthFirstSearch(Level level, BlockPos pos) {
        Queue<Tuple<BlockPos, Integer>> queue = Lists.<Tuple<BlockPos, Integer>>newLinkedList();
        queue.add(new Tuple<>(pos, 0));
        int i = 0;

        while (!queue.isEmpty()) {
            Tuple<BlockPos, Integer> tuple = (Tuple<BlockPos, Integer>)queue.poll();
            BlockPos blockPos = tuple.getA();
            int j = tuple.getB();

            for (Direction direction : Direction.values()) {
                BlockPos blockPos2 = blockPos.relative(direction);
                BlockState blockState = level.getBlockState(blockPos2);
                FluidState fluidState = level.getFluidState(blockPos2);
                Material material = blockState.getMaterial();
                if (fluidState.is(FluidTags.WATER)) {
                    if (blockState.getBlock() instanceof BucketPickup && !((BucketPickup)blockState.getBlock()).pickupBlock(level, blockPos2, blockState).isEmpty()) {
                        i++;
                        if (j < 6) {
                            queue.add(new Tuple<>(blockPos2, j + 1));
                        }
                    } else if (blockState.getBlock() instanceof LiquidBlock) {
                        level.setBlock(blockPos2, Blocks.AIR.defaultBlockState(), 3);
                        i++;
                        if (j < 6) {
                            queue.add(new Tuple<>(blockPos2, j + 1));
                        }
                    } else if (material == Material.WATER_PLANT || material == Material.REPLACEABLE_WATER_PLANT) {
                        BlockEntity blockEntity = blockState.hasBlockEntity() ? level.getBlockEntity(blockPos2) : null;
                        dropResources(blockState, level, blockPos2, blockEntity);
                        level.setBlock(blockPos2, Blocks.AIR.defaultBlockState(), 3);
                        i++;
                        if (j < 6) {
                            queue.add(new Tuple<>(blockPos2, j + 1));
                        }
                    }
                }
            }

            if (i > 64) {
                break;
            }
        }

        return i > 0;
    }

}
