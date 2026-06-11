package com.kazi_cat.papercraft_magic_decoration.datagen.recipe;

import com.kazi_cat.papercraft_magic_decoration.datagen.builder.PaperMakingBuilder;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class PaperMakingRecipeProvider extends ModRecipeProvider {
    public PaperMakingRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        PaperMakingBuilder.builder()
                .setIngredient(Tags.Items.ORES_COPPER)
                .setResult(ModItems.WHITE_PAPER_BLOCK.get())
                .save(consumer);

        PaperMakingBuilder.builder()
                .setIngredient(ItemTags.LOGS)
                .setResult(ModItems.BLUE_PAPER_BLOCK.get())
                .save(consumer);

        PaperMakingBuilder.builder()
                .setIngredient(Items.AMETHYST_BLOCK)
                .setResult(ModItems.BLACK_PAPER_BLOCK.get())
                .save(consumer);

        PaperMakingBuilder.builder()
                .setIngredient(Items.GLOWSTONE)
                .setResult(ModItems.RED_PAPER_BLOCK.get())
                .save(consumer);

        PaperMakingBuilder.builder()
                .setIngredient(Items.MOSS_BLOCK)
                .setResult(ModItems.YELLOW_PAPER_BLOCK.get())
                .save(consumer);

        PaperMakingBuilder.builder()
                .setIngredient(Items.BAMBOO_BLOCK)
                .setResult(ModItems.DEWY_MEMBRANE_BLOCK.get())
                .save(consumer);

        PaperMakingBuilder.builder()
                .setIngredient(ItemTags.WOOL)
                .setResult(ModItems.COTTON_SERGE_BLOCK.get())
                .save(consumer);
    }
}
