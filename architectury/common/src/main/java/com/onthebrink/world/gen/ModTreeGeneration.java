package com.onthebrink.world.gen;

import com.onthebrink.util.GroundCoverBiomeConfig;
import com.onthebrink.world.feature.ModPlacedFeatures;
import dev.architectury.registry.level.biome.BiomeModifications;
import net.minecraft.world.level.levelgen.GenerationStep;

public class ModTreeGeneration {

    public static void generateTrees() {
        // For some reason the game crashes if we remove this if statement
        // It doesn't even make sense, it's just for debugging. Literally what the fuck.
        if (ModPlacedFeatures.WOODS_CYCAD_PLACED == null) {
            System.err.println("WOODS_CYCAD_PLACED is not registered yet!");
        }

        //Wood's Cycad
        BiomeModifications.addProperties((context) -> {
            return GroundCoverBiomeConfig.isFeatureInBiome("woods_cycad", context);
            }
        , (context, mutable) -> {

            mutable.getGenerationProperties().addFeature(
                    GenerationStep.Decoration.VEGETAL_DECORATION,
                    ModPlacedFeatures.WOODS_CYCAD_PLACED
            );
        });
    }
}
