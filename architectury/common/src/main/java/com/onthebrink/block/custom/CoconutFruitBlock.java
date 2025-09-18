package com.onthebrink.block.custom;

import com.onthebrink.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CoconutFruitBlock extends CocoaBlock {

    public static final TagKey<Block> COCONUT_LOGS =
            TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation("onthebrink", "coconut_logs"));

    public CoconutFruitBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (projectile.getOwner() instanceof ServerPlayer serverPlayer) { // only run on server
            BlockPos pos = hit.getBlockPos();

            // drop the block as item and destroy it
            level.destroyBlock(pos, true);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos.relative(state.getValue(FACING)));
        return blockState.is(COCONUT_LOGS);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, net.minecraft.world.phys.BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(hand);

        // check if player is holding an axe or sword
        if (heldItem.getItem() instanceof AxeItem || heldItem.getItem() instanceof SwordItem) {
            if (!world.isClientSide) {
                // drop open coconut
                ItemStack drop = new ItemStack(ModItems.OPENED_COCONUT.get());
                popResource(world, pos, drop);

                world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

                // play breaking sound
                world.playSound(null, pos, SoundEvents.BAMBOO_BREAK,
                        net.minecraft.sounds.SoundSource.BLOCKS, 1.0f, 1.0f);

                // spawn breaking particles
                world.levelEvent(2001, pos, Block.getId(state));
            }
            return InteractionResult.sidedSuccess(world.isClientSide);
        }

        return super.use(state, world, pos, player, hand, hit);
    }

}
