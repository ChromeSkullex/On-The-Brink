package com.onthebrink.block.custom;

import com.onthebrink.block.ModBlocks;
import com.onthebrink.client.particle.ModParticles;
import com.onthebrink.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class CoconutLatexCollectorBlock extends HorizontalDirectionalBlock {
    public static final BooleanProperty IS_FULL = BooleanProperty.create("is_full");

    public CoconutLatexCollectorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(IS_FULL, false));
    }

    protected static final VoxelShape NORTH_AABB = Block.box(4.0, -11.0, 0.0, 12.0, 2.0, 8.0);
    protected static final VoxelShape SOUTH_AABB = Block.box(4.0, -11.0, 8.0, 12.0, 2.0, 16.0);
    protected static final VoxelShape WEST_AABB  = Block.box(0.0, -11.0, 4.0, 8.0, 2.0, 12.0);
    protected static final VoxelShape EAST_AABB  = Block.box(8.0, -11.0, 4.0, 16.0, 2.0, 12.0);

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, net.minecraft.world.phys.BlockHitResult hit) {
        if(!state.getValue(IS_FULL)){
            return InteractionResult.PASS;
        }

        ItemStack drop = new ItemStack(ModItems.RAW_RUBBER.get());
        popResource(world, pos, drop);

        world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

        world.playSound(null, pos, SoundEvents.CHICKEN_EGG, // the egg laying sound is a satisfying *pop* :)
                net.minecraft.sounds.SoundSource.BLOCKS, 1.0f, 1.0f);

        return InteractionResult.sidedSuccess(world.isClientSide);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        if (state.getValue(IS_FULL)) {
            return;
        }

        if (random.nextInt(40) == 0) {
            level.setBlock(pos, state.setValue(IS_FULL, true), 3);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {
        if (state.getValue(IS_FULL)) {
            return;
        }

        if (random.nextInt(3) != 0) {
            return;
        }

        Direction facing = state.getValue(FACING);

        double x = pos.getX() + 0.5;
        double y = pos.getY() - 0.0;
        double z = pos.getZ() + 0.5;

        double d = 0.235; // dist from center

        switch (facing) {
            case NORTH:
                z -= d;
                break;
            case SOUTH:
                z += d;
                break;
            case WEST:
                x -= d;
                break;
            case EAST:
                x += d;
                break;
        }

        level.addParticle(ModParticles.LATEX_DRIP.get(),
                x, y, z,
                0.0D, -0.00D, 0.0D);
    }


    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (!state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, IS_FULL);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos.relative(state.getValue(FACING)));
        BlockState blockBelow = level.getBlockState(pos.below());

        boolean hasTreeLog = blockState.is(ModBlocks.STRIPPED_RUBBER_TREE_LOG.get());

        boolean noCollisionBelow = blockBelow.getCollisionShape(level, pos.below()).isEmpty();

        return hasTreeLog && noCollisionBelow;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockState = this.defaultBlockState();
        LevelReader levelReader = context.getLevel();
        BlockPos blockPos = context.getClickedPos();

        for (Direction direction : context.getNearestLookingDirections()) {
            if (direction.getAxis().isHorizontal()) {
                blockState = blockState.setValue(FACING, direction);
                if (blockState.canSurvive(levelReader, blockPos)) {
                    return blockState;
                }
            }
        }

        return null;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        switch ((Direction)state.getValue(FACING)) {
            case SOUTH:
                return SOUTH_AABB;
            case NORTH:
            default:
                return NORTH_AABB;
            case WEST:
                return WEST_AABB;
            case EAST:
                return EAST_AABB;
        }
    }
}
