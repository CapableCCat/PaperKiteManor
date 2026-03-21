package com.kazi_cat.papercraft_magic_decoration.datagen.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.init.ModRecipes;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class DistillerBuilder implements RecipeBuilder {
    private static final String NAME = "distiller";

    private Ingredient wineBase;
    private List<Ingredient> ingredients = Lists.newArrayList();
    private ItemStack result = ItemStack.EMPTY;
    private int time;

    public static DistillerBuilder builder() { return new DistillerBuilder(); }

    public DistillerBuilder setWineBase(Ingredient wineBase) {
        this.wineBase = wineBase;
        return this;
    }

    public DistillerBuilder setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public DistillerBuilder setResult(ItemStack itemStack) {
        this.result = itemStack;
        return this;
    }

    public DistillerBuilder setResult(ItemLike itemLike) {
        this.result = new ItemStack(itemLike);
        return this;
    }

    public DistillerBuilder setTime(int time) {
        this.time = time;
        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String name, CriterionTriggerInstance trigger) {
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String groupName) {
        return this;
    }

    @Override
    public Item getResult() { return this.result.getItem(); }

    @Override
    public void save(Consumer<FinishedRecipe> output) {
        String path = RecipeBuilder.getDefaultRecipeId(this.getResult()).getPath();
        ResourceLocation filePath = PaperKiteManor.resourceLocation(NAME + "/" + path);
        this.save(output, filePath);
    }

    @Override
    public void save(Consumer<FinishedRecipe> output, String recipeId) {
        ResourceLocation filePath = PaperKiteManor.resourceLocation(NAME + "/" + recipeId);
        this.save(output, filePath);
    }

    @Override
    public void save(Consumer<FinishedRecipe> recipeOutput, ResourceLocation id) {
        recipeOutput.accept(new DistillerBuilderFinishedRecipe(id, wineBase, ingredients, result, time));
    }

    public static class DistillerBuilderFinishedRecipe implements FinishedRecipe {
        private final ResourceLocation id;
        private final Ingredient wineBase;
        private final List<Ingredient> ingredients;
        private final ItemStack result;
        private final int time;

        public DistillerBuilderFinishedRecipe(ResourceLocation id, Ingredient wineBase, List<Ingredient> ingredients, ItemStack result, int time) {
            this.id = id;
            this.wineBase = wineBase;
            this.ingredients = ingredients;
            this.result = result;
            this.time = time;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("wineBase", wineBase.toJson());
            JsonArray ingredients = new JsonArray();
            for (Ingredient ingredient : this.ingredients) {
                ingredients.add(ingredient.toJson());
            }
            json.add("ingredients", ingredients);
            JsonObject resultJson = new JsonObject();
            resultJson.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result.getItem())).toString());
            resultJson.addProperty("count", this.result.getCount());
            json.add("result", resultJson);
            json.addProperty("time", time);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() { return ModRecipes.DISTILLER_SERIALIZER.get(); }

        @Override
        @Nullable
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Override
        @Nullable
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
