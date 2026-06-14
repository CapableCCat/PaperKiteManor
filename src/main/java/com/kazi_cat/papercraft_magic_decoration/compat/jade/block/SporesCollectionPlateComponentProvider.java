package com.kazi_cat.papercraft_magic_decoration.compat.jade.block;

import com.kazi_cat.papercraft_magic_decoration.block.utility.SporesCollectionPlateBlock;
import com.kazi_cat.papercraft_magic_decoration.compat.jade.ModPlugin;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

public enum SporesCollectionPlateComponentProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig pluginConfig) {
        BlockState blockState = accessor.getBlockState();
        if (blockState.getValue(SporesCollectionPlateBlock.FILLED)) {
            IElement icon = IElementHelper.get().smallItem(ModItems.VITALITY_SPORES.get().getDefaultInstance());
            tooltip.add(Component.translatable("jade.papercraft_magic_decoration.spores_collection_plate.filled"));
            tooltip.append(IElementHelper.get().spacer(2, 1));
            tooltip.append(icon);
        } else {
            tooltip.add(Component.translatable("jade.papercraft_magic_decoration.spores_collection_plate.empty"));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ModPlugin.SPORES_COLLECTION_PLATE;
    }
}
