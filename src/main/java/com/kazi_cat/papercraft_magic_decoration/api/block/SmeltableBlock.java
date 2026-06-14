package com.kazi_cat.papercraft_magic_decoration.api.block;

import com.kazi_cat.papercraft_magic_decoration.init.tag.TagMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

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
        //level.addDestroyBlockEffect(pos, state);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SMOKE,
                    pos.getX() + 0.5,
                    pos.getY() + 0.5,
                    pos.getZ() + 0.5,
                    16, 0.25, 0.5, 0.25, 0.03);
        }
        level.setBlockAndUpdate(pos, state.trySetValue(COOKED, true));
    }

    @Nullable
    default BlockEntity getBlockEntity(Level level, BlockState state, BlockPos pos) {
        return level.getBlockEntity(pos);
    }
}
