package com.kazi_cat.papercraft_magic_decoration.block;

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
public class TwoByOneVerticalBlock extends HorizontalDirectionalBlock {
    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 1);
    public static final int DOWN = 0;
    public static final int UP = 1;
    protected final EnumMap<Direction, VoxelShape> shapesBelow;
    protected final EnumMap<Direction, VoxelShape> shapesAbove;

    public TwoByOneVerticalBlock(Properties properties, VoxelShape below, VoxelShape above) {
        super(properties);

        this.shapesBelow = VoxelShapeUtils.horizontalShapes(below);
        this.shapesAbove = VoxelShapeUtils.horizontalShapes(above);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PART, DOWN)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos belowPos = context.getClickedPos();
        Level level = context.getLevel();
        BlockPos abovePos = belowPos.relative(Direction.UP);
        if (level.getBlockState(abovePos).canBeReplaced(context)) {
            return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        BlockPos abovePos = pos.relative(Direction.UP);
        BlockState aboveState = state.setValue(PART, UP);
        pLevel.setBlock(abovePos, aboveState, Block.UPDATE_ALL);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        int position = state.getValue(PART);

        if ((position == DOWN && direction == Direction.UP) || (position == UP && direction == Direction.DOWN)) {
            if (!neighborState.is(this) || neighborState.getValue(FACING) != state.getValue(FACING) || neighborState.getValue(PART) == position) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.isCreative() && state.getValue(PART) == UP) {
            BlockPos belowPos = pos.relative(Direction.DOWN);
            BlockState belowState = level.getBlockState(belowPos);
            if (belowState.is(state.getBlock()) && belowState.getValue(PART) == DOWN) {
                level.setBlock(belowPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_ALL);
                level.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, belowPos, Block.getId(belowState));
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        if (state.getValue(PART) != DOWN) {
            return Collections.emptyList();
        }
        return super.getDrops(state, params);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(PART) == DOWN ? shapesBelow.get(state.getValue(FACING)) : shapesAbove.get(state.getValue(FACING));
    }


    public static class Waterlogged extends TwoByOneVerticalBlock implements SimpleWaterloggedBlock {
        public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

        public Waterlogged(Properties properties, VoxelShape below, VoxelShape above) {
            super(properties, below, above);

            StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
            this.overrideBlockStateDefinition(builder);
            this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

            this.registerDefaultState(this.stateDefinition.any()
                    .setValue(FACING, Direction.NORTH)
                    .setValue(PART, DOWN)
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
