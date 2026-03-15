package com.kazi_cat.papercraft_magic_decoration.block.food;

import com.kazi_cat.papercraft_magic_decoration.blockentity.food.AnimatedSmeltableBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class AnimatedTwoByOneSmeltableBlock extends TwoByOneSmeltableBlock{
    public AnimatedTwoByOneSmeltableBlock(Properties properties, VoxelShape frontShape, VoxelShape behindShape, int cookingTime, int requiredFlips,
                                     int flipCooldown, Supplier<ItemStack> ingredient, Supplier<ItemStack> result) {
        super(properties, frontShape, behindShape, cookingTime, requiredFlips, flipCooldown, ingredient, result);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        if (blockState.getValue(POSITION) == BEHIND) return null;
        return new AnimatedSmeltableBlockEntity(blockPos, blockState);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (state.getValue(POSITION) == BEHIND) return null;
        if (state.getValue(COOKED)) return null;
        return createTickerHelper(blockEntityType, ModBlocks.ANIMATED_SMELTABLE_BE.get(),
                (levelIn, blockPos, blockState, smeltable) -> smeltable.tick(levelIn));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
