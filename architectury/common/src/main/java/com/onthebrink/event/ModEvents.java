package com.onthebrink.event;

import dev.architectury.event.events.common.TickEvent;
import net.minecraft.server.level.ServerLevel;

public class ModEvents {

    public static void register(){
        // runs at the end of every tick
        TickEvent.SERVER_LEVEL_POST.register(ModEvents::onWorldTick);
    }

    private static void onWorldTick(ServerLevel level){
        SleepPoppyEvent.tickSleepPoppiesInCauldrons(level);
    }
}
