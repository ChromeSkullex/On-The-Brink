package com.onthebrink.item.custom;

import com.onthebrink.block.ModBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BucketOfPoppyTeaItem extends Item {
    public BucketOfPoppyTeaItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(new TranslatableComponent("item.onthebrink.bottle_of_poppy_tea.desc_0")
                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));

        tooltip.add(Component.nullToEmpty("")); // empty line

        tooltip.add(new TranslatableComponent("item.onthebrink.bottle_of_poppy_tea.desc_1")
                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));

        tooltip.add(new TranslatableComponent("item.onthebrink.bottle_of_poppy_tea.desc_2")
                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
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
                return interactWithCauldron(world, player, pos, stack);
            }
        }

        player.startUsingItem(hand); // NECESSARY FOR THE DRINKING MECHANIC TO ACTUALLY TRIGGER

        return InteractionResultHolder.pass(stack);
    }

    private InteractionResultHolder<ItemStack> interactWithCauldron(Level world, Player player, BlockPos pos, ItemStack stack){
        if (world.isClientSide) return InteractionResultHolder.pass(stack);
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
        return InteractionResultHolder.pass(stack);
    }

    // drinking stuff

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32; // drinking time
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        if (!world.isClientSide) {
            // we multiply by 20 to convert from seconds to ticks
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 10, 1));
            entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 20 * 1800, 0));
            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 30, 0)); // nausea
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * 1800, 1));
        }

        if (entity instanceof Player player) {
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);

                // if the original stack is now empty, return a bucket to put in hand
                ItemStack empty = new ItemStack(Items.BUCKET);
                if (stack.isEmpty()) {
                    return empty;
                } else {
                    // otherwise try to add the bucket to inventory (or drop it)
                    if (!player.getInventory().add(empty)) {
                        player.drop(empty, false);
                    }
                }
            }
        }

        return stack;
    }
}
