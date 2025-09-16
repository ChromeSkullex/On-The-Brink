package com.onthebrink.item.custom;

import com.onthebrink.block.ModBlocks;
import com.onthebrink.entity.projectiles.CoconutEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

import java.util.function.Supplier;

public class CoconutItem extends Item {
    private final Supplier<? extends Block> blockSupplier;

    public CoconutItem(Supplier<? extends Block> blockSupplier) {
        super(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_MISC));
        this.blockSupplier = blockSupplier;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // Raytrace to find what the player is aiming at (5 block reach here; adjust if needed)
        HitResult hit = player.pick(5.0D, 0.0F, false);

        // compute the candidate placement pos
        BlockPos placePos;
        if (hit.getType() == HitResult.Type.BLOCK) {
            net.minecraft.world.phys.BlockHitResult bhr = (net.minecraft.world.phys.BlockHitResult) hit;
            // place on the side of the block that was clicked
            placePos = bhr.getBlockPos().relative(bhr.getDirection());
        } else {
            // not looking at a block -> try throwing
            placePos = null;
        }

        Block block = blockSupplier.get();

        // Helper to check if the target position is replaceable (air or replaceable block like grass)
        java.util.function.Predicate<BlockPos> isReplaceable = (pos) -> {
            BlockState state = world.getBlockState(pos);
            return state.isAir() || state.getMaterial().isReplaceable();
        };

        // If we have a placePos and that position is suitable and the sapling can survive there -> place
        if (placePos != null && isReplaceable.test(placePos) && block.defaultBlockState().canSurvive(world, placePos)) {
            if (!world.isClientSide) {
                world.setBlock(placePos, block.defaultBlockState(), 3);
                if (!player.isCreative()) stack.shrink(1);
            }
            player.swing(hand, true);

            // play placing sound
            world.playSound(null, placePos, SoundEvents.WOOD_PLACE,
                    net.minecraft.sounds.SoundSource.BLOCKS, 1.0f, 1.0f);

            return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
        } else {
            // Can't place -> throw coconut
            if (!world.isClientSide) {
                CoconutEntity coconut = new CoconutEntity(world, player);
                // adjust velocity/power/spread to taste
                coconut.shootFromRotation(player, player.getXRot(), player.getYRot(), 0f, 1.5f, 1f);
                world.addFreshEntity(coconut);
                if (!player.isCreative()) stack.shrink(1);
            }
            player.swing(hand, true);

            // throwing sound
            world.playSound(null, player.blockPosition(), SoundEvents.SNOWBALL_THROW,
                    SoundSource.PLAYERS, 1.0f, 1.0f);

            return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
        }
    }


}

