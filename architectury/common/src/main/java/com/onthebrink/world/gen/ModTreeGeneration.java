package com.onthebrink.world.gen;

import com.onthebrink.world.feature.ModPlacedFeatures;
import dev.architectury.registry.level.biome.BiomeModifications;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;

public class ModTreeGeneration {

    public static void generateTrees() {
        BiomeModifications.addProperties((context) -> {
            ResourceLocation biomeName = context.getKey();

            // Add conditions to check for specific biome types or names
            return biomeName.getPath().contains("plains"); // Adjust to your needs
        }, (context, mutable) -> {
            mutable.getGenerationProperties().addFeature(
                    GenerationStep.Decoration.VEGETAL_DECORATION,
                    ModPlacedFeatures.WOODS_CYCAD_PLACED
            );
        });
    }
}
