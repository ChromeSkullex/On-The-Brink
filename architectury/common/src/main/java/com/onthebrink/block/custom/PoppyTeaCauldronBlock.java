package com.onthebrink.block.custom;

import com.onthebrink.block.ModBlocks;
import com.onthebrink.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.LavaFluid;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Random;

public class PoppyTeaCauldronBlock extends AbstractCauldronBlock {
    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_CAULDRON;

    public PoppyTeaCauldronBlock(Properties properties) {
        super(properties, CauldronInteraction.EMPTY);
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, Integer.valueOf(1)));
    }

    @Override // this only runs clientside btw
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
        BlockState stateBelow = level.getBlockState(pos.below());
        Block blockBelow = stateBelow.getBlock();

        boolean isHeated = false;
        if (blockBelow == Blocks.FIRE || blockBelow == Blocks.SOUL_FIRE) {
            isHeated = true;
        } else if (blockBelow instanceof CampfireBlock && stateBelow.getValue(CampfireBlock.LIT)) {
            isHeated = true;
        }
        else if (blockBelow == Blocks.LAVA || blockBelow == Blocks.LAVA_CAULDRON){
            isHeated = true;
        }

        if (isHeated) {
            if (random.nextFloat() < 0.8f) { // chance per tick to spawn bubbles
                float rand_x = (random.nextFloat() - 0.5f) * 0.5f;
                float rand_z = (random.nextFloat() - 0.5f) * 0.5f;

                double x = pos.getX() + 0.5D + rand_x;
                double y = pos.getY() + getContentHeight(state); // spawn just above liquid
                double z = pos.getZ() + 0.5D + rand_z;

                level.addParticle(ParticleTypes.BUBBLE_COLUMN_UP, x, y, z, 0.0D, 0.05D, 0.0D);
                level.addParticle(ParticleTypes.BUBBLE_POP, x, y, z, 0.0D, 0.05D, 0.0D);
                level.playLocalSound(x, y, z, SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT,
                        SoundSource.BLOCKS, 0.2F, 1.0F, false);
            }
        }
    }


    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        if (random.nextInt(3) != 0) return; // 1/3 chance of a random tick triggering the transformation into tranquilizer

        BlockState stateBelow = level.getBlockState(pos.below());
        Block blockBelow = stateBelow.getBlock();

        boolean isHeated = false;
        if (blockBelow == Blocks.FIRE || blockBelow == Blocks.SOUL_FIRE) {
            isHeated = true;
        } else if (blockBelow instanceof CampfireBlock && stateBelow.getValue(CampfireBlock.LIT)) {
            isHeated = true;
        }
        else if (blockBelow == Blocks.LAVA || blockBelow == Blocks.LAVA_CAULDRON){
            isHeated = true;
        }

        if (isHeated) {
            int currentLevel = state.getValue(LEVEL);

            level.setBlockAndUpdate(
                    pos,
                    ModBlocks.TRANQUILIZER_CAULDRON.get().defaultBlockState().setValue(LEVEL, currentLevel)
            );

            level.playSound(null, pos, SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.sendParticles(ParticleTypes.CLOUD,
                    pos.getX() + 0.5D,
                    pos.getY() + getContentHeight(state),
                    pos.getZ() + 0.5D,
                    8, 0.2, 0.1, 0.2, 0.0
            );

        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack heldItemStack = player.getItemInHand(hand);
        if (heldItemStack.isEmpty()) {
            return InteractionResult.PASS;
        }

        int currentLevel = state.getValue(LEVEL);
        Item heldItem = heldItemStack.getItem();

        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        // taking Tea From Cauldron
        if (heldItem == Items.BUCKET) {
            if (currentLevel == 3) {
                if (!player.getAbilities().instabuild) {
                    heldItemStack.shrink(1);
                }
                player.getInventory().add(new ItemStack(ModItems.BUCKET_OF_POPPY_TEA.get()));
                level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
                level.playSound(null, pos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                player.awardStat(Stats.USE_CAULDRON);
                return InteractionResult.SUCCESS;
            }
        } else if (heldItem == Items.GLASS_BOTTLE) {
            if (!player.getAbilities().instabuild) {
                heldItemStack.shrink(1);
            }

            player.getInventory().add(new ItemStack(ModItems.BOTTLE_OF_POPPY_TEA.get()));

            lowerFillLevel(state, level, pos);
            level.playSound(null, pos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            player.awardStat(Stats.USE_CAULDRON);
            return InteractionResult.SUCCESS;
        }

        // check if player is holding a BUCKET_OF_POPPY_TEA to fill it up
        else if (heldItem == ModItems.BUCKET_OF_POPPY_TEA.get().asItem()) {
            if (currentLevel < 3) {
                if (!player.getAbilities().instabuild) {
                    player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                }
                // fill the cauldron to the max level
                level.setBlockAndUpdate(pos, state.setValue(LEVEL, 3));
                level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                player.awardStat(Stats.FILL_CAULDRON);
                return InteractionResult.SUCCESS;
            }
        }
        // check if player is holding a BOTTLE_OF_POPPY_TEA to add one level
        else if (heldItem == ModItems.BOTTLE_OF_POPPY_TEA.get().asItem()) {
            if (currentLevel < 3) {
                if (!player.getAbilities().instabuild) {
                    ItemStack heldStack = player.getItemInHand(hand);

                    heldStack.shrink(1);

                    if (heldStack.isEmpty()) {
                        player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
                    } else {
                        if (!player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE))) {
                            player.drop(new ItemStack(Items.GLASS_BOTTLE), false);
                        }
                    }
                }

                // increase the cauldron level by one
                level.setBlockAndUpdate(pos, state.setValue(LEVEL, currentLevel + 1));
                level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                player.awardStat(Stats.FILL_CAULDRON);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    protected double getContentHeight(BlockState state) {
        return (6.0 + (double) ((Integer) state.getValue(LEVEL)).intValue() * 3.0) / 16.0;
    }

    // static version cuz apparently the other one isnt for some reason
    public static double getContentHeightStatic(BlockState state){
        return (6.0 + (double) ((Integer) state.getValue(LEVEL)).intValue() * 3.0) / 16.0;
    }



    public static void lowerFillLevel(BlockState state, Level level, BlockPos pos) {
        int i = (Integer) state.getValue(LEVEL) - 1;
        level.setBlockAndUpdate(pos, i == 0 ? Blocks.CAULDRON.defaultBlockState() : state.setValue(LEVEL, Integer.valueOf(i)));
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return (Integer) state.getValue(LEVEL);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    @Override
    public boolean isFull(BlockState state) {
        return (Integer) state.getValue(LEVEL) == 3;
    }

}
