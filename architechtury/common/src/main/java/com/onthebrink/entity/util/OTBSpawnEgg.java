package com.onthebrink.entity.util;

import com.onthebrink.entity.animal.base.AnimalBase;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.SpawnEggItem;

public class OTBSpawnEgg extends SpawnEggItem {

    public OTBSpawnEgg(EntityType<? extends AnimalBase> defaultType, int backgroundColor, int highlightColor, Properties properties) {
        super(defaultType, backgroundColor, highlightColor, properties);
    }


}
