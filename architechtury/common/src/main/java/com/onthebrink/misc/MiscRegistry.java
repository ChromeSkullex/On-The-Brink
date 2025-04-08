package com.onthebrink.misc;

import com.onthebrink.world.feature.foliageplacers.WoodsCycadFoliagePlacer;
import com.onthebrink.world.feature.trunkplacers.WoodsCycadTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class MiscRegistry {
    // These are only used on fabric because they're for a mixin that only fabric needs
    // The reason for that is because fabric doesn't have an API to handle new TrunkPlacers and FoliagePlacers
    // So we need to use mixins to add new ones. This isn't the case for forge.
    public static TrunkPlacerType<WoodsCycadTrunkPlacer> WOODS_CYCAD_TRUNK_PLACER;
    public static FoliagePlacerType<WoodsCycadFoliagePlacer> WOODS_CYCAD_FOLIAGE_PLACER;
}