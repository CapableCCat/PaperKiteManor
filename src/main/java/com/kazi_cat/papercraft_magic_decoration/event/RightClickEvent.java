package com.kazi_cat.papercraft_magic_decoration.event;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.api.block.SmeltableBlock;
import com.kazi_cat.papercraft_magic_decoration.block.decoration.VariantDecorationBlock;
import com.kazi_cat.papercraft_magic_decoration.block.smeltable.SausageMaceWeaponBlock;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import com.kazi_cat.papercraft_magic_decoration.item.StorageToolItem;
import com.kazi_cat.papercraft_magic_decoration.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
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

    /**
     * 收纳工具：手持**空符**右击宠物 → 把宠物收进符里。
     *
     * <p>为什么用 {@link PlayerInteractEvent.EntityInteract} 而不是在物品里覆写
     * {@code Item#interactLivingEntity}：本项目的实体交互一律走事件订阅
     * （见上方 {@link #onRightClickBlock}），保持一处集中、便于排查。
     *
     * <p><b>与实体自身交互的先后顺序</b>：本事件在实体自己的 {@code mobInteract} **之前**触发，
     * 所以「手持空符右击女仆」会被这里截住，不会先去开女仆的背包界面。
     * 空手右击时物品不是收纳工具，本方法直接返回，女仆的开包行为不受影响。
     */
    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        InteractionHand hand = event.getHand();
        ItemStack stack = event.getItemStack();

        // 只处理主手：否则一次右击会进入两次（主手 + 副手各一次）
        if (hand != InteractionHand.MAIN_HAND) {
            return;
        }
        if (!(stack.getItem() instanceof StorageToolItem tool)) {
            return;
        }

        Entity target = event.getTarget();

        // 被收的对象必须*不是*玩家自己。除了避免误触，也防「左手收右手」这类自指操作出怪问题
        if (target == player) {
            return;
        }

        // 判定不通过就静默放行，让实体自己的交互继续（例如女仆的喂食 / 开背包）
        if (!tool.canStore(target, player)) {
            return;
        }

        if (level.isClientSide()) {
            // 客户端只负责表现，真正改动在服务端；返回 SUCCESS 以阻止实体交互继续
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
            return;
        }

        // ① 关掉「正是这个实体」的界面，堵住「收纳后仍能从已消失实体隔空取物」
        StorageToolItem.closeOpenContainer(target, player);

        // ② 写入数据。失败时不得移除实体 —— 物品没拿到数据，实体必须留在世界里
        StorageToolItem.Result result = tool.writeEntityData(stack, target);
        if (result == StorageToolItem.Result.TOO_LARGE) {
            player.displayClientMessage(
                    Component.translatable("item.papercraft_magic_decoration.storage_tool.too_large"), true);
            event.setCancellationResult(InteractionResult.FAIL);
            event.setCanceled(true);
            return;
        }

        // ③ 换成对应的满符；换不成就别移除实体（否则数据只存在于被换掉的那个 stack 上）
        if (!tool.swapToFullTool(stack, player, hand)) {
            return;
        }

        // ④ 移除实体并给反馈
        target.discard();
        level.playSound(null, target.getX(), target.getY(), target.getZ(),
                SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.8F, 1.2F);
        player.displayClientMessage(
                Component.translatable("item.papercraft_magic_decoration.storage_tool.stored",
                        Component.translatable(target.getType().getDescriptionId())), true);

        event.setCancellationResult(InteractionResult.SUCCESS);
        event.setCanceled(true);
    }
}
