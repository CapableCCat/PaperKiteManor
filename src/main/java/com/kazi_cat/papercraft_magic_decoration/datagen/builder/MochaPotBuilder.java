package com.kazi_cat.papercraft_magic_decoration.datagen.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.init.ModRecipes;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;

public class MochaPotBuilder implements RecipeBuilder {
    private static final String NAME = "mocha_pot";

    private final NonNullList<Ingredient> ingredients = NonNullList.withSize(4, Ingredient.EMPTY);
    private int currentIndex;
    private ItemStack carrier = ItemStack.EMPTY;
    private ItemStack result = ItemStack.EMPTY;
    private int time;

    public static MochaPotBuilder builder() {
        return new MochaPotBuilder();
    }

    public MochaPotBuilder addIngredient(ItemLike itemLike) {
        this.ingredients.set(currentIndex, Ingredient.of(itemLike));
        currentIndex++;
        return this;
    }

    public MochaPotBuilder addIngredient(TagKey<Item> itemLike) {
        this.ingredients.set(currentIndex, Ingredient.of(itemLike));
        currentIndex++;
        return this;
    }

    public MochaPotBuilder setCarrier(ItemStack itemStack) {
        this.carrier = itemStack;
        return this;
    }

    public MochaPotBuilder setCarrier(ItemLike itemLike) {
        this.carrier = new ItemStack(itemLike);
        return this;
    }

    public MochaPotBuilder setResult(ItemStack itemStack) {
        this.result = itemStack;
        return this;
    }

    public MochaPotBuilder setResult(ItemLike itemLike) {
        this.result = new ItemStack(itemLike);
        return this;
    }

    public MochaPotBuilder setTime(int time) {
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
        recipeOutput.accept(new MochaPotBuilderFinishedRecipe(id, this.ingredients, this.carrier, this.result, this.time));
    }

    public static class MochaPotBuilderFinishedRecipe implements FinishedRecipe {
        private final ResourceLocation id;
        private final NonNullList<Ingredient> ingredients;
        private final ItemStack carrier;
        private final ItemStack result;
        private final int time;

        public MochaPotBuilderFinishedRecipe(ResourceLocation id, NonNullList<Ingredient> ingredients, ItemStack carrier, ItemStack result, int time) {
            this.id = id;
            this.ingredients = ingredients;
            this.carrier = carrier;
            this.result = result;
            this.time = time;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonArray ingredients = new JsonArray();
            for (Ingredient ingredient : this.ingredients) {
                ingredients.add(ingredient.toJson());
            }
            json.add("ingredients", ingredients);

            JsonObject carrierJson = new JsonObject();
            carrierJson.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.carrier.getItem())).toString());
            carrierJson.addProperty("count", this.carrier.getCount());
            json.add("carrier", carrierJson);

            JsonObject resultJson = new JsonObject();
            resultJson.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result.getItem())).toString());
            resultJson.addProperty("count", this.result.getCount());
            json.add("result", resultJson);

            json.addProperty("time", this.time);
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() { return ModRecipes.MOCHA_POT_SERIALIZER.get(); }

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
