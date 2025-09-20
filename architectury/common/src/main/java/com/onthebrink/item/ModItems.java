package com.onthebrink.item;

import com.onthebrink.OnTheBrink;
import com.onthebrink.block.ModBlocks;
import com.onthebrink.item.custom.*;
import com.onthebrink.misc.ModCreativeModeTabs;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(OnTheBrink.MOD_ID, Registry.ITEM_REGISTRY);

    public static final RegistrySupplier<Item> ADVANCED_BLOWGUN = ITEMS.register("advanced_blowgun",
            () -> new BlowgunItem(new Item.Properties().tab(ModCreativeModeTabs.TOOLS).stacksTo(1)));

    public static final RegistrySupplier<Item> SLEEP_POPPY = ITEMS.register("sleep_poppy",
            () -> new SleepPoppyItem(ModBlocks.SLEEP_POPPY.get(), new Item.Properties().tab(ModCreativeModeTabs.PLANTS)));

    public static final RegistrySupplier<BottleOfPoppyTeaItem> BOTTLE_OF_POPPY_TEA = ITEMS.register("bottle_of_poppy_tea",
            () -> new BottleOfPoppyTeaItem(new Item.Properties().tab(ModCreativeModeTabs.FOOD).stacksTo(16))); // same stack size as the honey bottle

    public static final RegistrySupplier<BucketOfPoppyTeaItem> BUCKET_OF_POPPY_TEA = ITEMS.register("bucket_of_poppy_tea",
            () -> new BucketOfPoppyTeaItem(new Item.Properties().tab(ModCreativeModeTabs.FOOD).stacksTo(1)));

    public static final RegistrySupplier<BottleOfTranquilizerItem> BOTTLE_OF_TRANQUILIZER = ITEMS.register("bottle_of_tranquilizer",
            () -> new BottleOfTranquilizerItem(new Item.Properties().tab(ModCreativeModeTabs.FOOD).stacksTo(16))); // same stack size as the honey bottle

    public static final RegistrySupplier<BucketOfTranquilizerItem> BUCKET_OF_TRANQUILIZER = ITEMS.register("bucket_of_tranquilizer",
            () -> new BucketOfTranquilizerItem(new Item.Properties().tab(ModCreativeModeTabs.FOOD).stacksTo(1)));

    public static final RegistrySupplier<Item> EMPTY_COCONUT = ITEMS.register("empty_coconut",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.PLANTS)));

    public static final RegistrySupplier<Item> SEASHELLS_ITEM = ITEMS.register("seashells",
            () -> new BlockItem(ModBlocks.SEASHELLS.get(), new Item.Properties().tab(ModCreativeModeTabs.NATURE)));

    public static final RegistrySupplier<Item> OPENED_COCONUT = ITEMS.register("opened_coconut",
            () -> new DrinkableCoconutItem(
                    new Item.Properties()
                            .tab(ModCreativeModeTabs.FOOD)
                            .stacksTo(1)
                            .food(new FoodProperties.Builder()
                                    .nutrition(4)       // 2 drumsticks
                                    .saturationMod(0.6f) // saturation
                                    .build())
            )
    );
    public static void register(){
        ITEMS.register();
    }
}
