package com.kazi_cat.papercraft_magic_decoration.block.utility;

import com.kazi_cat.papercraft_magic_decoration.blockentity.CopperBartenderBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.utils.VoxelShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;

@SuppressWarnings("deprecation")
public class CopperBartenderBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock, EntityBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;
    private static final EnumMap<Direction, VoxelShape> SHAPES = VoxelShapeUtils.horizontalShapes(Block.box(2, 0, 6, 14, 17, 16));

    public CopperBartenderBlock() {
        super(BlockBehaviour.Properties.of()
                .noOcclusion()
                .sound(SoundType.LANTERN)
                .requiresCorrectToolForDrops()
                .strength(1f, 10f));

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
                .setValue(TRIGGERED, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return InteractionResult.PASS;
        }

        if (level.getBlockEntity(pos) instanceof CopperBartenderBlockEntity be && !be.isShaking()) {
            if (player.isSecondaryUseActive()) {
                be.tryShake(level);
            } else {
                if (player instanceof ServerPlayer serverPlayer) {
                    NetworkHooks.openScreen(serverPlayer, be, pos);
                }
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, TRIGGERED);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        FluidState fluidState = level.getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER)
                .setValue(TRIGGERED, level.hasNeighborSignal(context.getClickedPos()));
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos neighborPos, boolean flag) {
        boolean signal = level.hasNeighborSignal(pos) || level.hasNeighborSignal(pos.below());
        boolean triggered = state.getValue(TRIGGERED);
        if (signal && !triggered) {
            level.setBlock(pos, state.setValue(TRIGGERED, true), 4);
            if (level.getBlockEntity(pos) instanceof CopperBartenderBlockEntity bartender) {
                bartender.tryShake(level);
            }
        } else if (!signal && triggered) {
            level.setBlock(pos, state.setValue(TRIGGERED, false), 4);
        }
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return true;
    }

    @Nullable
    @SuppressWarnings("all")
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(
            BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker) {
        return clientType == serverType ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CopperBartenderBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlocks.COPPER_BARTENDER_BE.get(),
                (levelIn, blockPos, blockState, bartender) -> bartender.tick(levelIn));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder lootParamsBuilder) {
        List<ItemStack> stacks = super.getDrops(state, lootParamsBuilder);
        BlockEntity parameter = lootParamsBuilder.getParameter(LootContextParams.BLOCK_ENTITY);
        if (parameter instanceof CopperBartenderBlockEntity bartender) {
            ItemStackHandler items = bartender.getItems();
            for (int i = 0; i < items.getSlots(); i++) {
                ItemStack itemStack = items.getStackInSlot(i);
                if (!itemStack.isEmpty()) {
                    stacks.add(itemStack);
                }
            }
        }
        return stacks;
    }
}
