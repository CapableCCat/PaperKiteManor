package com.kazi_cat.papercraft_magic_decoration.item.food;

import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LavaCauldronBlock;
import net.minecraftforge.items.ItemHandlerHelper;

public class BreadedRawChickenItem extends Item {
    public BreadedRawChickenItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        ItemStack itemInHand = context.getItemInHand();

        if (level.getBlockState(pos).getBlock() instanceof LavaCauldronBlock) {
            ItemHandlerHelper.giveItemToPlayer(context.getPlayer(), new ItemStack(ModItems.BUCKET_OF_FRIED_CHICKEN.get(), itemInHand.copyAndClear().getCount()));
            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }
}
