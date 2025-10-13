package com.onthebrink.entity;

import com.onthebrink.OnTheBrink;
import com.onthebrink.entity.projectiles.CoconutEntity;
import com.onthebrink.entity.projectiles.RubberBallEntity;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(OnTheBrink.MOD_ID, Registry.ENTITY_TYPE_REGISTRY);

    public static final RegistrySupplier<EntityType<CoconutEntity>> COCONUT_ENTITY = ENTITIES.register("coconut",
            () -> EntityType.Builder.<CoconutEntity>of(CoconutEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("coconut"));

    public static final RegistrySupplier<EntityType<RubberBallEntity>> RUBBER_BALL_ENTITY = ENTITIES.register("rubber_ball",
            () -> EntityType.Builder.<RubberBallEntity>of(RubberBallEntity::new, MobCategory.MISC)
                    .sized(0.6F, 0.6F)
                    .clientTrackingRange(12)
                    .updateInterval(1)
                    .build("rubber_ball"));

    public static void register(){
        ENTITIES.register();
    }
}
