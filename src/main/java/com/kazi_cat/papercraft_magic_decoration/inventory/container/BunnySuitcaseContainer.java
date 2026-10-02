package com.kazi_cat.papercraft_magic_decoration.inventory.container;

import com.kazi_cat.papercraft_magic_decoration.entity.WhiteRabbitMaidEntity;
import com.kazi_cat.papercraft_magic_decoration.init.ModContainers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class BunnySuitcaseContainer extends AbstractContainerMenu implements EntityBoundMenu {
    public static final MenuType<BunnySuitcaseContainer> TYPE = IForgeMenuType.create(BunnySuitcaseContainer::new);

    protected final WhiteRabbitMaidEntity entity;
    protected final Level level;

    public BunnySuitcaseContainer(int containerId, Inventory playerInv, WhiteRabbitMaidEntity entity) {
        super(ModContainers.BUNNY_SUITCASE_CONTAINER.get(), containerId);
        this.entity = entity;
        this.level = playerInv.player.level();

        ItemStackHandler items = entity.getInventory();
        this.addSlot(new SlotItemHandler(items, 0, 26, 21));
        this.addSlot(new SlotItemHandler(items, 1, 44, 21));
        this.addSlot(new SlotItemHandler(items, 2, 62, 21));
        this.addSlot(new SlotItemHandler(items, 3, 80, 21));
        this.addSlot(new SlotItemHandler(items, 4, 98, 21));
        this.addSlot(new SlotItemHandler(items, 5, 116, 21));
        this.addSlot(new SlotItemHandler(items, 6, 134, 21));
        this.addSlot(new SlotItemHandler(items, 7, 26, 39));
        this.addSlot(new SlotItemHandler(items, 8, 44, 39));
        this.addSlot(new SlotItemHandler(items, 9, 62, 39));
        this.addSlot(new SlotItemHandler(items, 10, 80, 39));
        this.addSlot(new SlotItemHandler(items, 11, 98, 39));
        this.addSlot(new SlotItemHandler(items, 12, 116, 39));
        this.addSlot(new SlotItemHandler(items, 13, 134, 39));
        this.addSlot(new SlotItemHandler(items, 14, 26, 57));
        this.addSlot(new SlotItemHandler(items, 15, 44, 57));
        this.addSlot(new SlotItemHandler(items, 16, 62, 57));
        this.addSlot(new SlotItemHandler(items, 17, 80, 57));
        this.addSlot(new SlotItemHandler(items, 18, 98, 57));
        this.addSlot(new SlotItemHandler(items, 19, 116, 57));
        this.addSlot(new SlotItemHandler(items, 20, 134, 57));

        for (int si = 0; si < 3; ++si)
            for (int sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(playerInv, sj + (si + 1) * 9, 8 + sj * 18, 84 + si * 18));
        for (int si = 0; si < 9; ++si)
            this.addSlot(new Slot(playerInv, si, 8 + si * 18, 142));
    }

    public BunnySuitcaseContainer(int containerId, Inventory playerInv, FriendlyByteBuf extraData) {
        this(containerId, playerInv, (WhiteRabbitMaidEntity) playerInv.player.level().getEntity(extraData.readVarInt()));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        ItemStack stack1 = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        if (slot.hasItem()) {
            ItemStack stack2 = slot.getItem();
            stack1 = stack2.copy();
            if (i > 20) {
                if (!this.moveItemStackTo(stack2, 0, 21, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack2, 21, 57, true)) {
                return ItemStack.EMPTY;
            }
            if (stack2.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return stack1;
    }

    @Override
    public boolean stillValid(Player player) {
        // 注意：isAlive() 只代表「未被移除标记之外」的状态 —— 实体被收纳（discard）后
        // 它仍可能为真，所以这里额外要求实体确实还在世界上，避免界面停留在已消失的女仆上。
        return this.entity != null && this.entity.isAlive() && !this.entity.isRemoved();
    }

    @Override
    public net.minecraft.world.entity.Entity getMenuEntity() {
        return this.entity;
    }
}
