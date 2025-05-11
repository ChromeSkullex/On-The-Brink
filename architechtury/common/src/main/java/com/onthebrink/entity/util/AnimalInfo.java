package com.onthebrink.entity.util;

import net.minecraft.network.chat.Component;

public interface AnimalInfo {
    Component getName();
    Component getDescription();
    Boolean getGenderBool();

    Gender getGenderPop(Boolean gender);
}
