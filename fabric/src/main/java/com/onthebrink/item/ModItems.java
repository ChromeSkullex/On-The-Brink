package com.onthebrink.item;

import com.onthebrink.OnTheBrink;
import net.fabricmc.fabric.impl.item.group.ItemGroupExtensions;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static final Item PENGUIN_TEMP_SPAWN_EGG = registerItem("penguin_temp_spawn_egg", SpawnEgg.PENGUIN_TEMP_SPAWN_EGG_FACTORY);

    private static Item registerItem(String name, Item item){
            return Registry.register(Registry.ITEM, new Identifier(OnTheBrink.MOD_ID, name), item);
    }


    public static void registerItems(){
        OnTheBrink.LOGGER.info("Registering Mod Items");


    }
}
