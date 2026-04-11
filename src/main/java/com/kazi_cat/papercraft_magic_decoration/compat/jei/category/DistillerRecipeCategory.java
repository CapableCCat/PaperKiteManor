package com.kazi_cat.papercraft_magic_decoration.compat.jei.category;

import com.google.common.collect.Lists;
import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.crafting.recipe.DistillerRecipe;
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
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DistillerRecipeCategory implements IRecipeCategory<DistillerRecipe> {
    public static final RecipeType<DistillerRecipe> TYPE = RecipeType.create(PaperKiteManor.MOD_ID, "distiller", DistillerRecipe.class);

    private static final ResourceLocation BG = PaperKiteManor.resourceLocation("textures/gui/jei/distiller.png");
    private static final MutableComponent TITLE = Component.translatable("jei.papercraft_magic_decoration.distiller.title");

    public static final int WIDTH = 192;
    public static final int HEIGHT = 103;

    private final IDrawable bgDraw;
    private final IDrawable iconDraw;

    public DistillerRecipeCategory(IGuiHelper guiHelper) {
        this.bgDraw = guiHelper.createDrawable(BG, 0, 0, WIDTH, HEIGHT);
        this.iconDraw = guiHelper.createDrawableItemLike(ModItems.COPPER_STILL.get());
    }

    public static List<DistillerRecipe> getRecipes() {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return List.of();
        }
        List<DistillerRecipe> distillerRecipes = Lists.newArrayList();
        distillerRecipes.addAll(level.getRecipeManager().getAllRecipesFor(ModRecipes.DISTILLER_RECIPE));
        return distillerRecipes;
    }

    @Override
    public void draw(DistillerRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.bgDraw.draw(guiGraphics);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, DistillerRecipe recipe, IFocusGroup focuses) {
        List<Ingredient> ingredients = NonNullList.withSize(9, Ingredient.EMPTY);
        for (int i = 0; i < recipe.ingredients().size(); i++) {
            ingredients.set(i, recipe.ingredients().get(i));
        }
        ItemStack output = recipe.result();

        builder.addSlot(RecipeIngredientRole.INPUT, 11, 25).addIngredients(ingredients.get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 29, 25).addIngredients(ingredients.get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 47, 25).addIngredients(ingredients.get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 11, 43).addIngredients(ingredients.get(3));
        builder.addSlot(RecipeIngredientRole.INPUT, 29, 43).addIngredients(ingredients.get(4));
        builder.addSlot(RecipeIngredientRole.INPUT, 47, 43).addIngredients(ingredients.get(5));
        builder.addSlot(RecipeIngredientRole.INPUT, 11, 61).addIngredients(ingredients.get(6));
        builder.addSlot(RecipeIngredientRole.INPUT, 29, 61).addIngredients(ingredients.get(7));
        builder.addSlot(RecipeIngredientRole.INPUT, 47, 61).addIngredients(ingredients.get(8));
        builder.addSlot(RecipeIngredientRole.INPUT, 164, 20).addIngredients(recipe.wineBase());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 164, 70).addItemStack(output);
    }

    @Override
    public RecipeType<DistillerRecipe> getRecipeType() {
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
