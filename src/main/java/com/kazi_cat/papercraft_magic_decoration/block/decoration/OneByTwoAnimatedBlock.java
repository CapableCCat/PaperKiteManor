package com.kazi_cat.papercraft_magic_decoration.block.decoration;

import com.kazi_cat.papercraft_magic_decoration.blockentity.AnimatedBlockEntity;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

@SuppressWarnings("deprecation")
public class OneByTwoAnimatedBlock extends SimpleAnimatedBlock {
    public static final IntegerProperty POSITION = IntegerProperty.create("position", 0, 1);
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    protected final EnumMap<Direction, VoxelShape> shapes1;

    public OneByTwoAnimatedBlock(Properties properties, VoxelShape rightShape, VoxelShape leftShape) {
        super(properties, rightShape);

        this.shapes1 = VoxelShapeUtils.horizontalShapes(leftShape);

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.createPositionBlockStateDefinition(builder);
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(POSITION, LEFT)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
    }

    protected void createPositionBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, POSITION);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        if (player.getItemInHand(hand).isEmpty() && state.getValue(POSITION) == RIGHT) {
            BlockPos leftPos = pos.relative(state.getValue(FACING).getClockWise());
            BlockState leftState = level.getBlockState(leftPos);
            if (leftState.is(state.getBlock()) && leftState.getValue(POSITION) == LEFT) {
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
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        int position = state.getValue(POSITION);
        Direction facing = state.getValue(FACING);

        if ((position == LEFT && direction == facing.getCounterClockWise())
                || (position == RIGHT && direction == facing.getClockWise())) {
            if (!neighborState.is(this) || neighborState.getValue(FACING) != facing || neighborState.getValue(POSITION) == position) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.isCreative() && state.getValue(POSITION) == RIGHT) {
            BlockPos leftPos = pos.relative(state.getValue(FACING).getClockWise());
            BlockState leftState = level.getBlockState(leftPos);
            if (leftState.is(state.getBlock()) && leftState.getValue(POSITION) == LEFT) {
                BlockState airBlockState = leftState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                level.setBlock(leftPos, airBlockState, Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_ALL);
                level.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, leftPos, Block.getId(leftState));
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos leftPos = context.getClickedPos();
        Direction facing = context.getHorizontalDirection();
        Level level = context.getLevel();
        BlockPos rightPos = leftPos.relative(facing.getOpposite().getCounterClockWise());
        if (level.getBlockState(rightPos).canBeReplaced(context)) {
            return super.getStateForPlacement(context);
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        Direction facing = state.getValue(FACING);
        BlockPos rightPos = pos.relative(facing.getCounterClockWise());
        BlockState rightState = state.setValue(POSITION, RIGHT);
        pLevel.setBlock(rightPos, rightState, Block.UPDATE_ALL);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        if (blockState.getValue(POSITION) == RIGHT) return null;
        return super.newBlockEntity(blockPos, blockState);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        if (state.getValue(POSITION) == RIGHT) {
            return Collections.emptyList();
        }
        return super.getDrops(state, params);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(POSITION) == LEFT ? shapes1.get(state.getValue(FACING)) : shapes.get(state.getValue(FACING));
    }
}
