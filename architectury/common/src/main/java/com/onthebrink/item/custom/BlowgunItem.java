package com.onthebrink.item.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.onthebrink.item.ModItems;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpyglassItem;
import net.minecraft.world.level.Level;
import net.minecraft.client.Minecraft;


public class BlowgunItem extends SpyglassItem {
    private static final ResourceLocation SPYGLASS_SCOPE_LOCATION = new ResourceLocation("minecraft", "textures/misc/spyglass_scope.png");


    public BlowgunItem(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> useSpyglass(Level level, Player player, InteractionHand usedHand) {
        player.playSound(SoundEvents.SPYGLASS_USE, 1.0F, 1.0F);
        player.awardStat(Stats.ITEM_USED.get(this));
        return ItemUtils.startUsingInstantly(level, player, usedHand);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand){
        if(!level.isClientSide) return InteractionResultHolder.success(new ItemStack(ModItems.ADVANCED_BLOWGUN.get()));

        return useSpyglass(level, player, usedHand);
    }


    public static void renderSpyglassOverlay(PoseStack poseStack, float scale) {
        Minecraft mc = Minecraft.getInstance();
        // Use getGuiScaledWidth/Height for GUI rendering!
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        // Get the matrix from the PoseStack
        Matrix4f matrix = poseStack.last().pose();

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, SPYGLASS_SCOPE_LOCATION);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();

        // The rest of your calculations are fine
        float g = (float)Math.min(screenWidth, screenHeight);
        float i = Math.min((float)screenWidth / g, (float)screenHeight / g) * scale;
        float j = g * i;
        float k = g * i;
        float l = ((float)screenWidth - j) / 2.0F;
        float m = ((float)screenHeight - k) / 2.0F;
        float n = l + j;
        float o = m + k;

        // Use the matrix in your vertex calls
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(matrix, l, o, -90.0f).uv(0.0F, 1.0F).endVertex();
        bufferBuilder.vertex(matrix, n, o, -90.0f).uv(1.0F, 1.0F).endVertex();
        bufferBuilder.vertex(matrix, n, m, -90.0f).uv(1.0F, 0.0F).endVertex();
        bufferBuilder.vertex(matrix, l, m, -90.0f).uv(0.0F, 0.0F).endVertex();
        tesselator.end();

        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.disableTexture();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferBuilder.vertex(matrix, 0.0f, screenHeight, -90.0f).color(0, 0, 0, 255).endVertex();
        bufferBuilder.vertex(matrix, screenWidth, screenHeight, -90.0f).color(0, 0, 0, 255).endVertex();
        bufferBuilder.vertex(matrix, screenWidth, o, -90.0f).color(0, 0, 0, 255).endVertex();
        bufferBuilder.vertex(matrix, 0.0f, o, -90.0f).color(0, 0, 0, 255).endVertex();
        // ... continue for all other vertex calls ...
        bufferBuilder.vertex(matrix, n, m, -90.0f).color(0, 0, 0, 255).endVertex();
        tesselator.end();

        RenderSystem.enableTexture();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

}
