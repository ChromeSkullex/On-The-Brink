package com.onthebrink.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SleepPoppyItem extends BlockItem {
    public SleepPoppyItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(new TranslatableComponent("block.onthebrink.sleep_poppy.desc_0")
                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        tooltip.add(new TranslatableComponent("block.onthebrink.sleep_poppy.desc_1")
                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));

        tooltip.add(Component.nullToEmpty("")); // empty line

        tooltip.add(new TranslatableComponent("block.onthebrink.sleep_poppy.desc_2")
                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        tooltip.add(new TranslatableComponent("block.onthebrink.sleep_poppy.desc_3")
                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
    }
}
