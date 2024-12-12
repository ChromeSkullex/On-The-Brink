package com.onthebrink;

import com.onthebrink.entity.ModEntities;
import net.fabricmc.api.ClientModInitializer;


/**
 * Class to do client side initializations such as renderings, entity, and models.
 * */
public class OnTheBrinkClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModEntities.registerEntities();

    }
}
