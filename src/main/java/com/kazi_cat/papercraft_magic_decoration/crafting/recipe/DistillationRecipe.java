package com.kazi_cat.papercraft_magic_decoration.crafting.recipe;

import com.kazi_cat.papercraft_magic_decoration.crafting.container.DistillationContainer;
import com.kazi_cat.papercraft_magic_decoration.init.ModRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.RecipeMatcher;

public record DistillationRecipe(ResourceLocation id, Ingredient wineBase, NonNullList<Ingredient> ingredients,
                                 ItemStack result, int time) implements Recipe<DistillationContainer> {
    @Override
    public boolean matches(DistillationContainer container, Level level) {
        return wineBase.test(container.getWineBase()) && RecipeMatcher.findMatches(container.getItems(), ingredients) != null;
    }

    @Override
    public ItemStack assemble(DistillationContainer container, RegistryAccess registryAccess) {return getResultItem(registryAccess).copy();}

    @Override
    public NonNullList<Ingredient> getIngredients() { return ingredients; }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.result;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() { return ModRecipes.DISTILLATION_SERIALIZER.get(); }

    @Override
    public RecipeType<?> getType() { return ModRecipes.DISTILLATION_RECIPE; }

    @Override
    public boolean isSpecial() { return true; }

    @Override
    public boolean canCraftInDimensions(int width, int height) { return false; }
}
