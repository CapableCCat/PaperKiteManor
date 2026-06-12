package com.kazi_cat.papercraft_magic_decoration.item;

import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MochaPotItem extends BlockItem {
    public MochaPotItem() {
        super(ModBlocks.MOCHA_POT.get(), new Properties());
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        CompoundTag tag = BlockItem.getBlockEntityData(stack);
        if (tag != null && tag.contains("result")) {
            ItemStack itemStack = ItemStack.of(tag.getCompound("result"));
            tooltip.add(itemStack.getHoverName().copy().withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.add(ItemStack.EMPTY.getHoverName().copy().withStyle(ChatFormatting.GRAY));
        }
    }
}
