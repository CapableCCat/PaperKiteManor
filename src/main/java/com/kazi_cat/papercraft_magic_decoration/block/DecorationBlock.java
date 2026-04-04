package com.kazi_cat.papercraft_magic_decoration.block;

import com.kazi_cat.papercraft_magic_decoration.utils.VoxelShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;

@SuppressWarnings({"deprecation"})
public final class DecorationBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

    public static class Waterlogged extends Block implements SimpleWaterloggedBlock {
        @Nullable
        protected VoxelShape shape = null;

        public Waterlogged(Properties properties, VoxelShape shape) {
            super(properties);
            this.shape = shape;

            this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
        }

        public Waterlogged(Properties properties) {
            super(properties);

            this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
        }

        @Override
        @Nullable
        public BlockState getStateForPlacement(BlockPlaceContext context) {
            FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
            return this.defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(WATERLOGGED);
        }

        @Override
        public FluidState getFluidState(BlockState state) {
            return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
        }

        @Override
        public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return shape != null ? shape : super.getShape(state, level, pos, context);
        }
    }


    public static class HorizontalDirectional extends HorizontalDirectionalBlock {
        protected final EnumMap<Direction, VoxelShape> shapes;

        public HorizontalDirectional(Properties properties, VoxelShape northShape) {
            super(properties);
            this.shapes = VoxelShapeUtils.horizontalShapes(northShape);

            this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
        }

        @Override
        @Nullable
        public BlockState getStateForPlacement(BlockPlaceContext context) {
            return this.defaultBlockState()
                    .setValue(FACING, context.getHorizontalDirection().getOpposite());
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(FACING);
        }

        @Override
        public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
            return shapes.get(state.getValue(FACING));
        }


        public static class Waterlogged extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
            protected final EnumMap<Direction, VoxelShape> shapes;

            public Waterlogged(Properties properties, VoxelShape northShape) {
                super(properties);
                this.shapes = VoxelShapeUtils.horizontalShapes(northShape);

                this.registerDefaultState(this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(WATERLOGGED, false));
            }

            @Override
            @Nullable
            public BlockState getStateForPlacement(BlockPlaceContext context) {
                FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
                return this.defaultBlockState()
                        .setValue(FACING, context.getHorizontalDirection().getOpposite())
                        .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
            }

            @Override
            protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
                builder.add(FACING, WATERLOGGED);
            }

            @Override
            public FluidState getFluidState(BlockState state) {
                return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
            }

            @Override
            public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
                return shapes.get(state.getValue(FACING));
            }


//            public static class Animated extends Waterlogged implements EntityBlock {
//
//            }
        }
    }
}
