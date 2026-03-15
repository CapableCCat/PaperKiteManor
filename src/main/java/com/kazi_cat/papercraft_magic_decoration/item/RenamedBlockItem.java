package com.kazi_cat.papercraft_magic_decoration.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class RenamedBlockItem extends BlockItem {
    protected final String descriptionId;

    public RenamedBlockItem(Block block, Properties settings, String descriptionId) {
        super(block, settings);
        this.descriptionId = "item.papercraft_magic_decoration." + descriptionId;
    }

    @Override
    public String getDescriptionId() {
        return descriptionId;
    }
}