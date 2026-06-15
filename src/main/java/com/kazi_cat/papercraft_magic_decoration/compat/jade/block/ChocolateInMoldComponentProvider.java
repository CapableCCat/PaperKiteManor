package com.kazi_cat.papercraft_magic_decoration.compat.jade.block;

import com.kazi_cat.papercraft_magic_decoration.block.chocolate.ChocolateInMoldBlock;
import com.kazi_cat.papercraft_magic_decoration.compat.jade.ModPlugin;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum ChocolateInMoldComponentProvider implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig iPluginConfig) {
        ResourceLocation blockKey = ForgeRegistries.BLOCKS.getKey(accessor.getBlock());
        if (blockKey == null) {
            return;
        }
        BlockState state = accessor.getBlockState();
        tooltip.clear();
        String path = (state.getValue(ChocolateInMoldBlock.MELTED) ? "melted_" : "") + blockKey.getPath();
        Component component = Component.translatable("block.papercraft_magic_decoration.%s".formatted(path)).withStyle(ChatFormatting.WHITE);
        tooltip.append(component);
;    }

    @Override
    public ResourceLocation getUid() {
        return ModPlugin.CHOCOLATE_IN_MOLD;
    }
}
