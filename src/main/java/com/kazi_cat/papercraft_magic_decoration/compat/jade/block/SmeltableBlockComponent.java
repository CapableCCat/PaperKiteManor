package com.kazi_cat.papercraft_magic_decoration.compat.jade.block;

import com.kazi_cat.papercraft_magic_decoration.api.block.SmeltableBlock;
import com.kazi_cat.papercraft_magic_decoration.blockentity.SmeltableBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.compat.jade.ModPlugin;
import com.kazi_cat.papercraft_magic_decoration.datamap.data.SmeltableBlockData;
import com.kazi_cat.papercraft_magic_decoration.datamap.resources.SmeltableBlockDataReloadListener;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.impl.ui.ProgressArrowElement;

public enum SmeltableBlockComponent implements IBlockComponentProvider {
    INSTANCE;

    @Override
    public @Nullable IElement getIcon(BlockAccessor accessor, IPluginConfig config, IElement currentIcon) {
        BlockState state = accessor.getBlockState();
        SmeltableBlockData data = SmeltableBlockDataReloadListener.INSTANCE.getOrDefault(state.getBlock(), null);
        if (data == null || data.ingredient() == Items.AIR) {
            return null;
        }
        Item item = state.getValue(SmeltableBlock.COOKED) ? data.result() : data.ingredient();
        return IElementHelper.get().item(item.getDefaultInstance());
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig iPluginConfig) {
        Level level = accessor.getLevel();
        BlockState state = accessor.getBlockState();
        BlockPos pos = accessor.getPosition();
        if (!(accessor.getBlock() instanceof SmeltableBlock block)) {
            return;
        }
        SmeltableBlockData data = SmeltableBlockDataReloadListener.INSTANCE.getOrDefault(state.getBlock(), null);
        if (data == null || data.ingredient() == Items.AIR) {
            return;
        }
        tooltip.clear();
        boolean cooked = state.getValue(SmeltableBlock.COOKED);
        Item item = cooked ? data.result() : data.ingredient();
        tooltip.add(item.getDefaultInstance().getHoverName().copy().withStyle(ChatFormatting.WHITE));
        if (!cooked && block.getBlockEntity(level, state, pos) instanceof SmeltableBlockEntity be
                && be.hasHeatSource(level)) {
            IElementHelper helper = IElementHelper.get();
            tooltip.add(helper.item(Items.CAMPFIRE.getDefaultInstance()));
            tooltip.append(new ProgressArrowElement(be.getProgressPercent()));
            tooltip.append(helper.item(data.result().getDefaultInstance()));
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ModPlugin.SMELTABLE_BLOCK;
    }
}
