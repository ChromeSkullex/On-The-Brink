package com.onthebrink.event;

import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.common.TickEvent;
import net.minecraft.server.level.ServerLevel;

public class ModEvents {

    public static void register(){
        // runs at the end of every tick
        TickEvent.SERVER_LEVEL_POST.register(ModEvents::onWorldTick);

        // hopefully this only gets called on the clientside (wouldn't make sense otherwise),
        // I'm not sure how to make it check without a reference to the world
        ClientGuiEvent.RENDER_HUD.register(BlowgunZoomEvent::update);
    }

    private static void onWorldTick(ServerLevel level){
        SleepPoppyEvent.tickSleepPoppiesInCauldrons(level);
    }
}
