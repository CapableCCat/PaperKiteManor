package com.kazi_cat.papercraft_magic_decoration.item;

import com.kazi_cat.papercraft_magic_decoration.block.drink.BoxedDrinkBlock;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class PackOfGuangSItem extends Item {
    public PackOfGuangSItem(Properties properties) { super(properties); }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());

        if (!level.getBlockState(pos).canBeReplaced()) {
            return InteractionResult.FAIL;
        }

        if (!level.isClientSide()) {
            BoxedDrinkBlock block = (BoxedDrinkBlock) ModBlocks.GUANG_S.get();
            Direction facing = context.getHorizontalDirection().getOpposite();
            FluidState fluidState = level.getFluidState(pos);
            level.setBlockAndUpdate(pos, ModBlocks.GUANG_S.get().defaultBlockState()
                    .setValue(block.getCountProperty(), 4)
                    .setValue(BoxedDrinkBlock.BOXED, true)
                    .setValue(BlockStateProperties.HORIZONTAL_FACING, facing)
                    .setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER));

            Player player = context.getPlayer();
            if (!(player != null && player.isCreative())) {
                context.getItemInHand().shrink(1);
            }
        }

        return InteractionResult.SUCCESS;
    }
}
