package com.kazi_cat.papercraft_magic_decoration.block;


import com.kazi_cat.papercraft_magic_decoration.blockentity.PaperCuttingTableBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.utils.VoxelShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
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
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;

@SuppressWarnings("deprecation")
public class PaperCuttingTableBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock, EntityBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected final EnumMap<Direction, VoxelShape> shapes;

    public PaperCuttingTableBlock() {
        super(BlockBehaviour.Properties.of()
                .sound(SoundType.WOOD)
                .strength(2f, 10f)
                .noOcclusion());

        shapes = VoxelShapeUtils.horizontalShapes(Block.box(0, 0, 0, 16, 15, 16));

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
    }

    @Override
    public InteractionResult use(BlockState blockstate, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!(level.getBlockEntity(pos) instanceof PaperCuttingTableBlockEntity table)) {
            return InteractionResult.PASS;
        }

        ItemStack itemInHand = player.getItemInHand(hand);
        // 如果不是空手，尝试裁剪或放入材料
        if (!itemInHand.isEmpty()) {
            if (table.cut(player, itemInHand, hand)) {
                return InteractionResult.SUCCESS;
            } else if (table.putIngredient(level, player, itemInHand)) {
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.PASS;
        }

        if (table.takeIngredient(level, player)) {
            return InteractionResult.SUCCESS;
        }

        return super.use(blockstate, level, pos, player, hand, hit);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PaperCuttingTableBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shapes.get(state.getValue(FACING));
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder lootParamsBuilder) {
        List<ItemStack> stacks = super.getDrops(state, lootParamsBuilder);
        BlockEntity parameter = lootParamsBuilder.getParameter(LootContextParams.BLOCK_ENTITY);
        if (parameter instanceof PaperCuttingTableBlockEntity table) {
            if (!table.getContent().isEmpty()) {
                stacks.add(table.getContent());
            }
        }
        return stacks;
    }
}
