package com.onthebrink.world.gen;


import com.onthebrink.util.GroundCoverBiomeConfig;
import com.onthebrink.world.feature.ModPlacedFeatures;
import dev.architectury.registry.level.biome.BiomeModifications;
import net.minecraft.world.level.levelgen.GenerationStep;

public class ModFlowerGeneration {
    public static void generateFlowers()
    {
        // African Violet
        BiomeModifications.addProperties((context) -> {
            return GroundCoverBiomeConfig.isFeatureInBiome("african_violet", context);
        }
        , (context, mutable) -> {

            mutable.getGenerationProperties().addFeature(
                    GenerationStep.Decoration.VEGETAL_DECORATION,
                    ModPlacedFeatures.AFRICAN_VIOLET_PLACED
            );
        });

        // Chocolate Cosmos
        BiomeModifications.addProperties((context) -> {
            return GroundCoverBiomeConfig.isFeatureInBiome("chocolate_cosmos", context);
        }
        , (context, mutable) -> {

            mutable.getGenerationProperties().addFeature(
                    GenerationStep.Decoration.VEGETAL_DECORATION,
                    ModPlacedFeatures.CHOCOLATE_COSMOS_PLACED
            );
        });

        // Sleep Poppy
        BiomeModifications.addProperties((context) -> {
                    return GroundCoverBiomeConfig.isFeatureInBiome("sleep_poppy", context);
                }
                , (context, mutable) -> {

                    mutable.getGenerationProperties().addFeature(
                            GenerationStep.Decoration.VEGETAL_DECORATION,
                            ModPlacedFeatures.SLEEP_POPPY_PLACED
                    );
                });

        // Pink Sand Verbena
        BiomeModifications.addProperties((context) -> {
                    return GroundCoverBiomeConfig.isFeatureInBiome("pink_sand_verbena", context);
                }
                , (context, mutable) -> {

                    mutable.getGenerationProperties().addFeature(
                            GenerationStep.Decoration.VEGETAL_DECORATION,
                            ModPlacedFeatures.PINK_SAND_VERBENA_PLACED
                    );
                });
    }
}
