package com.kazi_cat.papercraft_magic_decoration.compat.jade.block;

import com.kazi_cat.papercraft_magic_decoration.blockentity.MochaPotBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.compat.jade.ModPlugin;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IDisplayHelper;

public enum MochaPotComponentProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig pluginConfig) {
        if (!(accessor.getBlockEntity() instanceof MochaPotBlockEntity mochaPot)) {
            return;
        }
        MutableComponent stackName = IDisplayHelper.get().stripColor(mochaPot.getResult().getHoverName());
        tooltip.add(stackName);
        int currentTick = mochaPot.getCurrentTick();
        if (currentTick > 0) {
            tooltip.add(Component.translatable("jade.papercraft_magic_decoration.mocha_pot.heat_time", currentTick / 20));
        } else if (!mochaPot.getResult().isEmpty()) {
            tooltip.add(Component.translatable("jade.papercraft_magic_decoration.mocha_pot.heat_finished"));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ModPlugin.MOCHA_POT;
    }
}
