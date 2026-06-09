package com.kazi_cat.papercraft_magic_decoration.blockentity;

import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class DrinkBlockEntity extends BaseBlockEntity {
    private static final String TAG_X_OFFSET = "x_offset";
    private static final String TAG_Y_OFFSET = "y_offset";

    private int xOffset;
    private int yOffset;

    public DrinkBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.DRINK_BE.get(), pos, state);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(TAG_X_OFFSET)) {
            this.xOffset = tag.getInt(TAG_X_OFFSET);
        }
        if (tag.contains(TAG_Y_OFFSET)) {
            this.yOffset = tag.getInt(TAG_Y_OFFSET);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt(TAG_X_OFFSET, this.xOffset);
        tag.putInt(TAG_Y_OFFSET, this.yOffset);
    }

    public int getXOffset() { return this.xOffset; }

    public int getYOffset() { return this.yOffset; }

    public void setOffset(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.setChanged();
    }
}
