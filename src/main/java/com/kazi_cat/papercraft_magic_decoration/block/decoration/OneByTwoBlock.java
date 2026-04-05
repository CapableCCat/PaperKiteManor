package com.kazi_cat.papercraft_magic_decoration.block.decoration;

import com.kazi_cat.papercraft_magic_decoration.api.block.IRenderBoundingBoxProvider;
import com.kazi_cat.papercraft_magic_decoration.blockentity.AnimatedBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.utils.AABBUtils;
import com.kazi_cat.papercraft_magic_decoration.utils.VoxelShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

@SuppressWarnings("deprecation")
public class OneByTwoBlock extends HorizontalDirectionalBlock {
    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 1);
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    protected final EnumMap<Direction, VoxelShape> shapesLeft;
    protected final EnumMap<Direction, VoxelShape> shapesRight;

    public OneByTwoBlock(Properties properties, VoxelShape shapeLeft, VoxelShape shapeRight) {
        super(properties);

        this.shapesLeft = VoxelShapeUtils.horizontalShapes(shapeLeft);
        this.shapesRight = VoxelShapeUtils.horizontalShapes(shapeRight);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PART, LEFT)
                .setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos leftPos = context.getClickedPos();
        Direction facing = context.getHorizontalDirection();
        Level level = context.getLevel();
        BlockPos rightPos = leftPos.relative(facing.getOpposite().getCounterClockWise());
        if (level.getBlockState(rightPos).canBeReplaced(context)) {
            return this.defaultBlockState().setValue(FACING, facing.getOpposite());
        }
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        int position = state.getValue(PART);
        Direction facing = state.getValue(FACING);

        if ((position == LEFT && direction == facing.getCounterClockWise())
                || (position == RIGHT && direction == facing.getClockWise())) {
            if (!neighborState.is(this) || neighborState.getValue(FACING) != facing || neighborState.getValue(PART) == position) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.isCreative() && state.getValue(PART) == RIGHT) {
            BlockPos leftPos = pos.relative(state.getValue(FACING).getClockWise());
            BlockState leftState = level.getBlockState(leftPos);
            if (leftState.is(state.getBlock()) && leftState.getValue(PART) == LEFT) {
                level.setBlock(leftPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_ALL);
                level.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, leftPos, Block.getId(leftState));
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        Direction facing = state.getValue(FACING);
        BlockPos rightPos = pos.relative(facing.getCounterClockWise());
        BlockState rightState = state.setValue(PART, RIGHT);
        pLevel.setBlock(rightPos, rightState, Block.UPDATE_ALL);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        if (state.getValue(PART) == RIGHT) {
            return Collections.emptyList();
        }
        return super.getDrops(state, params);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(PART) == LEFT ? shapesLeft.get(state.getValue(FACING)) : shapesRight.get(state.getValue(FACING));
    }


    public static class Waterlogged extends OneByTwoBlock implements SimpleWaterloggedBlock {
        public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

        public Waterlogged(Properties properties, VoxelShape shapeLeft, VoxelShape shapeRight) {
            super(properties, shapeLeft, shapeRight);

            StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
            this.overrideBlockStateDefinition(builder);
            this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

            this.registerDefaultState(this.stateDefinition.any()
                    .setValue(FACING, Direction.NORTH)
                    .setValue(PART, LEFT)
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
    }


    public static class Animated extends OneByTwoBlock implements EntityBlock, IRenderBoundingBoxProvider {
        public Animated(Properties properties, VoxelShape shapeLeft, VoxelShape shapeRight) {
            super(properties, shapeLeft, shapeRight);
        }

        @Override
        public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                     InteractionHand hand, BlockHitResult hitResult) {
            if (player.getItemInHand(hand).isEmpty() && state.getValue(PART) == RIGHT) {
                BlockPos leftPos = pos.relative(state.getValue(FACING).getClockWise());
                BlockState leftState = level.getBlockState(leftPos);
                if (leftState.is(state.getBlock()) && leftState.getValue(PART) == LEFT) {
                    if (level.getBlockEntity(leftPos) instanceof AnimatedBlockEntity animated) {
                        animated.triggerAnim();
                        return InteractionResult.SUCCESS;
                    }
                }
                return InteractionResult.FAIL;
            }

            return super.use(state, level, pos, player, hand, hitResult);
        }

        @Override
        public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            if (state.getValue(PART) == RIGHT) return null;
            return new AnimatedBlockEntity(pos, state);
        }

        @Override
        public RenderShape getRenderShape(BlockState state) {
            return RenderShape.ENTITYBLOCK_ANIMATED;
        }

        @Override
        public AABB getRenderBoundingBox(BlockState state, BlockPos pos) {
            BlockPos rightPos = pos.immutable().relative(state.getValue(FACING).getCounterClockWise());
            return AABBUtils.fromTo(pos, rightPos.above());
        }


        public static class Waterlogged extends Animated implements SimpleWaterloggedBlock {
            public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

            public Waterlogged(Properties properties, VoxelShape shapeLeft, VoxelShape shapeRight) {
                super(properties, shapeLeft, shapeRight);

                StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
                this.overrideBlockStateDefinition(builder);
                this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

                this.registerDefaultState(this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(PART, LEFT)
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
        }
    }
}
