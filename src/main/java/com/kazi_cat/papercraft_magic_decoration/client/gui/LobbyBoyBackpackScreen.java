package com.kazi_cat.papercraft_magic_decoration.client.gui;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.entity.BlackCatLobbyBoyEntity;
import com.kazi_cat.papercraft_magic_decoration.inventory.container.LobbyBoyBackpackContainer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class LobbyBoyBackpackScreen extends AbstractContainerScreen<LobbyBoyBackpackContainer> {
    public static final ResourceLocation backgroundImage = PaperKiteManor.modLoc("textures/screens/lobby_boy_backpack.png");

    public LobbyBoyBackpackScreen(LobbyBoyBackpackContainer container, Inventory inventory, Component title) {
        super(container, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float v, int i, int i1) {
        renderBackground(graphics);
        graphics.blit(backgroundImage, this.leftPos, this.topPos, 0, 0, 176, 166, 176, 166);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float pt) {
        super.render(graphics, mouseX, mouseY, pt);
        if (this.hoveredSlot instanceof LobbyBoyBackpackContainer.TradeSlot tradeSlot && !tradeSlot.getOffer().isEmpty()) {
            ItemStack itemStack = tradeSlot.getItem();
            BlackCatLobbyBoyEntity.Offer offer = tradeSlot.getOffer();
            List<Component> tooltip = this.getTooltipFromContainerItem(itemStack);
            tooltip.add(Component.literal("价格: %d".formatted(offer.price)).withStyle(ChatFormatting.GOLD));
            tooltip.add(Component.literal("可购买次数: %d".formatted(offer.tradeLimit)).withStyle(ChatFormatting.GOLD));
            graphics.renderTooltip(font, tooltip, itemStack.getTooltipImage(), itemStack, mouseX, mouseY);
        } else {
            this.renderTooltip(graphics, mouseX, mouseY);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {}
}