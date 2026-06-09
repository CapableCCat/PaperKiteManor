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
import net.minecraft.world.entity.LivingEntity;
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

@SuppressWarnings("deprecation")
public class TwoByOneSmeltableBlock extends MultipartBlock implements SmeltableBlock, SimpleWaterloggedBlock, EntityBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected final EnumMap<Direction, VoxelShape> shapesFront;
    protected final EnumMap<Direction, VoxelShape> shapesBehind;

    public TwoByOneSmeltableBlock(Properties properties, VoxelShape shapeFront, VoxelShape shapeBehind) {
        super(properties, 2);
        this.shapesFront = VoxelShapeUtils.horizontalShapes(shapeFront);
        this.shapesBehind = VoxelShapeUtils.horizontalShapes(shapeBehind);

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.overrideBlockStateDefinition(builder);
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(partProperty, 0)
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
        BlockPos front = context.getClickedPos();
        BlockPos behind = front.relative(context.getHorizontalDirection());
        if (!level.getBlockState(front).canBeReplaced(context) || !level.getBlockState(behind).canBeReplaced(context)) {
            return null;
        }
        FluidState fluidState = level.getFluidState(front);
        SmeltableBlockData data = SmeltableBlockDataReloadListener.INSTANCE.getOrDefault(this, null);
        boolean cooked = data != null && context.getItemInHand().is(data.result());
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(COOKED, cooked)
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        BlockPos behind = pos.relative(state.getValue(FACING).getOpposite());
        FluidState fluidState = level.getFluidState(behind);
        level.setBlockAndUpdate(behind, state
                .setValue(partProperty, 1)
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER));
    }

    @Override
    public List<BlockPos> getOrderedParts(BlockPos pos, BlockState state) {
        Direction facing = state.getValue(FACING);
        return switch (state.getValue(partProperty)) {
            case 0 -> List.of(pos, pos.relative(facing.getOpposite()));
            case 1 -> List.of(pos.relative(facing), pos);
            default -> List.of();
        };
    }

    @Override
    public boolean hasLitSource(Level level, BlockState state, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        BlockPos behind = pos.relative(facing.getOpposite());
        BlockState stateBehind = level.getBlockState(behind);
        return  SmeltableBlock.super.hasLitSource(level, state, pos)
                && SmeltableBlock.super.hasLitSource(level, stateBehind, behind);
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
        if (state.getValue(partProperty) == 0) {
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
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder pParams) {
        if (state.getValue(partProperty) != 0) {
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
        return switch (state.getValue(partProperty)) {
            case 0 -> shapesFront.get(facing);
            case 1 -> shapesBehind.get(facing);
            default -> Shapes.empty();
        };
    }
}
