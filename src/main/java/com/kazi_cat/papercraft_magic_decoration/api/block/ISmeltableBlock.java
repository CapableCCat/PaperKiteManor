package com.kazi_cat.papercraft_magic_decoration.api.block;

import com.kazi_cat.papercraft_magic_decoration.block.food.SmeltableBlock;
import com.kazi_cat.papercraft_magic_decoration.init.tag.TagMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public interface ISmeltableBlock {
    default boolean hasLitSource(Level level, BlockState state, BlockPos pos) {
        BlockState belowState = level.getBlockState(pos.below());
        if (belowState.hasProperty(BlockStateProperties.LIT)) {
            return belowState.getValue(BlockStateProperties.LIT);
        }
        return belowState.is(TagMod.HEAT_SOURCE_WITHOUT_LIT);
    }

    default void onFinished(Level level, BlockState state, BlockPos pos) {
        level.addDestroyBlockEffect(pos, state);
        level.setBlockAndUpdate(pos, state.setValue(SmeltableBlock.COOKED, true));
    }

    int getCookingTime();

    int getRequiredFlips();

    int getFlipCooldown();

    ItemStack getIngredient();

    ItemStack getResult();
}
