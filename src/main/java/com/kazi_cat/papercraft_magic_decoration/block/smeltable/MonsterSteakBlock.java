package com.kazi_cat.papercraft_magic_decoration.block.smeltable;

import com.kazi_cat.papercraft_magic_decoration.api.block.SmeltableBlock;
import com.kazi_cat.papercraft_magic_decoration.block.base.MultipartBlock;
import com.kazi_cat.papercraft_magic_decoration.blockentity.SmeltableBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.datamap.data.SmeltableBlockData;
import com.kazi_cat.papercraft_magic_decoration.datamap.resources.SmeltableBlockDataReloadListener;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.utils.VoxelShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
public class MonsterSteakBlock extends MultipartBlock implements SmeltableBlock, SimpleWaterloggedBlock, EntityBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final EnumMap<Direction, VoxelShape>[] SHAPE_MAP = new EnumMap[] {
            VoxelShapeUtils.horizontalShapes(Shapes.or(
                    Block.box(0, 0, 0, 16, 2, 16),
                    Block.box(0, 1, 3, 13, 10, 16)
            )),
            VoxelShapeUtils.horizontalShapes(Shapes.or(
                    Block.box(0, 0, 0, 16, 2, 16),
                    Block.box(0, 2, 3, 16, 10, 16)
            )),
            VoxelShapeUtils.horizontalShapes(Shapes.or(
                    Block.box(0, 0, 0, 16, 2, 16),
                    Block.box(3, 2, 3, 16, 10, 16)
            )),
            VoxelShapeUtils.horizontalShapes(Shapes.or(
                    Block.box(0, 0, 0, 16, 2, 16),
                    Block.box(0, 2, 0, 13, 10, 13)
            )),
            VoxelShapeUtils.horizontalShapes(Shapes.or(
                    Block.box(0, 0, 0, 16, 2, 16),
                    Block.box(0, 2, 0, 16, 10, 13)
            )),
            VoxelShapeUtils.horizontalShapes(Shapes.or(
                    Block.box(0, 0, 0, 16, 2, 16),
                    Block.box(3, 2, 0, 16, 10, 8)
            ))
    };

    public MonsterSteakBlock(Properties properties) {
        super(properties, 6);

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.overrideBlockStateDefinition(builder);
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(partProperty, 1)
                .setValue(FACING, Direction.NORTH)
                .setValue(COOKED, false)
                .setValue(WATERLOGGED, false));
    }

    protected void overrideBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, COOKED, WATERLOGGED, partProperty);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite());

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
    public void handleRemove(Level level, BlockPos pos, BlockState state, @Nullable Player player) {
        if (!state.getValue(COOKED)) {
            super.handleRemove(level, pos, state, player);
        }
    }

    @Override
    public List<BlockPos> getOrderedParts(BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING).getOpposite();
        BlockPos center = switch (state.getValue(partProperty)) {
            case 0 -> pos.relative(direction.getClockWise());
            case 1 -> pos;
            case 2 -> pos.relative(direction.getCounterClockWise());
            case 3 -> pos.relative(direction.getClockWise()).relative(direction.getOpposite());
            case 4 -> pos.relative(direction.getOpposite());
            case 5 -> pos.relative(direction.getCounterClockWise()).relative(direction.getOpposite());
            default -> BlockPos.ZERO;
        };
        return List.of(
                center.relative(direction.getCounterClockWise()),
                center,
                center.relative(direction.getClockWise()),
                center.relative(direction.getCounterClockWise()).relative(direction),
                center.relative(direction),
                center.relative(direction.getClockWise()).relative(direction)
        );
    }

    @Override
    public boolean hasHeatSource(Level level, BlockState state, BlockPos pos) {
        for (var part : getOrderedParts(pos, state)) {
            if (!SmeltableBlock.super.hasHeatSource(level, level.getBlockState(part), part)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onCookFinished(Level level, BlockState state, BlockPos pos) {
        for (var part : getOrderedParts(pos, state)) {
            SmeltableBlock.super.onCookFinished(level, level.getBlockState(part), part);
        }
    }

    @Override
    @Nullable
    public BlockEntity getBlockEntity(Level level, BlockState state, BlockPos pos) {
        Direction direction = state.getValue(FACING).getOpposite();
        BlockPos center = switch (state.getValue(partProperty)) {
            case 0 -> pos.relative(direction.getClockWise());
            case 1 -> pos;
            case 2 -> pos.relative(direction.getCounterClockWise());
            case 3 -> pos.relative(direction.getClockWise()).relative(direction.getOpposite());
            case 4 -> pos.relative(direction.getOpposite());
            case 5 -> pos.relative(direction.getCounterClockWise()).relative(direction.getOpposite());
            default -> BlockPos.ZERO;
        };
        return level.getBlockEntity(center);
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
            return new SmeltableBlockEntity(pos, state);
        }
        return null;
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (state.getValue(COOKED)) {
            return null;
        }
        return createTickerHelper(blockEntityType, ModBlocks.SMELTABLE_BE.get(),
                (levelIn, blockPos, blockState, smeltable) -> smeltable.tick(levelIn));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        int count = Mth.clamp(state.getValue(partProperty), 0, SHAPE_MAP.length - 1);
        return SHAPE_MAP[count].get(state.getValue(FACING));
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        if (state.getValue(partProperty) == 1 || state.getValue(COOKED)) {
            return super.getDrops(state, params);
        }
        return Collections.emptyList();
    }
}
