package com.onthebrink.item.custom;

import com.onthebrink.block.ModBlocks;
import com.onthebrink.entity.projectiles.CoconutEntity;
import com.onthebrink.misc.ModCreativeModeTabs;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;

import java.util.function.Predicate;
import java.util.function.Supplier;

public class CoconutItem extends Item {
    private final Supplier<? extends Block> blockSupplier;

    public CoconutItem(Supplier<? extends Block> blockSupplier) {
        super(new Item.Properties().stacksTo(1).tab(ModCreativeModeTabs.TREES_AND_WOOD));
        this.blockSupplier = blockSupplier;
    }

    // TODO: Fix the bug where the coconut gets thrown when it could've been placed by replacing a block such as grass.
    // Maybe the code of the BlockItem class shines some light

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // creative mode players have a slightly longer reach.
        // would be cleaner to use the Minecraft singleton to get the gamemode's range
        // but it's only available clientside :/
        double range = player.isCreative() ? 5.0D : 4.5D;

        HitResult hitResult = getPlayerPOVHitResult(world, player, range);

        // try the block placement logic if we hit a block.
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            BlockPos placePos = blockHitResult.getBlockPos().relative(blockHitResult.getDirection());
            Block block = this.blockSupplier.get();

            Predicate<BlockPos> isReplaceable = (pos) -> {
                BlockState state = world.getBlockState(pos);
                return state.isAir() || state.getMaterial().isReplaceable();
            };

            if (isReplaceable.test(placePos) && block.defaultBlockState().canSurvive(world, placePos)) {
                if (!world.isClientSide) {
                    world.setBlock(placePos, block.defaultBlockState(), 3);
                    if (!player.getAbilities().instabuild) { // Correct check for creative mode
                        stack.shrink(1);
                    }
                }
                player.swing(hand, true);
                world.playSound(null, placePos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0f, 1.0f);
                return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
            }
        }

        // if we didn't place a block (either because we hit an entity, missed, or placement failed),
        // then we throw the coconut
        if (!world.isClientSide) {
            CoconutEntity coconut = new CoconutEntity(world, player);
            coconut.shootFromRotation(player, player.getXRot(), player.getYRot(), 0f, 1.5f, 1f);
            world.addFreshEntity(coconut);
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        player.swing(hand, true);
        world.playSound(null, player.blockPosition(), SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 1.0f, 1.0f);
        return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
    }


    // performs a raycast from the player's point of view to find the first block or entity they are looking at
    public static HitResult getPlayerPOVHitResult(Level world, Player player, double range) {
        Vec3 startVec = player.getEyePosition();
        Vec3 lookVec = player.getViewVector(1.0F);
        Vec3 endVec = startVec.add(lookVec.x * range, lookVec.y * range, lookVec.z * range);

        // block raycast.
        BlockHitResult blockHitResult = world.clip(new ClipContext(
                startVec,
                endVec,
                ClipContext.Block.OUTLINE, // Considers the outline of blocks.
                ClipContext.Fluid.NONE,   // Ignores fluids.
                player
        ));

        // entity raycast, but only up to the point where we hit a block.
        // so that we don't hit an entity that is behind a wall.
        double blockHitDistance = blockHitResult.getLocation().distanceToSqr(startVec);
        Vec3 entitySearchEndVec = endVec;

        if (blockHitResult.getType() != HitResult.Type.MISS) {
            range = blockHitResult.getLocation().distanceTo(startVec);
            entitySearchEndVec = blockHitResult.getLocation();
        }

        // find entities in a bounding box along the ray's path.
        AABB searchBox = player.getBoundingBox().expandTowards(lookVec.scale(range)).inflate(1.0D);
        EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(
                world,
                player, // The entity performing the raycast (to ignore itself).
                startVec,
                entitySearchEndVec,
                searchBox,
                (entity) -> !entity.isSpectator() && entity.isPickable() // A filter for which entities can be hit.
        );

        // if we hit an entity and it's closer than the block we hit, prioritize the entity.
        if (entityHitResult != null) {
            double entityHitDistance = entityHitResult.getLocation().distanceToSqr(startVec);
            if (entityHitDistance < blockHitDistance || blockHitResult.getType() == HitResult.Type.MISS) {
                return entityHitResult;
            }
        }

        // otherwise, the block hit (or a miss if we hit nothing) is our result.
        return blockHitResult;
    }
}

