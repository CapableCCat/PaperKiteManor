package com.kazi_cat.papercraft_magic_decoration.blockentity.drink;

import com.kazi_cat.papercraft_magic_decoration.blockentity.BaseBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public class MochaPotBlockEntity extends BaseBlockEntity {
    protected static final String ITEMS = "items";
    protected static final String RESULT = "result";
    protected static final String PROGRESS = "progress";
    protected static final String HEATING_TIME = "heating_time";

    protected ItemStackHandler items = new ItemStackHandler(4){
        @Override
        public int getSlotLimit(int slot) { return 1; }
    };
    protected ItemStack result = ItemStack.EMPTY;
    protected int progress;
    protected int heatingTime;

    public MochaPotBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.MOCHA_POT_BE.get(), pos, state);
    }

    public ItemStack dropAsItem() {
        ItemStack result = ModItems.MOCHA_POT.get().getDefaultInstance();
        CompoundTag tag = new CompoundTag();
        tag.put(ITEMS, this.items.serializeNBT());
        tag.put(RESULT, this.result.save(new CompoundTag()));
        BlockItem.setBlockEntityData(result, this.getType(), tag);
        return result;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(ITEMS, this.items.serializeNBT());
        tag.put(RESULT, this.result.save(new CompoundTag()));
        tag.putInt(PROGRESS, this.progress);
        tag.putInt(HEATING_TIME, this.heatingTime);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(ITEMS)) {
            this.items = new ItemStackHandler(4);
            this.items.deserializeNBT(tag.getCompound(ITEMS));
        }
        if (tag.contains(RESULT)) {
            this.result = ItemStack.of(tag.getCompound(RESULT));
        }
        if (tag.contains(PROGRESS)) {
            this.progress = tag.getInt(PROGRESS);
        }
        if (tag.contains(HEATING_TIME)) {
            this.heatingTime = tag.getInt(HEATING_TIME);
        }
    }

    public ItemStackHandler getItems() { return this.items; }

    public ItemStack getResult() { return this.result; }

    public int getProgress() { return this.progress; }

    public int getHeatingTime() { return this.heatingTime; }
}
