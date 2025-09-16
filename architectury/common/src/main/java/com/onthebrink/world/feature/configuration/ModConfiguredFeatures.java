package com.onthebrink.world.feature.configuration;

import com.onthebrink.OnTheBrink;
import com.onthebrink.block.ModBlocks;
import com.onthebrink.world.feature.ModFeatures;
import com.onthebrink.world.feature.foliageplacers.CoconutTreeFoliagePlacer;
import com.onthebrink.world.feature.foliageplacers.WoodsCycadFoliagePlacer;
import com.onthebrink.world.feature.trunkplacers.CoconutTreeTrunkPlacer;
import com.onthebrink.world.feature.trunkplacers.WoodsCycadTrunkPlacer;
import dev.architectury.platform.Mod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.JungleTreeGrower;
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



    public static final Holder<ConfiguredFeature<TreeConfiguration, ?>> COCONUT_TREE = FeatureUtils.register("coconut_tree", Feature.TREE,
            new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(ModBlocks.COCONUT_TREE_LOG.get()),
                    new CoconutTreeTrunkPlacer(7, 2, 1),
                    BlockStateProvider.simple(ModBlocks.COCONUT_TREE_LEAVES.get()),
                    new CoconutTreeFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0)),
                    new TwoLayersFeatureSize(1, 0, 2)
            ).build());

    public static Holder<PlacedFeature> COCONUT_TREE_CHECKED = PlacementUtils.register("coconut_tree_checked", COCONUT_TREE,
            PlacementUtils.filteredByBlockSurvival(ModBlocks.COCONUT.get()));

    public static Holder<ConfiguredFeature<RandomFeatureConfiguration, ?>> COCONUT_TREE_SPAWN = FeatureUtils.register("coconut_tree_spawn", Feature.RANDOM_SELECTOR,
            new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(COCONUT_TREE_CHECKED, 0.5F)),
                    COCONUT_TREE_CHECKED));



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
                    PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, // i = attempts, j = x spread, k = y spread
                            new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.AFRICAN_VIOLET.get())))));


    public static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> CHOCOLATE_COSMOS = FeatureUtils.register("flower_chocolate_cosmos", Feature.RANDOM_PATCH,
            new RandomPatchConfiguration(32, 6, 2,
                    PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.CHOCOLATE_COSMOS.get())))));

    public static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> PINK_SAND_VERBENA = FeatureUtils.register("flower_pink_sand_verbena", Feature.RANDOM_PATCH,
            new RandomPatchConfiguration(80, 8, 2,
                    PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.PINK_SAND_VERBENA.get())))));


    private static <C extends FeatureConfiguration, F extends Feature<C>> Holder<ConfiguredFeature<C, ?>> register(String name, F feature, C config) {
        return FeatureUtils.register(OnTheBrink.MOD_ID + ":" + name, feature, config);
    }
}
