package com.onthebrink.world.feature.forge;

import com.onthebrink.OnTheBrink;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import static com.onthebrink.world.feature.ModFeatures.*;

@Mod.EventBusSubscriber(modid = OnTheBrink.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModFeaturesImpl{
    public static void register() {
        BARNACLES_FEATURE.feature().setRegistryName(BARNACLES_FEATURE.location());
    }

    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        IForgeRegistry<Feature<?>> registry = event.getRegistry();
        registry.register(BARNACLES_FEATURE.feature());
    }
}
