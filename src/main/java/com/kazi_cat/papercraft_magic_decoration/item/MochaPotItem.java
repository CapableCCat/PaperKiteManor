package com.kazi_cat.papercraft_magic_decoration.item;

import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

public class MochaPotItem extends BlockItem {
    public MochaPotItem() {
        super(ModBlocks.MOCHA_POT.get(), new Properties());
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }
}
