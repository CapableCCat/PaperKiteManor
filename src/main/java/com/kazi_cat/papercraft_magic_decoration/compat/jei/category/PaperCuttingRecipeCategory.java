package com.kazi_cat.papercraft_magic_decoration.compat.jei.category;

import com.google.common.collect.Lists;
import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.crafting.recipe.PaperCuttingRecipe;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import com.kazi_cat.papercraft_magic_decoration.init.ModRecipes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PaperCuttingRecipeCategory implements IRecipeCategory<PaperCuttingRecipe> {
    public static final RecipeType<PaperCuttingRecipe> TYPE = RecipeType.create(PaperKiteManor.MOD_ID, "papercutting", PaperCuttingRecipe.class);

    private static final ResourceLocation BG = PaperKiteManor.modLoc("textures/gui/jei/papercutting.png");
    private static final MutableComponent TITLE = Component.translatable("jei.papercraft_magic_decoration.papercutting.title");

    public static final int WIDTH = 155;
    public static final int HEIGHT = 86;

    private final IDrawable bgDraw;
    private final IDrawable iconDraw;

    public PaperCuttingRecipeCategory(IGuiHelper guiHelper) {
        this.bgDraw = guiHelper.createDrawable(BG, 0, 0, WIDTH, HEIGHT);
        this.iconDraw = guiHelper.createDrawableItemLike(ModItems.PAPER_CUTTING_TABLE.get());
    }

    public static List<PaperCuttingRecipe> getRecipes() {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return List.of();
        }
        List<PaperCuttingRecipe> paperCuttingRecipes = Lists.newArrayList();
        paperCuttingRecipes.addAll(level.getRecipeManager().getAllRecipesFor(ModRecipes.PAPERCUTTING_RECIPE));
        return paperCuttingRecipes;
    }

    @Override
    public void draw(PaperCuttingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.bgDraw.draw(guiGraphics);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, PaperCuttingRecipe recipe, IFocusGroup focuses) {
        Ingredient input = recipe.getIngredient();
        ItemStack output = recipe.getResult();

        builder.addSlot(RecipeIngredientRole.INPUT, 32, 29).addIngredients(input);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 118, 38).addItemStack(output);
    }

    @Override
    public RecipeType<PaperCuttingRecipe> getRecipeType() {
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
