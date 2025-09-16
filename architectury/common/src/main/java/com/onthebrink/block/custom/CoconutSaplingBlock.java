package com.onthebrink.block.custom;

import com.onthebrink.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;

public class CoconutSaplingBlock extends SaplingBlock {

    public CoconutSaplingBlock(AbstractTreeGrower treeGrower, Properties properties) {
        super(treeGrower, properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, net.minecraft.world.phys.BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(hand);

        // check if player is holding an axe or sword
        if (heldItem.getItem() instanceof AxeItem || heldItem.getItem() instanceof SwordItem) {
            if (!world.isClientSide) {
                // drop open coconut
                ItemStack drop = new ItemStack(ModItems.OPENED_COCONUT.get()); // Replace with your item
                popResource(world, pos, drop);

                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

                // play breaking sound
                world.playSound(null, pos, SoundEvents.BAMBOO_BREAK,
                        net.minecraft.sounds.SoundSource.BLOCKS, 1.0f, 1.0f);

                // spawn breaking particles
                world.levelEvent(2001, pos, Block.getId(state));
            }
            return InteractionResult.sidedSuccess(world.isClientSide);
        }

        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(Blocks.SAND)
                || state.is(Blocks.RED_SAND)
                || state.is(Blocks.TERRACOTTA)
                || state.is(Blocks.WHITE_TERRACOTTA)
                || state.is(Blocks.ORANGE_TERRACOTTA)
                || state.is(Blocks.MAGENTA_TERRACOTTA)
                || state.is(Blocks.LIGHT_BLUE_TERRACOTTA)
                || state.is(Blocks.YELLOW_TERRACOTTA)
                || state.is(Blocks.LIME_TERRACOTTA)
                || state.is(Blocks.PINK_TERRACOTTA)
                || state.is(Blocks.GRAY_TERRACOTTA)
                || state.is(Blocks.LIGHT_GRAY_TERRACOTTA)
                || state.is(Blocks.CYAN_TERRACOTTA)
                || state.is(Blocks.PURPLE_TERRACOTTA)
                || state.is(Blocks.BLUE_TERRACOTTA)
                || state.is(Blocks.BROWN_TERRACOTTA)
                || state.is(Blocks.GREEN_TERRACOTTA)
                || state.is(Blocks.RED_TERRACOTTA)
                || state.is(Blocks.BLACK_TERRACOTTA)
                || state.is(BlockTags.DIRT);
    }
}
