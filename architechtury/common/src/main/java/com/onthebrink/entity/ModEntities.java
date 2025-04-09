package com.onthebrink.entity;

import com.onthebrink.OnTheBrink;
import com.onthebrink.entity.animal.base.AnimalBase;
import com.onthebrink.entity.util.AnimalDefinition;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.*;

import static com.onthebrink.item.ModItems.registerSpawnEggs;

public class ModEntities {
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(OnTheBrink.MOD_ID, Registry.ENTITY_TYPE_REGISTRY);
    public static final Map<String, RegistrySupplier<EntityType<AnimalBase>>> REGISTERED_ENTITIES = new HashMap<>();

    // Example hardcoded entries
//    public static final RegistrySupplier<EntityType<PenguinTemp>> PENGUIN_TEMP = registerAnimal("penguin_temp", PenguinTemp::new, .5f, .5f);
//    public static final RegistrySupplier<EntityType<AfricanPenguin>> AFRICAN_PENGUIN = registerAnimal("african_penguin", AfricanPenguin::new, .5f, .5f);

    private static <T extends Entity> RegistrySupplier<EntityType<T>> registerAnimal(String name, EntityType.EntityFactory<T> factory, float width, float height) {
        return ENTITIES.register(name, () -> EntityType.Builder
                .of(factory, MobCategory.CREATURE)
                .sized(width, height)
                .build(name));
    }

    public static void register() {
        ENTITIES.register();
        loadAnimalsFromFile(); // Load
    }



    // Loading from a readable file with the all the animals
    private static void loadAnimalsFromFile() {
        Yaml yaml = new Yaml();

        try (InputStream input = ModEntities.class.getClassLoader().getResourceAsStream("assets/onthebrink/dictionaries/animals.yml")) {
            if (input == null) {
                OnTheBrink.LOGGER.error("animals.yml not found!");
                return;
            }

            List<Map<String, Object>> defs = yaml.load(input);

            for (Map<String, Object> map : defs) {
                AnimalDefinition def = new AnimalDefinition();
                def.id = (String) map.get("id");
                def.alias = (String) map.get("alias");
                def.className = (String) map.get("className");
                def.width = Float.parseFloat(map.get("width").toString());
                def.height = Float.parseFloat(map.get("height").toString());
                def.hp = Integer.parseInt(map.get("hp").toString());
                def.primary_color = Integer.parseInt(map.get("primary_color").toString());
                def.secondary_color = Integer.parseInt(map.get("secondary_color").toString());


                OnTheBrink.LOGGER.info("Loading animal: {}", def.id);

                EntityType.EntityFactory<AnimalBase> factory = getAnimalBaseEntityFactory(def);

                RegistrySupplier<EntityType<AnimalBase>> supplier = ENTITIES.register(def.id, () ->
                        EntityType.Builder.of(factory, MobCategory.CREATURE)
                                .sized(def.width, def.height)
                                .build(def.id)
                );

                REGISTERED_ENTITIES.put(def.id, supplier);

                EntityAttributeRegistry.register(() -> (EntityType<? extends LivingEntity>) supplier.get(), AnimalBase::createMobAttributes);
                registerSpawnEggs(def);
            }

        } catch (Exception e) {
            OnTheBrink.LOGGER.error("Error loading entity file", e);
        }
    }

    private static EntityType.@NotNull EntityFactory<AnimalBase> getAnimalBaseEntityFactory(AnimalDefinition def) {
        Class<? extends AnimalBase> entityClass = def.getEntityClass();

        return (type, level) -> {
            try {
                return entityClass.getConstructor(EntityType.class, Level.class)
                        .newInstance(type, level);
            } catch (Exception e) {
                throw new RuntimeException("Failed to instantiate entity: " + def.id, e);
            }
        };
    }

}
