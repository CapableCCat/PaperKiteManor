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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

@SuppressWarnings("deprecation")
public class TwoByOneBlock extends HorizontalDirectionalBlock {
    public static final IntegerProperty POSITION = IntegerProperty.create("position", 0, 1);
    public static final int FRONT = 0;
    public static final int BEHIND = 1;
    protected final EnumMap<Direction, VoxelShape> frontShapes;
    protected final EnumMap<Direction, VoxelShape> behindShapes;

    public TwoByOneBlock(Properties properties, VoxelShape frontShape, VoxelShape behindShape) {
        super(properties);
        this.frontShapes = VoxelShapeUtils.horizontalShapes(frontShape);
        this.behindShapes = VoxelShapeUtils.horizontalShapes(behindShape);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(POSITION, FRONT)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POSITION);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos frontPos = context.getClickedPos();
        Direction facing = context.getHorizontalDirection();
        Level level = context.getLevel();
        BlockPos behindPos = frontPos.relative(facing);
        if (level.getBlockState(behindPos).canBeReplaced(context)) {
            return defaultBlockState().setValue(FACING, facing.getOpposite());
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        Direction facing = pState.getValue(FACING);
        BlockPos behindPos = pPos.relative(facing.getOpposite());
        BlockState behindState = pState.setValue(POSITION, BEHIND);
        pLevel.setBlock(behindPos, behindState, Block.UPDATE_ALL);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        int position = state.getValue(POSITION);
        Direction facing = state.getValue(FACING);

        if ((position == FRONT && direction == facing.getOpposite())
                || (position == BEHIND && direction == facing)) {
            if (!neighborState.is(this) || neighborState.getValue(FACING) != facing || neighborState.getValue(POSITION) == position) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.isCreative() && state.getValue(POSITION) == BEHIND) {
            BlockPos front = pos.relative(state.getValue(FACING));
            BlockState frontState = level.getBlockState(front);
            if (frontState.is(state.getBlock()) && frontState.getValue(POSITION) == FRONT) {
                BlockState airBlockState = frontState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                level.setBlock(front, airBlockState, Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_ALL);
                level.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, front, Block.getId(frontState));
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder pParams) {
        if (state.getValue(POSITION) == BEHIND) {
            return Collections.emptyList();
        }
        return super.getDrops(state, pParams);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        return state.getValue(POSITION) == FRONT ? frontShapes.get(direction) : behindShapes.get(direction);
    }
}
