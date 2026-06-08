package com.kazi_cat.papercraft_magic_decoration.block.smeltable;

import com.kazi_cat.papercraft_magic_decoration.api.block.ICustomRenderBoundingBox;
import com.kazi_cat.papercraft_magic_decoration.api.block.SmeltableBlock;
import com.kazi_cat.papercraft_magic_decoration.block.base.MultipartBlock;
import com.kazi_cat.papercraft_magic_decoration.blockentity.AnimatedSmeltableBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.blockentity.SmeltableBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.datamap.data.SmeltableBlockData;
import com.kazi_cat.papercraft_magic_decoration.datamap.resources.SmeltableBlockDataReloadListener;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
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
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

@SuppressWarnings("deprecation")
public class SausageMaceWeaponBlock extends MultipartBlock implements SmeltableBlock, SimpleWaterloggedBlock, EntityBlock, ICustomRenderBoundingBox {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<AttachFace> FACE = BlockStateProperties.ATTACH_FACE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final EnumMap<Direction, VoxelShape> SHAPE0 = VoxelShapeUtils.horizontalShapes(Block.box(6, 6, 0, 10, 10, 16));
    private static final EnumMap<Direction, VoxelShape> SHAPE1 = VoxelShapeUtils.horizontalShapes(Block.box(1, 1, 0, 15, 15, 16));
    private static final EnumMap<Direction, VoxelShape> SHAPE2 = VoxelShapeUtils.horizontalShapes(Block.box(1, 1, 0, 15, 15, 16));
    private static final EnumMap<Direction, VoxelShape> SHAPE3 = VoxelShapeUtils.horizontalShapes(Block.box(1, 1, 0, 15, 15, 8));
    private static final EnumMap<Direction, VoxelShape> SHAPE0Y = VoxelShapeUtils.horizontalShapes(Block.box(6, 0, 6, 10, 16, 10));
    private static final EnumMap<Direction, VoxelShape> SHAPE1Y = VoxelShapeUtils.horizontalShapes(Block.box(1, 0, 1, 15, 16, 15));
    private static final EnumMap<Direction, VoxelShape> SHAPE2Y = VoxelShapeUtils.horizontalShapes(Block.box(1, 0, 1, 15, 16, 15));
    private static final EnumMap<Direction, VoxelShape> SHAPE3F = VoxelShapeUtils.horizontalShapes(Block.box(1, 0, 1, 15, 8, 15));
    private static final EnumMap<Direction, VoxelShape> SHAPE3C = VoxelShapeUtils.horizontalShapes(Block.box(1, 8, 1, 15, 16, 15));

    public SausageMaceWeaponBlock(Properties properties) {
        super(properties, 4);

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.overrideBlockStateDefinition(builder);
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(partProperty, 0)
                .setValue(FACING, Direction.NORTH)
                .setValue(COOKED, false)
                .setValue(WATERLOGGED, false)
                .setValue(FACE, AttachFace.WALL));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        ItemStack itemInHand = player.getItemInHand(hand);
        if (hand != InteractionHand.MAIN_HAND || !itemInHand.isEmpty() || state.getValue(FACE) != AttachFace.WALL) {
            return InteractionResult.PASS;
        }

        Direction facing = state.getValue(FACING).getOpposite();
        BlockPos center = switch (state.getValue(partProperty)) {
            case 0 -> pos.relative(facing);
            case 1 -> pos;
            case 2 -> pos.relative(facing.getOpposite());
            case 3 -> pos.relative(facing.getOpposite(), 2);
            default -> BlockPos.ZERO;
        };
        if (level.getBlockEntity(center) instanceof SmeltableBlockEntity smeltableBE) {
            return smeltableBE.flip(level, player) ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }

