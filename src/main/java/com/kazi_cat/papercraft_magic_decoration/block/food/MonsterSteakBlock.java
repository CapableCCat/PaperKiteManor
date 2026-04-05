package com.kazi_cat.papercraft_magic_decoration.block.food;

import com.kazi_cat.papercraft_magic_decoration.block.decoration.DecorationBlock;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.tag.TagMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class MonsterSteakBlock extends SmeltableBlock {
    public static final IntegerProperty PART = IntegerProperty.create("part", 0, 5);

    public MonsterSteakBlock(Properties properties, VoxelShape northShape, int cookingTime, int requiredFlips,
                             int flipCooldown, Supplier<ItemStack> ingredient, Supplier<ItemStack> result) {
        super(properties, northShape, cookingTime, requiredFlips, flipCooldown, ingredient, result);

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.createPartBlockStateDefinition(builder);
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PART, 1)
                .setValue(FACING, Direction.NORTH)
                .setValue(COOKED, false));
    }

    protected void createPartBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, COOKED, PART);
    }

    @Override
    public boolean hasLitSource(Level level, BlockState state, BlockPos pos) {
        List<BlockPos> ordered = getOrderedPos(pos, state.getValue(FACING).getOpposite());
        for (int i = 0; i < 6; i++) {
            BlockState belowState = level.getBlockState(ordered.get(i).below());
            boolean isLit = belowState.is(TagMod.HEAT_SOURCE_WITHOUT_LIT);
            if (belowState.hasProperty(BlockStateProperties.LIT)) {
                isLit = belowState.getValue(BlockStateProperties.LIT);
            }

            if (!isLit) {
                return false;
            }
        }
        return true;
    }

    private static void handleRemove(Level world, BlockPos pos, Direction direction, @Nullable Player player, boolean instant) {
        if (world.isClientSide) {
            return;
        }
        List<BlockPos> ordered = getOrderedPos(pos, direction);
        for (int i = 0; i < 6; i++) {
            if (!instant && i == 1) {
                world.destroyBlock(ordered.get(i), true, player);
            } else {
                world.setBlock(ordered.get(i), Blocks.AIR.defaultBlockState(), Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_ALL);
            }
        }
    }

    @Override
    public void onFinished(Level level, BlockState state, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        List<BlockPos> ordered = getOrderedPos(pos, direction.getOpposite());
        for (int i = 0; i < 6; i++) {
            level.setBlockAndUpdate(ordered.get(i), ModBlocks.LARGE_STEAK.get().defaultBlockState()
                    .setValue(FACING, direction)
                    .setValue(((DecorationBlock.HorizontalDirectional.Variant) ModBlocks.LARGE_STEAK.get()).getVariantProperty(), i));
        }
    }

    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        handleRemove(world, getCenter(pos, state), state.getValue(FACING).getOpposite(), player, player.isCreative());
        super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public void onBlockExploded(BlockState state, Level world, BlockPos pos, Explosion explosion) {
        handleRemove(world, getCenter(pos, state), state.getValue(FACING).getOpposite(), null, false);
        super.onBlockExploded(state, world, pos, explosion);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        if (state.getValue(PART) == 1) {
            return super.newBlockEntity(pos, state);
        }
        return null;
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (state.getValue(PART) == 1) {
            return super.getTicker(level, state, blockEntityType);
        }
        return null;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection();
        List<BlockPos> ordered = getOrderedPos(context.getClickedPos(), direction);
        if (ordered.stream().anyMatch(pos -> !context.getLevel().getBlockState(pos).canBeReplaced())) {
            return null;
        }
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (level.isClientSide) {
            return;
        }
        Direction direction = state.getValue(FACING).getOpposite();
        List<BlockPos> ordered = getOrderedPos(pos, direction);
        for (int i = 0; i < 6; i++) {
            if (i == 1) continue;
            level.setBlockAndUpdate(ordered.get(i), state.setValue(PART, i));
        }
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder pParams) {
        if (state.getValue(PART) != 1) {
            return Collections.emptyList();
        }
        return super.getDrops(state, pParams);
    }

    public static List<BlockPos> getOrderedPos(BlockPos pos, Direction direction) {
        BlockPos middleUp = pos.relative(direction);
        BlockPos leftDown = pos.relative(direction.getCounterClockWise());
        BlockPos leftUp = leftDown.relative(direction);
        BlockPos rightDown = pos.relative(direction.getClockWise());
        BlockPos rightUp = rightDown.relative(direction);
        return List.of(leftDown, pos, rightDown, leftUp, middleUp, rightUp);
    }

    public static BlockPos getCenter(BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING).getOpposite();
        int position = state.getValue(PART);
        return switch (position) {
            case 0 -> pos.relative(direction.getClockWise());
            case 1 -> pos;
            case 2 -> pos.relative(direction.getCounterClockWise());
            case 3 -> pos.relative(direction.getClockWise()).relative(direction.getOpposite());
            case 4 -> pos.relative(direction.getOpposite());
            case 5 -> pos.relative(direction.getCounterClockWise()).relative(direction.getOpposite());
            default -> BlockPos.ZERO;
        };
    }
}
