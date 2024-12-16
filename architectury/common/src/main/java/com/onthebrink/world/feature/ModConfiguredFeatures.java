package com.onthebrink.world.feature;

import com.onthebrink.block.ModBlocks;
import com.onthebrink.world.feature.foliageplacers.WoodsCycadFoliagePlacer;
import com.onthebrink.world.feature.trunkplacers.WoodsCycadTrunkPlacer;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class ModConfiguredFeatures {
    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> WOODS_CYCAD =
            FeatureUtils.register("woods_cycad", Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(ModBlocks.WOODS_CYCAD_LOG.get()),
                    new WoodsCycadTrunkPlacer(5, 2, 1),
                    BlockStateProvider.simple(ModBlocks.WOODS_CYCAD_LEAVES.get()),
                    new WoodsCycadFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
                    new TwoLayersFeatureSize(1, 0, 2)).build());

    public static final Holder<PlacedFeature> WOODS_CYCAD_CHECKED = PlacementUtils.register("woods_cycad_checked", WOODS_CYCAD,
            PlacementUtils.filteredByBlockSurvival(ModBlocks.WOODS_CYCAD_SPROUT.get()));

    public static final Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> WOODS_CYCAD_SPAWN =
            FeatureUtils.register("woods_cycad_spawn", Feature.RANDOM_SELECTOR,
                    new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WOODS_CYCAD_CHECKED,
                            0.5F)), WOODS_CYCAD_CHECKED));
}
