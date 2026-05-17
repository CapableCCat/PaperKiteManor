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

        PaperCuttingBuilder.builder()
                .setIngredient(ModItems.LOW_CABINET_WITH_TABLECLOTH_ORIGAMI.get())
                .setResult(ModItems.LOW_CABINET_WITH_TABLECLOTH.get())
                .save(consumer);

        PaperCuttingBuilder.builder()
                .setIngredient(ModItems.WOODEN_BARREL_BOOKSHELF_ORIGAMI.get())
                .setResult(ModItems.WOODEN_BARREL_BOOKSHELF.get())
                .save(consumer);

        PaperCuttingBuilder.builder()
                .setIngredient(ModItems.WOODWORKING_TABLE_ORIGAMI.get())
                .setResult(ModItems.WOODWORKING_TABLE.get())
                .save(consumer);

        PaperCuttingBuilder.builder()
                .setIngredient(ModItems.LONG_STORAGE_TABLE_ORIGAMI.get())
                .setResult(ModItems.LONG_STORAGE_TABLE.get())
                .save(consumer);

        PaperCuttingBuilder.builder()
                .setIngredient(ModItems.EDGED_CHALKBOARD_ORIGAMI.get())
                .setResult(ModItems.EDGED_CHALKBOARD.get())
                .save(consumer);

        PaperCuttingBuilder.builder()
                .setIngredient(ModItems.CUPBOARD_ORIGAMI.get())
                .setResult(ModItems.CUPBOARD.get())
                .save(consumer);

        PaperCuttingBuilder.builder()
                .setIngredient(ModItems.FIREPLACE_DECORATION_ORIGAMI.get())
                .setResult(ModItems.FIREPLACE_DECORATION.get())
                .save(consumer);

        PaperCuttingBuilder.builder()
                .setIngredient(ModItems.LARGE_DINING_TABLE_ORIGAMI.get())
                .setResult(ModItems.LARGE_DINING_TABLE.get())
                .save(consumer);

        PaperCuttingBuilder.builder()
                .setIngredient(ModItems.OLD_ORGAN_ORIGAMI.get())
                .setResult(ModItems.OLD_ORGAN.get())
                .save(consumer);

        PaperCuttingBuilder.builder()
                .setIngredient(ModItems.RED_VELVET_CHAISE_LONGUE_ORIGAMI.get())
                .setResult(ModItems.RED_VELVET_CHAISE_LONGUE.get())
                .save(consumer);
    }
}
