package com.onthebrink.misc.dispenser_behaviors;

import com.onthebrink.entity.projectiles.RubberBallEntity;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class RubberBallDispenserBehavior extends DefaultDispenseItemBehavior {
    @Override
    protected ItemStack execute(BlockSource source, ItemStack stack) {
        Level world = source.getLevel();

        if (!world.isClientSide) {
            Direction facing = source.getBlockState().getValue(DispenserBlock.FACING);
            RubberBallEntity rubberBall = new RubberBallEntity(
                    world,
                    source.getPos().getX() + 0.5,
                    source.getPos().getY() + 0.2,
                    source.getPos().getZ() + 0.5
            );

            rubberBall.shoot(
                    facing.getStepX(),
                    facing.getStepY() + 0.1, // slight upward arc
                    facing.getStepZ(),
                    1.5f,
                    1.0f
            );
            world.addFreshEntity(rubberBall);
        }

        stack.shrink(1);
        return stack;
    }

}
