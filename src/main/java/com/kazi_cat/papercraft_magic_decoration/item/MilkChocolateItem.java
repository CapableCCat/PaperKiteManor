package com.kazi_cat.papercraft_magic_decoration.item;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class MilkChocolateItem extends BlockItem {
    public MilkChocolateItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        entity.getActiveEffectsMap().keySet().stream().filter(i -> i.getCategory().equals(MobEffectCategory.HARMFUL)).toList().forEach(entity::removeEffect);
        return super.finishUsingItem(stack, level, entity);
    }
}
