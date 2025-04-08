package com.onthebrink.fabric.client;

import com.onthebrink.client.ClientInit;
import net.fabricmc.api.ClientModInitializer;

public final class OnTheBrinkFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientInit.immediate();
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
    }
}
