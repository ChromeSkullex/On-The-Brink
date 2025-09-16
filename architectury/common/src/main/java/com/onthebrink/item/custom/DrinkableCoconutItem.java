package com.onthebrink.item.custom;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class DrinkableCoconutItem extends Item {

    private final Item emptyCoconut; // item left behind

    public DrinkableCoconutItem(Item emptyCoconut, Properties properties) {
        super(properties);
        this.emptyCoconut = emptyCoconut;
    }

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
        if (entity instanceof Player player) {
            // Restore hunger if this is a food item
            if (this.getFoodProperties() != null) {
                player.getFoodData().eat(this.getFoodProperties().getNutrition(), this.getFoodProperties().getSaturationModifier());
            }

            if (!player.getAbilities().instabuild) {
                // Give empty coconut after drinking
                ItemStack empty = new ItemStack(emptyCoconut);
                if (!player.getInventory().add(empty)) {
                    player.drop(empty, false);
                }
            }
        }

        return super.finishUsingItem(stack, world, entity);
    }
}

