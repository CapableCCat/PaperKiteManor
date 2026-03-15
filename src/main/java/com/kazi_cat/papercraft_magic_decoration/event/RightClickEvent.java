package com.kazi_cat.papercraft_magic_decoration.event;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.block.decoration.TrayBlock;
import com.kazi_cat.papercraft_magic_decoration.block.food.SmeltableBlock;
import com.kazi_cat.papercraft_magic_decoration.block.food.TwoByOneSmeltableBlock;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import com.kazi_cat.papercraft_magic_decoration.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PaperKiteManor.MOD_ID)
public class RightClickEvent {
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack itemInHand = player.getItemInHand(hand);

        boolean success = false;
        if (itemInHand.is(ItemTags.AXES)) {
            if (state.is(ModBlocks.LARGE_STEAK.get())) {
                ItemUtils.spawnItemEntity(level, pos.getCenter(), ModItems.LARGE_STEAK.get().getDefaultInstance());
                level.setBlockAndUpdate(pos, ModBlocks.TRAY_BLOCK.get().defaultBlockState()
                        .setValue(TrayBlock.FACING, state.getValue(HorizontalDirectionalBlock.FACING)));
                success = true;
            } else if (state.is(ModBlocks.SAUSAGE_MACE_WEAPON_BLOCK.get()) && state.getValue(SmeltableBlock.COOKED)) {
                ItemUtils.spawnItemEntity(level, pos.getCenter(), new ItemStack(ModItems.CUBED_SAUSAGE.get(), 3));
                ItemUtils.spawnItemEntity(level, pos.getCenter(), Items.BAMBOO.getDefaultInstance());
                BlockPos entityPos = state.getValue(TwoByOneSmeltableBlock.POSITION) == 0 ? pos : pos.relative(state.getValue(HorizontalDirectionalBlock.FACING));
                level.setBlock(entityPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_ALL);
                success = true;
            }

            if (success) {
                itemInHand.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
            }
        } else if (itemInHand.isEmpty()) {
            if (state.is(ModBlocks.CHUNKY_SALMON.get())) {
                if (state.getValue(SmeltableBlock.COOKED)) {
                    ItemUtils.spawnItemEntity(level, pos.getCenter(), ModItems.CHUNKY_SMOKED_SALMON.get().getDefaultInstance());
                } else {
                    ItemUtils.spawnItemEntity(level, pos.getCenter(), ModItems.CHUNKY_SALMON.get().getDefaultInstance());
                }
                level.setBlockAndUpdate(pos, ModBlocks.TRAY_BLOCK.get().defaultBlockState()
                        .setValue(TrayBlock.VARIANT, 2)
                        .setValue(TrayBlock.FACING, state.getValue(HorizontalDirectionalBlock.FACING)));
                success = true;
                player.swing(hand);
            }
        }

        if (success) {
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }
    }
}
