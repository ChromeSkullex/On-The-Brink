package com.onthebrink.item;

import com.onthebrink.OnTheBrink;
import com.onthebrink.block.ModBlocks;
import com.onthebrink.item.custom.*;
import com.onthebrink.misc.ModCreativeModeTabs;
import com.onthebrink.misc.dispenser_behaviors.RubberBallDispenserBehavior;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.DispenserBlock;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(OnTheBrink.MOD_ID, Registry.ITEM_REGISTRY);

    public static final RegistrySupplier<Item> ADVANCED_BLOWGUN = ITEMS.register("advanced_blowgun",
            () -> new BlowgunItem(new Item.Properties().tab(ModCreativeModeTabs.TOOLS_AND_INGREDIENTS).stacksTo(1)));

    public static final RegistrySupplier<Item> SAGO_FLOUR = ITEMS.register("sago_flour",
            () -> new SagoFlourItem(new Item.Properties().tab(ModCreativeModeTabs.FOOD)));

    public static final RegistrySupplier<Item> SAGO_BREAD = ITEMS.register("sago_bread",
            () -> new SagoBreadItem(new Item.Properties().tab(ModCreativeModeTabs.FOOD)));

    public static final RegistrySupplier<Item> BOWL_OF_SAGO_PEARLS = ITEMS.register("bowl_of_sago_pearls",
            () -> new BowlOfSagoPearlsItem(new Item.Properties().tab(ModCreativeModeTabs.FOOD).stacksTo(1)));


    public static final RegistrySupplier<Item> RUBBER = ITEMS.register("rubber",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTabs.TOOLS_AND_INGREDIENTS)));

    public static final RegistrySupplier<Item> RAW_RUBBER = ITEMS.register("raw_rubber",
            () -> new RawRubberItem(new Item.Properties().tab(ModCreativeModeTabs.TOOLS_AND_INGREDIENTS)));

    public static final RegistrySupplier<Item> BURNT_RUBBER = ITEMS.register("burnt_rubber",
            () -> new BurntRubberItem(new Item.Properties().tab(ModCreativeModeTabs.TOOLS_AND_INGREDIENTS)));

    public static final RegistrySupplier<Item> RUBBER_BALL = ITEMS.register("rubber_ball",
            () -> new RubberBallItem(new Item.Properties().tab(ModCreativeModeTabs.TOOLS_AND_INGREDIENTS).stacksTo(16)));

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
            () -> new EmptyCoconutItem(ModBlocks.COCONUT_LATEX_COLLECTOR.get(), new Item.Properties().tab(ModCreativeModeTabs.TOOLS_AND_INGREDIENTS)));

    public static final RegistrySupplier<Item> SEASHELLS_ITEM = ITEMS.register("seashells",
            () -> new BlockItem(ModBlocks.SEASHELLS.get(), new Item.Properties().tab(ModCreativeModeTabs.NATURE)));

    public static final RegistrySupplier<Item> OPENED_COCONUT = ITEMS.register("opened_coconut",
            () -> new DrinkableCoconutItem(
                    new Item.Properties()
                            .tab(ModCreativeModeTabs.FOOD)
                            .stacksTo(1)
                            .food(new FoodProperties.Builder()
                                    .nutrition(4)       // 2 drumsticks
                                    .saturationMod(3.6f) // carrot saturation
                                    .build())
            )
    );
    public static void register(){
        ITEMS.register();

        DispenserBlock.registerBehavior(RUBBER_BALL.get(), new RubberBallDispenserBehavior());
    }
}
