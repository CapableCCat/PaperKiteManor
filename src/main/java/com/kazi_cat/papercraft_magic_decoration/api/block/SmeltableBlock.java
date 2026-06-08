package com.kazi_cat.papercraft_magic_decoration.api.block;

import com.kazi_cat.papercraft_magic_decoration.init.tag.TagMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public interface SmeltableBlock {
    BooleanProperty COOKED = BooleanProperty.create("cooked");

    default boolean hasLitSource(Level level, BlockState state, BlockPos pos) {
        BlockState belowState = level.getBlockState(pos.below());
        if (belowState.hasProperty(BlockStateProperties.LIT)) {
            return belowState.getValue(BlockStateProperties.LIT);
        }
        return belowState.is(TagMod.HEAT_SOURCE_WITHOUT_LIT);
    }

    default void onCookFinished(Level level, BlockState state, BlockPos pos) {
        level.addDestroyBlockEffect(pos, state);
        level.setBlockAndUpdate(pos, state.trySetValue(COOKED, true));
    }
}
