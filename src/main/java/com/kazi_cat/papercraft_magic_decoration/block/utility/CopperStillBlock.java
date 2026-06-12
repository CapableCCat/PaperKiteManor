package com.kazi_cat.papercraft_magic_decoration.block.utility;

import com.kazi_cat.papercraft_magic_decoration.block.base.MultipartBlock;
import com.kazi_cat.papercraft_magic_decoration.blockentity.CopperStillBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.utils.VoxelShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;

@SuppressWarnings({"deprecation","unchecked"})
public class CopperStillBlock extends MultipartBlock implements SimpleWaterloggedBlock, EntityBlock {
    public static final IntegerProperty STATUS = IntegerProperty.create("status", 0, 3);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final EnumMap<Direction, VoxelShape>[] SHAPE_MAP = new EnumMap[] {
            VoxelShapeUtils.horizontalShapes(Shapes.or(
                    Block.box(1, 0, 1, 15, 16, 15),
                    Block.box(0, 0, 0, 16, 2, 16)
            )),
            VoxelShapeUtils.horizontalShapes(Shapes.or(
                    Block.box(0, 0, 0, 16, 2, 16),
                    Block.box(1, 2, 2, 13, 4, 14)
            )),
            VoxelShapeUtils.horizontalShapes(
                    Block.box(2, 0, 2, 13, 16, 14)
            ),
            VoxelShapeUtils.horizontalShapes(
                    Block.box(4, 7, 5, 16, 13, 11)
            )
    };

    public CopperStillBlock() {
        super(BlockBehaviour.Properties.of()
                .noOcclusion()
                .sound(SoundType.LANTERN)
                .requiresCorrectToolForDrops()
                .strength(4f, 20f), 4);

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.overrideBlockStateDefinition(builder);
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(partProperty, 0)
                .setValue(STATUS, 0)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        int part = state.getValue(partProperty);
        Direction facing = state.getValue(FACING);
        BlockPos ep = switch (part) {
            case 0 -> pos;
            case 1 -> pos.relative(facing.getClockWise());
            case 2 -> pos.below();
            case 3 -> pos.relative(facing.getClockWise()).below();
            default -> BlockPos.ZERO;
        };

        if (!(level.getBlockEntity(ep) instanceof CopperStillBlockEntity be)) {
            return InteractionResult.PASS;
        }

        ItemStack itemInHand = player.getItemInHand(hand);
        switch (part) {
            case 0 -> {
                if (!itemInHand.isEmpty() && be.addFuel(level, player, itemInHand)) {
                    return InteractionResult.SUCCESS;
                }
            }
            case 2 -> {
                if (itemInHand.isEmpty()) {
                    if (be.removeIngredient(level, player)) {
                        return InteractionResult.SUCCESS;
                    }
                } else {
                    if (be.addIngredient(level, player, itemInHand)) {
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            default -> {
                if (itemInHand.isEmpty()) {
                    if (be.takeOutResult(level, player)) {
                        return InteractionResult.SUCCESS;
                    }
                    if (be.removeWineBase(level, player)) {
                        return InteractionResult.SUCCESS;
                    }
                } else {
                    if (be.addWineBase(level, player, itemInHand)) {
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }

        return InteractionResult.PASS;
    }

    protected void overrideBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, STATUS, WATERLOGGED, partProperty);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction facing = context.getHorizontalDirection().getOpposite();
        BlockState state = this.defaultBlockState().setValue(FACING, facing);
        for (var part : getOrderedParts(pos, state)) {
            if (!level.getBlockState(part).canBeReplaced(context)) {
                return null;
            }
        }
        FluidState fluidState = level.getFluidState(pos);
        return state.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
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
        BlockPos start = switch (state.getValue(partProperty)) {
            case 0 -> pos;
            case 1 -> pos.relative(facing.getClockWise());
            case 2 -> pos.below();
            case 3 -> pos.relative(facing.getClockWise()).below();
            default -> BlockPos.ZERO;
        };
        return List.of(
                start,
                start.relative(facing.getCounterClockWise()),
                start.above(),
                start.relative(facing.getCounterClockWise()).above()
        );
    }

    @Nullable
    @SuppressWarnings("all")
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(
            BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker) {
        return clientType == serverType ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(partProperty) == 0 ? new CopperStillBlockEntity(pos, state) : null;
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (state.getValue(STATUS) != 2) {
            return null;
        }
        return createTickerHelper(blockEntityType, ModBlocks.COPPER_STILL_BE.get(),
                (levelIn, blockPos, blockState, be) -> be.tick(levelIn));
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return state.getValue(partProperty) == 0 && state.getValue(STATUS) == 2 ? 13 : 0;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_MAP[state.getValue(partProperty)].get(state.getValue(FACING));
    }

    public static void updateStatus(Level level, BlockPos pos, BlockState state, int status) {
        if (state.getBlock() instanceof CopperStillBlock block) {
            for (var part : block.getOrderedParts(pos, state)) {
                BlockState origin = level.getBlockState(part);
                level.setBlockAndUpdate(part ,origin.setValue(STATUS, Mth.clamp(status, 0, 3)));
            }
        }
    }
}
