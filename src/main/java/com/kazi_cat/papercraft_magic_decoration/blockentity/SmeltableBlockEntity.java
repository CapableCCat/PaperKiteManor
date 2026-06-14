package com.kazi_cat.papercraft_magic_decoration.blockentity;

import com.kazi_cat.papercraft_magic_decoration.api.block.SmeltableBlock;
import com.kazi_cat.papercraft_magic_decoration.datamap.data.SmeltableBlockData;
import com.kazi_cat.papercraft_magic_decoration.datamap.resources.SmeltableBlockDataReloadListener;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SmeltableBlockEntity extends BaseBlockEntity {
    public static final String PROGRESS = "progress";
    public static final String FLIPS = "flips";
    public static final String LAST_FLIPPED = "last_flipped";

    protected int litLevel;
    protected int progress;
    protected int flips;
    protected long lastFlipped;

    public SmeltableBlockEntity(BlockEntityType<?> entityType, BlockPos pos, BlockState state) {
        super(entityType, pos, state);
    }

    public SmeltableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.SMELTABLE_BE.get(), pos, state);
    }

    public void tick(Level level) {
        if (level.getGameTime() % 5 == 0) {
            updateLitLevel(level);
        }

        if (this.litLevel > 0) {
            cookingTick(level);
        } else {
            cooldownTick(level);
        }
    }

    public boolean flip(Level level, @Nullable LivingEntity user) {
        SmeltableBlockData data = getData();
        if (data == null || data.cooldown() <= 0 || data.flips() <= 0) {
            return false;
        }
        if (level.getGameTime() - lastFlipped >= data.cooldown()) {
            lastFlipped = level.getGameTime();
            flips = Mth.clamp(flips + 1, 0, data.flips());
            return true;
        }
        return false;
    }

    public void cookingTick(Level level) {
        SmeltableBlockData data = getData();
        if (data == null || data.time() <= 0 || data.flips() < 0) {
            return;
        }
        int limit = data.flips() == 0 ? data.time() : data.time() * flips / data.flips();
        if (progress >= limit) {
            return;
        }
        progress++;
        if (progress >= data.time()) {
            SmeltableBlock smeltable = getSmeltable();
            if (smeltable != null) {
                smeltable.onCookFinished(level, getBlockState(), worldPosition);
            }
        }
        setChanged();
    }

    public void cooldownTick(Level level) {
        SmeltableBlockData data = getData();
        if (data == null || data.time() <= 0) {
            return;
        }
        progress = Mth.clamp(progress - 2, 0, data.time());
        setChanged();
    }

    public void updateLitLevel(Level level) {
        litLevel = hasHeatSource(level) ? 1 : 0;
    }

    public float getProgressPercent() {
        SmeltableBlockData data = getData();
        if (data == null) {
            return 0;
        }
        int total = Math.max(data.time(), 1);
        return (float) progress / total;
    }

    public boolean hasHeatSource(Level level) {
        SmeltableBlock block = getSmeltable();
        return block != null && block.hasHeatSource(level, getBlockState(), worldPosition);
    }

    @Nullable
    public SmeltableBlock getSmeltable() {
        return getBlockState().getBlock() instanceof SmeltableBlock smeltable ? smeltable : null;
    }

    @Nullable
    public SmeltableBlockData getData() {
        return SmeltableBlockDataReloadListener.INSTANCE.getOrDefault(getBlockState().getBlock(), null);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.progress = tag.getInt(PROGRESS);
        this.flips = tag.getInt(FLIPS);
        this.lastFlipped = tag.getLong(LAST_FLIPPED);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt(PROGRESS, progress);
        tag.putInt(FLIPS, flips);
        tag.putLong(LAST_FLIPPED, lastFlipped);
    }
}
