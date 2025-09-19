package com.onthebrink.event;

import com.onthebrink.item.ModItems;
import com.onthebrink.item.custom.BlowgunItem;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;

public class ModEvents {

    public static void register(){
        // runs at the end of every tick
        TickEvent.SERVER_LEVEL_POST.register(ModEvents::onWorldTick);

        ClientGuiEvent.RENDER_HUD.register((poseStack, tickDelta) -> {
            Minecraft mc = Minecraft.getInstance();
            // check if the player is using the Advanced Blowgun
            if (mc.player != null && mc.player.isUsingItem() && mc.player.getUseItem().is(ModItems.ADVANCED_BLOWGUN.get())) {
                // render the overlay
                BlowgunItem.renderSpyglassOverlay(poseStack, 1.125f);
            }
        });
    }



    private static void onWorldTick(ServerLevel level){
        SleepPoppyEvent.tickSleepPoppiesInCauldrons(level);
    }
}
