package com.kazi_cat.papercraft_magic_decoration.blockentity;

import com.kazi_cat.papercraft_magic_decoration.block.ChocolateInMoldBlock;
import com.kazi_cat.papercraft_magic_decoration.block.MeltedCocoaInMoldBlock;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class MeltedCocoaInMoldBlockEntity extends BaseBlockEntity {
    public static final String COOLDOWN_PROGRESS = "cooldown_progress";

    protected int cooldownProgress;
    protected int freezeLevel;

    public MeltedCocoaInMoldBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.MELTED_COCOA_IN_MOLD_BE.get(), pos, state);
    }

    public void tick(Level level) {
        if (level.getGameTime() % 5 == 0) {
            updateFreezeLevel(level);
        }

        if (freezeLevel > 0) {
            cooldownTick(level);
        }
    }

    public void cooldownTick(Level level) {
        cooldownProgress++;
        spawnCooldownParticles(level);
        if (cooldownProgress >= getCooldownTime() && getChocolateBlock() != null) {
            if (level instanceof ServerLevel serverLevel) {
                Vec3 particlePos = worldPosition.getCenter();
                serverLevel.sendParticles(
                        ParticleTypes.GLOW,
                        particlePos.x(), particlePos.y(), particlePos.z(),
                        16,
                        0.25, 0.25, 0.25,
                        0
                );
            }

            level.setBlockAndUpdate(worldPosition, getChocolateBlock().defaultBlockState()
                    .setValue(ChocolateInMoldBlock.FACING, getBlockState().getValue(MeltedCocoaInMoldBlock.FACING)));
        }
    }

    public void spawnCooldownParticles(Level level) {
        if (level instanceof ServerLevel serverLevel && level.getRandom().nextDouble() <= 0.05) {
            serverLevel.sendParticles(ParticleTypes.CLOUD,
                    worldPosition.getX() + 0.5,
                    worldPosition.getY() + 0.7,
                    worldPosition.getZ() + 0.5,
                    1, 0.1, 0.08, 0.1, 0.0025);
        }
    }

    public void updateFreezeLevel(Level level) {
        freezeLevel = level.getBlockState(worldPosition.below()).is(BlockTags.ICE) ? 1 : 0;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(COOLDOWN_PROGRESS)) {
            cooldownProgress = tag.getInt(COOLDOWN_PROGRESS);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
       tag.putInt(COOLDOWN_PROGRESS, cooldownProgress);
    }

    public int getCooldownTime() {
        MeltedCocoaInMoldBlock block = getBlock();
        return block != null ? block.getCooldownTime() : -1;
    }

    public @Nullable ChocolateInMoldBlock getChocolateBlock() {
        MeltedCocoaInMoldBlock block = getBlock();
        return block != null ? block.getChocolateBlock() : null;
    }

    private @Nullable MeltedCocoaInMoldBlock getBlock() {
        if (getBlockState().getBlock() instanceof MeltedCocoaInMoldBlock block) {
            return block;
        }
        return null;
    }
}
