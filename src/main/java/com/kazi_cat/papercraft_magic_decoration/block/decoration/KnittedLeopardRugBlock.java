package com.kazi_cat.papercraft_magic_decoration.block.decoration;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class KnittedLeopardRugBlock extends VariantDecorationBlock {
    public KnittedLeopardRugBlock(Properties properties, VoxelShape shape) {
        super(properties, shape, 2);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }

        ItemStack itemInHand = player.getMainHandItem();
        if (itemInHand.getItem() instanceof ShearsItem) {
            itemInHand.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
            level.playSound(player, pos, SoundEvents.SNOW_GOLEM_SHEAR, SoundSource.PLAYERS);
            level.setBlockAndUpdate(pos, state.cycle(variantProperty));
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}
