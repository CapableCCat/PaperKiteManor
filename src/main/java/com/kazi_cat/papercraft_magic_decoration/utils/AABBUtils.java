package com.kazi_cat.papercraft_magic_decoration.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

public class AABBUtils {
    public static AABB fromTo(BlockPos from, BlockPos to) {
        return new AABB(
                Math.min(from.getX(), to.getX()),
                from.getY(),
                Math.min(from.getZ(), to.getZ()),
                Math.max(from.getX(), to.getX()) + 1,
                to.getY() + 1,
                Math.max(from.getZ(), to.getZ()) + 1
        );
    }
}
