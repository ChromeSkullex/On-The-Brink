package com.onthebrink.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BowlOfSagoPearlsItem extends Item {
    public BowlOfSagoPearlsItem(Properties properties) {
        super(properties.food(new FoodProperties.Builder()
                .nutrition(10) // 5 chopsticks
                .saturationMod(14.4f) // golden carrot saturation
                .build()));
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32; // eating time
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(new TranslatableComponent("item.onthebrink.bowl_of_sago_pearls.desc_0")
                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, Level level, net.minecraft.world.entity.LivingEntity entity) {
        if (entity instanceof Player player) {
            if (this.getFoodProperties() != null) {
                player.getFoodData().eat(this.getFoodProperties().getNutrition(), this.getFoodProperties().getSaturationModifier());
            }

            if (!player.getAbilities().instabuild) {
                stack.shrink(1);

                // if the original stack is now empty, return an empty coconut to put in hand
                ItemStack empty = new ItemStack(Items.BOWL);
                if (stack.isEmpty()) {
                    return empty;
                } else {
                    // otherwise try to add the empty coconut to inventory (or drop it)
                    if (!player.getInventory().add(empty)) {
                        player.drop(empty, false);
                    }
                }
            }
        }

        return super.finishUsingItem(stack, level, entity);
    }

}
