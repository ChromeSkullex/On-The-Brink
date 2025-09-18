package com.onthebrink.item.custom;

import com.onthebrink.item.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class DrinkableCoconutItem extends Item {
    public DrinkableCoconutItem(Properties properties) {
        super(properties);
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
                stack.shrink(1);

                // if the original stack is now empty, return an empty coconut to put in hand
                ItemStack empty = new ItemStack(ModItems.EMPTY_COCONUT.get());
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

        return super.finishUsingItem(stack, world, entity);
    }
}

