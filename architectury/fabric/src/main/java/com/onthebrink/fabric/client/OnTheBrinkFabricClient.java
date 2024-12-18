package com.onthebrink.fabric.client;

import com.onthebrink.client.OnTheBrinkClient;
import net.fabricmc.api.ClientModInitializer;

public final class OnTheBrinkFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        OnTheBrinkClient.init();
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
    }
}
