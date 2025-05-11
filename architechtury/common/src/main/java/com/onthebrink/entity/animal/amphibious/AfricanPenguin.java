package com.onthebrink.entity.animal.amphibious;

import com.onthebrink.entity.ai.AnimalPanicGoal;
import com.onthebrink.entity.ai.AnimalWanderGoal;
import com.onthebrink.entity.animal.base.AnimalBase;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;


public class AfricanPenguin extends AnimalBase  {
    public static final String CLASS_ID = "african_penguin";

    public static final String IDLE = CLASS_ID+".animation.idle";
    public static final String WALK = CLASS_ID+".animation.walk";

    protected static final AnimationBuilder IDLE_ANIM = new AnimationBuilder().addAnimation(IDLE, true);

    public AfricanPenguin(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }


    protected <E extends AnimalBase> PlayState moveController(final AnimationEvent<E> event) {
        if (!event.isMoving()) {
            event.getController().setAnimation(IDLE_ANIM);

            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }
    @Override
    // From IAnimatable
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "idle", 5, this::moveController));

    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(3, new AnimalWanderGoal(this, .5));
        goalSelector.addGoal(5, new AnimalPanicGoal(this, 0.8));
    }

}