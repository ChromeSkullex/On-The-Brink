package com.onthebrink.entity.util;

import com.onthebrink.OnTheBrink;
import com.onthebrink.entity.animal.base.AnimalBase;
import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import org.jetbrains.annotations.NotNull;

public class OTBSpawnEgg extends ArchitecturySpawnEggItem {
    private final Component name;
    public OTBSpawnEgg(AnimalDefinition animalDefinition, RegistrySupplier<? extends EntityType<? extends Mob>> entityType) {
        super(entityType, animalDefinition.primary_color,animalDefinition.secondary_color,
                new Item.Properties().tab(CreativeModeTab.TAB_MISC));
        this.name = new TranslatableComponent("item." + OnTheBrink.MOD_ID +"."+animalDefinition.id);
    }

@Override
public @NotNull Component getName(ItemStack stack) {
    return name;
}

}
