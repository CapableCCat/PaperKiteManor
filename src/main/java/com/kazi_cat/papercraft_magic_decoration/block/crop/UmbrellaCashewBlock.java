package com.kazi_cat.papercraft_magic_decoration.block.crop;

import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import com.kazi_cat.papercraft_magic_decoration.utils.ItemUtils;
import com.kazi_cat.papercraft_magic_decoration.utils.VoxelShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;

@SuppressWarnings("deprecation")
public class UmbrellaCashewBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock, BonemealableBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty MATURE = BooleanProperty.create("mature");
    protected final EnumMap<Direction, VoxelShape> shapes;
    protected final EnumMap<Direction, VoxelShape> shapesMature;

    public UmbrellaCashewBlock(Properties properties, VoxelShape shape, VoxelShape shapeMature) {
        super(properties);
        this.shapes = VoxelShapeUtils.horizontalShapes(shape);
        this.shapesMature = VoxelShapeUtils.horizontalShapes(shapeMature);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(MATURE, false)
                .setValue(WATERLOGGED, false)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MATURE, WATERLOGGED, FACING);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        if (!level.getBlockState(pos.above()).is(ModBlocks.CANOPY_TREE_FOLIAGE.get())
            || context.getClickedFace() != Direction.DOWN) {
            return null;
        }

        FluidState fluidState = level.getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (state.getValue(MATURE) && player.getItemInHand(hand).isEmpty()) {
            ItemUtils.spawnItemEntity(level, pos.getCenter(), ModItems.GLOW_CASHEWS.get().getDefaultInstance(), Vec3.ZERO);
            level.setBlockAndUpdate(pos, state.setValue(MATURE, false));
            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.above()).is(ModBlocks.CANOPY_TREE_FOLIAGE.get());
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return !state.getValue(MATURE);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isAreaLoaded(pos, 1)) return;
        if (random.nextInt(10) == 0) {
            level.setBlockAndUpdate(pos, state.setValue(MATURE, true));
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
        return !state.getValue(MATURE);
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return random.nextDouble() < 0.45;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        level.setBlockAndUpdate(pos, state.setValue(MATURE, true));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(MATURE) ? shapesMature.get(state.getValue(FACING)) : shapes.get(state.getValue(FACING));
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder lootParamsBuilder) {
        List<ItemStack> stacks = super.getDrops(state, lootParamsBuilder);
        if (state.getValue(MATURE)) {
            stacks.add(ModItems.GLOW_CASHEWS.get().getDefaultInstance());
        }
        return stacks;
    }

    @Override
    public int getLightEmission(BlockState state, BlockGetter level, BlockPos pos) {
        return 15;
    }
}
