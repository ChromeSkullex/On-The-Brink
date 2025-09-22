package com.onthebrink.misc;

import com.onthebrink.OnTheBrink;
import com.onthebrink.block.ModBlocks;
import com.onthebrink.item.ModItems;
import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTabs {
    public static final CreativeModeTab PLANTS = CreativeTabRegistry.create(
            OnTheBrink.location("otb_plants"), // Tab ID
            () -> new ItemStack(ModItems.SLEEP_POPPY.get()) // Icon
    );

    public static final CreativeModeTab TREES_AND_WOOD = CreativeTabRegistry.create(
            OnTheBrink.location("otb_trees_and_wood"),
            () -> new ItemStack(ModBlocks.WOODS_CYCAD_SPROUT_ITEM.get())
    );

    public static final CreativeModeTab BUILDING = CreativeTabRegistry.create(
            OnTheBrink.location("otb_building"),
            () -> new ItemStack(ModBlocks.WOODEN_CRATE_ITEM.get())
    );

    public static final CreativeModeTab NATURE = CreativeTabRegistry.create(
            OnTheBrink.location("otb_nature"),
            () -> new ItemStack(ModItems.SEASHELLS_ITEM.get())
    );

    public static final CreativeModeTab FOOD = CreativeTabRegistry.create(
            OnTheBrink.location("otb_food"),
            () -> new ItemStack(ModItems.OPENED_COCONUT.get())
    );

    public static final CreativeModeTab TOOLS_AND_INGREDIENTS = CreativeTabRegistry.create(
            OnTheBrink.location("otb_tools_and_ingredients"),
            () -> new ItemStack(ModItems.ADVANCED_BLOWGUN.get())
    );

}
