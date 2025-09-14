package com.onthebrink.world.feature.configuration;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.Collections;
import java.util.List;

public record BarnaclesFeatureConfiguration(
        int searchRange,
        float chanceOfSpreading
) implements FeatureConfiguration {
    public static final Codec<BarnaclesFeatureConfiguration> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.intRange(1, 64).fieldOf("searchRange").forGetter(BarnaclesFeatureConfiguration::searchRange),
                    Codec.floatRange(0.0F, 1.0F).fieldOf("chanceOfSpreading").forGetter(BarnaclesFeatureConfiguration::chanceOfSpreading)
            ).apply(instance, BarnaclesFeatureConfiguration::new)
    );

    public static final List<Direction> validDirections = List.of(
            Direction.UP, Direction.DOWN,
            Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST
    );

    public static HolderSet<Block> canBePlacedOn = HolderSet.direct(
            Holder.direct(Blocks.STONE),
            Holder.direct(Blocks.GRAVEL),
            Holder.direct(Blocks.ANDESITE),
            Holder.direct(Blocks.GRANITE),
            Holder.direct(Blocks.DIORITE),
            Holder.direct(Blocks.OAK_LOG),
            Holder.direct(Blocks.BRAIN_CORAL_BLOCK),
            Holder.direct(Blocks.BUBBLE_CORAL_BLOCK),
            Holder.direct(Blocks.FIRE_CORAL_BLOCK),
            Holder.direct(Blocks.HORN_CORAL_BLOCK),
            Holder.direct(Blocks.TUBE_CORAL_BLOCK),
            Holder.direct(Blocks.JUNGLE_PLANKS),
            Holder.direct(Blocks.OAK_PLANKS),
            Holder.direct(Blocks.SPRUCE_PLANKS)
    );
}
