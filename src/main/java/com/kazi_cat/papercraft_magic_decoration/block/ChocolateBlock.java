package com.kazi_cat.papercraft_magic_decoration.block;

import com.kazi_cat.papercraft_magic_decoration.block.decoration.DecorationBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemHandlerHelper;

@SuppressWarnings("deprecation")
public class ChocolateBlock extends DecorationBlock.HorizontalDirectional.Waterlogged {
    public ChocolateBlock(Properties properties, VoxelShape northShape) {
        super(properties, northShape);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pHand == InteractionHand.MAIN_HAND && pPlayer.getMainHandItem().isEmpty() && !pPlayer.isSecondaryUseActive()) {
            ItemHandlerHelper.giveItemToPlayer(pPlayer, asItem().getDefaultInstance());
            pLevel.removeBlock(pPos, false);
            return InteractionResult.SUCCESS;
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
