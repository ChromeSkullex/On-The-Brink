package com.onthebrink.block.custom;

import com.onthebrink.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.WetSpongeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public class WetGlassSpongeBlock extends WetSpongeBlock {

    public WetGlassSpongeBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    // render it as if it was waterlogged (this just by itself still spawns water when broken)
    @Override
    @SuppressWarnings("deprecation") // it's not actually deprecated in 1.18.2 (mojang marked it like that in preparation)
    public @NotNull FluidState getFluidState(BlockState state) {
        return Fluids.WATER.getSource(false);
    }

    @Override
    @SuppressWarnings("deprecation") // unavoidable in 1.18.2
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock()) && !level.dimensionType().ultraWarm()) { // if it's in the nether don't bother removing the water
            // force the position to AIR so no water is left
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (level.dimensionType().ultraWarm()) {
            level.setBlock(pos, ModBlocks.GLASS_SPONGE.get().defaultBlockState(), 3);
            level.levelEvent(2009, pos, 0);
            level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, (1.0F + level.getRandom().nextFloat() * 0.2F) * 0.7F);
        }
    }
}
