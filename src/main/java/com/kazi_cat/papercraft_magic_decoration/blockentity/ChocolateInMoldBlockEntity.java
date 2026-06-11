package com.kazi_cat.papercraft_magic_decoration.blockentity;

import com.kazi_cat.papercraft_magic_decoration.block.chocolate.ChocolateInMoldBlock;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ChocolateInMoldBlockEntity extends BaseBlockEntity {
    public static final String TAG_PROGRESS = "progress";
    public static final String TAG_COOLDOWN_TIME = "cooldown_time";

    protected int progress;
    protected int cooldownTime;
    protected boolean cooling;

    public ChocolateInMoldBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.CHOCOLATE_IN_MOLD_BE.get(), pos, state);
    }

    public void tick(Level level) {
        if (cooldownTime <= 0) {
            return;
        }

        if (level.getGameTime() % 5 == 0) {
            cooling = level.getBlockState(worldPosition.below()).is(BlockTags.ICE);
        }

        if (cooling) {
            spawnCooldownParticles(level);
            progress++;
            if (progress >= cooldownTime) {
                if (level instanceof ServerLevel serverLevel) {
                    Vec3 particlePos = worldPosition.getCenter();
                    serverLevel.sendParticles(ParticleTypes.GLOW,
                            particlePos.x(), particlePos.y(), particlePos.z(),
                            16, 0.25, 0.25, 0.25, 0);
                }

                level.setBlockAndUpdate(worldPosition, getBlockState().setValue(ChocolateInMoldBlock.MELTED, false));
            }
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

    public void setCooldownTime(int cooldownTime) {
        this.cooldownTime = cooldownTime;
        setChanged();
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(TAG_PROGRESS)) {
            this.progress = tag.getInt(TAG_PROGRESS);
        }
        if (tag.contains(TAG_COOLDOWN_TIME)) {
            this.cooldownTime = tag.getInt(TAG_COOLDOWN_TIME);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt(TAG_PROGRESS, this.progress);
        tag.putInt(TAG_COOLDOWN_TIME, this.cooldownTime);
    }
}
