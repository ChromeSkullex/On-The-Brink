package com.onthebrink.block.custom;

import com.onthebrink.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

public class PoppyTeaCauldronBlock extends AbstractCauldronBlock {
    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_CAULDRON;

    public PoppyTeaCauldronBlock(Properties properties) {
        super(properties, CauldronInteraction.EMPTY);
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, Integer.valueOf(1)));
    }

    // TODO: when the block is updated, check if there's a heat source below, if so, make it summon bubbles
    // this is important feedback so that players know something is happening

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        BlockState stateBelow = level.getBlockState(pos.below());
        Block blockBelow = stateBelow.getBlock();

        boolean isHeated = false;
        if (blockBelow == Blocks.FIRE || blockBelow == Blocks.SOUL_FIRE) {
            isHeated = true;
        } else if (blockBelow instanceof CampfireBlock && stateBelow.getValue(CampfireBlock.LIT)) {
            isHeated = true;
        }

        if (isHeated) {
            // WIP. I'll implement the transformation into a Tranquilizer Cauldron here.
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack heldItemStack = player.getItemInHand(hand);
        if (heldItemStack.isEmpty()) {
            return InteractionResult.PASS;
        }

        int currentLevel = state.getValue(LEVEL);
        Item heldItem = heldItemStack.getItem();

        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        // taking Tea From Cauldron
        if (heldItem == Items.BUCKET) {
            if (currentLevel == 3) {
                if (!player.getAbilities().instabuild) {
                    heldItemStack.shrink(1);
                }
                player.getInventory().add(new ItemStack(ModItems.BUCKET_OF_POPPY_TEA.get())); // Assumes you have this item registered
                level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
                level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                player.awardStat(Stats.USE_CAULDRON);
                return InteractionResult.SUCCESS;
            }
        }
        else if (heldItem == Items.GLASS_BOTTLE) {
            if (!player.getAbilities().instabuild) {
                heldItemStack.shrink(1);
            }
            // give the player a bottle of poppy tea (TODO: IMPLEMENT THE BOTTLE OF POPPY TEA)
            player.getInventory().add(new ItemStack(ModItems.BUCKET_OF_POPPY_TEA.get()));

            lowerFillLevel(state, level, pos);
            level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            player.awardStat(Stats.USE_CAULDRON);
            return InteractionResult.SUCCESS;
        }

        // check if player is holding a BUCKET_OF_POPPY_TEA to fill it up
        else if (heldItem == ModItems.BUCKET_OF_POPPY_TEA.get().asItem()) {
            if (currentLevel < 3) {
                if (!player.getAbilities().instabuild) {
                    player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                }
                // fill the cauldron to the max level
                level.setBlockAndUpdate(pos, state.setValue(LEVEL, 3));
                level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                player.awardStat(Stats.FILL_CAULDRON);
                return InteractionResult.SUCCESS;
            }
        }
        // check if player is holding a BOTTLE_OF_POPPY_TEA to add one level
        else if (heldItem == ModItems.BUCKET_OF_POPPY_TEA.get().asItem()) { // TODO: CHANGE TO BOTTLE OF POPPY TEA WHEN IMPLEMENTED
            if (currentLevel < 3) {
                if (!player.getAbilities().instabuild) {
                    player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
                }
                // increase the cauldron level by one
                level.setBlockAndUpdate(pos, state.setValue(LEVEL, currentLevel + 1));
                level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                player.awardStat(Stats.FILL_CAULDRON);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    protected double getContentHeight(BlockState state) {
        return (6.0 + (double)((Integer)state.getValue(LEVEL)).intValue() * 3.0) / 16.0;
    }

    public static void lowerFillLevel(BlockState state, Level level, BlockPos pos) {
        int i = (Integer)state.getValue(LEVEL) - 1;
        level.setBlockAndUpdate(pos, i == 0 ? Blocks.CAULDRON.defaultBlockState() : state.setValue(LEVEL, Integer.valueOf(i)));
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return (Integer)state.getValue(LEVEL);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    @Override
    public boolean isFull(BlockState state) {
        return (Integer)state.getValue(LEVEL) == 3;
    }

}
