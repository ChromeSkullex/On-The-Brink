package com.onthebrink.item;

import com.onthebrink.entity.ModEntities;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;

public class SpawnEgg {

    public static final Item PENGUIN_TEMP_SPAWN_EGG_FACTORY = new SpawnEggItem(
            ModEntities.PENGUIN_ENTITY,
            0xd2d5d9,
            0x212429,
            new FabricItemSettings().group(ItemGroup.MISC)

    );




}
