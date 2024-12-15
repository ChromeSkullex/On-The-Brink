package com.onthebrink.world.feature;

import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModPlacedFeatures {
    public static final Holder<PlacedFeature> WOODS_CYCAD_PLACED = PlacementUtils.register("woods_cycad_placed",
            ModConfiguredFeatures.WOODS_CYCAD_SPAWN, VegetationPlacements.treePlacement(
                    PlacementUtils.countExtra(3, 0.1f, 2)));
}
