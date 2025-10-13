package com.onthebrink.item.custom;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

public class SagoBreadItem extends Item {
    public SagoBreadItem(Properties properties) {
        super(properties.food(new FoodProperties.Builder()
                .nutrition(8) // 4 chopsticks
                .saturationMod(12.8f) // cooked beef saturation
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
}
