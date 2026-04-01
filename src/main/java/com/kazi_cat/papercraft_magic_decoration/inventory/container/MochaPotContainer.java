package com.kazi_cat.papercraft_magic_decoration.inventory.container;

import com.kazi_cat.papercraft_magic_decoration.blockentity.drink.MochaPotBlockEntity;
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

public class MochaPotContainer extends AbstractContainerMenu {
    public static final MenuType<MochaPotContainer> TYPE = IForgeMenuType.create(MochaPotContainer::new);

    protected final MochaPotBlockEntity blockEntity;
    protected final Level level;

    public MochaPotContainer(int containerId, Inventory playerInv, MochaPotBlockEntity blockEntity) {
        super(ModContainers.MOCHA_POT_CONTAINER.get(), containerId);
        this.blockEntity = blockEntity;
        this.level = playerInv.player.level();

        ItemStackHandler items = blockEntity.getItems();
        this.addSlot(new SlotItemHandler(items, 0, 62, 6) {
            @Override
            public int getMaxStackSize() { return 1; }

            @Override
            public int getMaxStackSize(ItemStack stack) { return 1; }
        });
        this.addSlot(new SlotItemHandler(items, 1, 97, 6) {
            @Override
            public int getMaxStackSize() { return 1; }

            @Override
            public int getMaxStackSize(ItemStack stack) { return 1; }
        });
        this.addSlot(new SlotItemHandler(items, 2, 80, 32) {
            @Override
            public int getMaxStackSize() { return 1; }

            @Override
            public int getMaxStackSize(ItemStack stack) { return 1; }
        });
        this.addSlot(new SlotItemHandler(items, 3, 80, 57) {
            @Override
            public int getMaxStackSize() { return 1; }

            @Override
            public int getMaxStackSize(ItemStack stack) { return 1; }
        });

        addPlayerInv(playerInv);
    }

    public MochaPotContainer(int containerId, Inventory playerInv, FriendlyByteBuf extraData) {
        this(containerId, playerInv, (MochaPotBlockEntity) Objects.requireNonNull(playerInv.player.level().getBlockEntity(extraData.readBlockPos())));
    }

    private void addPlayerInv(Inventory playerInv) {
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
            if (i > 3) {
                if (!this.moveItemStackTo(stack2, 0, 4, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack2, 4, 40, true)) {
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
        return this.blockEntity != null && this.blockEntity.getStatus() == 0
                && stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, ModBlocks.MOCHA_POT.get());
    }
}
