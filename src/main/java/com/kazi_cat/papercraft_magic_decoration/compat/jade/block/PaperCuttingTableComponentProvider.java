package com.kazi_cat.papercraft_magic_decoration.compat.jade.block;

import com.kazi_cat.papercraft_magic_decoration.blockentity.PaperCuttingTableBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.compat.jade.ModPlugin;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IDisplayHelper;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

public enum PaperCuttingTableComponentProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig pluginConfig) {
        if (!(accessor.getBlockEntity() instanceof PaperCuttingTableBlockEntity table)) {
            return;
        }
        ItemStack stack = table.getContent();
        if (stack.isEmpty()) {
            return;
        }
        IElement icon = IElementHelper.get().smallItem(stack);
        MutableComponent stackName = IDisplayHelper.get().stripColor(stack.getHoverName());
        tooltip.add(icon);
        tooltip.append(IElementHelper.get().spacer(2, 1));
        tooltip.append(stackName);
    }

    @Override
    public ResourceLocation getUid() {
        return ModPlugin.PAPERCUTTING_TABLE;
    }
}
