package com.kazi_cat.papercraft_magic_decoration.crafting.serializer;

import com.google.gson.JsonObject;
import com.kazi_cat.papercraft_magic_decoration.crafting.recipe.PaperCuttingRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.jetbrains.annotations.Nullable;

public class PaperCuttingRecipeSerializer implements RecipeSerializer<PaperCuttingRecipe> {
    @Override
    public PaperCuttingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        Ingredient ingredient = Ingredient.fromJson(json.getAsJsonObject("ingredient"));
        ItemStack result = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true, true);
        return new PaperCuttingRecipe(recipeId, ingredient, result);
    }

    @Override
    public @Nullable PaperCuttingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buf) {
        Ingredient ingredient = Ingredient.fromNetwork(buf);
        ItemStack result = buf.readItem();
        return new PaperCuttingRecipe(recipeId, ingredient, result);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, PaperCuttingRecipe recipe) {
        recipe.getIngredient().toNetwork(buf);
        buf.writeItem(recipe.getResult());
    }
}
