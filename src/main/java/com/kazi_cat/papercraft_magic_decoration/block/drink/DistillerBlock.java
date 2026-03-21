package com.kazi_cat.papercraft_magic_decoration.block.drink;

import com.kazi_cat.papercraft_magic_decoration.blockentity.drink.DistillerBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
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
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

@SuppressWarnings("deprecation")
public class DistillerBlock extends HorizontalDirectionalBlock implements EntityBlock, SimpleWaterloggedBlock {
    public static final IntegerProperty STATUS = IntegerProperty.create("status", 0, 3);
    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 3);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final int LEFT_DOWN = 0;
    public static final int RIGHT_DOWN = 1;
    public static final int LEFT_UP = 2;
    public static final int RIGHT_UP = 3;

    public static final EnumMap<Direction, VoxelShape> LEFT_DOWN_SHAPE = VoxelShapeUtils.horizontalShapes(Shapes.or(
            Block.box(1, 0, 1, 15, 16, 15),
            Block.box(0, 0, 0, 16, 2, 16)
    ));
    public static final EnumMap<Direction, VoxelShape> RIGHT_DOWN_SHAPE = VoxelShapeUtils.horizontalShapes(Shapes.or(
            Block.box(0, 0, 0, 16, 2, 16),
            Block.box(1, 2, 2, 13, 4, 14)
    ));
    public static final EnumMap<Direction, VoxelShape> LEFT_UP_SHAPE = VoxelShapeUtils.horizontalShapes(
            Block.box(2, 0, 2, 13, 16, 14)
    );
    public static final EnumMap<Direction, VoxelShape> RIGHT_UP_SHAPE = VoxelShapeUtils.horizontalShapes(
            Block.box(4, 7, 5, 16, 13, 11)
    );

    public DistillerBlock(Properties properties) {
        super(properties);
    }

    public DistillerBlock() {
        this(Properties.of().noOcclusion());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, STATUS, PART, WATERLOGGED);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        Direction right = context.getHorizontalDirection().getOpposite().getCounterClockWise();
        if (getOrderedPos(context.getClickedPos(), right).stream().anyMatch(pos -> !level.getBlockState(pos).canBeReplaced(context))) {
            return null;
        }

        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(STATUS, 0)
                .setValue(PART, LEFT_DOWN)
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        Direction right = state.getValue(FACING).getCounterClockWise();
        List<BlockPos> posList = getOrderedPos(pos, right);
        for (int i = 0; i < 4; i++) {
            if (i == LEFT_DOWN) continue;
            level.setBlockAndUpdate(posList.get(i), state.setValue(PART, i));
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (hand == InteractionHand.OFF_HAND) {
            return InteractionResult.PASS;
        }

        Direction left = state.getValue(FACING).getClockWise();
        BlockEntity blockEntity = switch (state.getValue(PART)) {
            case LEFT_DOWN -> level.getBlockEntity(pos);
            case RIGHT_DOWN -> level.getBlockEntity(pos.relative(left));
            case LEFT_UP -> level.getBlockEntity(pos.below());
            case RIGHT_UP -> level.getBlockEntity(pos.below().relative(left));
            default -> null;
        };

        if (!(blockEntity instanceof DistillerBlockEntity distiller)) {
            return InteractionResult.PASS;
        }

        ItemStack itemInHand = player.getItemInHand(hand);
        switch (state.getValue(PART)) {
            case LEFT_DOWN -> {
                if (!player.isSecondaryUseActive() && !itemInHand.isEmpty() && distiller.startDistilling(level, player, itemInHand)) {
                    return InteractionResult.SUCCESS;
                }
            }
            case LEFT_UP -> {
                if (player.isSecondaryUseActive()) {
                    return InteractionResult.PASS;
                }

                if (itemInHand.isEmpty()) {
                    if (distiller.removeIngredient(level, player)) {
                        return InteractionResult.SUCCESS;
                    }
                } else {
                    if (distiller.addIngredient(level, player, itemInHand)) {
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            case RIGHT_DOWN, RIGHT_UP -> {
                if (player.isSecondaryUseActive()) {
                    return InteractionResult.PASS;
                }

                if (itemInHand.isEmpty()) {
                    if (distiller.takeOutResult(level, player)) {
                        return InteractionResult.SUCCESS;
                    }

                    if (distiller.removeWineBase(level, player)) {
                        return InteractionResult.SUCCESS;
                    }
                } else {
                    if (distiller.addWineBase(level, player, itemInHand)) {
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        int part = state.getValue(PART);
        Direction facing = state.getValue(FACING);
        Direction left = facing.getClockWise();
        Direction right = facing.getCounterClockWise();

        if (part == LEFT_DOWN && (direction == right || direction == Direction.UP)
        || part == RIGHT_DOWN && (direction == left || direction == Direction.UP)
        || part == LEFT_UP && (direction == right || direction == Direction.DOWN)
        || part == RIGHT_UP && (direction == left || direction == Direction.DOWN)) {
            if (!neighborState.is(this) || neighborState.getValue(FACING) != facing || neighborState.getValue(PART) == part) {
                return Blocks.AIR.defaultBlockState();
            }
            int neighborStatus = neighborState.getValue(STATUS);
            if (neighborStatus != state.getValue(STATUS)) {
                return state.setValue(STATUS, neighborStatus);
            }
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    private static List<BlockPos> getOrderedPos(BlockPos leftDown, Direction right) {
        List<BlockPos> ans = new ArrayList<>();
        ans.add(leftDown);
        ans.add(leftDown.relative(right));
        BlockPos leftUp = leftDown.above();
        ans.add(leftUp);
        ans.add(leftUp.relative(right));
        return ans;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        if (state.getValue(PART) != LEFT_DOWN) return null;
        return new DistillerBlockEntity(pos, state);
    }

    @Nullable
    @SuppressWarnings("all")
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(
            BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker) {
        return clientType == serverType ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (state.getValue(PART) != LEFT_DOWN) return null;
        if (state.getValue(STATUS) != 2) return null;
        return createTickerHelper(blockEntityType, ModBlocks.DISTILLER_BE.get(),
                (levelIn, blockPos, blockState, distiller) -> distiller.tick(levelIn));
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        int part = state.getValue(PART);
        if (!level.isClientSide && player.isCreative() && part != LEFT_DOWN) {
            Direction right = state.getValue(FACING).getCounterClockWise();
            BlockPos leftDownPos = switch (part) {
                case RIGHT_DOWN -> pos.relative(right);
                case LEFT_UP -> pos.above();
                case RIGHT_UP -> pos.above().relative(right);
                default -> pos;
            };
            BlockState leftDownState = level.getBlockState(leftDownPos);
            if (leftDownState.is(state.getBlock()) && leftDownState.getValue(PART) == LEFT_DOWN) {
                BlockState airBlockState = leftDownState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                level.setBlock(leftDownPos, airBlockState, Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_ALL);
                level.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, leftDownPos, Block.getId(leftDownState));
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        if (state.getValue(PART) != LEFT_DOWN) {
            return Collections.emptyList();
        }
        return super.getDrops(state, params);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(PART)) {
            case LEFT_DOWN -> LEFT_DOWN_SHAPE.get(state.getValue(FACING));
            case RIGHT_DOWN -> RIGHT_DOWN_SHAPE.get(state.getValue(FACING));
            case LEFT_UP -> LEFT_UP_SHAPE.get(state.getValue(FACING));
            case RIGHT_UP -> RIGHT_UP_SHAPE.get(state.getValue(FACING));
            default -> super.getShape(state, level, pos, context);
        };
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        if (state.getValue(PART) == LEFT_DOWN && state.getValue(STATUS) == 2) {
            return 13;
        }

        return 0;
    }
}
