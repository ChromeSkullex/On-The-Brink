package com.onthebrink.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.onthebrink.OnTheBrink;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class OnTheBrinkScreen extends Screen {
    private static final ResourceLocation ONTHEBRINK_BACKGROUND = OnTheBrink.location("textures/gui/book_gui.png");

    private final LivingEntity entity;
    private final int xSize = 256;
    private final int ySize = 170;
    private int leftPos;
    private int topPos;

    public OnTheBrinkScreen(LivingEntity entity) {
        super(new TextComponent(""));
        this.entity = entity;
    }


    @Override
    protected void init() {
        leftPos = (width - xSize) / 2;
        topPos = (height - ySize) / 2;

    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(poseStack);
        renderBackgroundLayer(poseStack, mouseX, mouseY);
    }

    private void renderBackgroundLayer(PoseStack poseStack, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ONTHEBRINK_BACKGROUND);

        blit(poseStack, leftPos, topPos, 0, 0, xSize, ySize, 256, 256);

    }


}
