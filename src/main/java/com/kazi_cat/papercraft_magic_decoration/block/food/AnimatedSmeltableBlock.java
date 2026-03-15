package com.kazi_cat.papercraft_magic_decoration.block.food;

import com.kazi_cat.papercraft_magic_decoration.blockentity.AnimatedSmeltableBlockEntity;
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
public class AnimatedSmeltableBlock extends SmeltableBlock {
    public AnimatedSmeltableBlock(Properties properties, VoxelShape northShape, int cookingTime,
                             int maxFlipCount, int flipCooldown, Supplier<ItemStack> rawSupplier, Supplier<ItemStack> resultSupplier) {
        super(properties, northShape, cookingTime, maxFlipCount, flipCooldown, rawSupplier, resultSupplier);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new AnimatedSmeltableBlockEntity(blockPos, blockState);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (state.getValue(COOKED)) return null;
        return createTickerHelper(blockEntityType, ModBlocks.ANIMATED_SMELTABLE_BE.get(),
                (levelIn, blockPos, blockState, smeltable) -> smeltable.tick(levelIn));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
