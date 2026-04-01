package com.kazi_cat.papercraft_magic_decoration.crafting.serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kazi_cat.papercraft_magic_decoration.crafting.recipe.MochaPotRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import org.jetbrains.annotations.Nullable;

public class MochaPotRecipeSerializer implements RecipeSerializer<MochaPotRecipe> {
    @Override
    public MochaPotRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
        NonNullList<Ingredient> inputs = NonNullList.createWithCapacity(4);
        for (JsonElement e : ingredients) {
            inputs.add(Ingredient.fromJson(e));
        }
        ItemStack carrier = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "carrier"), true, true);
        ItemStack result = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true, true);
        int time = GsonHelper.getAsInt(json, "time");
        return new MochaPotRecipe(recipeId, inputs, carrier, result, time);
    }

    @Override
    public @Nullable MochaPotRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buf) {
        NonNullList<Ingredient> inputs = NonNullList.createWithCapacity(4);
        for (int i = 0; i < 4; i++) {
            inputs.add(Ingredient.fromNetwork(buf));
        }
        return new MochaPotRecipe(recipeId, inputs, buf.readItem(), buf.readItem(), buf.readVarInt());
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, MochaPotRecipe recipe) {
        recipe.getIngredients().forEach(i -> i.toNetwork(buf));
        buf.writeItem(recipe.carrier());
        buf.writeItem(recipe.result());
        buf.writeVarInt(recipe.time());
    }
}
