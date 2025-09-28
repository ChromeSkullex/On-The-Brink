package com.onthebrink.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.onthebrink.OnTheBrink;
import com.onthebrink.entity.ModEntities;
import com.onthebrink.entity.animal.base.AnimalBase;
import com.onthebrink.entity.util.AnimalDefinition;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;


public class OnTheBrinkScreen extends Screen {
    private static final ResourceLocation ONTHEBRINK_BACKGROUND = OnTheBrink.location("textures/gui/book_gui.png");
    private static final ResourceLocation AFRICAN_PENGUIN = OnTheBrink.location("textures/gui/african_penguin.png");

    private final AnimalBase entity;
    private final int xSize = 256;
    private final int ySize = 170;
    private final int textureSize = 256;

    private final int xBookPos = 21;
    private final int yBookPos = 15;
    
    private int leftPos;
    private int topPos;


    public OnTheBrinkScreen(AnimalBase entity) {
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
        // Background Rendered First
        this.renderBackground(poseStack);
        // Background Textures
        this.renderBackgroundLayer(poseStack, mouseX, mouseY);
        // Widgets of screen (Child)
        super.render(poseStack, mouseX, mouseY, partialTicks);
        this.renderForeground(poseStack, mouseX, mouseY);
        // After Widgets (tooltips)

    }



    private void renderBackgroundLayer(PoseStack poseStack, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ONTHEBRINK_BACKGROUND);

