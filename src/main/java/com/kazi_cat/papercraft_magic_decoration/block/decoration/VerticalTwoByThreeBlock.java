package com.kazi_cat.papercraft_magic_decoration.block.decoration;

import com.kazi_cat.papercraft_magic_decoration.blockentity.decoration.AnimatedBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.utils.VoxelShapeUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

@SuppressWarnings("deprecation")
public class VerticalTwoByThreeBlock extends SimpleDecorationBlock {
    public static final IntegerProperty POSITION = IntegerProperty.create("position", 0, 5);
    public static final int LEFT_DOWN = 0;
    public static final int CENTER_DOWN = 1;
    public static final int RIGHT_DOWN = 2;
    public static final int LEFT_UP = 3;
    public static final int CENTER_UP = 4;
    public static final int RIGHT_UP = 5;
    protected final Int2ObjectArrayMap<EnumMap<Direction, VoxelShape>> shapeMap;

    public VerticalTwoByThreeBlock(Properties properties, VoxelShape... shapes) {
        super(properties, shapes[0]);

        shapeMap = new Int2ObjectArrayMap<>();
        if (shapes.length == 1) {
            for (int i = 0; i < 6; i++) {
                shapeMap.put(i, VoxelShapeUtils.horizontalShapes(shapes[0]));
            }
        } else {
            for (int i = 0; i < 6; i++) {
                shapeMap.put(i, VoxelShapeUtils.horizontalShapes(shapes[i]));
            }
        }

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.createPositionBlockStateDefinition(builder);
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(POSITION, CENTER_DOWN)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
    }

    protected void createPositionBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, POSITION);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        Direction facing = context.getHorizontalDirection().getOpposite();
        BlockPos leftDown = context.getClickedPos().relative(facing.getClockWise());
        if (getOrderedPosList(leftDown, facing.getCounterClockWise()).stream().anyMatch(pos -> !level.getBlockState(pos).canBeReplaced(context))) {
            return null;
        }

        return super.getStateForPlacement(context);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        Direction facing = state.getValue(FACING);
        BlockPos leftDown = pos.relative(facing.getClockWise());
        List<BlockPos> posList = getOrderedPosList(leftDown, facing.getCounterClockWise());
        for (int i = 0; i < 6; i++) {
            if (i == CENTER_DOWN) continue;
            level.setBlockAndUpdate(posList.get(i), state.setValue(POSITION, i));
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        int position = state.getValue(POSITION);
        Direction facing = state.getValue(FACING);
        Direction left = facing.getClockWise();
        Direction right = facing.getCounterClockWise();

        if (player.getItemInHand(hand).isEmpty() && position != CENTER_DOWN) {
            BlockPos centerPos = switch (position) {
                case LEFT_DOWN -> pos.relative(right);
                case RIGHT_DOWN -> pos.relative(left);
                case LEFT_UP -> pos.relative(right).below();
                case CENTER_UP -> pos.below();
                case RIGHT_UP -> pos.relative(left).below();
                default -> pos;
            };
            BlockState centerState = level.getBlockState(centerPos);
            if (centerState.is(state.getBlock()) && centerState.getValue(POSITION) == CENTER_DOWN) {
                if (level.getBlockEntity(centerPos) instanceof AnimatedBlockEntity animated) {
                    animated.triggerAnim();
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.FAIL;
        }

        return super.use(state, level, pos, player, hand, hitResult);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        int position = state.getValue(POSITION);
        Direction facing = state.getValue(FACING);
        Direction left = facing.getClockWise();
        Direction right = facing.getCounterClockWise();

        if ((position == LEFT_DOWN && (direction == right || direction == Direction.UP))
                || (position == CENTER_DOWN && (direction == right || direction == left || direction == Direction.UP))
                || (position == RIGHT_DOWN && (direction == left || direction == Direction.UP))
                || (position == LEFT_UP && (direction == right || direction == Direction.DOWN))
                || (position == CENTER_UP && (direction == left || direction == right || direction == Direction.DOWN))
                || (position == RIGHT_UP && (direction == left || direction == Direction.DOWN))) {
            if (!neighborState.is(this) || neighborState.getValue(FACING) != facing || neighborState.getValue(POSITION) == position) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        int position = state.getValue(POSITION);
        Direction facing = state.getValue(FACING);
        Direction left = facing.getClockWise();
        Direction right = facing.getCounterClockWise();

        if (!level.isClientSide && player.isCreative() && position != CENTER_DOWN) {
            BlockPos centerPos = switch (position) {
                case LEFT_DOWN -> pos.relative(right);
                case RIGHT_DOWN -> pos.relative(left);
                case LEFT_UP -> pos.relative(right).below();
                case CENTER_UP -> pos.below();
                case RIGHT_UP -> pos.relative(left).below();
                default -> pos;
            };
            BlockState centerState = level.getBlockState(centerPos);
            if (centerState.is(state.getBlock()) && centerState.getValue(POSITION) == CENTER_DOWN) {
                BlockState airBlockState = centerState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                level.setBlock(centerPos, airBlockState, Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_ALL);
                level.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, centerPos, Block.getId(centerState));
            }
        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        if (state.getValue(POSITION) != CENTER_DOWN) {
            return Collections.emptyList();
        }
        return super.getDrops(state, params);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shapeMap.get(state.getValue(POSITION)).get(state.getValue(FACING));
    }

    public List<BlockPos> getOrderedPosList(BlockPos leftDown, Direction right) {
        List<BlockPos> ans = new ArrayList<>();
        BlockPos leftUp = leftDown.above();
        for (int i = 0; i < 3; i++) {
            ans.add(leftDown);
            leftDown = leftDown.relative(right);
        }
        for (int i = 0; i < 3; i++) {
            ans.add(leftUp);
            leftUp = leftUp.relative(right);
        }
        return ans;
    }
}
