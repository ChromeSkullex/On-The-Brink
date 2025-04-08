package com.onthebrink.world.feature.foliageplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.function.BiConsumer;

import com.onthebrink.misc.MiscRegistry;
import dev.architectury.platform.Platform;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class WoodsCycadFoliagePlacer extends FoliagePlacer {
    public static final Codec<WoodsCycadFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) ->
            foliagePlacerParts(instance).apply(instance, WoodsCycadFoliagePlacer::new)
    );

    public WoodsCycadFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        if(Platform.isFabric()) {
            return MiscRegistry.WOODS_CYCAD_FOLIAGE_PLACER;
        }
        return FoliagePlacerType.ACACIA_FOLIAGE_PLACER; // Replace with custom registration for PalmFoliagePlacer
    }

    @Override
    protected void createFoliage(LevelSimulatedReader reader, BiConsumer<BlockPos, BlockState> leavesPlacer, Random random, TreeConfiguration config, int trunkHeight, FoliageAttachment attachment, int foliageHeight, int radius, int offset) {
        BlockPos top = attachment.pos().above(offset); // Start at the top of the trunk

        for (BlockPos leafPos : LeafData.ENCEPHALARTOS_WOODII_0) {
            placeLeavesIfAllowed(reader, leavesPlacer, random, config, top.offset(leafPos));
        }
    }

    private boolean isAirOrLeaves(LevelSimulatedReader reader, BlockPos pos) {
        return reader.isStateAtPosition(pos, state ->
                state.isAir() || state.is(net.minecraft.tags.BlockTags.LEAVES)
        );
    }

    private void placeLeavesIfAllowed(LevelSimulatedReader reader, BiConsumer<BlockPos, BlockState> leavesPlacer, Random random, TreeConfiguration config, BlockPos pos) {
        if (isAirOrLeaves(reader, pos)) {
            leavesPlacer.accept(pos, config.foliageProvider.getState(random, pos));
        }
    }

    @Override
    public int foliageHeight(Random random, int trunkHeight, TreeConfiguration config) {
        return 2; // Palm trees typically have a small foliage height
    }

    @Override
    protected boolean shouldSkipLocation(Random random, int baseX, int baseY, int baseZ, int radius, boolean doubleTrunk) {
        return false; // Don't skip locations to ensure full radial spread
    }
}