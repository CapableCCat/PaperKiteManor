package com.kazi_cat.papercraft_magic_decoration.item;

import com.kazi_cat.papercraft_magic_decoration.utils.ItemUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class DrinkBlockItem extends BlockItem {
    public DrinkBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    public DrinkBlockItem(Block block, FoodProperties food) {
        this(block, new Properties().craftRemainder(Items.GLASS_BOTTLE).food(food));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!(entity instanceof Player player && player.isCreative())) {
            ItemUtils.getItemToLivingEntity(entity, stack.getCraftingRemainingItem());
        }
        return super.finishUsingItem(stack, level, entity);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemstack) {
        return UseAnim.DRINK;
    }
}
