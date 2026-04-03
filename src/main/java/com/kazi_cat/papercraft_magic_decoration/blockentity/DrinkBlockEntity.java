package com.kazi_cat.papercraft_magic_decoration.blockentity;

import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.utils.Vector2iCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector2i;

public class DrinkBlockEntity extends BaseBlockEntity {
    private final NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    private Vector2i offset = new Vector2i();

    public DrinkBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.DRINK_BE.get(), pos, state);
    }

    public boolean addItem(ItemStack stack) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).isEmpty()) {
                items.set(i, stack.copyWithCount(1));
                return true;
            }
        }

        return false;
    }

    public ItemStack removeItem() {
        // 倒序取出最后一个物品
        for (int i = items.size() - 1; i >= 0; i--) {
            if (!items.get(i).isEmpty()) {
                ItemStack stack = items.get(i);
                items.set(i, ItemStack.EMPTY);
                return stack.copyWithCount(1);
            }
        }

        return ItemStack.EMPTY;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, this.items);
        if (tag.contains("offset")) {
            this.offset = Vector2iCodec.decode(tag.getLong("offset"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.items);
        tag.putLong("offset", Vector2iCodec.encode(this.offset));
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public Vector2i getOffset() { return this.offset; }
}
