package com.onthebrink.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.onthebrink.block.ModBlocks;
import com.onthebrink.client.gui.OnTheBrinkScreen;
import com.onthebrink.client.renderer.AnimalGeoRenderer;
import com.onthebrink.entity.ModEntities;
import com.onthebrink.item.ModItems;
import com.onthebrink.world.gen.ModTreeGeneration;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.animal.Animal;

public class ClientInit {

    public static void later(){
        registerBlockRenderers();
        ModTreeGeneration.generateTrees();
        registerEventHandlers();
    }

    public static void immediate(){
        registerEntityRenderer();

    }
    private static void registerEntityRenderer(){
        EntityRendererRegistry.register(ModEntities.PENGUIN_TEMP, context -> new AnimalGeoRenderer<>(context, "penguin_temp"));

    }


    public static void registerBlockRenderers(){
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.WOODS_CYCAD_SPROUT.get());
        RenderTypeRegistry.register(RenderType.cutout(), ModBlocks.CHOCOLATE_COSMOS.get());
    }

    private static void registerEventHandlers() {
        InteractionEvent.INTERACT_ENTITY.register(((player, entity, interactionHand) -> {
            if (player.level.isClientSide()) {
                if(player.getItemInHand(interactionHand).is(ModItems.ANIMAL_BOOK.get())){
                    if (entity instanceof Animal animal){
                        Minecraft.getInstance().setScreen(new OnTheBrinkScreen(animal));

                    }
                    return EventResult.interruptTrue();

                }
            }
            return EventResult.pass();
        }));

    }


    /**
     * Renders the page with the information for Animal
     * TODO: * Create Dynamic Translatable texts.
     * */
    private void renderPage(PoseStack poseStack, int mouseX, int mouseY) {
        
    }
}
