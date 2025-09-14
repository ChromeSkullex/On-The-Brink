package com.onthebrink.world.feature.configuration;

import com.onthebrink.OnTheBrink;
import com.onthebrink.block.ModBlocks;
import com.onthebrink.world.feature.ModFeatures;
import com.onthebrink.world.feature.foliageplacers.WoodsCycadFoliagePlacer;
import com.onthebrink.world.feature.trunkplacers.WoodsCycadTrunkPlacer;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class ModConfiguredFeatures {

    public static final BarnaclesFeatureConfiguration BARNACLES_CONFIG = new BarnaclesFeatureConfiguration(
            10,
            0.8f
    );

    public static Holder<ConfiguredFeature<BarnaclesFeatureConfiguration, ?>> BARNACLES = register("barnacles", ModFeatures.BARNACLES_FEATURE.feature(), BARNACLES_CONFIG);

    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> WOODS_CYCAD = FeatureUtils.register("woods_cycad", Feature.TREE,
            new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(ModBlocks.WOODS_CYCAD_LOG.get()),
                    new WoodsCycadTrunkPlacer(5, 2, 1),
                    BlockStateProvider.simple(ModBlocks.WOODS_CYCAD_LEAVES.get()),
                    new WoodsCycadFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
                    new TwoLayersFeatureSize(1, 0, 2)
            ).build());

    public static Holder<PlacedFeature> WOODS_CYCAD_CHECKED = PlacementUtils.register("woods_cycad_checked", WOODS_CYCAD,
            PlacementUtils.filteredByBlockSurvival(ModBlocks.WOODS_CYCAD_SPROUT.get()));

    public static Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> WOODS_CYCAD_SPAWN = FeatureUtils.register("woods_cycad_spawn", Feature.RANDOM_SELECTOR,
            new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WOODS_CYCAD_CHECKED, 0.5F)),
                    WOODS_CYCAD_CHECKED));


    public static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> AFRICAN_VIOLET = FeatureUtils.register("flower_african_violet", Feature.RANDOM_PATCH,
            new RandomPatchConfiguration(32, 6, 2,
                    PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.AFRICAN_VIOLET.get())))));


    public static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> CHOCOLATE_COSMOS = FeatureUtils.register("flower_chocolate_cosmos", Feature.RANDOM_PATCH,
            new RandomPatchConfiguration(32, 6, 2,
                    PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.CHOCOLATE_COSMOS.get())))));


    private static <C extends FeatureConfiguration, F extends Feature<C>> Holder<ConfiguredFeature<C, ?>> register(String name, F feature, C config) {
        return FeatureUtils.register(OnTheBrink.MOD_ID + ":" + name, feature, config);
    }
}
