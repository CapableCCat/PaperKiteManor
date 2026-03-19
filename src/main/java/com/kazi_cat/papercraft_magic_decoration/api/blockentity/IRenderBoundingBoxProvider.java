package com.kazi_cat.papercraft_magic_decoration.api.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public interface IRenderBoundingBoxProvider {
    AABB getRenderBoundingBox(BlockState state, BlockPos pos);
}
