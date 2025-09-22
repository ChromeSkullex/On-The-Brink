package com.onthebrink.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class LatexDripParticle extends TextureSheetParticle {
    protected LatexDripParticle(ClientLevel level, double x, double y, double z,
                                double dx, double dy, double dz) {
        super(level, x, y, z, dx, dy, dz);

        this.gravity = 1.0F;
        this.lifetime = 10;           // in ticks
        this.xd = dx;
        this.yd = dy;
        this.zd = dz;

        this.quadSize = 0.02F;
        this.setSize(0.01F, 0.01F);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.removed && this.age++ >= this.lifetime) {
            this.remove();
        }
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz) {
            LatexDripParticle particle = new LatexDripParticle(level, x, y, z, dx, dy, dz);
            particle.pickSprite(this.spriteSet);
            return particle;
        }
    }

}
