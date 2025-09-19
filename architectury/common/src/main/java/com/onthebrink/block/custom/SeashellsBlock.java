package com.onthebrink.block.custom;

import com.onthebrink.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SeashellsBlock extends GroundCoverBlock {
    public SeashellsBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, net.minecraft.world.phys.BlockHitResult hit) {
        ItemStack drop = new ItemStack(ModItems.SEASHELLS_ITEM.get());
        popResource(world, pos, drop);

        world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

        world.playSound(null, pos, SoundEvents.CHICKEN_EGG, // the egg laying sound is a satisfying *pop* :)
                net.minecraft.sounds.SoundSource.BLOCKS, 1.0f, 1.0f);

        return InteractionResult.sidedSuccess(world.isClientSide);
    }
}