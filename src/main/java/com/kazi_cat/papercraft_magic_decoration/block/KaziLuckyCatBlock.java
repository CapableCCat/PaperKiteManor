package com.kazi_cat.papercraft_magic_decoration.block;

import com.kazi_cat.papercraft_magic_decoration.block.decoration.DecorationBlock;
import com.kazi_cat.papercraft_magic_decoration.blockentity.KaziLuckyCatBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.utils.VoxelShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

@SuppressWarnings("deprecation")
public class KaziLuckyCatBlock extends DecorationBlock.HorizontalDirectional.Animated.Waterlogged {
    public static final IntegerProperty POSITION = IntegerProperty.create("position", 0, 1);
    public static final int DOWN = 0;
    public static final int UP = 1;
    protected final EnumMap<Direction, VoxelShape> shapes1;

    public KaziLuckyCatBlock(BlockBehaviour.Properties properties, VoxelShape belowShape, VoxelShape aboveShape) {
        super(properties, belowShape);

        this.shapes1 = VoxelShapeUtils.horizontalShapes(aboveShape);

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.createPositionBlockStateDefinition(builder);
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(POSITION, DOWN)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
    }

    public KaziLuckyCatBlock(VoxelShape belowShape, VoxelShape aboveShape) {
        this(BlockBehaviour.Properties.of().sound(SoundType.DECORATED_POT).strength(1f, 10f).noOcclusion(), belowShape, aboveShape);
    }

    protected void createPositionBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, POSITION);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos belowPos = context.getClickedPos();
        Level level = context.getLevel();
        BlockPos abovePos = belowPos.relative(Direction.UP);
        if (level.getBlockState(abovePos).canBeReplaced(context)) {
            return super.getStateForPlacement(context);
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        BlockPos abovePos = pos.relative(Direction.UP);
        BlockState aboveState = state.setValue(POSITION, UP);
        pLevel.setBlock(abovePos, aboveState, Block.UPDATE_ALL);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide() && !player.isSecondaryUseActive()) {
            int position = state.getValue(POSITION);
            BlockPos entityPos = position == DOWN ? pos : pos.below();
            if (level.getBlockEntity(entityPos) instanceof KaziLuckyCatBlockEntity luckyCat) {
                NetworkHooks.openScreen((ServerPlayer) player, luckyCat,  entityPos);
                luckyCat.triggerAnim();
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.FAIL;
        }

        return super.use(state, level, pos, player, hand, hitResult);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        int position = state.getValue(POSITION);

        if ((position == DOWN && direction == Direction.UP) || (position == UP && direction == Direction.DOWN)) {
            if (!neighborState.is(this) || neighborState.getValue(FACING) != state.getValue(FACING) || neighborState.getValue(POSITION) == position) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide && player.isCreative() && state.getValue(POSITION) == UP) {
            BlockPos belowPos = pos.relative(Direction.DOWN);
            BlockState belowState = level.getBlockState(belowPos);
            if (belowState.is(state.getBlock()) && belowState.getValue(POSITION) == DOWN) {
                BlockState airBlockState = belowState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                level.setBlock(belowPos, airBlockState, Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_ALL);
                level.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, belowPos, Block.getId(belowState));
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        if (state.getValue(POSITION) != DOWN) return null;
        return new KaziLuckyCatBlockEntity(pos, state);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        if (state.getValue(POSITION) != DOWN) {
            return Collections.emptyList();
        }
        return super.getDrops(state, params);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(POSITION) == DOWN ? shapes.get(state.getValue(FACING)) : shapes1.get(state.getValue(FACING));
    }
}

