package com.kazi_cat.papercraft_magic_decoration.blockentity;

import com.kazi_cat.papercraft_magic_decoration.api.block.ISmeltableBlock;
import com.kazi_cat.papercraft_magic_decoration.api.blockentity.ISmeltable;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SmeltableBlockEntity extends BaseBlockEntity implements ISmeltable {
    public static final String COOKING_PROGRESS = "cookingProgress";
    public static final String FLIP_COUNT = "flipCount";
    public static final String LAST_FLIPPED_TIME = "lastFlippedTime";

    protected int cookingProgress;
    protected int flipCount;
    protected int litLevel;
    protected long lastFlippedTime;

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

    public boolean isInFlipCooldown(Level level) {
        return lastFlippedTime + getFlipCooldown() >= level.getGameTime();
    }

    public boolean onFlip(Level level, LivingEntity user) {
        if (!isInFlipCooldown(level)) {
            lastFlippedTime = level.getGameTime();
            int maxFlipCount = getMaxFlipCount();
            if (maxFlipCount > 0) {
                flipCount = Mth.clamp(flipCount + 1, 0, maxFlipCount);
                return true;
            }
        }
        return false;
    }

    public void cookingTick(Level level) {
        int cookingTime = getCookingTime();
        if (cookingTime <= 0) return;
        int maxFlipCount = getMaxFlipCount();
        int limit = maxFlipCount == 0 ? cookingTime : cookingTime * flipCount / maxFlipCount;
        if (cookingProgress >= limit) return;
        cookingProgress++;
        if (cookingProgress >= cookingTime) {
            onFinished(level, this.getBlockState(), this.worldPosition);
        }
        setChanged();
    }

    public void cooldownTick(Level level) {
        int cookingTime = getCookingTime();
        if (cookingTime <= 0) return;
        cookingProgress = Mth.clamp(cookingProgress - 2, 0, cookingTime);
        setChanged();
    }

    public void updateLitLevel(Level level) {
        litLevel = hasLitSource(level) ? 1 : 0;
    }

    public boolean hasLitSource(Level level) {
        ISmeltableBlock smeltable = getSmeltable();
        return smeltable != null && smeltable.hasLitSource(level, this.getBlockState(), this.worldPosition);
    }

    public ItemStack dropAsItem() {
        ItemStack result = getRaw();
        CompoundTag tag = new CompoundTag();
        tag.putInt(COOKING_PROGRESS, cookingProgress);
        tag.putInt(FLIP_COUNT, flipCount);
        BlockItem.setBlockEntityData(result, this.getType(), tag);
        return result;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        cookingProgress = tag.getInt(COOKING_PROGRESS);
        flipCount = tag.getInt(FLIP_COUNT);
        lastFlippedTime = tag.getLong(LAST_FLIPPED_TIME);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt(COOKING_PROGRESS, cookingProgress);
        tag.putInt(FLIP_COUNT, flipCount);
        tag.putLong(LAST_FLIPPED_TIME, lastFlippedTime);
    }

    public void onFinished(Level level, BlockState state, BlockPos pos) {
        ISmeltableBlock smeltable = getSmeltable();
        if (smeltable != null) smeltable.onFinished(level, state, pos);
    }

    public int getCookingTime() {
        ISmeltableBlock smeltable = getSmeltable();
        return smeltable == null ? -1 : smeltable.getCookingTime();
    }

    public int getMaxFlipCount() {
        ISmeltableBlock smeltable = getSmeltable();
        return smeltable == null ? -1 : smeltable.getMaxFlipCount();
    }

    public int getFlipCooldown() {
        ISmeltableBlock smeltable = getSmeltable();
        return smeltable == null ? -1 : smeltable.getFlipCooldown();
    }

    public ItemStack getRaw() {
        ISmeltableBlock smeltable = getSmeltable();
        if (smeltable != null) {
            return smeltable.getRaw();
        } else {
            return this.getBlockState().getBlock().asItem().getDefaultInstance();
        }
    }

    @Nullable
    public ISmeltableBlock getSmeltable() {
        if (this.getBlockState().getBlock() instanceof ISmeltableBlock smeltable) {
            return smeltable;
        }
        return null;
    }
}
