package com.kazi_cat.papercraft_magic_decoration.block.food;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class MangaMeatBlock extends SmeltableBlock.Animated {
    public static final BooleanProperty HAS_BASE = BooleanProperty.create("has_base");

    public MangaMeatBlock(BlockBehaviour.Properties properties, VoxelShape northShape, int cookingTime, int requiredFlips,
                          int flipCooldown, Supplier<ItemStack> ingredient, Supplier<ItemStack> result) {
        super(properties, northShape, cookingTime, requiredFlips, flipCooldown, ingredient, result);

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.createBaseBlockStateDefinition(builder);
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(HAS_BASE, false)
                .setValue(FACING, Direction.NORTH)
                .setValue(COOKED, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        if (!state.getValue(HAS_BASE)) {
            return InteractionResult.FAIL;
        }

        return super.use(state, level, pos, player, hand, hitResult);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (!state.getValue(HAS_BASE)) return null;
        return super.getTicker(level, state, blockEntityType);
    }

    protected void createBaseBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, COOKED, HAS_BASE);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        ItemStack itemStack = context.getItemInHand();
        return this.defaultBlockState()
                .setValue(HAS_BASE, shouldHasBase(context.getLevel(), context.getClickedPos()))
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(COOKED, result.get().is(itemStack.getItem()));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor levelAccessor, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN) {
            state = state.setValue(HAS_BASE, shouldHasBase(levelAccessor, pos));
        }
        return state;
    }

    private boolean shouldHasBase(LevelAccessor level, BlockPos pos) {
        BlockState belowState = level.getBlockState(pos.below());
        return belowState.is(BlockTags.CAMPFIRES);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = shapes.get(state.getValue(FACING));
        if (state.getValue(HAS_BASE)) shape = shape.move(0, -0.4, 0);
        return shape;
    }
}

