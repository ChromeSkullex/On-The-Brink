package com.onthebrink.entity.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.Random;

public enum Gender implements AnimalInfo{
    MALE, FEMALE;

    private final TranslatableComponent name = new TranslatableComponent("book.onthebrink.gender." + name().toLowerCase());
    private final TranslatableComponent description = new TranslatableComponent("book.onthebrink.gender.desc");


    public static Gender random(Random random) {
        return Gender.values()[random.nextInt(Gender.values().length)];

    }
    @Override
    public Component getName() {
        return name;
    }

    @Override
    public Component getDescription() {
        return description;
    }
}
