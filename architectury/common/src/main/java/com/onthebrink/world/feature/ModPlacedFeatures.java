package com.onthebrink.world.feature;

import com.onthebrink.world.feature.configuration.ModConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

public class ModPlacedFeatures {
    public static Holder<PlacedFeature> WOODS_CYCAD_PLACED;
    public static Holder<PlacedFeature> AFRICAN_VIOLET_PLACED;
    public static Holder<PlacedFeature> CHOCOLATE_COSMOS_PLACED;
    public static Holder<PlacedFeature> SLEEP_POPPY_PLACED;
    public static Holder<PlacedFeature> PINK_SAND_VERBENA_PLACED;
    public static Holder<PlacedFeature> SEASHELLS_BEACH_DRY_PLACED;
    public static Holder<PlacedFeature> SEASHELLS_BEACH_WATERLOGGED_PLACED;
    public static Holder<PlacedFeature> SEASHELLS_SEAFLOOR_PLACED;
    public static Holder<PlacedFeature> BARNACLES_STONY_SHORES_PLACED;
    public static Holder<PlacedFeature> BARNACLES_CORAL_REEF_PLACED;
    public static Holder<PlacedFeature> BARNACLES_SWAMP_PLACED;
    public static Holder<PlacedFeature> COCONUT_TREE_PLACED;
    public static void register() {

        BARNACLES_STONY_SHORES_PLACED = PlacementUtils.register("barnacles_stony_shores",
                ModConfiguredFeatures.BARNACLES,
                CountPlacement.of(20),
                InSquarePlacement.spread(),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(60), VerticalAnchor.absolute(65)));

        BARNACLES_CORAL_REEF_PLACED = PlacementUtils.register("barnacles_coral_reef",
                ModConfiguredFeatures.BARNACLES,
                CountPlacement.of(10),
                InSquarePlacement.spread(),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(40), VerticalAnchor.absolute(65)));

        BARNACLES_SWAMP_PLACED = PlacementUtils.register("barnacles_swamp",
                ModConfiguredFeatures.BARNACLES,
                CountPlacement.of(1),
                InSquarePlacement.spread(),
                RarityFilter.onAverageOnceEvery(10),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(50), VerticalAnchor.absolute(65)));


        WOODS_CYCAD_PLACED = PlacementUtils.register("woods_cycad_placed",
                ModConfiguredFeatures.WOODS_CYCAD_SPAWN, VegetationPlacements.treePlacement(
                        PlacementUtils.countExtra(0, 0.1f, 1)));
                        // at least i per chunk, plus f chance of putting one, potentially j times
                        // yes, they should've labeled the parameters better.

        COCONUT_TREE_PLACED = PlacementUtils.register("coconut_tree_placed",
                ModConfiguredFeatures.COCONUT_TREE_SPAWN, RarityFilter.onAverageOnceEvery(3),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP
                );

        AFRICAN_VIOLET_PLACED = PlacementUtils.register("african_violet_placed",
                ModConfiguredFeatures.AFRICAN_VIOLET, RarityFilter.onAverageOnceEvery(10),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

        CHOCOLATE_COSMOS_PLACED = PlacementUtils.register("chocolate_cosmos_placed",
                ModConfiguredFeatures.CHOCOLATE_COSMOS, RarityFilter.onAverageOnceEvery(10),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

        SLEEP_POPPY_PLACED = PlacementUtils.register("sleep_poppy_placed",
                ModConfiguredFeatures.SLEEP_POPPY, RarityFilter.onAverageOnceEvery(40),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

        PINK_SAND_VERBENA_PLACED = PlacementUtils.register("pink_sand_verbena_placed",
                ModConfiguredFeatures.PINK_SAND_VERBENA, RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP);

        SEASHELLS_BEACH_DRY_PLACED = PlacementUtils.register("seashells_dry_placed",
                ModConfiguredFeatures.SEASHELLS_DRY, RarityFilter.onAverageOnceEvery(1),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP);

        SEASHELLS_BEACH_WATERLOGGED_PLACED = PlacementUtils.register("seashells_waterlogged_placed",
                ModConfiguredFeatures.SEASHELLS_WATERLOGGED, RarityFilter.onAverageOnceEvery(1),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP);

        SEASHELLS_SEAFLOOR_PLACED = PlacementUtils.register("seashells_seafloor_placed",
                ModConfiguredFeatures.SEASHELLS_WATERLOGGED, RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP);
    }
}