package com.kazi_cat.papercraft_magic_decoration.block.decoration;

import com.kazi_cat.papercraft_magic_decoration.api.block.IRenderBoundingBoxProvider;
import com.kazi_cat.papercraft_magic_decoration.blockentity.AnimatedBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.utils.AABBUtils;
import com.kazi_cat.papercraft_magic_decoration.utils.VoxelShapeUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

@SuppressWarnings("deprecation")
public class OneByThreeBlock extends HorizontalDirectionalBlock {
    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 2);
    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;
    protected final Int2ObjectArrayMap<EnumMap<Direction, VoxelShape>> shapeMap;

    public OneByThreeBlock(Properties properties, VoxelShape shapeLeft, VoxelShape shapeCenter, VoxelShape shapeRight) {
        super(properties);

        this.shapeMap = new Int2ObjectArrayMap<>();
        this.shapeMap.put(LEFT, VoxelShapeUtils.horizontalShapes(shapeLeft));
        this.shapeMap.put(CENTER, VoxelShapeUtils.horizontalShapes(shapeCenter));
        this.shapeMap.put(RIGHT, VoxelShapeUtils.horizontalShapes(shapeRight));

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PART, CENTER)
                .setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos centerPos = context.getClickedPos();
        Direction facing = context.getHorizontalDirection();
        Level level = context.getLevel();
        BlockPos leftPos = centerPos.relative(facing.getOpposite().getClockWise());
        BlockPos rightPos = centerPos.relative(facing.getOpposite().getCounterClockWise());
        if (level.getBlockState(rightPos).canBeReplaced(context) && level.getBlockState(leftPos).canBeReplaced(context)) {
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
                || (position == RIGHT && direction == facing.getClockWise())
                || (position == CENTER && (direction == facing.getClockWise() || direction == facing.getCounterClockWise()))) {
            if (!neighborState.is(this) || neighborState.getValue(FACING) != facing || neighborState.getValue(PART) == position) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.isCreative()) {
            if (state.getValue(PART) == RIGHT) {
                Direction direction = state.getValue(FACING).getClockWise();
                BlockPos centerPos = pos.relative(direction);
                BlockState centerState = level.getBlockState(centerPos);
                if (centerState.is(state.getBlock()) && centerState.getValue(PART) == CENTER) {
                    BlockState airBlockState = centerState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                    level.setBlock(centerPos, airBlockState, Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_ALL);
                    level.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, centerPos, Block.getId(centerState));
                }
            } else if (state.getValue(PART) == LEFT) {
                Direction direction = state.getValue(FACING).getCounterClockWise();
                BlockPos centerPos = pos.relative(direction);
                BlockState centerState = level.getBlockState(centerPos);
                if (centerState.is(state.getBlock()) && centerState.getValue(PART) == CENTER) {
                    BlockState airBlockState = centerState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                    level.setBlock(centerPos, airBlockState, Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_ALL);
                    level.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, centerPos, Block.getId(centerState));
                }
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

        BlockPos leftPos = pos.relative(facing.getClockWise());
        BlockState leftState = state.setValue(PART, LEFT);
        pLevel.setBlock(leftPos, leftState, Block.UPDATE_ALL);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        if (state.getValue(PART) != CENTER) {
            return Collections.emptyList();
        }
        return super.getDrops(state, params);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        return shapeMap.get(state.getValue(PART)).get(facing);
    }


    public static class Animated extends OneByThreeBlock implements EntityBlock, IRenderBoundingBoxProvider {
        public Animated(Properties properties, VoxelShape shapeLeft, VoxelShape shapeCenter, VoxelShape shapeRight) {
            super(properties, shapeLeft, shapeCenter, shapeRight);
        }

        @Override
        public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            if (state.getValue(PART) != CENTER) return null;
            return new AnimatedBlockEntity(pos, state);
        }

        @Override
        public AABB getRenderBoundingBox(BlockState state, BlockPos pos) {
            Direction facing = state.getValue(FACING);
            BlockPos leftPos = pos.relative(facing.getClockWise());
            BlockPos rightPos = pos.relative(facing.getCounterClockWise());
            return AABBUtils.fromTo(leftPos, rightPos);
        }

        @Override
        public RenderShape getRenderShape(BlockState state) {
            return RenderShape.ENTITYBLOCK_ANIMATED;
        }


        public static class Waterlogged extends Animated implements SimpleWaterloggedBlock {
            public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

            public Waterlogged(Properties properties, VoxelShape shapeLeft, VoxelShape shapeCenter, VoxelShape shapeRight) {
                super(properties, shapeLeft, shapeCenter, shapeRight);

                StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
                this.overrideBlockStateDefinition(builder);
                this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

                this.registerDefaultState(this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(PART, CENTER)
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
