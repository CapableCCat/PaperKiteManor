package com.kazi_cat.papercraft_magic_decoration.crafting.serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kazi_cat.papercraft_magic_decoration.crafting.recipe.MixologyRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.jetbrains.annotations.Nullable;

public class MixologyRecipeSerializer implements RecipeSerializer<MixologyRecipe> {
    @Override
    public MixologyRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
        NonNullList<Ingredient> inputs = NonNullList.createWithCapacity(4);
        for (JsonElement e : ingredients) {
            inputs.add(Ingredient.fromJson(e));
        }
        ItemStack result = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true, true);
        return new MixologyRecipe(recipeId, inputs, result);
    }

    @Override
    public @Nullable MixologyRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buf) {
        NonNullList<Ingredient> inputs = NonNullList.createWithCapacity(4);
        for (int i = 0; i < 4; i++) {
            inputs.add(Ingredient.fromNetwork(buf));
        }
        ItemStack result = buf.readItem();
        return new MixologyRecipe(recipeId, inputs, result);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, MixologyRecipe recipe) {
        for (int i = 0; i < 4; i++) {
            recipe.getIngredients().get(i).toNetwork(buf);
        }
        buf.writeItem(recipe.result());
    }
}
