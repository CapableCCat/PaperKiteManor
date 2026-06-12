package com.kazi_cat.papercraft_magic_decoration.block.decoration;


import com.kazi_cat.papercraft_magic_decoration.block.base.MultipartBlock;
import com.kazi_cat.papercraft_magic_decoration.utils.VoxelShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
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
public class TwoByOneVerticalDecorationBlock extends MultipartBlock implements SimpleWaterloggedBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected final EnumMap<Direction, VoxelShape> shapesBelow;
    protected final EnumMap<Direction, VoxelShape> shapesAbove;

    public TwoByOneVerticalDecorationBlock(Properties properties, VoxelShape shapeBelow, VoxelShape shapeAbove) {
        super(properties, 2);
        this.shapesBelow = VoxelShapeUtils.horizontalShapes(shapeBelow);
        this.shapesAbove = VoxelShapeUtils.horizontalShapes(shapeAbove);

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.overrideBlockStateDefinition(builder);
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(partProperty, 0)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
    }

    protected void overrideBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, partProperty);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        Direction facing = context.getHorizontalDirection().getOpposite();
        BlockPos pos = context.getClickedPos();
        BlockPos above = pos.above();
        if (!level.getBlockState(pos).canBeReplaced(context) || !level.getBlockState(above).canBeReplaced(context)) {
            return null;
        }
        FluidState fluidState = level.getFluidState(pos);
        return this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        BlockPos above = pos.above();
        FluidState fluidState = level.getFluidState(above);
        level.setBlockAndUpdate(above, state
                .setValue(partProperty, 1)
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER));
    }

    @Override
    public List<BlockPos> getOrderedParts(BlockPos pos, BlockState state) {
        return switch (state.getValue(partProperty)) {
            case 0 -> List.of(pos, pos.above());
            case 1 -> List.of(pos.below(), pos);
            default -> List.of();
        };
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        return state.getValue(partProperty) == 0 ? super.getDrops(state, params) : Collections.emptyList();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        return state.getValue(partProperty) == 0 ? shapesBelow.get(facing) : shapesAbove.get(facing);
    }
}
