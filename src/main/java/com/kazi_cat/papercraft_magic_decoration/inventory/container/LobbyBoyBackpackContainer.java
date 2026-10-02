package com.kazi_cat.papercraft_magic_decoration.inventory.container;

import com.kazi_cat.papercraft_magic_decoration.entity.BlackCatLobbyBoyEntity;
import com.kazi_cat.papercraft_magic_decoration.init.ModContainers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Optional;

public class LobbyBoyBackpackContainer extends AbstractContainerMenu implements EntityBoundMenu {
    public static final MenuType<LobbyBoyBackpackContainer> TYPE = IForgeMenuType.create(LobbyBoyBackpackContainer::new);

    public final BlackCatLobbyBoyEntity entity;
    protected final Level level;
    protected final SimpleContainer container;

    public LobbyBoyBackpackContainer(int containerId, Inventory playerInv, BlackCatLobbyBoyEntity entity) {
        super(ModContainers.LOBBY_BOY_BACKPACK_CONTAINER.get(), containerId);
        this.entity = entity;
        this.level = playerInv.player.level();
        this.container = new SimpleContainer(7);

        this.addSlot(new Slot(container, 0, 25, 21) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(Items.AMETHYST_SHARD);
            }
        });

        this.addSlot(new TradeSlot(container, 1, entity, 62, 16));
        this.addSlot(new TradeSlot(container, 2, entity, 98, 16));
        this.addSlot(new TradeSlot(container, 3, entity, 134, 16));
        this.addSlot(new TradeSlot(container, 4, entity, 62, 44));
        this.addSlot(new TradeSlot(container, 5, entity, 98, 44));
        this.addSlot(new TradeSlot(container, 6, entity, 134, 44));

        for (int si = 0; si < 3; ++si)
            for (int sj = 0; sj < 9; ++sj)
                this.addSlot(new Slot(playerInv, sj + (si + 1) * 9, 8 + sj * 18, 84 + si * 18));
        for (int si = 0; si < 9; ++si)
            this.addSlot(new Slot(playerInv, si, 8 + si * 18, 142));
    }

    public LobbyBoyBackpackContainer(int containerId, Inventory playerInv, FriendlyByteBuf extraData) {
        this(containerId, playerInv, (BlackCatLobbyBoyEntity) playerInv.player.level().getEntity(extraData.readVarInt()));
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        ItemStack stack1 = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        if (slot.hasItem()) {
            ItemStack stack2 = slot.getItem();
            stack1 = stack2.copy();
            if (i > 7) {
                if (!(stack2.is(Items.AMETHYST_SHARD) && this.moveItemStackTo(stack2, 0, 1, false))) {
                    return ItemStack.EMPTY;
                }
            } else if (i > 0) {
                if (!(slot.mayPickup(player) && this.moveItemStackTo(stack2.copy(), 7, 43, true))) {
                    return ItemStack.EMPTY;
                }
                slot.onTake(player, stack2);
            } else if (!this.moveItemStackTo(stack2, 7, 43, true)) {
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
        // 同 BunnySuitcaseContainer：discard() 之后 isAlive() 仍可能为真，
        // 所以额外要求实体确实还在世界上。
        return this.entity.isAlive() && !this.entity.isRemoved();
    }

    @Override
    public net.minecraft.world.entity.Entity getMenuEntity() {
        return this.entity;
    }

    @Override
    public void removed(Player player) {
        ItemStack stack = container.getItem(0);
        if (!stack.isEmpty()) {
            ItemHandlerHelper.giveItemToPlayer(player, stack);
        }
        super.removed(player);
    }

    public static class TradeSlot extends Slot {
        protected final BlackCatLobbyBoyEntity entity;

        public TradeSlot(Container container, int slot, BlackCatLobbyBoyEntity entity, int x, int y) {
            super(container, slot, x, y);
            this.entity = entity;
        }

        @Override
        public ItemStack getItem() {
            return getOffer().result;
        }

        @Override
        public boolean mayPickup(Player pPlayer) {
            return container.getItem(0).getCount() >= getOffer().price && getOffer().tradeLimit > 0;
        }

        @Override
        public boolean mayPlace(ItemStack pStack) {
            return false;
        }

        public Optional<ItemStack> tryRemove(int pCount, int pDecrement, Player pPlayer) {
            pCount = Math.min(pCount, pDecrement);
            if (pCount < getOffer().result.getCount() || !mayPickup(pPlayer)) {
                return Optional.empty();
            }
            ItemStack itemstack = getOffer().result.copy();
            return Optional.of(itemstack);
        }

        @Override
        public void onTake(Player pPlayer, ItemStack pStack) {
            BlackCatLobbyBoyEntity.Offer offer = getOffer();
            offer.tradeLimit--;
            if (offer.tradeLimit <= 0) {
                entity.getOffers().set(this.getSlotIndex() - 1, BlackCatLobbyBoyEntity.Offer.EMPTY);
            }
            entity.setPersistenceRequired();
            container.removeItem(0, offer.price);
            setChanged();
        }

        public BlackCatLobbyBoyEntity.Offer getOffer() {
            return entity.getOffers().get(this.getSlotIndex() - 1);
        }
    }
}
