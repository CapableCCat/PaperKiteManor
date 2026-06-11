package com.kazi_cat.papercraft_magic_decoration.blockentity;

import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.inventory.container.KaziLuckyCatContainer;
import com.kazi_cat.papercraft_magic_decoration.utils.AABBUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class KaziLuckyCatBlockEntity extends SimpleAnimatedBlockEntity implements MenuProvider {
    private static final String TAG_ITEMS = "items";
    protected final ItemStackHandler items = new ItemStackHandler(11) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            refresh();
        }
    };

    public KaziLuckyCatBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.KAZI_LUCKY_CAT_BE.get(), pos, state);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return AABBUtils.fromTo(worldPosition, worldPosition.above());
    }

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new KaziLuckyCatContainer(i, inventory, this);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(TAG_ITEMS)) {
            items.deserializeNBT(tag.getCompound(TAG_ITEMS));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(TAG_ITEMS, items.serializeNBT());
    }

    public ItemStackHandler getItems() { return this.items; }
}
