package com.onthebrink.entity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.onthebrink.OnTheBrink;
import com.onthebrink.entity.animal.base.AnimalBase;
import com.onthebrink.entity.util.AnimalDefinition;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static com.onthebrink.item.ModItems.registerSpawnEggs;

public class ModEntities {
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(OnTheBrink.MOD_ID, Registry.ENTITY_TYPE_REGISTRY);
    public static final Map<String, RegistrySupplier<EntityType<AnimalBase>>> REGISTERED_ENTITIES = new HashMap<>();
    public static final Map<String, AnimalDefinition> DEFINITIONS = new HashMap<>();
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
        loadJsonAnimals();
    }


    private static void loadJsonAnimals(){
        Gson obj = new Gson();
        InputStream input = ModEntities.class.getClassLoader().getResourceAsStream("assets/onthebrink/data/animals.json");
        try {
            if (input == null) {
                OnTheBrink.LOGGER.error("Error loading entity file [NOT FOUND]");
                return;
            }

            JsonObject root = obj.fromJson(new InputStreamReader(input), JsonObject.class);
            JsonObject animalsRoot = root.getAsJsonObject("animals");

            for (Map.Entry<String, JsonElement> entry : animalsRoot.entrySet()) {
                String key = entry.getKey();
                OnTheBrink.LOGGER.info("{} Animals Root {}", key, entry );
                JsonObject animalRoot = entry.getValue().getAsJsonObject();

                AnimalDefinition def = new AnimalDefinition();
                def.id = key;
                def.alias = animalRoot.get("alias").getAsString();
                def.className = animalRoot.get("className").getAsString();
                def.width = animalRoot.getAsJsonObject("size").get("width").getAsFloat();
                def.height = animalRoot.getAsJsonObject("size").get("height").getAsFloat();
                def.hp = animalRoot.get("hp").getAsInt();
                def.primary_color = Integer.parseInt(animalRoot.getAsJsonObject("colors").get("primary").getAsString(),16);
                def.secondary_color = Integer.parseInt(animalRoot.getAsJsonObject("colors").get("secondary").getAsString(),16);
                def.diet = animalRoot.get("diet").getAsString();
                def.activity = animalRoot.get("activity").getAsString();
                def.command_item = animalRoot.get("command_item").getAsString();

                // Book Image
                def.animal_book_image = OnTheBrink.location("textures/gui/"+def.id+".png");
                JsonObject textureBookSizes = animalRoot.getAsJsonObject("book");
                def.textureWidth = textureBookSizes.get("textureWidth").getAsInt();
                def.textureHeight = textureBookSizes.get("textureHeight").getAsInt();

                OnTheBrink.LOGGER.info("Loading animal: {}", def.id);

                EntityType.EntityFactory<AnimalBase> factory = getAnimalBaseEntityFactory(def);

                RegistrySupplier<EntityType<AnimalBase>> supplier = ENTITIES.register(def.id, () ->
                        EntityType.Builder.of(factory, MobCategory.CREATURE)
                                .sized(def.width, def.height)
                                .build(def.id)
                );

                REGISTERED_ENTITIES.put(def.id, supplier);
                DEFINITIONS.put(def.id, def);

                EntityAttributeRegistry.register(() -> (EntityType<? extends LivingEntity>) supplier.get(), AnimalBase::createMobAttributes);
//                registerSpawnEggs(def);

            }

        }
        catch (Exception e) {
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
