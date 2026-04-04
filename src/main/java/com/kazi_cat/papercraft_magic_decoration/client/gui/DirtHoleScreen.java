package com.kazi_cat.papercraft_magic_decoration.client.gui;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.inventory.container.DirtHoleContainer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DirtHoleScreen extends AbstractContainerScreen<DirtHoleContainer> {
    public static final ResourceLocation backgroundImage = PaperKiteManor.resourceLocation("textures/screens/dirt_hole_ui.png");

    public DirtHoleScreen(DirtHoleContainer container, Inventory inventory, Component title) {
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
        this.renderTooltip(graphics, mouseX, mouseY);
    }
}
