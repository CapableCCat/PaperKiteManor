package com.kazi_cat.papercraft_magic_decoration.blockentity.decoration;

import com.kazi_cat.papercraft_magic_decoration.blockentity.BaseBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.inventory.container.DirtHoleContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DirtHoleBlockEntity extends BaseBlockEntity implements MenuProvider {
    private static final String ITEMS = "items";

    protected ItemStackHandler items = new ItemStackHandler(4);

    public DirtHoleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.DIRT_HOLE_BE.get(), pos, state);
    }

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new DirtHoleContainer(i, inventory, this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(ITEMS, this.items.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(ITEMS, Tag.TAG_COMPOUND)) {
            this.items = new ItemStackHandler();
            this.items.deserializeNBT(tag.getCompound(ITEMS));
        }
    }

    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<>();
        for (int i = 0; i < this.items.getSlots(); i++) {
            ItemStack itemStack = this.items.getStackInSlot(i);
            if (!itemStack.isEmpty()) {
                drops.add(itemStack);
            }
        }

        return drops;
    }

    public ItemStackHandler getItems() { return this.items; }
}
