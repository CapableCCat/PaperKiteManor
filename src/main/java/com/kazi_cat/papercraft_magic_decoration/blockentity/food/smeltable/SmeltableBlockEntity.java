package com.kazi_cat.papercraft_magic_decoration.blockentity.food.smeltable;

import com.kazi_cat.papercraft_magic_decoration.block.food.smeltable.SmeltableBlock;
import com.kazi_cat.papercraft_magic_decoration.blockentity.BaseBlockEntity;
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

public class SmeltableBlockEntity extends BaseBlockEntity {
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

    public void onFlip(Level level, LivingEntity user) {
        if (isInFlipCooldown(level)) return;
        lastFlippedTime = level.getGameTime();
        int maxFlipCount = getMaxFlipCount();
        if (maxFlipCount < 0) return;
        flipCount = Mth.clamp(flipCount + 1, 0, maxFlipCount);
    }

    public void cookingTick(Level level) {
        int cookingTime = getCookingTime();
        if (cookingTime <= 0) return;
        int maxFlipCount = getMaxFlipCount();
        int limit = maxFlipCount == 0 ? cookingTime : cookingTime * flipCount / maxFlipCount;
        if (cookingProgress >= limit) return;
        cookingProgress++;
        if (cookingProgress >= cookingTime) {
            level.addDestroyBlockEffect(this.getBlockPos(), this.getBlockState());
            level.setBlockAndUpdate(worldPosition, this.getBlockState().setValue(SmeltableBlock.COOKED, true));
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
        SmeltableBlock smeltable = getBlock();
        return smeltable != null && smeltable.hasLitSource(level, this.getBlockState(), this.worldPosition);
    }

    public ItemStack dropAsItem() {
        ItemStack result = getRawItemStack();
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

    public int getCookingTime() {
        SmeltableBlock smeltable = getBlock();
        return smeltable == null ? -1 : smeltable.getCookingTime();
    }

    public int getMaxFlipCount() {
        SmeltableBlock smeltable = getBlock();
        return smeltable == null ? -1 : smeltable.getMaxFlipCount();
    }

    public int getFlipCooldown() {
        SmeltableBlock smeltable = getBlock();
        return smeltable == null ? -1 : smeltable.getFlipCooldown();
    }

    public ItemStack getRawItemStack() {
        SmeltableBlock smeltable = getBlock();
        if (smeltable != null) {
            return smeltable.getRawItemStack();
        } else {
            return this.getBlockState().getBlock().asItem().getDefaultInstance();
        }
    }

    public @Nullable SmeltableBlock getBlock() {
        if (this.getBlockState().getBlock() instanceof SmeltableBlock smeltable) {
            return smeltable;
        }
        return null;
    }
}
