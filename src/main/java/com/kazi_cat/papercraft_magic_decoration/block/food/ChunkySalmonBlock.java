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
    public static final IntegerProperty VARIANT = IntegerProperty.create("variant", 0, 3);

    public ChunkySalmonBlock(Properties properties, VoxelShape northShape, int cookingTime, int requiredFlips,
                             int flipCooldown, Supplier<ItemStack> ingredient, Supplier<ItemStack> result) {
        super(properties, northShape, cookingTime, requiredFlips, flipCooldown, ingredient, result);

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.createVariantBlockStateDefinition(builder);
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(VARIANT, 0)
                .setValue(FACING, Direction.NORTH)
                .setValue(COOKED, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        if (player.getItemInHand(hand).isEmpty() && player.isSecondaryUseActive()) {
            level.setBlockAndUpdate(pos, state.cycle(VARIANT));
            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, hitResult);
    }

    protected void createVariantBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, COOKED, VARIANT);
    }
}
