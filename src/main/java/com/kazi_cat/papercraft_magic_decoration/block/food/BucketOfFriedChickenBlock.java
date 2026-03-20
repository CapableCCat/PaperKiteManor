package com.kazi_cat.papercraft_magic_decoration.block.food;

import com.kazi_cat.papercraft_magic_decoration.block.decoration.SimpleDecorationBlock;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemHandlerHelper;

@SuppressWarnings("deprecation")
public class BucketOfFriedChickenBlock extends SimpleDecorationBlock {
    public BucketOfFriedChickenBlock(Properties properties, VoxelShape northShape) {
        super(properties, northShape);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide() && !player.isSecondaryUseActive() && player.getMainHandItem().isEmpty()) {
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ModItems.FRIED_CHICKEN_LEG.get(), 4));
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, hitResult);
    }
}
