package com.kazi_cat.papercraft_magic_decoration.event;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.api.block.SmeltableBlock;
import com.kazi_cat.papercraft_magic_decoration.block.decoration.VariantDecorationBlock;
import com.kazi_cat.papercraft_magic_decoration.block.smeltable.SausageMaceWeaponBlock;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;

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

        if (hand != InteractionHand.MAIN_HAND) {
            return;
        }

        if (itemInHand.is(ItemTags.AXES)) {
            if (state.is(ModBlocks.MONSTER_STEAK.get()) && state.getValue(SmeltableBlock.COOKED)) {
                ItemUtils.spawnItemEntity(level, pos.getCenter(), ModItems.LARGE_STEAK.get().getDefaultInstance()).setPickUpDelay(0);
                level.setBlockAndUpdate(pos, ModBlocks.TRAY_BLOCK.get().defaultBlockState()
                        .setValue(BlockStateProperties.WATERLOGGED, state.getValue(BlockStateProperties.WATERLOGGED))
                        .setValue(((VariantDecorationBlock) ModBlocks.TRAY_BLOCK.get()).getVariantProperty(), 1)
                        .setValue(BlockStateProperties.HORIZONTAL_FACING, state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
                itemInHand.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
                player.swing(hand);
                event.setCancellationResult(InteractionResult.SUCCESS);
            }

            if (state.is(ModBlocks.SAUSAGE_MACE_WEAPON_BLOCK.get()) && state.getValue(SmeltableBlock.COOKED)) {
                SausageMaceWeaponBlock sausageBlock = (SausageMaceWeaponBlock) ModBlocks.SAUSAGE_MACE_WEAPON_BLOCK.get();
                ItemUtils.spawnItemEntity(level, pos.getCenter(), new ItemStack(ModItems.CUBED_SAUSAGE.get(), 3));
                ItemUtils.spawnItemEntity(level, pos.getCenter(), Items.BAMBOO.getDefaultInstance());
                for (var part : sausageBlock.getOrderedParts(pos, state)) {
                    level.destroyBlock(part, false);
                }
                itemInHand.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
                player.swing(hand);
                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }

        if (itemInHand.isEmpty()) {
            if (state.is(ModBlocks.CHUNKY_SALMON.get())) {
                if (state.getValue(SmeltableBlock.COOKED)) {
                    ItemUtils.spawnItemEntity(level, pos.getCenter(), ModItems.CHUNKY_SMOKED_SALMON.get().getDefaultInstance()).setPickUpDelay(0);
                } else {
                    ItemUtils.spawnItemEntity(level, pos.getCenter(), ModItems.CHUNKY_SALMON.get().getDefaultInstance()).setPickUpDelay(0);
                }
                level.setBlockAndUpdate(pos, ModBlocks.TRAY_BLOCK.get().defaultBlockState()
                        .setValue(BlockStateProperties.WATERLOGGED, state.getValue(BlockStateProperties.WATERLOGGED))
                        .setValue(HorizontalDirectionalBlock.FACING, state.getValue(HorizontalDirectionalBlock.FACING)));
                player.swing(hand);
                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }

        if (itemInHand.is(ModItems.BREADED_RAW_CHICKEN.get())) {
            if (state.is(Blocks.LAVA_CAULDRON)) {
                itemInHand.shrink(1);
                ItemHandlerHelper.giveItemToPlayer(player, ModItems.BUCKET_OF_FRIED_CHICKEN.get().getDefaultInstance());
                player.swing(hand);
                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }
    }
}
