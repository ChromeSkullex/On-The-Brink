package com.onthebrink.entity;

import com.onthebrink.OnTheBrink;
import com.onthebrink.entity.animal.PenguinTemp;
import com.onthebrink.entity.animal.base.AnimalBase;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(OnTheBrink.MOD_ID, Registry.ENTITY_TYPE_REGISTRY);

    public static final RegistrySupplier<EntityType<PenguinTemp>> PENGUIN_TEMP = registerAnimal("penguin_temp", PenguinTemp::new, .5f, .5f);



    /**
     * Registers the individual entity to the Registry
     * @param name Name of the Entity !Must be lowercase (a-z0-9./_)
     * @param factory Is the Entity itself
     * @return {@link EntityType} of any custom created Entity
     * */
    private static <T extends Entity> RegistrySupplier<EntityType<T>> registerAnimal(String name, EntityType.EntityFactory<T> factory, float width, float height) {
        return ENTITIES.register(name, () -> EntityType.Builder.of(factory, MobCategory.CREATURE).sized(width, height).build(name));
    }

    public static void register() {
        ENTITIES.register();

        EntityAttributeRegistry.register(PENGUIN_TEMP, AnimalBase::createMobAttributes);


    }


}