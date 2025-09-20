package com.onthebrink.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.onthebrink.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class BlowgunZoomEvent {
    private static final ResourceLocation SPYGLASS_SCOPE_LOCATION = new ResourceLocation("minecraft", "textures/misc/spyglass_scope.png");

    public static Player player;

    public static boolean runZoomMechanic = false;

    private static boolean isZoomingIn = false;

    private static double originalFOV;
    private static double originalSensitivity;

    private static final double targetFOV = 10.0f; // zoomed in fov

    private static float scopeScale = 0.5f;

    public static void update(PoseStack poseStack, float tickDelta){
        Minecraft mc = Minecraft.getInstance();

        // needed so that the spyglass overlay renders properly
        // hopefully we aren't breaking the rendering of other stuff
        RenderSystem.enableBlend();
        if (!Minecraft.useFancyGraphics()) {
            RenderSystem.enableDepthTest();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.defaultBlendFunc();
        }

        if(player != null){
            if(!player.isCrouching() || !mc.player.getMainHandItem().is(ModItems.ADVANCED_BLOWGUN.get())){
                runZoomMechanic = false;
            }
        }

        if (runZoomMechanic) {
            scopeScale = Mth.lerp(0.4F * tickDelta, scopeScale, 1.125F);

            renderSpyglassOverlay(scopeScale);

            if(!isZoomingIn){
                originalFOV = mc.options.fov;
                originalSensitivity = mc.options.sensitivity;
            }

            Minecraft.getInstance().options.fov = Mth.lerp(0.4f * tickDelta, Minecraft.getInstance().options.fov, targetFOV);

            // this is the calculation in the og spyglass related code
            // copying it for consistency
            double f = mc.options.sensitivity * 0.6F + 0.2F;
            double g = f * f * f;

            mc.options.sensitivity = originalSensitivity * g;

            isZoomingIn = true;
        }
        else if(isZoomingIn){ // this means we have just stopped using the zoom
            isZoomingIn = false;

            scopeScale = 0.5f;

            mc.options.fov = originalFOV;
            mc.options.sensitivity = originalSensitivity;
        }
    }

    // copied from Gui.renderSpyglassOverlay(...) (and slightly modified) because the original method is private
    private static void renderSpyglassOverlay(float f) {
        Minecraft mc = Minecraft.getInstance();

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, SPYGLASS_SCOPE_LOCATION);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        float g = (float)Math.min(screenWidth, screenHeight);
        float i = Math.min((float)screenWidth / g, (float)screenHeight / g) * f;
        float j = g * i;
        float k = g * i;
        float l = ((float)screenWidth - j) / 2.0F;
        float m = ((float)screenHeight - k) / 2.0F;
        float n = l + j;
        float o = m + k;
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(l, o, -90.0).uv(0.0F, 1.0F).endVertex();
        bufferBuilder.vertex(n, o, -90.0).uv(1.0F, 1.0F).endVertex();
        bufferBuilder.vertex(n, m, -90.0).uv(1.0F, 0.0F).endVertex();
        bufferBuilder.vertex(l, m, -90.0).uv(0.0F, 0.0F).endVertex();
        tesselator.end();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.disableTexture();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferBuilder.vertex(0.0, screenHeight, -90.0).color(0, 0, 0, 255).endVertex();
        bufferBuilder.vertex(screenWidth, screenHeight, -90.0).color(0, 0, 0, 255).endVertex();
        bufferBuilder.vertex(screenWidth, o, -90.0).color(0, 0, 0, 255).endVertex();
        bufferBuilder.vertex(0.0, o, -90.0).color(0, 0, 0, 255).endVertex();
        bufferBuilder.vertex(0.0, m, -90.0).color(0, 0, 0, 255).endVertex();
        bufferBuilder.vertex(screenWidth, m, -90.0).color(0, 0, 0, 255).endVertex();
        bufferBuilder.vertex(screenWidth, 0.0, -90.0).color(0, 0, 0, 255).endVertex();
        bufferBuilder.vertex(0.0, 0.0, -90.0).color(0, 0, 0, 255).endVertex();
        bufferBuilder.vertex(0.0, o, -90.0).color(0, 0, 0, 255).endVertex();
        bufferBuilder.vertex(l, o, -90.0).color(0, 0, 0, 255).endVertex();
        bufferBuilder.vertex(l, m, -90.0).color(0, 0, 0, 255).endVertex();
        bufferBuilder.vertex(0.0, m, -90.0).color(0, 0, 0, 255).endVertex();
        bufferBuilder.vertex(n, o, -90.0).color(0, 0, 0, 255).endVertex();
        bufferBuilder.vertex(screenWidth, o, -90.0).color(0, 0, 0, 255).endVertex();
        bufferBuilder.vertex(screenWidth, m, -90.0).color(0, 0, 0, 255).endVertex();
        bufferBuilder.vertex(n, m, -90.0).color(0, 0, 0, 255).endVertex();
        tesselator.end();
        RenderSystem.enableTexture();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

}
