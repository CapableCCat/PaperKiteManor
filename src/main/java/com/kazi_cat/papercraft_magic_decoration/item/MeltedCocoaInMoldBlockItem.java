package com.kazi_cat.papercraft_magic_decoration.item;

import com.kazi_cat.papercraft_magic_decoration.blockentity.ChocolateInMoldBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class MeltedCocoaInMoldBlockItem extends ItemNameBlockItem {
    private final int cooldownTime;

    public MeltedCocoaInMoldBlockItem(Block block, Properties properties, int cooldownTime) {
        super(block, properties);
        this.cooldownTime = cooldownTime;
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, @Nullable Player player, ItemStack stack, BlockState state) {
        if (level.getBlockEntity(pos) instanceof ChocolateInMoldBlockEntity be) {
            be.setCooldownTime(cooldownTime);
        }
        return super.updateCustomBlockEntityTag(pos, level, player, stack, state);
    }
}
