package com.onthebrink.item.custom;

import com.onthebrink.entity.projectiles.CoconutEntity;
import com.onthebrink.entity.projectiles.RubberBallEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.function.Predicate;

public class RubberBallItem extends Item {
    public RubberBallItem(Properties properties) {
        super(properties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!world.isClientSide) {
            RubberBallEntity rubberBall = new RubberBallEntity(world, player);
            rubberBall.shootFromRotation(player, player.getXRot(), player.getYRot(), 0f, 1.5f, 1f);
            world.addFreshEntity(rubberBall);
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        player.swing(hand, true);
        world.playSound(null, player.blockPosition(), SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 1.0f, 1.0f);
        return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
    }
}
