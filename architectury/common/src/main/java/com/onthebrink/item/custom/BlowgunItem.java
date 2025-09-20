package com.onthebrink.item.custom;

import com.onthebrink.event.BlowgunZoomEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class BlowgunItem extends BowItem {
    public BlowgunItem(Properties properties) {
        super(properties);
    }

    public void useSpyglass(Level level, Player player) {
        if (level.isClientSide) return;

        BlowgunZoomEvent.runZoomMechanic = true;
        BlowgunZoomEvent.player = player;
    }



    // we do the zooming mechanic here (so that the use() method can be used for the projectile stuff)
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (!(entity instanceof Player player)) return;

        boolean isHolding = player.getMainHandItem() == stack || player.getOffhandItem() == stack;

        if (isHolding && player.isCrouching()) {
            if (!stack.hasTag() || !stack.getTag().getBoolean("SpyglassSoundPlayed")) {
                player.playSound(SoundEvents.SPYGLASS_USE, 1.0F, 1.0F);
            }

            useSpyglass(level, player);
            stack.getOrCreateTag().putBoolean("SpyglassSoundPlayed", true);
        }
        else {
            if (stack.hasTag()) stack.getTag().putBoolean("SpyglassSoundPlayed", false);
        }
    }

}
