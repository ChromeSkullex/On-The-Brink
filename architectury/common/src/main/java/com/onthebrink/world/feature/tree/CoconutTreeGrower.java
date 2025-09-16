package com.onthebrink.world.feature.tree;

import com.onthebrink.world.feature.configuration.ModConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class CoconutTreeGrower extends AbstractTreeGrower {
    @Override
    protected @Nullable Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random random, boolean bl) {
        return ModConfiguredFeatures.COCONUT_TREE;
    }
}