        return InteractionResult.PASS;
    }

    protected void overrideBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, FACE, COOKED, WATERLOGGED, partProperty);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = this.defaultBlockState();
        Direction face = context.getClickedFace();
        Direction nearest = context.getNearestLookingDirection();
        boolean isVertical = face.getAxis() == Direction.Axis.Y;

        Direction facing = isVertical ? context.getHorizontalDirection().getOpposite() : face.getOpposite();
        state = state.setValue(FACING, facing);

        if (isVertical && nearest == face.getOpposite()) {
            state = state.setValue(FACE, face == Direction.UP ? AttachFace.FLOOR : AttachFace.CEILING);
        }

        for (BlockPos part : getOrderedParts(pos, state)) {
            if (!level.getBlockState(part).canBeReplaced(context)) {
                return null;
            }
        }

        FluidState fluidState = level.getFluidState(pos);
        SmeltableBlockData data = SmeltableBlockDataReloadListener.INSTANCE.getOrDefault(this, null);
        boolean cooked = data != null && context.getItemInHand().is(data.result());
        return state.setValue(COOKED, cooked)
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        List<BlockPos> parts = getOrderedParts(pos, state);
        for (int i = 1; i < parts.size(); i++) {
            FluidState fluidState = level.getFluidState(parts.get(i));
            level.setBlockAndUpdate(parts.get(i), state.setValue(partProperty, i)
                    .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER));
        }
    }

    @Override
    public List<BlockPos> getOrderedParts(BlockPos pos, BlockState state) {
        Direction facing = state.getValue(FACING);
        AttachFace face = state.getValue(FACE);

        Direction axis = switch (face) {
            case WALL -> facing.getOpposite();
            case FLOOR -> Direction.UP;
            case CEILING -> Direction.DOWN;
        };

        BlockPos start = switch (state.getValue(partProperty)) {
            case 0 -> pos;
            case 1 -> pos.relative(axis.getOpposite());
            case 2 -> pos.relative(axis.getOpposite(), 2);
            case 3 -> pos.relative(axis.getOpposite(), 3);
            default -> BlockPos.ZERO;
        };

        return List.of(
                start,
                start.relative(axis),
                start.relative(axis, 2),
                start.relative(axis, 3)
        );
    }

    @Override
    public boolean hasLitSource(Level level, BlockState state, BlockPos pos) {
        Direction facing = state.getValue(FACING).getOpposite();
        BlockPos pos1 = pos.relative(facing);
        BlockState state1 = level.getBlockState(pos1);
        return state.getValue(FACE) == AttachFace.WALL
                && SmeltableBlock.super.hasLitSource(level, state, pos)
                && SmeltableBlock.super.hasLitSource(level, state1, pos1);
    }

    @Override
    public void onCookFinished(Level level, BlockState state, BlockPos pos) {
        for (var part : getOrderedParts(pos, state)) {
            SmeltableBlock.super.onCookFinished(level, level.getBlockState(part), part);
        }
    }

    @Nullable
    @SuppressWarnings("all")
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(
            BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker) {
        return clientType == serverType ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        if (state.getValue(partProperty) == 1) {
            return new AnimatedSmeltableBlockEntity(pos, state);
        }
        return null;
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (state.getValue(COOKED) || state.getValue(FACE) != AttachFace.WALL) {
            return null;
        }
        return createTickerHelper(blockEntityType, ModBlocks.ANIMATED_SMELTABLE_BE.get(),
                (levelIn, blockPos, blockState, smeltable) -> smeltable.tick(levelIn));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction facing = state.getValue(FACING);
        AttachFace face = state.getValue(FACE);
        return switch (state.getValue(partProperty)) {
            case 0 -> switch (face) {
                case WALL -> SHAPE0.get(facing);
                case FLOOR, CEILING -> SHAPE0Y.get(facing);
            };
            case 1 -> switch (face) {
                case WALL -> SHAPE1.get(facing);
                case FLOOR, CEILING -> SHAPE1Y.get(facing);
            };
            case 2 -> switch (face) {
                case WALL -> SHAPE2.get(facing);
                case FLOOR, CEILING -> SHAPE2Y.get(facing);
            };
            case 3 -> switch (face) {
                case WALL -> SHAPE3.get(facing);
                case FLOOR -> SHAPE3F.get(facing);
                case CEILING -> SHAPE3C.get(facing);
            };
            default -> Shapes.empty();
        };
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        if (state.getValue(partProperty) != 1) {
            return Collections.emptyList();
        }
        return super.getDrops(state, params);
    }

    @Override
    public AABB getRenderBoundingBox(BlockState state, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        AttachFace face = state.getValue(FACE);

        Direction axis = switch (face) {
            case WALL -> facing.getOpposite();
            case FLOOR -> Direction.UP;
            case CEILING -> Direction.DOWN;
        };

        return AABBUtils.fromTo(pos.relative(axis.getOpposite()), pos.relative(axis, 2));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
