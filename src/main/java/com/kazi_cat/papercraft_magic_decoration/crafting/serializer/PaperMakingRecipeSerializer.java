package com.kazi_cat.papercraft_magic_decoration.crafting.serializer;

import com.google.gson.JsonObject;
import com.kazi_cat.papercraft_magic_decoration.crafting.recipe.PaperMakingRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.jetbrains.annotations.Nullable;

public class PaperMakingRecipeSerializer implements RecipeSerializer<PaperMakingRecipe> {
    @Override
    public PaperMakingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        Ingredient ingredient = Ingredient.fromJson(json.getAsJsonObject("ingredient"));
        ItemStack result = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true, true);
        return new PaperMakingRecipe(recipeId, ingredient, result);
    }

    @Override
    public @Nullable PaperMakingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buf) {
        return new PaperMakingRecipe(recipeId, Ingredient.fromNetwork(buf), buf.readItem());
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, PaperMakingRecipe recipe) {
        recipe.getIngredient().toNetwork(buf);
        buf.writeItem(recipe.getResult());
    }
}
