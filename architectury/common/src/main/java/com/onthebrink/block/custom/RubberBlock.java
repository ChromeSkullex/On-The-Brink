package com.onthebrink.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class RubberBlock extends Block {
    // actually adds energy. not physically accurate but it works to make it feel more bouncy.
    // it doesn't just keep bouncing you until you reach space because of Minecraft's really strong air resistance
    // so the end result is that energy *does* get lost from the system, it's just not solely from elasticity
    // so it's as if it had a COR of a little less than 1. Don't worry, physics isn't completely broken.
    public static final double coefficientOfRestitution = 1.2;

    public RubberBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float f) {
        if (entity.isSuppressingBounce()) {
            super.fallOn(level, state, pos, entity, f);
        } else {
            entity.causeFallDamage(f, 0.0F, DamageSource.FALL);
        }
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter level, Entity entity) {
        if (entity.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(level, entity);
        } else {
            bounceUp(entity);
        }
    }

    private void bounceUp(Entity entity) {
        Vec3 vec3 = entity.getDeltaMovement();
        if (vec3.y < -0.3) { // check if we have some decent downwards velocity before bouncing so that we don't get stuck bouncing
            double d = entity instanceof LivingEntity ? coefficientOfRestitution : 0.8;
            entity.setDeltaMovement(vec3.x, -vec3.y * d, vec3.z);

            entity.level.playSound(null, entity.blockPosition(), SoundEvents.SLIME_JUMP,
                    net.minecraft.sounds.SoundSource.BLOCKS, 1.0f, 1.0f);
        }
        else{
            entity.setDeltaMovement(vec3.x, 0, vec3.z); // le no bounce
        }
    }
}
