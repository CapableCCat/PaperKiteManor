package com.kazi_cat.papercraft_magic_decoration.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;

public class ShiftPlaceBlockItem extends BlockItem {
    public ShiftPlaceBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult place(BlockPlaceContext pContext) {
        Player player = pContext.getPlayer();
        if (player != null && !player.isSecondaryUseActive()) return InteractionResult.FAIL;
        return super.place(pContext);
    }
}
