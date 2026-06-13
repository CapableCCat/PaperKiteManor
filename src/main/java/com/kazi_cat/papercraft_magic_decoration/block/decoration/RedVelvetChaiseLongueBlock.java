package com.kazi_cat.papercraft_magic_decoration.block.decoration;

import com.kazi_cat.papercraft_magic_decoration.block.base.MultipartBlock;
import com.kazi_cat.papercraft_magic_decoration.utils.VoxelShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
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
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

@SuppressWarnings({"deprecation","unchecked"})
public class RedVelvetChaiseLongueBlock extends MultipartBlock implements SimpleWaterloggedBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final EnumMap<Direction, VoxelShape>[] SHAPE_MAP = new EnumMap[] {
            VoxelShapeUtils.horizontalShapes(Shapes.or(
                    Block.box(0, 0, 9.5, 7, 5, 13.5),
                    Block.box(0, 5, 9, 7, 8, 16),
                    Block.box(0, 8, 8, 7, 14, 16),
                    Block.box(3, 9, 7, 10.5, 16, 16)
            )),
            VoxelShapeUtils.horizontalShapes(Shapes.or(
                    Block.box(0, 5, 9, 16, 8, 16),
                    Block.box(0, 8, 8, 16, 14, 16),
                    Block.box(7, 14, 7, 15, 16, 16)
            )),
            VoxelShapeUtils.horizontalShapes(Shapes.or(
                    Block.box(3, 0, 9.5, 9, 5, 13.5),
                    Block.box(3, 5, 9, 16, 8, 16),
                    Block.box(2, 8, 8, 16, 14, 16)
            )),
            VoxelShapeUtils.horizontalShapes(Shapes.or(
                    Block.box(0, 0, 8.5, 7, 5, 12.5),
                    Block.box(0, 5, 0, 7, 14, 13),
                    Block.box(3, 9, 0, 10.5, 16, 10),
                    Block.box(0, 8, 10, 9, 16, 16)
            )),
            VoxelShapeUtils.horizontalShapes(Shapes.or(
                    Block.box(0, 5, 0, 16, 14, 13),
                    Block.box(0, 8, 10, 16, 16, 16)
            )),
            VoxelShapeUtils.horizontalShapes(Shapes.or(
                    Block.box(3, 0, 8.5, 10, 5, 12.5),
                    Block.box(3, 5, 0, 16, 8, 13),
                    Block.box(2, 8, 0, 16, 14, 14),
                    Block.box(6, 8, 10, 16, 16, 16)
            )),
            VoxelShapeUtils.horizontalShapes(Block.box(0, 0, 7, 13, 13, 16)),
            VoxelShapeUtils.horizontalShapes(Block.box(7, 0, 7, 15, 6, 16)),
            VoxelShapeUtils.horizontalShapes(Shapes.or(
                    Block.box(0, 0, 0, 13, 13, 10),
                    Block.box(0, 0, 10, 9, 14, 16)
            )),
            VoxelShapeUtils.horizontalShapes(Shapes.or(
                    Block.box(7, 0, 0, 15, 6, 10),
                    Block.box(8, 0, 10, 16, 14, 16),
                    Block.box(0, 0, 10, 8, 5, 16),
                    Block.box(4, 0, 10, 8, 10, 16)
            )),
            VoxelShapeUtils.horizontalShapes(Block.box(6, 0, 10, 16, 5, 16))
    };

    public RedVelvetChaiseLongueBlock(Properties properties) {
        super(properties, 11);

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.overrideBlockStateDefinition(builder);
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(partProperty, 1)
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
        BlockPos pos = context.getClickedPos();
        BlockState state = this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
        for (var part : getOrderedParts(pos, state)) {
            if (!level.getBlockState(part).canBeReplaced(context)) {
                return null;
            }
        }
        FluidState fluidState = level.getFluidState(pos);
        return state.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        List<BlockPos> parts = getOrderedParts(pos, state);
        for (int i = 0; i < parts.size(); i++) {
            if (i == 1) {
                continue;
            }
            FluidState fluidState = level.getFluidState(parts.get(i));
            level.setBlockAndUpdate(parts.get(i), state.setValue(partProperty, i)
                    .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER));
        }
    }

    @Override
    public List<BlockPos> getOrderedParts(BlockPos pos, BlockState state) {
        Direction facing = state.getValue(FACING);
        Direction opposite = facing.getOpposite();
        Direction left = facing.getClockWise();
        Direction right = facing.getCounterClockWise();
        BlockPos center = switch (state.getValue(partProperty)) {
            case 0 -> pos.relative(right);
            case 1 -> pos;
            case 2 -> pos.relative(left);
            case 3 -> pos.relative(right).relative(facing);
            case 4 -> pos.relative(facing);
            case 5 -> pos.relative(left).relative(facing);
            case 6 -> pos.relative(right).below();
            case 7 -> pos.below();
            case 8 -> pos.relative(right).relative(facing).below();
            case 9 -> pos.relative(facing).below();
            case 10 -> pos.relative(left).relative(facing).below();
            default -> BlockPos.ZERO;
        };
        return List.of(
                center.relative(left),
                center,
                center.relative(right),
                center.relative(left).relative(opposite),
                center.relative(opposite),
                center.relative(right).relative(opposite),
                center.relative(left).above(),
                center.above(),
                center.relative(left).relative(opposite).above(),
                center.relative(opposite).above(),
                center.relative(right).relative(opposite).above()
        );
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder pParams) {
        if (state.getValue(partProperty) != 1) {
            return Collections.emptyList();
        }
        return super.getDrops(state, pParams);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        int part = Mth.clamp(state.getValue(partProperty), 0, SHAPE_MAP.length - 1);
        return SHAPE_MAP[part].get(facing);
    }
}
