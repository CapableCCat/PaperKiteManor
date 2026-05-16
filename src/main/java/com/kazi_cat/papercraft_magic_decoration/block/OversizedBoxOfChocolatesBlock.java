package com.kazi_cat.papercraft_magic_decoration.block;

import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import com.kazi_cat.papercraft_magic_decoration.item.OversizedBoxOfChocolatesItem;
import com.kazi_cat.papercraft_magic_decoration.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("deprecation")
public class OversizedBoxOfChocolatesBlock extends HorizontalDirectionalBlock {
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 14, 16);

    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 5);
    public static final IntegerProperty CONTENT = IntegerProperty.create("content", 0, 3);
    public static final BooleanProperty OPENED = BooleanProperty.create("opened");

    public static final int LEFT_DOWN = 0;
    public static final int CENTER_DOWN = 1;
    public static final int RIGHT_DOWN = 2;
    public static final int LEFT_UP = 3;
    public static final int CENTER_UP = 4;
    public static final int RIGHT_UP = 5;

    public OversizedBoxOfChocolatesBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(PART, 1)
                .setValue(OPENED, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        if (hand != InteractionHand.MAIN_HAND) {
            return super.use(state, level, pos, player, hand, hitResult);
        }

        ItemStack itemInHand = player.getMainHandItem();
        if (state.getValue(OPENED)) {
            if (player.isSecondaryUseActive() && itemInHand.isEmpty()) {
                for (var blockPos : getOrderedList(pos, state)) {
                    level.setBlockAndUpdate(blockPos, level.getBlockState(blockPos).setValue(OPENED, false));
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
                for (var blockPos : getOrderedList(pos, state)) {
                    level.setBlockAndUpdate(blockPos, level.getBlockState(blockPos).setValue(OPENED, true));
                }
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART, CONTENT, OPENED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos center = context.getClickedPos();
        Direction facing = context.getHorizontalDirection().getOpposite();
        for (var pos : getOrderedList(center, facing)) {
            if (!level.getBlockState(pos).canBeReplaced(context)) return null;
        }

        ItemStack itemStack = context.getItemInHand();
        int[] contents = OversizedBoxOfChocolatesItem.getContent(itemStack);

        return this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(CONTENT, contents != null ? contents[1] : 0);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos center, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        List<BlockPos> ordered = getOrderedList(center, state);
        int[] contents = OversizedBoxOfChocolatesItem.getContent(stack);
        for (int i = 0; i < 6; i++) {
            if (i == 1) continue;
            level.setBlockAndUpdate(ordered.get(i), state
                    .setValue(PART, i)
                    .setValue(CONTENT, contents != null ? contents[i] : 0));
        }
    }

    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        handleRemove(world, pos, state, player);
        super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public void onBlockExploded(BlockState state, Level world, BlockPos pos, Explosion explosion) {
        handleRemove(world, pos, state, null);
        super.onBlockExploded(state, world, pos, explosion);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        if (state.getValue(OPENED)) {
            return Collections.singletonList(getChocolateItem(state.getValue(CONTENT)));
        }
        return super.getDrops(state, params);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    private static void handleRemove(Level level, BlockPos pos, BlockState state, @Nullable Player player) {
        if (level.isClientSide()) {
            return;
        }
        List<BlockPos> ordered = getOrderedList(pos, state);

        if (!state.getValue(OPENED)) {
            ItemStack itemStack = state.getBlock().asItem().getDefaultInstance();
            int[] content = new int[6];
            for (int i = 0; i < 6; i++) {
                content[i] = level.getBlockState(ordered.get(i)).getValue(CONTENT);
            }
            OversizedBoxOfChocolatesItem.setContent(itemStack, content);
            ItemUtils.spawnItemEntity(level, pos.getCenter(), itemStack);
        }

        for (var p : ordered) {
            level.destroyBlock(p, true);
        }
    }

    private static ItemStack getChocolateItem(int content) {
        return switch (content) {
            case 1 -> ModItems.TRUFFLE_CHOCOLATE.get().getDefaultInstance();
            case 2 -> ModItems.MILK_CHOCOLATE.get().getDefaultInstance();
            case 3 -> ModItems.PRALINE_CHOCOLATE.get().getDefaultInstance();
            default -> ItemStack.EMPTY;
        };
    }

    private static List<BlockPos> getOrderedList(BlockPos center, Direction facing) {
        Direction left = facing.getClockWise();
        Direction right = facing.getCounterClockWise();
        return List.of(
                center.relative(left),
                center,
                center.relative(right),
                center.relative(left).relative(facing.getOpposite()),
                center.relative(facing.getOpposite()),
                center.relative(right).relative(facing.getOpposite())
        );
    }

    private static List<BlockPos> getOrderedList(BlockPos pos, BlockState state) {
        return getOrderedList(getCenter(pos, state), state.getValue(FACING));
    }

    private static BlockPos getCenter(BlockPos pos, BlockState state) {
        Direction front = state.getValue(FACING);
        Direction left = front.getClockWise();
        Direction right = front.getCounterClockWise();
        return switch (state.getValue(PART)) {
            case LEFT_DOWN -> pos.relative(right);
            case CENTER_DOWN -> pos;
            case RIGHT_DOWN -> pos.relative(left);
            case LEFT_UP -> pos.relative(right).relative(front);
            case CENTER_UP -> pos.relative(front);
            case RIGHT_UP -> pos.relative(left).relative(front);
            default -> BlockPos.ZERO;
        };
    }
}
