package com.kazi_cat.papercraft_magic_decoration.datagen.recipe;

import com.kazi_cat.papercraft_magic_decoration.datagen.builder.PaperCuttingBuilder;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class PaperCuttingRecipeProvider extends ModRecipeProvider {
    public PaperCuttingRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        PaperCuttingBuilder.builder()
                .setIngredient(ModItems.BLACK_PAPER_BLOCK.get())
                .setResult(new ItemStack(ModItems.BLACK_PAPER.get(), 8))
                .save(consumer);

        PaperCuttingBuilder.builder()
                .setIngredient(ModItems.BLUE_PAPER_BLOCK.get())
                .setResult(new ItemStack(ModItems.BLUE_PAPER.get(), 8))
                .save(consumer);

        PaperCuttingBuilder.builder()
                .setIngredient(ModItems.COTTON_SERGE_BLOCK.get())
                .setResult(new ItemStack(ModItems.COTTON_SERGE.get(), 8))
                .save(consumer);

        PaperCuttingBuilder.builder()
                .setIngredient(ModItems.DEWY_MEMBRANE_BLOCK.get())
                .setResult(new ItemStack(ModItems.DEWY_MEMBRANE.get(), 8))
                .save(consumer);

        PaperCuttingBuilder.builder()
                .setIngredient(ModItems.RED_PAPER_BLOCK.get())
                .setResult(new ItemStack(ModItems.RED_PAPER.get(), 8))
                .save(consumer);

        PaperCuttingBuilder.builder()
                .setIngredient(ModItems.WHITE_PAPER_BLOCK.get())
                .setResult(new ItemStack(ModItems.WHITE_PAPER.get(), 8))
                .save(consumer);

        PaperCuttingBuilder.builder()
                .setIngredient(ModItems.YELLOW_PAPER_BLOCK.get())
                .setResult(new ItemStack(ModItems.YELLOW_PAPER.get(), 8))
                .save(consumer);
    }
}
