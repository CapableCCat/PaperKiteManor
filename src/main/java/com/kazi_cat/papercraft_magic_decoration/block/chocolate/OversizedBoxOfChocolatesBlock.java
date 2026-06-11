package com.kazi_cat.papercraft_magic_decoration.block.chocolate;

import com.kazi_cat.papercraft_magic_decoration.block.base.MultipartBlock;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import com.kazi_cat.papercraft_magic_decoration.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("deprecation")
public class OversizedBoxOfChocolatesBlock extends MultipartBlock implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty OPENED = BooleanProperty.create("opened");
    public static final IntegerProperty CONTENT = IntegerProperty.create("content", 0, 3);

    // TODO 优化超大号巧克力盒的碰撞箱
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 14, 16);
    private static final String TAG_CONTENT = "content";

    public OversizedBoxOfChocolatesBlock(Properties properties) {
        super(properties, 6);

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.overrideBlockStateDefinition(builder);
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
                .setValue(OPENED, false)
                .setValue(CONTENT, 0)
                .setValue(partProperty, 1));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        ItemStack itemInHand = player.getMainHandItem();
        if (state.getValue(OPENED)) {
            if (player.isSecondaryUseActive() && itemInHand.isEmpty()) {
                for (var part : getOrderedParts(pos, state)) {
                    level.setBlockAndUpdate(part, level.getBlockState(part).setValue(OPENED, false));
                }
                return InteractionResult.SUCCESS;
            }

            int content = state.getValue(CONTENT);
            if (!player.isSecondaryUseActive() && itemInHand.isEmpty() && content != 0) {
                ItemHandlerHelper.giveItemToPlayer(player, getChocolateItem(content));
                level.setBlockAndUpdate(pos, state.setValue(CONTENT, 0));
                return InteractionResult.SUCCESS;
            }

            if (!player.isSecondaryUseActive() && !itemInHand.isEmpty() && content == 0) {
                if (itemInHand.is(ModItems.TRUFFLE_CHOCOLATE.get())) {
                    itemInHand.shrink(1);
                    level.setBlockAndUpdate(pos, state.setValue(CONTENT, 1));
                    return InteractionResult.SUCCESS;
                }

                if (itemInHand.is(ModItems.MILK_CHOCOLATE.get())) {
                    itemInHand.shrink(1);
                    level.setBlockAndUpdate(pos, state.setValue(CONTENT, 2));
                    return InteractionResult.SUCCESS;
                }

                if (itemInHand.is(ModItems.PRALINE_CHOCOLATE.get())) {
                    itemInHand.shrink(1);
                    level.setBlockAndUpdate(pos, state.setValue(CONTENT, 3));
                    return InteractionResult.SUCCESS;
                }
            }
        } else {
            if (itemInHand.isEmpty()) {
                for (var part : getOrderedParts(pos, state)) {
                    level.setBlockAndUpdate(part, level.getBlockState(part).setValue(OPENED, true));
                }
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    protected void overrideBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, OPENED, CONTENT, partProperty);
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

        ItemStack itemStack = context.getItemInHand();
        int[] contents = getContent(itemStack);
        FluidState fluidState = level.getFluidState(pos);

        return state.setValue(CONTENT, contents != null ? contents[1] : 0)
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        List<BlockPos> parts = getOrderedParts(pos, state);
        int[] contents = getContent(stack);
        for (int i = 0; i < 6; i++) {
            if (i == 1) continue;
            FluidState fluidState = level.getFluidState(pos);
            level.setBlockAndUpdate(parts.get(i), state
                    .setValue(partProperty, i)
                    .setValue(CONTENT, contents != null ? contents[i] : 0)
                    .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER));
        }
    }

    @Override
    public List<BlockPos> getOrderedParts(BlockPos pos, BlockState state) {
        Direction facing = state.getValue(FACING);
        Direction left = facing.getClockWise();
        Direction right = facing.getCounterClockWise();
        BlockPos center = switch (state.getValue(partProperty)) {
            case 0 -> pos.relative(right);
            case 1 -> pos;
            case 2 -> pos.relative(left);
            case 3 -> pos.relative(right).relative(facing);
            case 4 -> pos.relative(facing);
            case 5 -> pos.relative(left).relative(facing);
            default -> BlockPos.ZERO;
        };
        return List.of(
                center.relative(left),
                center,
                center.relative(right),
                center.relative(left).relative(facing.getOpposite()),
                center.relative(facing.getOpposite()),
                center.relative(right).relative(facing.getOpposite())
        );
    }

    @Override
    public void handleRemove(Level level, BlockPos pos, BlockState state, @Nullable Player player) {
        boolean drop = !(player != null && player.isCreative());
        List<BlockPos> parts = getOrderedParts(pos, state);

        if (drop && !state.getValue(OPENED)) {
            ItemStack itemStack = asItem().getDefaultInstance();
            int[] content = new int[6];
            for (int i = 0; i < 6; i++) {
                content[i] = level.getBlockState(parts.get(i)).getValue(CONTENT);
            }
            setContent(itemStack, content);
            ItemUtils.spawnItemEntity(level, pos.getCenter(), itemStack);
        }

        for (var part : parts) {
            level.destroyBlock(part, drop);
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        if (state.getValue(OPENED)) {
            return Collections.singletonList(getChocolateItem(state.getValue(CONTENT)));
        }
        return List.of();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    private static ItemStack getChocolateItem(int content) {
        return switch (content) {
            case 1 -> ModItems.TRUFFLE_CHOCOLATE.get().getDefaultInstance();
            case 2 -> ModItems.MILK_CHOCOLATE.get().getDefaultInstance();
            case 3 -> ModItems.PRALINE_CHOCOLATE.get().getDefaultInstance();
            default -> ItemStack.EMPTY;
        };
    }

    public static int @Nullable [] getContent(ItemStack itemStack) {
        CompoundTag tag = itemStack.getOrCreateTag();
        if (tag.contains(TAG_CONTENT)) {
            return tag.getIntArray(TAG_CONTENT);
        }
        return null;
    }

    public static void setContent(ItemStack itemStack, int[] content) {
        itemStack.getOrCreateTag().putIntArray(TAG_CONTENT, content);
    }
}
