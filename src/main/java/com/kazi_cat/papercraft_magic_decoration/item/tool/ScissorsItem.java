package com.kazi_cat.papercraft_magic_decoration.item.tool;

import com.kazi_cat.papercraft_magic_decoration.init.ModRecipes;
import com.kazi_cat.papercraft_magic_decoration.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ScissorsItem extends ShearsItem {
    public ScissorsItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        return level.getRecipeManager().getRecipeFor(ModRecipes.PAPERMAKING_RECIPE, new SimpleContainer(new ItemStack(state.getBlock().asItem())), level)
                .map(recipe -> {
                    Player player = context.getPlayer();
                    if (player != null && !player.isCreative()) {
                        context.getItemInHand().hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(context.getHand()));
                    }
                    level.destroyBlock(pos, false, context.getPlayer(), Block.UPDATE_ALL);
                    ItemUtils.spawnItemEntity(level, pos.getCenter(), recipe.getResult().copy());
                    return InteractionResult.SUCCESS;
                }).orElse(super.useOn(context));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemstack) {
        return UseAnim.BLOCK;
    }
}
