package com.kazi_cat.papercraft_magic_decoration.item;

import com.kazi_cat.papercraft_magic_decoration.init.ModRecipes;
import com.kazi_cat.papercraft_magic_decoration.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AmethystScissorsItem extends ShearsItem {
    public AmethystScissorsItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        // 如果为紫水晶母岩，破坏并掉落
        if (state.is(Blocks.BUDDING_AMETHYST)) {
            Player player = context.getPlayer();
            if (player != null && !player.isCreative()) {
                context.getItemInHand().hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(context.getHand()));
            }
            level.playSound(context.getPlayer(), pos, SoundEvents.SNOW_GOLEM_SHEAR, SoundSource.PLAYERS);
            level.destroyBlock(pos, false, context.getPlayer(), Block.UPDATE_ALL);
            ItemUtils.spawnItemEntity(level, pos.getCenter(), new ItemStack(Items.BUDDING_AMETHYST));
            return InteractionResult.SUCCESS;
        }

        return level.getRecipeManager().getRecipeFor(ModRecipes.PAPERMAKING_RECIPE, new SimpleContainer(new ItemStack(state.getBlock().asItem())), level)
                .map(recipe -> {
                    Player player = context.getPlayer();
                    if (player != null && !player.isCreative()) {
                        context.getItemInHand().hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(context.getHand()));
                    }
                    level.playSound(context.getPlayer(), pos, SoundEvents.SNOW_GOLEM_SHEAR, SoundSource.PLAYERS);
                    level.destroyBlock(pos, false, context.getPlayer(), Block.UPDATE_ALL);
                    ItemUtils.spawnItemEntity(level, pos.getCenter(), recipe.getResult().copy());
                    return InteractionResult.SUCCESS;
                }).orElse(super.useOn(context));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemstack) {
        return UseAnim.BLOCK;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(ItemStack itemstack) {
        return true;
    }
}
