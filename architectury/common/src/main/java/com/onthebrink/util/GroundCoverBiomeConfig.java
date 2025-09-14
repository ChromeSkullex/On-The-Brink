package com.onthebrink.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.onthebrink.OnTheBrink;
import dev.architectury.registry.level.biome.BiomeModifications;
import net.minecraft.resources.ResourceLocation;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GroundCoverBiomeConfig {
    private static Map<String, List<String>> groundCoverBiomeMap = Collections.emptyMap();
    private static boolean isLoaded = false;

    public static void load() {
        if (isLoaded) {
            return;
        }

        String filePath = "/assets/onthebrink/ground_cover_generation_biomes.json";

        try (InputStream stream = GroundCoverBiomeConfig.class.getResourceAsStream(filePath)) {
            if (stream == null) {
                OnTheBrink.LOGGER.error("Failed to find resource: {}", filePath);
                return;
            }

            try (InputStreamReader reader = new InputStreamReader(stream)) {
                Type type = new TypeToken<Map<String, List<String>>>() {}.getType();
                groundCoverBiomeMap = new Gson().fromJson(reader, type);
                isLoaded = true;
            }

            if (groundCoverBiomeMap.isEmpty()) {
                OnTheBrink.LOGGER.warn("No ground cover generation data loaded from {}", filePath);
            } else {
                OnTheBrink.LOGGER.info("Loaded ground cover generation data: {}", String.join(", ", groundCoverBiomeMap.keySet()));
            }

        } catch (Exception e) {
            OnTheBrink.LOGGER.error("Failed to load ground cover generation biomes from internal resources.", e);
        }
    }

    public static Boolean isFeatureInBiome(String featureName, BiomeModifications.BiomeContext context)
    {
        ResourceLocation biomeName = context.getKey();

        String namespace = biomeName.getNamespace();
        String path = biomeName.getPath();

        String fullName = namespace + ":" + path;

        return getBiomesForFeature(featureName).contains(fullName);
    }

    public static List<String> getBiomesForFeature(String featureName) {
        if (!isLoaded) {
            load();
        }
        return groundCoverBiomeMap.getOrDefault(featureName, Collections.emptyList());
    }
}