        blit(poseStack, leftPos, topPos, 0, 0, xSize, ySize, textureSize, textureSize);

    }


    private void renderForeground(PoseStack poseStack, int mouseX, int mouseY) {
        if (entity instanceof AnimalBase) {
            AnimalDefinition def = ModEntities.DEFINITIONS.get("african_penguin");
            this.renderFirstPage(poseStack, mouseX, mouseY, def);

        }

    }

    private void renderFirstPage(PoseStack poseStack, int mouseX, int mouseY, AnimalDefinition def) {
        renderImageGraphic(poseStack, mouseX, mouseY, def);
        renderFirstPageStatus(poseStack, mouseX, mouseY, def);
    }

    private void renderImageGraphic(PoseStack poseStack, int mouseX, int mouseY, AnimalDefinition def) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, def.animal_book_image);
        poseStack.pushPose();
        float scale = 0.04f;
        poseStack.scale(scale, scale, 1.0f);

        int drawX = (int) ((leftPos  + xBookPos)/ scale);
        int drawY = (int) ((topPos + yBookPos)/ scale);
        blit(
            poseStack,
            drawX , drawY ,   // scaled position
            0, 0,           // texture U,V
            def.textureWidth, def.textureHeight, // width/height of the drawn quad
            def.textureWidth, def.textureHeight // texture atlas size
        );
        poseStack.popPose();
    }

    private void renderFirstPageStatus(PoseStack poseStack, int mouseX, int mouseY, AnimalDefinition def) {
        int textXInitial = (leftPos+xBookPos);
        int textYInitial = (topPos+yBookPos) + 40; // Text Starting position under the image
        int textColor = 0x0;
        float largeText = 0.85f;
        float mediumText = 0.65f;
        float smallText = 0.50f;


        /*
        * Container Title
        * Common Name
        * Scientific Name
        * Conservation Status
        * */
        int container_title_y = 20;
        int line_spacing = 3;

        Component animal_name = new TranslatableComponent("book." + OnTheBrink.MOD_ID +".name.label.common."+def.id);
        Component animal_sci_name = new TranslatableComponent("book." + OnTheBrink.MOD_ID +".name.label.scientific."+def.id);

        Component conservation_status_label = new TranslatableComponent("book." + OnTheBrink.MOD_ID +".name.label.conservation_status");
        Component conservation_status_abbr = new TranslatableComponent("book." + OnTheBrink.MOD_ID +".name.label.critically_endangered_abbr");
        Component combined_status = conservation_status_label.copy().append(": ").append(conservation_status_abbr);

        textBuilder(poseStack, textXInitial, textYInitial + container_title_y, largeText, animal_name, textColor );
        textBuilder(poseStack, textXInitial, textYInitial + container_title_y + 5 + line_spacing, smallText, animal_sci_name, textColor );
        textBuilder(poseStack, textXInitial, textYInitial + container_title_y + 10 + line_spacing, smallText, combined_status, textColor );

        /*
        * Container Live
        * Age
        * Health
        * Hunger
        * Diet
        * */
        int container_live_y = 43;
        Component age_title = new TranslatableComponent("book." + OnTheBrink.MOD_ID +".status.age");
        Component health_title = new TranslatableComponent("book." + OnTheBrink.MOD_ID +".status.health");
        Component hunger_title = new TranslatableComponent("book." + OnTheBrink.MOD_ID +".status.hunger");
        Component diet_title = new TranslatableComponent("book." + OnTheBrink.MOD_ID +".status.diet");
        Component diet_value = new TranslatableComponent("book." + OnTheBrink.MOD_ID +".name.label."+def.diet);

        Component age_combined = age_title.copy().append(": ").append(String.valueOf(entity.getAge()));
        Component health_combined = health_title.copy().append(": ").append(String.valueOf(entity.getHealth()));
        Component hunger_combined = hunger_title.copy().append(": ").append(String.valueOf(entity.getHappiness()));
        Component diet_combined = diet_title.copy().append(": ").append(diet_value);

        textBuilder(poseStack, textXInitial, textYInitial + container_live_y + line_spacing, smallText, age_combined, textColor );
        textBuilder(poseStack, textXInitial, textYInitial + container_live_y + line_spacing + 5, smallText, health_combined, textColor );
        textBuilder(poseStack, textXInitial, textYInitial + container_live_y + line_spacing + 10, smallText, hunger_combined, textColor );
        textBuilder(poseStack, textXInitial, textYInitial + container_live_y + line_spacing + 15, smallText, diet_combined, textColor );



        /*
         * Container Game Data
         * Temperament
         * Command Item
         * Activity
         * Spawn Rate
         * */
        int container_data_y = 67;
        Component temperament_title = new TranslatableComponent("book." + OnTheBrink.MOD_ID +".name.label.temperament");
        Component command_item_title = new TranslatableComponent("book." + OnTheBrink.MOD_ID +".name.label.command_item");
        Component activity_title = new TranslatableComponent("book." + OnTheBrink.MOD_ID +".name.label.activity");
        Component spawn_rate_title = new TranslatableComponent("book." + OnTheBrink.MOD_ID +".name.label.spawn_rate");

        Component temperament_combined = temperament_title.copy().append(": ").append(new TranslatableComponent("book." + OnTheBrink.MOD_ID +".name.label.skittish"));
        Component command_item_combined = command_item_title.copy().append(": ").append(def.command_item);
        Component activity_combined = activity_title.copy().append(": ").append(def.activity);
        Component spawn_rate_combined = spawn_rate_title.copy().append(": ").append("N/A");

        textBuilder(poseStack, textXInitial, textYInitial + container_data_y + line_spacing, smallText, temperament_combined, textColor );
        textBuilder(poseStack, textXInitial, textYInitial + container_data_y + line_spacing + 5, smallText, command_item_combined, textColor );
        textBuilder(poseStack, textXInitial, textYInitial + container_data_y + line_spacing + 10, smallText, activity_combined, textColor );
        textBuilder(poseStack, textXInitial, textYInitial + container_data_y + line_spacing + 15, smallText, spawn_rate_combined, textColor );





    }

    private void textBuilder(PoseStack poseStack, int leftPos, int topPos, float fontSize, Component text, int textColor ) {
        poseStack.pushPose();
        poseStack.scale(fontSize, fontSize, 1.0f);

        int textX = (int) (leftPos / fontSize);
        int textY = (int) (topPos / fontSize);

        this.font.draw(poseStack, text, textX, textY, textColor);
//        this.font.draw(poseStack, text, textX, textY + 10, textColor);

        poseStack.popPose();

    }
}
