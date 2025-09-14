package com.onthebrink.world.feature.fabric;

import com.onthebrink.OnTheBrink;
import net.minecraft.core.Registry;

import static com.onthebrink.world.feature.ModFeatures.*;

public class ModFeaturesImpl
{
    public static void register()
    {
        Registry.register(Registry.FEATURE, BARNACLES_FEATURE.location(), BARNACLES_FEATURE.feature());
    }
}
