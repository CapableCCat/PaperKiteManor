package com.kazi_cat.papercraft_magic_decoration.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class OversizedBoxOfChocolatesItem extends ItemNameBlockItem {
    public static final String CONTENT = "content";

    public OversizedBoxOfChocolatesItem(Block block, Properties properties) {
        super(block, properties);
    }

    public static int @Nullable [] getContent(ItemStack itemStack) {
        CompoundTag tag = itemStack.getOrCreateTag();
        if (tag.contains(CONTENT)) {
            return tag.getIntArray(CONTENT);
        }
        return null;
    }

    public static void setContent(ItemStack itemStack, int[] content) {
        itemStack.getOrCreateTag().putIntArray(CONTENT, content);
    }
}
