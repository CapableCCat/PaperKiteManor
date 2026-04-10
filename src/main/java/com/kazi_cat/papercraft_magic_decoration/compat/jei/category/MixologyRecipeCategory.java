package com.kazi_cat.papercraft_magic_decoration.compat.jei.category;

import com.google.common.collect.Lists;
import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.crafting.recipe.MixologyRecipe;
import com.kazi_cat.papercraft_magic_decoration.crafting.recipe.PaperMakingRecipe;
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

public class MixologyRecipeCategory implements IRecipeCategory<MixologyRecipe> {
    public static final RecipeType<MixologyRecipe> TYPE = RecipeType.create(PaperKiteManor.MOD_ID, "mixology", MixologyRecipe.class);

    private static final ResourceLocation BG = PaperKiteManor.resourceLocation("textures/gui/jei/mixology.png");
    private static final MutableComponent TITLE = Component.literal("调酒");

    public static final int WIDTH = 114;
    public static final int HEIGHT = 147;

    private final IDrawable bgDraw;
    private final IDrawable iconDraw;

    public MixologyRecipeCategory(IGuiHelper guiHelper) {
        this.bgDraw = guiHelper.createDrawable(BG, 0, 0, WIDTH, HEIGHT);
        this.iconDraw = guiHelper.createDrawableItemLike(ModItems.COPPER_BARTENDER.get());
    }

    public static List<MixologyRecipe> getRecipes() {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return List.of();
        }
        List<MixologyRecipe> mixologyRecipes = Lists.newArrayList();
        mixologyRecipes.addAll(level.getRecipeManager().getAllRecipesFor(ModRecipes.MIXOLOGY_RECIPE));
        return mixologyRecipes;
    }

    @Override
    public void draw(MixologyRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        this.bgDraw.draw(guiGraphics);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, MixologyRecipe recipe, IFocusGroup focuses) {
        List<Ingredient> ingredients = recipe.ingredients();
        ItemStack output = recipe.result();

        builder.addSlot(RecipeIngredientRole.INPUT, 61, 117).addIngredients(ingredients.get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 60, 99).addIngredients(ingredients.get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 61, 81).addIngredients(ingredients.get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 60, 63).addIngredients(ingredients.get(3));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 61, 34).addItemStack(output);
    }

    @Override
    public RecipeType<MixologyRecipe> getRecipeType() {
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
