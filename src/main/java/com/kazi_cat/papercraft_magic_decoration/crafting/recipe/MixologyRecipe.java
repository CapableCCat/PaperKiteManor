package com.kazi_cat.papercraft_magic_decoration.crafting.recipe;

import com.kazi_cat.papercraft_magic_decoration.init.ModRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record MixologyRecipe(ResourceLocation id, NonNullList<Ingredient> ingredients, ItemStack result) implements Recipe<SimpleContainer> {
    @Override
    public boolean matches(SimpleContainer simpleContainer, Level level) {
        for (int i = 0; i < 4; i++) {
            if (!ingredients.get(i).test(simpleContainer.getItem(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer, RegistryAccess registryAccess) {
        return getResultItem(registryAccess).copy();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }
    
    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.result;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() { return ModRecipes.MIXOLOGY_SERIALIZER.get(); }

    @Override
    public RecipeType<?> getType() { return ModRecipes.MIXOLOGY_RECIPE; }

    @Override
    public boolean isSpecial() { return true; }

    @Override
    public boolean canCraftInDimensions(int width, int height) { return false; }
}
