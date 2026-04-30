package com.kazi_cat.papercraft_magic_decoration.crafting.recipe;

import com.kazi_cat.papercraft_magic_decoration.init.ModRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.StringUtils;

public class PaperCuttingRecipe extends SingleItemRecipe {
    public PaperCuttingRecipe(ResourceLocation id, Ingredient ingredient, ItemStack result) {
        super(ModRecipes.PAPERCUTTING_RECIPE, ModRecipes.PAPERCUTTING_SERIALIZER.get(),
                id, StringUtils.EMPTY, ingredient, result);
    }

    @Override
    public boolean matches(Container container, Level level) {
        return this.ingredient.test(container.getItem(0));
    }

    @Override
    public NonNullList<Ingredient> getIngredients() { return NonNullList.of(Ingredient.EMPTY, ingredient); }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public ItemStack getResult() {
        return this.result;
    }

    @Override
    public boolean isSpecial() { return true; }

    @Override
    public RecipeSerializer<?> getSerializer() { return ModRecipes.PAPERCUTTING_SERIALIZER.get(); }
}
