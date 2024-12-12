package com.onthebrink.entity;

import com.onthebrink.OnTheBrink;
import com.onthebrink.entity.client.PenguinTemp.PenguinTempRenderer;
import com.onthebrink.entity.penguinTemp.PenguinTempEntity;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;



public class ModEntities {

    public static final EntityType<PenguinTempEntity> PENGUIN_ENTITY = registerEntity(
            "penguin_temp",
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PenguinTempEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 1f))
                    .build()
    );

    /**
     * Registers the individual entity to the Registry
     * @param name Name of the Entity !Must be lowercase (a-z0-9./_)
     * @param entityType The Entity type. Recommend to use {@link FabricEntityTypeBuilder}
     * @return {@link EntityType} of any custom created Entity
     * */
    private static <T extends EntityType<?>> T registerEntity(String name, T entityType) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(OnTheBrink.MOD_ID, name), entityType);
    }

    public static void registerEntities() {

        OnTheBrink.LOGGER.info("Registering entities");

        // All other Entities must register the renderer and the attributes
        EntityRendererRegistry.register(ModEntities.PENGUIN_ENTITY, PenguinTempRenderer::new);
        FabricDefaultAttributeRegistry.register(PENGUIN_ENTITY, ChickenEntity.createChickenAttributes());

    }

}
