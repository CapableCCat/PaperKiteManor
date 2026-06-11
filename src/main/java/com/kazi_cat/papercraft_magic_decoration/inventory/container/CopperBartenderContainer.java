package com.kazi_cat.papercraft_magic_decoration.inventory.container;

import com.kazi_cat.papercraft_magic_decoration.blockentity.CopperBartenderBlockEntity;
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

public class CopperBartenderContainer extends AbstractContainerMenu {
    public static final MenuType<CopperBartenderContainer> TYPE = IForgeMenuType.create(CopperBartenderContainer::new);

    protected final CopperBartenderBlockEntity blockEntity;
    protected final Level level;

    public CopperBartenderContainer(int containerId, Inventory playerInv, CopperBartenderBlockEntity blockEntity) {
        super(ModContainers.COPPER_BARTENDER_CONTAINER.get(), containerId);
        this.blockEntity = blockEntity;
        this.level = playerInv.player.level();

        ItemStackHandler items = blockEntity.getItems();
        this.addSlot(new SlotItemHandler(items, 0, 43, 4));
        this.addSlot(new SlotItemHandler(items, 1, 44, 22));
        this.addSlot(new SlotItemHandler(items, 2, 43, 40));
        this.addSlot(new SlotItemHandler(items, 3, 44, 58));
        this.addSlot(new SlotItemHandler(items, 4, 116, 31){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        addPlayerInv(playerInv);
    }

    public CopperBartenderContainer(int containerId, Inventory playerInv, FriendlyByteBuf extraData) {
        this(containerId, playerInv, (CopperBartenderBlockEntity) playerInv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    private void addPlayerInv(Inventory playerInv) {
        for (int si = 0; si < 3; ++si)
            for (int sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(playerInv, sj + (si + 1) * 9, 8 + sj * 18, 84 + si * 18));
        for (int si = 0; si < 9; ++si)
            this.addSlot(new Slot(playerInv, si, 8 + si * 18, 142));
    }

    @Override
    public boolean clickMenuButton(Player player, int buttonId) {
        if (buttonId == 0) {
            if (!level.isClientSide && blockEntity != null) {
                if (blockEntity.tryShake(player.level())) {
                    player.closeContainer();
                    return true;
                }
            }
        }
        return super.clickMenuButton(player, buttonId);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        ItemStack stack1 = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        if (slot.hasItem()) {
            ItemStack stack2 = slot.getItem();
            stack1 = stack2.copy();
            if (i > 4) {
                if (!this.moveItemStackTo(stack2, 0, 5, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack2, 5, 41, true)) {
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
        return this.blockEntity != null && !this.blockEntity.isShaking()
                && stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, ModBlocks.COPPER_BARTENDER.get());
    }
}
