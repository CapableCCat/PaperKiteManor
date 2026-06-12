package com.kazi_cat.papercraft_magic_decoration.crafting.serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kazi_cat.papercraft_magic_decoration.crafting.recipe.DistillationRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.jetbrains.annotations.Nullable;

public class DistillationRecipeSerializer implements RecipeSerializer<DistillationRecipe> {
    @Override
    public DistillationRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        Ingredient wineBase = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "wineBase"));
        JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
        NonNullList<Ingredient> inputs = NonNullList.create();
        for (JsonElement e : ingredients) {
            inputs.add(Ingredient.fromJson(e));
        }
        ItemStack result = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true, true);
        int time = GsonHelper.getAsInt(json, "time");
        return new DistillationRecipe(recipeId, wineBase, inputs, result, time);
    }

    @Override
    public @Nullable DistillationRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buf) {
        int time = buf.readInt();
        int ingredientSize = buf.readInt();
        Ingredient wineBase = Ingredient.fromNetwork(buf);
        NonNullList<Ingredient> ingredients = NonNullList.withSize(ingredientSize, Ingredient.EMPTY);
        for (int i = 0; i < ingredientSize; i++) {
            ingredients.set(i, Ingredient.fromNetwork(buf));
        }
        ItemStack result = buf.readItem();
        return new DistillationRecipe(recipeId, wineBase, ingredients, result, time);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, DistillationRecipe recipe) {
        buf.writeInt(recipe.time());
        buf.writeInt(recipe.getIngredients().size());
        recipe.wineBase().toNetwork(buf);
        recipe.getIngredients().forEach(i -> i.toNetwork(buf));
        buf.writeItem(recipe.result());
    }
}
