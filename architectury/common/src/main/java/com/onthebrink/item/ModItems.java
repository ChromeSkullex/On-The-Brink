package com.onthebrink.item;

import com.onthebrink.OnTheBrink;
import com.onthebrink.block.ModBlocks;
import com.onthebrink.item.custom.CoconutItem;
import com.onthebrink.item.custom.DrinkableCoconutItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(OnTheBrink.MOD_ID, Registry.ITEM_REGISTRY);

    public static final RegistrySupplier<Item> COCONUT_ITEM = ModItems.ITEMS.register("coconut", () -> new CoconutItem(ModBlocks.COCONUT));


    public static final RegistrySupplier<Item> EMPTY_COCONUT = ITEMS.register("empty_coconut",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistrySupplier<Item> OPENED_COCONUT = ITEMS.register("opened_coconut",
            () -> new DrinkableCoconutItem(
                    EMPTY_COCONUT.get(),
                    new Item.Properties()
                            .tab(CreativeModeTab.TAB_MISC)
                            .stacksTo(1)
                            .food(new FoodProperties.Builder()
                                    .nutrition(2)       // 2 drumsticks
                                    .saturationMod(0.6f) // saturation
                                    .build())
            )
    );
    public static void register(){
        ITEMS.register();
    }
}
