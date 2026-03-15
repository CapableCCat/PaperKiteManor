package com.kazi_cat.papercraft_magic_decoration.api.blockentity;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public interface ISmeltable {
    default boolean onFlip(Level level, LivingEntity user) { return false; }

    void updateLitLevel(Level level);

    boolean hasLitSource(Level level);
}
