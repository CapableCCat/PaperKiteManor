package com.kazi_cat.papercraft_magic_decoration.block.food;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class ChunkySalmonBlock extends SmeltableBlock {
    public static final IntegerProperty VARIANT = IntegerProperty.create("variant", 1, 4);

    public ChunkySalmonBlock(Properties properties, VoxelShape northShape, int cookingTime, int maxFlipCount, int flipCooldown, Supplier<ItemStack> rawSupplier, Supplier<ItemStack> resultSupplier) {
        super(properties, northShape, cookingTime, maxFlipCount, flipCooldown, rawSupplier, resultSupplier);

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.createVariantBlockStateDefinition(builder);
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(VARIANT, 1)
                .setValue(FACING, Direction.NORTH)
                .setValue(COOKED, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        if (player.getItemInHand(hand).isEmpty() && player.isSecondaryUseActive()) {
            int variant = state.getValue(VARIANT) + 1;
            if (variant > 4) variant = 1;
            level.setBlockAndUpdate(pos, state.setValue(VARIANT, variant));
            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, hitResult);
    }

    protected void createVariantBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, COOKED, VARIANT);
    }
}
