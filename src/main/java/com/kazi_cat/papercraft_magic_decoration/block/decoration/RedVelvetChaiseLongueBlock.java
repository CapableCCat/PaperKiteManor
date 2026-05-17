package com.kazi_cat.papercraft_magic_decoration.block.decoration;

import com.kazi_cat.papercraft_magic_decoration.utils.VoxelShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
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

@SuppressWarnings("deprecation")
public class RedVelvetChaiseLongueBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 10);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final EnumMap<Direction, VoxelShape> SHAPE_0 = VoxelShapeUtils.horizontalShapes(Shapes.or(
            Block.box(0, 0, 9.5, 7, 5, 13.5),
            Block.box(0, 5, 9, 7, 8, 16),
            Block.box(0, 8, 8, 7, 14, 16),
            Block.box(3, 9, 7, 10.5, 16, 16)
    ));
    private static final EnumMap<Direction, VoxelShape> SHAPE_1 = VoxelShapeUtils.horizontalShapes(Shapes.or(
            Block.box(0, 5, 9, 16, 8, 16),
            Block.box(0, 8, 8, 16, 14, 16),
            Block.box(7, 14, 7, 15, 16, 16)
    ));
    private static final EnumMap<Direction, VoxelShape> SHAPE_2 = VoxelShapeUtils.horizontalShapes(Shapes.or(
            Block.box(3, 0, 9.5, 9, 5, 13.5),
            Block.box(3, 5, 9, 16, 8, 16),
            Block.box(2, 8, 8, 16, 14, 16)
    ));
    private static final EnumMap<Direction, VoxelShape> SHAPE_3 = VoxelShapeUtils.horizontalShapes(Shapes.or(
            Block.box(0, 0, 8.5, 7, 5, 12.5),
            Block.box(0, 5, 0, 7, 14, 13),
            Block.box(3, 9, 0, 10.5, 16, 10),
            Block.box(0, 8, 10, 9, 16, 16)
    ));
    private static final EnumMap<Direction, VoxelShape> SHAPE_4 = VoxelShapeUtils.horizontalShapes(Shapes.or(
            Block.box(0, 5, 0, 16, 14, 13),
            Block.box(0, 8, 10, 16, 16, 16)
    ));
    private static final EnumMap<Direction, VoxelShape> SHAPE_5 = VoxelShapeUtils.horizontalShapes(Shapes.or(
            Block.box(3, 0, 8.5, 10, 5, 12.5),
            Block.box(3, 5, 0, 16, 8, 13),
            Block.box(2, 8, 0, 16, 14, 14),
            Block.box(6, 8, 10, 16, 16, 16)
    ));
    private static final EnumMap<Direction, VoxelShape> SHAPE_6 = VoxelShapeUtils.horizontalShapes(Block.box(0, 0, 7, 13, 13, 16));
    private static final EnumMap<Direction, VoxelShape> SHAPE_7 = VoxelShapeUtils.horizontalShapes(Block.box(7, 0, 7, 15, 6, 16));
    private static final EnumMap<Direction, VoxelShape> SHAPE_8 = VoxelShapeUtils.horizontalShapes(Shapes.or(
            Block.box(0, 0, 0, 13, 13, 10),
            Block.box(0, 0, 10, 9, 14, 16)
    ));
    private static final EnumMap<Direction, VoxelShape> SHAPE_9 = VoxelShapeUtils.horizontalShapes(Shapes.or(
            Block.box(7, 0, 0, 15, 6, 10),
            Block.box(8, 0, 10, 16, 14, 16),
            Block.box(0, 0, 10, 8, 5, 16),
            Block.box(4, 0, 10, 8, 10, 16)
    ));
    private static final EnumMap<Direction, VoxelShape> SHAPE_10 = VoxelShapeUtils.horizontalShapes(Block.box(6, 0, 10, 16, 5, 16));

    public RedVelvetChaiseLongueBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(PART, 1)
                .setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART, WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos center = context.getClickedPos();
        Direction facing = context.getHorizontalDirection().getOpposite();
        for (var pos : getOrderedPosList(center, facing)) {
            if (!level.getBlockState(pos).canBeReplaced(context)) {
                return null;
            }
        }
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos center, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        List<BlockPos> ordered = getOrderedPosList(center, state);
        for (int i = 0; i < 11; i++) {
            if (i == 1) continue;
            FluidState fluidState = level.getFluidState(ordered.get(i));
            level.setBlockAndUpdate(ordered.get(i), state
                    .setValue(PART, i)
                    .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER));
        }
    }

    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        handleRemove(world, pos, state, player);
        super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public void onBlockExploded(BlockState state, Level world, BlockPos pos, Explosion explosion) {
        handleRemove(world, pos, state, null);
        super.onBlockExploded(state, world, pos, explosion);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return switch (state.getValue(PART)) {
            case 0 -> SHAPE_0.get(direction);
            case 1 -> SHAPE_1.get(direction);
            case 2 -> SHAPE_2.get(direction);
            case 3 -> SHAPE_3.get(direction);
            case 4 -> SHAPE_4.get(direction);
            case 5 -> SHAPE_5.get(direction);
            case 6 -> SHAPE_6.get(direction);
            case 7 -> SHAPE_7.get(direction);
            case 8 -> SHAPE_8.get(direction);
            case 9 -> SHAPE_9.get(direction);
            case 10 -> SHAPE_10.get(direction);
            default -> Shapes.empty();
        };
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        if (state.getValue(PART) != 1) {
            return Collections.emptyList();
        }
        return super.getDrops(state, params);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }

    private static void handleRemove(Level level, BlockPos pos, BlockState state, @Nullable Player player) {
        BlockPos center = getCenter(pos, state);
        boolean drop = !(player != null && player.isCreative());
        for (var p : getOrderedPosList(center, level.getBlockState(center))) {
            level.destroyBlock(p, drop);
        }
    }

    public static BlockPos getCenter(BlockPos pos, BlockState state) {
        Direction front = state.getValue(FACING);
        Direction left = front.getClockWise();
        Direction right = front.getCounterClockWise();
        return switch (state.getValue(PART)) {
            case 0 -> pos.relative(right);
            case 1 -> pos;
            case 2 -> pos.relative(left);
            case 3 -> pos.relative(right).relative(front);
            case 4 -> pos.relative(front);
            case 5 -> pos.relative(left).relative(front);
            case 6 -> pos.relative(right).below();
            case 7 -> pos.below();
            case 8 -> pos.relative(right).relative(front).below();
            case 9 -> pos.relative(front).below();
            case 10 -> pos.relative(left).relative(front).below();
            default -> BlockPos.ZERO;
        };
    }

    public static List<BlockPos> getOrderedPosList(BlockPos center, BlockState state) {
        return getOrderedPosList(center, state.getValue(FACING));
    }

    public static List<BlockPos> getOrderedPosList(BlockPos center, Direction front) {
        Direction left = front.getClockWise();
        Direction right = front.getCounterClockWise();
        Direction behind = front.getOpposite();
        return List.of(
                center.relative(left),
                center,
                center.relative(right),
                center.relative(left).relative(behind),
                center.relative(behind),
                center.relative(right).relative(behind),
                center.relative(left).above(),
                center.above(),
                center.relative(left).relative(behind).above(),
                center.relative(behind).above(),
                center.relative(right).relative(behind).above()
        );
    }
}
