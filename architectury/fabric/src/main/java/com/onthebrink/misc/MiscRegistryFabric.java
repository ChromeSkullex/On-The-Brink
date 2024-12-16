package com.onthebrink.misc;

import com.onthebrink.mixin.FoliagePlacerTypeInvoker;
import com.onthebrink.mixin.TrunkPlacerTypeInvoker;
import com.onthebrink.world.feature.foliageplacers.WoodsCycadFoliagePlacer;
import com.onthebrink.world.feature.trunkplacers.WoodsCycadTrunkPlacer;

public class MiscRegistryFabric {
    public static void registerTreePlacerTypes() {
        // Register your trunk placer type for fabric and forge
        MiscRegistry.WOODS_CYCAD_TRUNK_PLACER = TrunkPlacerTypeInvoker.callRegister("onthebrink:woods_cycad_trunk_placer", WoodsCycadTrunkPlacer.CODEC);
        MiscRegistry.WOODS_CYCAD_FOLIAGE_PLACER = FoliagePlacerTypeInvoker.callRegister("onthebrink:woods_cycad_foliage_placer", WoodsCycadFoliagePlacer.CODEC);
    }
}
