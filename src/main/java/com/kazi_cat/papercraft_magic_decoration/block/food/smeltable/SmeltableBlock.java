package com.kazi_cat.papercraft_magic_decoration.block.food.smeltable;

import com.kazi_cat.papercraft_magic_decoration.blockentity.food.smeltable.SmeltableBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.tag.TagMod;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class SmeltableBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final BooleanProperty COOKED = BooleanProperty.create("cooked");
    protected final EnumMap<Direction, VoxelShape> shapes;
    protected final int cookingTime;
    protected final int maxFlipCount;
    protected final int flipCooldown;
    protected final Supplier<ItemStack> rawSupplier;
    protected final Supplier<ItemStack> resultSupplier;

    public SmeltableBlock(Properties properties, VoxelShape northShape, int cookingTime, int maxFlipCount, int flipCooldown, Supplier<ItemStack> rawSupplier, Supplier<ItemStack> resultSupplier) {
        super(properties);
        this.shapes = VoxelShapeUtils.horizontalShapes(northShape);
        this.cookingTime = cookingTime;
        this.maxFlipCount = maxFlipCount;
        this.flipCooldown = flipCooldown;
        this.rawSupplier = rawSupplier;
        this.resultSupplier = resultSupplier;

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(COOKED, false)
                .setValue(FACING, Direction.NORTH));
    }

    public boolean hasLitSource(Level level, BlockState state, BlockPos pos) {
        BlockState belowState = level.getBlockState(pos.below());
        if (belowState.hasProperty(BlockStateProperties.LIT)) {
            return belowState.getValue(BlockStateProperties.LIT);
        }
        return belowState.is(TagMod.HEAT_SOURCE_WITHOUT_LIT);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        if (!player.getItemInHand(hand).isEmpty()) {
            return super.use(state, level, pos, player, hand, hitResult);
        }

        if (maxFlipCount > 0 && !player.isSecondaryUseActive() && level.getBlockEntity(pos) instanceof SmeltableBlockEntity smeltable) {
            smeltable.onFlip(level, player);
            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, hitResult);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        ItemStack itemStack = context.getItemInHand();
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(COOKED, resultSupplier.get().is(itemStack.getItem()));
    }

    @Nullable
    @SuppressWarnings("all")
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(
            BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker) {
        return clientType == serverType ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SmeltableBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (state.getValue(COOKED)) return null;
        return createTickerHelper(blockEntityType, ModBlocks.SMELTABLE_BE.get(),
                (levelIn, blockPos, blockState, smeltable) -> smeltable.tick(levelIn));
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder lootParamsBuilder) {
        if (state.getValue(COOKED)) return Collections.singletonList(resultSupplier.get());
        BlockEntity parameter = lootParamsBuilder.getParameter(LootContextParams.BLOCK_ENTITY);
        if (parameter instanceof SmeltableBlockEntity smeltable) {
            return Collections.singletonList(smeltable.dropAsItem());
        }
        return super.getDrops(state, lootParamsBuilder);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, COOKED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shapes.get(state.getValue(FACING));
    }

    public int getCookingTime() { return this.cookingTime; }

    public int getMaxFlipCount() { return this.maxFlipCount; }

    public int getFlipCooldown() { return this.flipCooldown; }

    public ItemStack getRawItemStack() { return this.rawSupplier.get(); }
}
