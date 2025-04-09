package com.onthebrink.item;

import com.onthebrink.OnTheBrink;
import com.onthebrink.entity.animal.base.AnimalBase;
import com.onthebrink.entity.util.AnimalDefinition;
import com.onthebrink.entity.util.OTBSpawnEgg;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EggItem;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

import static com.onthebrink.entity.ModEntities.REGISTERED_ENTITIES;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(OnTheBrink.MOD_ID, Registry.ITEM_REGISTRY);

    public static final Map<String, RegistrySupplier<Item>> SPAWN_EGGS = new HashMap<>();

    public static final RegistrySupplier<Item> ANIMAL_BOOK = ITEMS.register("animal_book",
            () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));


    public static void register(){
        ITEMS.register();
    }

    public static void registerSpawnEggs(AnimalDefinition def) {
        RegistrySupplier<EntityType<AnimalBase>> entityTypeSupplier = REGISTERED_ENTITIES.get(def.id);
        OnTheBrink.LOGGER.info("P:{} S:{}", def.primary_color, def.secondary_color );
        RegistrySupplier<Item> egg = ModItems.ITEMS.register(def.id + "_egg", () ->
                new OTBSpawnEgg(entityTypeSupplier.get(), def.primary_color, def.secondary_color,
                        new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
        ModItems.SPAWN_EGGS.put(def.id, egg);

    }
}
