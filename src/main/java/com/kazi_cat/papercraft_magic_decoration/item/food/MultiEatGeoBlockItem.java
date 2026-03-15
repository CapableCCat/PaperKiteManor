package com.kazi_cat.papercraft_magic_decoration.item.food;

import com.kazi_cat.papercraft_magic_decoration.item.GeoBlockItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class MultiEatGeoBlockItem extends GeoBlockItem {
    protected final int useDuration;

    public MultiEatGeoBlockItem(Block block, Properties settings, String descriptionId, int useDuration) {
        super(block, settings, descriptionId);
        this.useDuration = useDuration;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        entity.eat(level, stack.copy());
        ItemStack remainder = stack.copy();
        remainder.hurtAndBreak(1, entity, (p) -> p.broadcastBreakEvent(entity.getUsedItemHand()));
        return remainder;
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return useDuration;
    }
}
