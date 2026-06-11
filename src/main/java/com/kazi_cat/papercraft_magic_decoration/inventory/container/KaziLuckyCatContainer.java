package com.kazi_cat.papercraft_magic_decoration.inventory.container;

import com.kazi_cat.papercraft_magic_decoration.blockentity.KaziLuckyCatBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.ModContainers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class KaziLuckyCatContainer extends AbstractContainerMenu {
    public static final MenuType<KaziLuckyCatContainer> TYPE = IForgeMenuType.create(KaziLuckyCatContainer::new);

    protected final KaziLuckyCatBlockEntity blockEntity;
    protected final Level level;

    public KaziLuckyCatContainer(int containerId, Inventory playerInv, KaziLuckyCatBlockEntity blockEntity) {
        super(ModContainers.KAZI_LUCKY_CAT_CONTAINER.get(), containerId);
        this.blockEntity = blockEntity;
        this.level = playerInv.player.level();

        ItemStackHandler items = blockEntity.getItems();
        this.addSlot(new SlotItemHandler(items, 0, 62, 7));
        this.addSlot(new SlotItemHandler(items, 1, 98, 7));
        this.addSlot(new SlotItemHandler(items, 2, 62, 25));
        this.addSlot(new SlotItemHandler(items, 3, 80, 25));
        this.addSlot(new SlotItemHandler(items, 4, 98, 25));
        this.addSlot(new SlotItemHandler(items, 5, 62, 43));
        this.addSlot(new SlotItemHandler(items, 6, 80, 43));
        this.addSlot(new SlotItemHandler(items, 7, 98, 43));
        this.addSlot(new SlotItemHandler(items, 8, 62, 61));
        this.addSlot(new SlotItemHandler(items, 9, 80, 61));
        this.addSlot(new SlotItemHandler(items, 10, 98, 61));

        addPlayerInv(playerInv);
    }

    public KaziLuckyCatContainer(int containerId, Inventory playerInv, FriendlyByteBuf extraData) {
        this(containerId, playerInv, (KaziLuckyCatBlockEntity) Objects.requireNonNull(playerInv.player.level().getBlockEntity(extraData.readBlockPos())));
    }

    protected void addPlayerInv(Inventory playerInv) {
        for (int si = 0; si < 3; ++si)
            for (int sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(playerInv, sj + (si + 1) * 9, 8 + sj * 18, 84 + si * 18));
        for (int si = 0; si < 9; ++si)
            this.addSlot(new Slot(playerInv, si, 8 + si * 18, 142));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        ItemStack stack1 = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        if (slot.hasItem()) {
            ItemStack stack2 = slot.getItem();
            stack1 = stack2.copy();
            if (i > 10) {
                if (!this.moveItemStackTo(stack2, 0, 11, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack2, 11, 47, true)) {
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
        return this.blockEntity != null && stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, ModBlocks.KAZI_LUCKY_CAT.get());
    }
}
