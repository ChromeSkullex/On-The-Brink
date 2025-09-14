package com.onthebrink.world.feature;

import com.onthebrink.OnTheBrink;
import com.onthebrink.world.feature.configuration.BarnaclesFeatureConfiguration;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class ModFeatures {
    public static final Tuple<BarnaclesFeatureConfiguration, BarnaclesFeature> BARNACLES_FEATURE =
            create("barnacles", new BarnaclesFeature());

    @ExpectPlatform
    public static void register() {
    }

    public static <C extends FeatureConfiguration, F extends Feature<C>> Tuple<C, F> create(String name, F feature) {
        return new Tuple<>(OnTheBrink.location(name), feature);
    }

    public record Tuple<C extends FeatureConfiguration, F extends Feature<C>>(ResourceLocation location, F feature) {

    }
}
