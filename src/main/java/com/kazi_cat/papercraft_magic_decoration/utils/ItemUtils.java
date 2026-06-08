package com.kazi_cat.papercraft_magic_decoration.utils;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemHandlerHelper;

public class ItemUtils {
    public static void getItemToLivingEntity(LivingEntity entity, ItemStack stack) {
        getItemToLivingEntity(entity, stack, -1);
    }

    public static void getItemToLivingEntity(LivingEntity entity, ItemStack stack, int preferredSlot) {
        if (stack.isEmpty()) {
            return;
        }
        if (entity.getMainHandItem().isEmpty()) {
            RandomSource random = entity.level().random;
            entity.setItemInHand(InteractionHand.MAIN_HAND, stack);
            entity.playSound(SoundEvents.ITEM_PICKUP, 0.2F, ((random.nextFloat() - random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
        } else if (entity instanceof Player player) {
            ItemHandlerHelper.giveItemToPlayer(player, stack, preferredSlot);
        } else {
            // 否则直接在实体所处位置生成物品
            ItemEntity dropItem = entity.spawnAtLocation(stack);
            if (dropItem != null) {
                dropItem.setPickUpDelay(0);
            }
        }
    }

    public static void spawnItemEntity(Level level, Vec3 pos, ItemStack itemStack) {
        ItemEntity itemEntity = new ItemEntity(level, pos.x(), pos.y(), pos.z(), itemStack);
        itemEntity.setDefaultPickUpDelay();
        level.addFreshEntity(itemEntity);
    }

    public static void spawnItemEntity(Level level, Vec3 pos, ItemStack itemStack, Vec3 deltaMovement) {
        ItemEntity itemEntity = new ItemEntity(level, pos.x(), pos.y(), pos.z(), itemStack);
        itemEntity.setDeltaMovement(deltaMovement);
        itemEntity.setDefaultPickUpDelay();
        level.addFreshEntity(itemEntity);
    }
}
