package com.onthebrink.event;


import com.onthebrink.OnTheBrink;
import com.onthebrink.block.ModBlocks;
import com.onthebrink.block.custom.PoppyTeaCauldronBlock;
import com.onthebrink.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SleepPoppyEvent {
    // number of sleep poppies needed to make tea
    public static final int POPPY_COUNT_TO_TEA = 3;

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

        Set<ItemEntity> items = new HashSet<>();

        // iterate over each player (so that we get all the surrounding items)
        for (ServerPlayer player : level.players()) {
            ChunkPos playerChunk = new ChunkPos(player.blockPosition());

            // 3x3 chunk area around player
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    ChunkPos targetChunk = new ChunkPos(playerChunk.x + dx, playerChunk.z + dz);

                    // build AABB for the chunk
                    int minX = targetChunk.getMinBlockX();
                    int maxX = targetChunk.getMaxBlockX();
                    int minZ = targetChunk.getMinBlockZ();
                    int maxZ = targetChunk.getMaxBlockZ();

                    AABB chunkAABB = new AABB(
                            minX, level.getMinBuildHeight(), minZ,
                            maxX + 1, level.getMaxBuildHeight(), maxZ + 1
                    );

                    // collect all ItemEntities in this chunk
                    List<ItemEntity> found = level.getEntitiesOfClass(ItemEntity.class, chunkAABB);
                    items.addAll(found); // use a Set so duplicates don't get added twice
                }
            }
        }

        for (ItemEntity itemEntity : items) {
            ItemStack stack = itemEntity.getItem();

            // we only care about Sleep Poppies
            if (!stack.is(ModItems.SLEEP_POPPY.get())) continue;

            BlockPos pos = itemEntity.blockPosition();
            BlockState state = level.getBlockState(pos);

            // ignore everything that isn't inside of a cauldron
            if (!state.is(Blocks.WATER_CAULDRON)) continue;

            int cauldronLevel = state.getValue(PoppyTeaCauldronBlock.LEVEL);
            if (cauldronLevel < 3) continue; // cauldron needs to be full to make tea

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

                level.playSound(null, pos, SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.sendParticles(ParticleTypes.CLOUD,
                        pos.getX() + 0.5D,
                        pos.getY() + PoppyTeaCauldronBlock.getContentHeightStatic(state),
                        pos.getZ() + 0.5D,
                        8, 0.2, 0.1, 0.2, 0.0
                );
            }
        }
    }
}
