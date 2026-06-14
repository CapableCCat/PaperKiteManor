package com.kazi_cat.papercraft_magic_decoration.compat.jei.category;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.datamap.data.SmeltableBlockData;
import com.kazi_cat.papercraft_magic_decoration.datamap.resources.SmeltableBlockDataReloadListener;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

public class SmeltableBlockCategory implements IRecipeCategory<SmeltableBlockData> {
    public static final RecipeType<SmeltableBlockData> TYPE = RecipeType.create(PaperKiteManor.MOD_ID, "smeltable_block", SmeltableBlockData.class);

    private static final ResourceLocation BG = PaperKiteManor.modLoc("textures/gui/jei/smeltable_block.png");
    private static final MutableComponent TITLE = Component.translatable("jei.papercraft_magic_decoration.smeltable_block.title");

    public static final int WIDTH = 132;
    public static final int HEIGHT = 50;

    private final IDrawable bgDraw;
    private final IDrawable iconDraw;

    public SmeltableBlockCategory(IGuiHelper guiHelper) {
        this.bgDraw = guiHelper.createDrawable(BG, 0, 0, WIDTH, HEIGHT);
        this.iconDraw = guiHelper.createDrawableItemLike(Items.CAMPFIRE);
    }

    public static List<SmeltableBlockData> getRecipes() {
        return SmeltableBlockDataReloadListener.INSTANCE.values().stream().toList();
    }

    @Override
    public void draw(SmeltableBlockData data, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.bgDraw.draw(guiGraphics);
        Component component = Component.translatable("jei.papercraft_magic_decoration.smeltable_block.smelt_time", data.time() / 20);
        if (data.flips() > 0) {
            component = component.copy().append(Component.translatable("jei.papercraft_magic_decoration.smeltable_block.flips", data.flips()));
        }
        drawCenteredString(guiGraphics, component, 66, 4);
    }

    private void drawCenteredString(GuiGraphics guiGraphics, Component text, int centerX, int y) {
        Font font = Minecraft.getInstance().font;
        FormattedCharSequence sequence = text.getVisualOrderText();
        guiGraphics.drawString(font, sequence, centerX - font.width(sequence) / 2, y, 0x555555, false);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SmeltableBlockData data, IFocusGroup focuses) {
        Ingredient input = Ingredient.of(data.ingredient());
        ItemStack output = data.result().getDefaultInstance();

        builder.addSlot(RecipeIngredientRole.INPUT, 14, 17).addIngredients(input);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 104, 17).addItemStack(output);
    }

    @Override
    public RecipeType<SmeltableBlockData> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return TITLE;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    @Nullable
    public IDrawable getIcon() {
        return iconDraw;
    }
}
