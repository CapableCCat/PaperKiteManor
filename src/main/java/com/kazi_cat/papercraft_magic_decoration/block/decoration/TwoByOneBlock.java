package com.kazi_cat.papercraft_magic_decoration.block.decoration;


import com.kazi_cat.papercraft_magic_decoration.utils.VoxelShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

@SuppressWarnings("deprecation")
public class TwoByOneBlock extends HorizontalDirectionalBlock {
    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 1);
    public static final int FRONT = 0;
    public static final int BEHIND = 1;
    protected final EnumMap<Direction, VoxelShape> shapesFront;
    protected final EnumMap<Direction, VoxelShape> shapesBehind;

    public TwoByOneBlock(Properties properties, VoxelShape front, VoxelShape behind) {
        super(properties);
        this.shapesFront = VoxelShapeUtils.horizontalShapes(front);
        this.shapesBehind = VoxelShapeUtils.horizontalShapes(behind);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PART, FRONT)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos frontPos = context.getClickedPos();
        Direction facing = context.getHorizontalDirection();
        Level level = context.getLevel();
        BlockPos behindPos = frontPos.relative(facing);
        if (level.getBlockState(behindPos).canBeReplaced(context)) {
            return defaultBlockState().setValue(FACING, facing.getOpposite());
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        Direction facing = pState.getValue(FACING);
        BlockPos behindPos = pPos.relative(facing.getOpposite());
        BlockState behindState = pState.setValue(PART, BEHIND);
        pLevel.setBlock(behindPos, behindState, Block.UPDATE_ALL);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        int position = state.getValue(PART);
        Direction facing = state.getValue(FACING);

        if ((position == FRONT && direction == facing.getOpposite())
                || (position == BEHIND && direction == facing)) {
            if (!neighborState.is(this) || neighborState.getValue(FACING) != facing || neighborState.getValue(PART) == position) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.isCreative() && state.getValue(PART) == BEHIND) {
            BlockPos front = pos.relative(state.getValue(FACING));
            BlockState frontState = level.getBlockState(front);
            if (frontState.is(state.getBlock()) && frontState.getValue(PART) == FRONT) {
                BlockState airBlockState = frontState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                level.setBlock(front, airBlockState, Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_ALL);
                level.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, front, Block.getId(frontState));
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder pParams) {
        if (state.getValue(PART) == BEHIND) {
            return Collections.emptyList();
        }
        return super.getDrops(state, pParams);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return state.getValue(PART) == FRONT ? shapesFront.get(direction) : shapesBehind.get(direction);
    }


    public static class Waterlogged extends TwoByOneBlock implements SimpleWaterloggedBlock {
        public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

        public Waterlogged(Properties properties, VoxelShape front, VoxelShape behind) {
            super(properties, front, behind);

            StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
            this.overrideBlockStateDefinition(builder);
            this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

            this.registerDefaultState(this.stateDefinition.any()
                    .setValue(FACING, Direction.NORTH)
                    .setValue(PART, FRONT)
                    .setValue(WATERLOGGED, false));
        }

        @Nullable
        @Override
        public BlockState getStateForPlacement(BlockPlaceContext context) {
            BlockState state = super.getStateForPlacement(context);
            FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
            return state == null ? null : state.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
        }

        protected void overrideBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(FACING, PART, WATERLOGGED);
        }

        @Override
        public FluidState getFluidState(BlockState state) {
            return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
        }

        @Override
        public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
            Direction facing = pState.getValue(FACING);
            BlockPos behindPos = pPos.relative(facing.getOpposite());
            FluidState fluidState = pLevel.getFluidState(behindPos);
            BlockState behindState = pState.setValue(PART, BEHIND).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
            pLevel.setBlock(behindPos, behindState, Block.UPDATE_ALL);
        }
    }
}
