package com.onthebrink.event;


import com.onthebrink.OnTheBrink;
import com.onthebrink.block.ModBlocks;
import com.onthebrink.block.custom.PoppyTeaCauldronBlock;
import com.onthebrink.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class SleepPoppyEvent {
    // number of sleep poppies needed to make tea
    public static final int POPPY_COUNT_TO_TEA = 4;

    private static int tickCount = 0;
    
    private static final int tickEvery = 20;
    
    // gets called every tick
    public static void tickSleepPoppiesInCauldrons(ServerLevel level) {
        if (level.isClientSide) return;

        // little counter so that we only run this every 20 ticks (1 second)
        // this method is a bit slow so it helps with lag
        if(level.dimension().equals(ServerLevel.OVERWORLD)) { tickCount += 1; } // if we don't do this, every dimention will increment
        if (tickCount < tickEvery){
            return;
        }
        tickCount = 0;

        final double WORLD_LIMIT = 30_000_000D;
        AABB entireWorldAABB = new AABB(
                -WORLD_LIMIT, level.getMinBuildHeight(), -WORLD_LIMIT,
                WORLD_LIMIT, level.getMaxBuildHeight(),  WORLD_LIMIT
        );

        // iterate over all item entities in the world
        List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, entireWorldAABB);

        for (ItemEntity itemEntity : items) {
            ItemStack stack = itemEntity.getItem();

            // we only care about Sleep Poppies
            if (!stack.is(ModItems.SLEEP_POPPY.get())) continue;

            BlockPos pos = itemEntity.blockPosition();
            BlockState state = level.getBlockState(pos);

            // ignore everything that isn't inside of a cauldron
            if (!state.is(Blocks.WATER_CAULDRON)) continue;

            int availablePoppies = stack.getCount();

            if (availablePoppies >= POPPY_COUNT_TO_TEA) {
                // consume poppies nom nom
                stack.shrink(POPPY_COUNT_TO_TEA);
                if (stack.isEmpty()) {
                    itemEntity.remove(Entity.RemovalReason.DISCARDED);
                } else {
                    itemEntity.setItem(stack);
                }

                // replace water cauldron with poppy tea cauldron
                level.setBlock(
                        pos,
                        ModBlocks.POPPY_TEA_CAULDRON.get().defaultBlockState()
                                .setValue(PoppyTeaCauldronBlock.LEVEL, 3), // completly filled
                        3
                );
            }
        }
    }
}
