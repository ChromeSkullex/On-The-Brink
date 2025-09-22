package com.onthebrink.client.particle;

import com.onthebrink.OnTheBrink;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(OnTheBrink.MOD_ID, Registry.PARTICLE_TYPE_REGISTRY);

    public static final RegistrySupplier<SimpleParticleType> LATEX_DRIP =
            PARTICLE_TYPES.register("latex_drip", Type::new);

    public static void register() {
        PARTICLE_TYPES.register();
    }

    public static class Type extends SimpleParticleType {
        public Type() {
            super(false);
        }
    }
}

