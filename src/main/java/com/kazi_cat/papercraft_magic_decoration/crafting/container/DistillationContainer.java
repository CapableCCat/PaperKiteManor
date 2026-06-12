package com.kazi_cat.papercraft_magic_decoration.crafting.container;

import net.minecraft.core.NonNullList;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class DistillationContainer extends SimpleContainer {
    private final ItemStack wineBase;

    public DistillationContainer(List<ItemStack> items, ItemStack wineBase) {
        super(items.size());
        for (int i = 0; i < items.size(); i++) {
            this.setItem(i, items.get(i));
        }
        this.wineBase = wineBase;
    }

    public ItemStack getWineBase() {
        return wineBase;
    }

    public NonNullList<ItemStack> getItems() {
        return items;
    }
}
