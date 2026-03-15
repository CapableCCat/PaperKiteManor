package com.kazi_cat.papercraft_magic_decoration.block.food;

import com.kazi_cat.papercraft_magic_decoration.blockentity.SmeltableBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.tag.TagMod;
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
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class TwoByOneSmeltableBlock extends SmeltableBlock {
    public static final IntegerProperty POSITION = IntegerProperty.create("position", 0, 1);
    public static final int FRONT = 0;
    public static final int BEHIND = 1;
    protected final EnumMap<Direction, VoxelShape> behindShapes;

    public TwoByOneSmeltableBlock(Properties properties, VoxelShape frontShape, VoxelShape behindShape, int cookingTime, int requiredFlips,
                                  int flipCooldown, Supplier<ItemStack> ingredient, Supplier<ItemStack> result) {
        super(properties, frontShape, cookingTime, requiredFlips, flipCooldown, ingredient, result);
        this.behindShapes = VoxelShapeUtils.horizontalShapes(behindShape);

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.createPositionBlockStateDefinition(builder);
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(POSITION, FRONT)
                .setValue(FACING, Direction.NORTH)
                .setValue(COOKED, false));
    }

    protected void createPositionBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, COOKED, POSITION);
    }

    @Override
    public boolean hasLitSource(Level level, BlockState state, BlockPos pos) {
        BlockState frontBelow = level.getBlockState(pos.below());
        Direction direction = state.getValue(FACING).getOpposite();
        BlockState behindBelow = level.getBlockState(pos.relative(direction).below());

        boolean front = frontBelow.is(TagMod.HEAT_SOURCE_WITHOUT_LIT);
        if (frontBelow.hasProperty(BlockStateProperties.LIT)) {
            front = frontBelow.getValue(BlockStateProperties.LIT);
        }
        boolean behind = behindBelow.is(TagMod.HEAT_SOURCE_WITHOUT_LIT);
        if (behindBelow.hasProperty(BlockStateProperties.LIT)) {
            behind = behindBelow.getValue(BlockStateProperties.LIT);
        }

        return front && behind;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        if (requiredFlips <= 0 || player.isSecondaryUseActive() || !player.getItemInHand(hand).isEmpty()) {
            return InteractionResult.FAIL;
        }

        if (state.getValue(POSITION) == BEHIND) {
            BlockPos front = pos.relative(state.getValue(FACING));
            BlockState frontState = level.getBlockState(front);
            if (frontState.is(state.getBlock()) && frontState.getValue(POSITION) == FRONT) {
                if (level.getBlockEntity(front) instanceof SmeltableBlockEntity smeltable) {
                    smeltable.onFlip(level, player);
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.FAIL;
        } else {
            return super.use(state, level, pos, player, hand, hitResult);
        }
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
            boolean neighborCooked = neighborState.getValue(COOKED);
            if (neighborCooked != state.getValue(COOKED)) {
                return state.setValue(COOKED, neighborCooked);
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

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos frontPos = context.getClickedPos();
        Direction facing = context.getHorizontalDirection();
        Level level = context.getLevel();
        BlockPos behindPos = frontPos.relative(facing);
        if (level.getBlockState(behindPos).canBeReplaced(context)) {
            return super.getStateForPlacement(context);
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
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        if (blockState.getValue(POSITION) == BEHIND) return null;
        return super.newBlockEntity(blockPos, blockState);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (state.getValue(POSITION) == BEHIND) return null;
        if (state.getValue(COOKED)) return null;
        return createTickerHelper(blockEntityType, ModBlocks.SMELTABLE_BE.get(),
                (levelIn, blockPos, blockState, smeltable) -> smeltable.tick(levelIn));
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
        if (state.getValue(POSITION) == BEHIND) {
            return behindShapes.get(state.getValue(FACING));
        }
        return shapes.get(state.getValue(FACING));
    }
}
