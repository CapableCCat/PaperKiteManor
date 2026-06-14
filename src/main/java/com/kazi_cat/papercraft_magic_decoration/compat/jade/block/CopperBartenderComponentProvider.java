package com.kazi_cat.papercraft_magic_decoration.compat.jade.block;

import com.kazi_cat.papercraft_magic_decoration.blockentity.CopperBartenderBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.compat.jade.ModPlugin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import snownee.jade.api.Accessor;
import snownee.jade.api.view.*;

import java.util.ArrayList;
import java.util.List;

public enum CopperBartenderComponentProvider implements IServerExtensionProvider<Object, ItemStack>, IClientExtensionProvider<ItemStack, ItemView> {
    INSTANCE;

    @Override
    public List<ClientViewGroup<ItemView>> getClientGroups(Accessor<?> accessor, List<ViewGroup<ItemStack>> list) {
        return ClientViewGroup.map(list, ItemView::new, null);
    }

    @Override
    @Nullable
    public List<ViewGroup<ItemStack>> getGroups(ServerPlayer serverPlayer, ServerLevel serverLevel, Object target, boolean showDetails) {
        if (target instanceof CopperBartenderBlockEntity bartender) {
            List<ItemStack> stacks = new ArrayList<>();
            for (int i = 0; i < bartender.getItems().getSlots(); i++) {
                stacks.add(bartender.getItems().getStackInSlot(i));
            }
            return List.of(new ViewGroup<>(stacks));
        }
        return null;
    }

    @Override
    public ResourceLocation getUid() {
        return ModPlugin.COPPER_BARTENDER;
    }
}
