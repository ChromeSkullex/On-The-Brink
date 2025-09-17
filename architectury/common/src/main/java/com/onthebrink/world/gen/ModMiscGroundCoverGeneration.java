package com.onthebrink.world.gen;

import com.onthebrink.util.GroundCoverBiomeConfig;
import com.onthebrink.world.feature.ModPlacedFeatures;
import dev.architectury.registry.level.biome.BiomeModifications;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.GenerationStep;

public class ModMiscGroundCoverGeneration {
    public static void generateMiscGroundCover()
    {
        // Barnacles in stony shores
        BiomeModifications.addProperties((context, mutable) -> {
            if(GroundCoverBiomeConfig.isFeatureInBiome("barnacles_stony_shores", context))
            {
                mutable.getGenerationProperties().addFeature(
                        GenerationStep.Decoration.VEGETAL_DECORATION,
                        ModPlacedFeatures.BARNACLES_STONY_SHORES_PLACED
                );
            }
        });

        // Barnacles in coral reef
        BiomeModifications.addProperties((context, mutable) -> {
            if(GroundCoverBiomeConfig.isFeatureInBiome("barnacles_coral_reef", context))
            {
                mutable.getGenerationProperties().addFeature(
                        GenerationStep.Decoration.TOP_LAYER_MODIFICATION, // TOP_LAYER_MODIFICATION happens after VEGETAL_DECORATION
                        ModPlacedFeatures.BARNACLES_CORAL_REEF_PLACED     // that allows us to put barnacles over trees, coral, etc.
                );
            }
        });

        // Barnacles in swamp
        BiomeModifications.addProperties((context, mutable) -> {
            if(GroundCoverBiomeConfig.isFeatureInBiome("barnacles_swamp", context))
            {
                mutable.getGenerationProperties().addFeature(
                        GenerationStep.Decoration.TOP_LAYER_MODIFICATION,
                        ModPlacedFeatures.BARNACLES_SWAMP_PLACED
                );
            }
        });

        // Seashells in beach (non waterlogged)
        BiomeModifications.addProperties((context, mutable) -> {
            if(GroundCoverBiomeConfig.isFeatureInBiome("seashells_beach", context))
            {
                mutable.getGenerationProperties().addFeature(
                        GenerationStep.Decoration.TOP_LAYER_MODIFICATION,
                        ModPlacedFeatures.SEASHELLS_BEACH_DRY_PLACED
                );
            }
        });
        // Seashells in beach (waterlogged)
        BiomeModifications.addProperties((context, mutable) -> {
            if(GroundCoverBiomeConfig.isFeatureInBiome("seashells_beach", context))
            {
                mutable.getGenerationProperties().addFeature(
                        GenerationStep.Decoration.TOP_LAYER_MODIFICATION,
                        ModPlacedFeatures.SEASHELLS_BEACH_WATERLOGGED_PLACED
                );
            }
        });

        // Seashells on seafloor
        BiomeModifications.addProperties((context, mutable) -> {
            if(GroundCoverBiomeConfig.isFeatureInBiome("seashells_seafloor", context))
            {
                mutable.getGenerationProperties().addFeature(
                        GenerationStep.Decoration.TOP_LAYER_MODIFICATION,
                        ModPlacedFeatures.SEASHELLS_SEAFLOOR_PLACED
                );
            }
        });
    }
}
