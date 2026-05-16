package com.kazi_cat.papercraft_magic_decoration.blockentity;

import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class OldOrganBlockEntity extends AnimatedBlockEntity {
    public static final String INTERACTION_COOLDOWN = "interact_cooldown";

    protected int interactionCooldown;

    public OldOrganBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.OLD_ORGAN_BE.get(), pos, state);
    }

    public void tick(Level level) {
        if (interactionCooldown > 0) {
            interactionCooldown--;
        }
    }

    public boolean triggerAnim() {
        if (interactionCooldown == 0) {
            interactionCooldown = 420;
            super.triggerAnim();
            return true;
        }
        return false;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        interactionCooldown = tag.getInt(INTERACTION_COOLDOWN);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt(INTERACTION_COOLDOWN, interactionCooldown);
    }
}
