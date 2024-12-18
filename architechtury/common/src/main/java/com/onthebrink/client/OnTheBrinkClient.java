package com.onthebrink.client;


import com.onthebrink.OnTheBrink;
import com.onthebrink.client.renderer.AnimalGeoRenderer;
import com.onthebrink.entity.ModEntities;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;

public class OnTheBrinkClient {



    private static void registerEntityRenderer(){
        EntityRendererRegistry.register(ModEntities.PENGUIN_TEMP, context -> new AnimalGeoRenderer<>(context, "penguin_temp"));

    }

    public static void init() {

    }
}
