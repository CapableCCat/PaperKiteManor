package com.kazi_cat.papercraft_magic_decoration.datagen.recipe;

import com.google.common.collect.Lists;
import com.kazi_cat.papercraft_magic_decoration.datagen.builder.DistillerBuilder;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

public class DistillerRecipeProvider extends ModRecipeProvider {
    public DistillerRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        DistillerBuilder.builder()
                .setWineBase(Ingredient.of(ModItems.WHISKEY_RAW.get()))
                .setIngredients(Lists.newArrayList(
                        Ingredient.of(Items.APPLE),
                        Ingredient.of(Items.APPLE),
                        Ingredient.of(Items.APPLE),
                        Ingredient.of(Items.APPLE),
                        Ingredient.of(Items.APPLE),
                        Ingredient.of(Items.APPLE)
                ))
                .setResult(ModItems.BOTTLE_OF_LAND_NO1.get())
                .setTime(600)
                .save(consumer);
    }
}
