package com.kazi_cat.papercraft_magic_decoration.item;

import com.kazi_cat.papercraft_magic_decoration.block.BoxedDrinkBlock;
import com.kazi_cat.papercraft_magic_decoration.blockentity.DrinkBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PackOfGuangSItem extends ItemNameBlockItem {
    public PackOfGuangSItem() {
        super(ModBlocks.GUANG_S.get(), new Properties());
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        BoxedDrinkBlock guangS = (BoxedDrinkBlock) ModBlocks.GUANG_S.get();
        return super.placeBlock(context, state.setValue(guangS.getCountProperty(), 4).setValue(BoxedDrinkBlock.BOXED, true));
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, @Nullable Player player, ItemStack stack, BlockState state) {
        if (level.getBlockEntity(pos) instanceof DrinkBlockEntity be) {
            for (int i = 0; i < 4; i++) {
                be.getItems().set(i, ModItems.GUANG_S.get().getDefaultInstance());
            }
            be.refresh();
        }
        return super.updateCustomBlockEntityTag(pos, level, player, stack, state);
    }
}
