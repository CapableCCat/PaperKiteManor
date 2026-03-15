package com.kazi_cat.papercraft_magic_decoration.blockentity.drink;

import com.kazi_cat.papercraft_magic_decoration.blockentity.BaseBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector2i;

public class GlassDrinkBlockEntity extends BaseBlockEntity {
    private final NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    private Vector2i offset = new Vector2i().zero();

    public GlassDrinkBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.GLASS_DRINK_BE.get(), pos, state);
    }

    public boolean addItem(ItemStack stack) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).isEmpty()) {
                items.set(i, stack.copyWithCount(1));
                return true;
            }
        }
        // 没有空位了，一般来说应该在外面调用这个方法之前就检查过了
        // 但为了安全起见，我们返回 false
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
        // 没有物品了，返回空的 ItemStack
        return ItemStack.EMPTY;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, this.items);
        if (tag.contains("offset")) {
            String[] pos = tag.getString("offset").split(":");
            offset = new Vector2i(Integer.parseInt(pos[0]), Integer.parseInt(pos[1]));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, this.items);
        tag.putString("offset", offset.x() + ":" + offset.y());
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }

    public Vector2i getOffset() { return this.offset; }
}
