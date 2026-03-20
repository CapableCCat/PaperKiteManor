package com.kazi_cat.papercraft_magic_decoration.item.food;

import com.kazi_cat.papercraft_magic_decoration.block.ModBlockStateProperties;
import com.kazi_cat.papercraft_magic_decoration.block.drink.GuangSBlock;
import com.kazi_cat.papercraft_magic_decoration.blockentity.drink.GlassDrinkBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import com.kazi_cat.papercraft_magic_decoration.item.StateBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PackOfGuangSItem extends StateBlockItem {
    public PackOfGuangSItem(Properties properties) {
        super(ModBlocks.GUANG_S.get(), properties,
                (s) -> s.setValue(GuangSBlock.BOXED, true).setValue(ModBlockStateProperties.COUNT_4, 4));
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, @Nullable Player player, ItemStack stack, BlockState state) {
        if (level.getBlockEntity(pos) instanceof GlassDrinkBlockEntity be) {
            for (int i = 0; i < 4; i++) {
                be.getItems().set(i, ModItems.GUANG_S.get().getDefaultInstance());
            }
            be.refresh();
        }
        return super.updateCustomBlockEntityTag(pos, level, player, stack, state);
    }
}
