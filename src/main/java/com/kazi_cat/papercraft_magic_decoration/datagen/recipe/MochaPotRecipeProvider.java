package com.kazi_cat.papercraft_magic_decoration.datagen.recipe;

import com.kazi_cat.papercraft_magic_decoration.datagen.builder.MochaPotBuilder;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class MochaPotRecipeProvider extends ModRecipeProvider {
    public MochaPotRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        MochaPotBuilder.builder()
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.MILK_BUCKET)
                .addIngredient(ModItems.COFFEE_FRUIT.get())
                .addIngredient(Items.WATER_BUCKET)
                .setCarrier(new ItemStack(Items.GLASS_BOTTLE, 2))
                .setResult(new ItemStack(ModItems.NOCTURNAL_CAT_COFFEE.get(), 2))
                .setTime(120)
                .save(consumer);

        MochaPotBuilder.builder()
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.MILK_BUCKET)
                .addIngredient(ModItems.GOLDEN_COFFEE_FRUIT.get())
                .addIngredient(Items.WATER_BUCKET)
                .setCarrier(new ItemStack(Items.GLASS_BOTTLE, 2))
                .setResult(new ItemStack(ModItems.GOLD_MEDAL_COFFEE.get(), 2))
                .setTime(120)
                .save(consumer);
    }
}
