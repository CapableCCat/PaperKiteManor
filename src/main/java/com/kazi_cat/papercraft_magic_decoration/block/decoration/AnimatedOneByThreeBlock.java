package com.kazi_cat.papercraft_magic_decoration.block.decoration;

import com.kazi_cat.papercraft_magic_decoration.api.block.ICustomRenderBoundingBox;
import com.kazi_cat.papercraft_magic_decoration.block.base.MultipartBlock;
import com.kazi_cat.papercraft_magic_decoration.blockentity.SimpleAnimatedBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.utils.AABBUtils;
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
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

@SuppressWarnings({"deprecation","unchecked"})
public class AnimatedOneByThreeBlock extends MultipartBlock implements SimpleWaterloggedBlock, EntityBlock, ICustomRenderBoundingBox {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected final EnumMap<Direction, VoxelShape>[] shapeMap;

    public AnimatedOneByThreeBlock(Properties properties, VoxelShape... shapes) {
        super(properties, 3);
        this.shapeMap = new EnumMap[shapes.length];
        for (int i = 0; i < shapes.length; i++) {
            this.shapeMap[i] = VoxelShapeUtils.horizontalShapes(shapes[i]);
        }

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.overrideBlockStateDefinition(builder);
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(partProperty, 1)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {
        if (player.getItemInHand(hand).isEmpty()) {
            Direction facing = state.getValue(FACING);
            BlockPos ep = switch (state.getValue(partProperty)) {
                case 0 -> pos.relative(facing.getCounterClockWise());
                case 1 -> pos;
                case 2 -> pos.relative(facing.getClockWise());
                default -> BlockPos.ZERO;
            };
            if (level.getBlockEntity(ep) instanceof SimpleAnimatedBlockEntity be) {
                be.triggerAnimation();
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    protected void overrideBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, partProperty);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
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
    public List<BlockPos> getOrderedParts(BlockPos pos, BlockState state) {
        Direction facing = state.getValue(FACING);
        Direction left = facing.getClockWise();
        Direction right = facing.getCounterClockWise();
        return switch (state.getValue(partProperty)) {
            case 0 -> List.of(pos, pos.relative(right), pos.relative(right, 2));
            case 1 -> List.of(pos.relative(left), pos, pos.relative(right));
            case 2 -> List.of(pos.relative(left, 2), pos.relative(left), pos);
            default -> List.of();
        };
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(partProperty) == 1 ? new SimpleAnimatedBlockEntity(pos, state) : null;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder pParams) {
        if (state.getValue(partProperty) != 1) {
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
        int part = Mth.clamp(state.getValue(partProperty), 0, shapeMap.length - 1);
        return shapeMap[part].get(facing);
    }

    @Override
    public AABB getRenderBoundingBox(BlockState state, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        return AABBUtils.fromTo(pos.relative(facing.getClockWise()), pos.relative(facing.getCounterClockWise()));
    }
}
