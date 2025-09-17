package com.onthebrink.item.custom;

import com.onthebrink.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class BucketOfPoppyTea extends Item {
    public BucketOfPoppyTea(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        BlockHitResult hitResult = getPlayerPOVHitResult(world, player, ClipContext.Fluid.NONE);

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos pos = hitResult.getBlockPos();
            BlockState state = world.getBlockState(pos);

            // placing on semi filled Poppy Tea Cauldrons is done by the cauldron's code
            // so we only need to concern ourselves with the vanilla cauldron
            if (state.getBlock() instanceof CauldronBlock) {
                if (!world.isClientSide) {
                    world.setBlock(
                            pos,
                            ModBlocks.POPPY_TEA_CAULDRON.get().defaultBlockState()
                                    .setValue(LayeredCauldronBlock.LEVEL, 3),
                            3
                    );

                    world.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);

                    if (!player.isCreative()) {
                        stack.shrink(1);
                        if (stack.isEmpty()) {
                            return InteractionResultHolder.success(new ItemStack(Items.BUCKET));
                        } else {
                            if (!player.getInventory().add(new ItemStack(Items.BUCKET))) {
                                player.drop(new ItemStack(Items.BUCKET), false);
                            }
                        }
                    }
                }
                return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
            }
        }

        return InteractionResultHolder.pass(stack);
    }

}
