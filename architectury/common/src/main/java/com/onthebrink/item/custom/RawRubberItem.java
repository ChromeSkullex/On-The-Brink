package com.onthebrink.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RawRubberItem extends Item {
    public RawRubberItem(Properties properties) {
        super(properties.food(new FoodProperties.Builder()
                .nutrition(1) // 0.5 chopsticks
                .saturationMod(0.1f)
                .alwaysEat() // eat even if not hungry. who am I to judge
                .build()));

    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32; // eating time
    }

    @Override
    public @NotNull SoundEvent getEatingSound() {
        return SoundEvents.SLIME_SQUISH; // squishy chewing nom nom
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }

    // TODO SKULLEX IF YOU'RE READING THIS DO NOT TELL THE TEAM THAT YOU CAN EAT RUBBER
    // TODO I WANT TO TAKE THEM BY SURPRISE
    // yes I am using TODO just to highlight

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        if (entity instanceof Player player) {
            player.addEffect(new MobEffectInstance(MobEffects.POISON, 200, 0)); // poison 10s
            // glow for 30 seconds, why not.
            player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 20 * 30, 0));
        }
        return super.finishUsingItem(stack, world, entity);
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        // we use the same description as the empty coconut cuz it explains how to acquire raw rubber
        // (useful when going through recipes on JEI/REI/whatever)
        tooltip.add(new TranslatableComponent("item.onthebrink.empty_coconut.desc_0")
                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        tooltip.add(new TranslatableComponent("item.onthebrink.empty_coconut.desc_1")
                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        tooltip.add(new TranslatableComponent("item.onthebrink.empty_coconut.desc_2")
                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        tooltip.add(new TranslatableComponent("item.onthebrink.empty_coconut.desc_3")
                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        tooltip.add(new TranslatableComponent("item.onthebrink.empty_coconut.desc_4")
                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
    }
}
