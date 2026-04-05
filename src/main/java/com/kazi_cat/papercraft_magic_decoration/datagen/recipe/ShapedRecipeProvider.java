package com.kazi_cat.papercraft_magic_decoration.datagen.recipe;

import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class ShapedRecipeProvider extends ModRecipeProvider {
    public ShapedRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.AMETHYST_SCISSORS.get())
                .pattern("A A")
                .pattern(" C ")
                .pattern("C C")
                .define('A', Items.AMETHYST_SHARD)
                .define('C', Items.COPPER_INGOT)
                .unlockedBy("has_amethyst_shard", has(Items.AMETHYST_SHARD))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModItems.COPPER_BARTENDER.get())
                .pattern(" A ")
                .pattern("BCB")
                .pattern("BDB")
                .define('A', Items.CARVED_PUMPKIN)
                .define('B', Items.COPPER_INGOT)
                .define('C', Items.IRON_INGOT)
                .define('D', Items.PISTON)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.PAPER_CUTTING_TABLE.get())
                .pattern("AAA")
                .pattern("BCB")
                .pattern("BCB")
                .define('A', Items.PAPER)
                .define('B', ItemTags.PLANKS)
                .define('C', ItemTags.WOODEN_SLABS)
                .unlockedBy("has_paper", has(Items.PAPER))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.COPPER_STILL.get())
                .pattern("ABB")
                .pattern("C B")
                .pattern("DDD")
                .define('A', Items.COPPER_BLOCK)
                .define('B', Items.COPPER_INGOT)
                .define('C', Items.BLAZE_ROD)
                .define('D', Items.IRON_INGOT)
                .unlockedBy("has_blaze_rod", has(Items.BLAZE_ROD))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.WHISKEY_RAW.get())
                .pattern("ABA")
                .pattern("CDC")
                .pattern("CEC")
                .define('A', Items.WHEAT_SEEDS)
                .define('B', Items.WATER_BUCKET)
                .define('C', Items.GLASS_BOTTLE)
                .define('D', Items.SUGAR)
                .define('E', Items.WHEAT)
                .unlockedBy("has_wheat", has(Items.WHEAT))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.CUPBOARD_ORIGAMI.get())
                .pattern("ABA")
                .pattern("BAB")
                .pattern("ABA")
                .define('A', ModItems.BLUE_PAPER.get())
                .define('B', ModItems.WHITE_PAPER.get())
                .unlockedBy("has_amethyst_scissors", has(ModItems.AMETHYST_SCISSORS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.EDGED_CHALKBOARD_ORIGAMI.get())
                .pattern(" A ")
                .pattern("BBB")
                .pattern("BBB")
                .define('A', ModItems.WHITE_PAPER.get())
                .define('B', ModItems.BLUE_PAPER.get())
                .unlockedBy("has_amethyst_scissors", has(ModItems.AMETHYST_SCISSORS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.FIREPLACE_DECORATION_ORIGAMI.get())
                .pattern("AAA")
                .pattern("A A")
                .pattern("BCB")
                .define('A', ModItems.BLUE_PAPER.get())
                .define('B', ModItems.WHITE_PAPER.get())
                .define('C', ModItems.WHITE_PAPER_BLOCK.get())
                .unlockedBy("has_amethyst_scissors", has(ModItems.AMETHYST_SCISSORS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.LARGE_DINING_TABLE_ORIGAMI.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("BCB")
                .define('A', ModItems.COTTON_SERGE.get())
                .define('B', ModItems.BLUE_PAPER.get())
                .define('C', ModItems.BLUE_PAPER_BLOCK.get())
                .unlockedBy("has_amethyst_scissors", has(ModItems.AMETHYST_SCISSORS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.LONG_STORAGE_TABLE_ORIGAMI.get())
                .pattern("ABA")
                .pattern("CCC")
                .define('A', ModItems.BLUE_PAPER_BLOCK.get())
                .define('B', ModItems.COTTON_SERGE.get())
                .define('C', ModItems.BLUE_PAPER.get())
                .unlockedBy("has_amethyst_scissors", has(ModItems.AMETHYST_SCISSORS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.LOW_CABINET_WITH_TABLECLOTH_ORIGAMI.get())
                .pattern("A")
                .pattern("B")
                .pattern("C")
                .define('A', ModItems.COTTON_SERGE.get())
                .define('B', ModItems.BLUE_PAPER_BLOCK.get())
                .define('C', ModItems.BLUE_PAPER.get())
                .unlockedBy("has_amethyst_scissors", has(ModItems.AMETHYST_SCISSORS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.OLD_ORGAN_ORIGAMI.get())
                .pattern("AAA")
                .pattern("BCB")
                .pattern("D D")
                .define('A', ModItems.WHITE_PAPER.get())
                .define('B', ModItems.BLUE_PAPER_BLOCK.get())
                .define('C', ModItems.WHITE_PAPER_BLOCK.get())
                .define('D', ModItems.BLUE_PAPER.get())
                .unlockedBy("has_amethyst_scissors", has(ModItems.AMETHYST_SCISSORS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.RED_VELVET_CHAISE_LONGUE_ORIGAMI.get())
                .pattern("A  ")
                .pattern("ABA")
                .pattern("CDC")
                .define('A', ModItems.COTTON_SERGE.get())
                .define('B', ModItems.COTTON_SERGE_BLOCK.get())
                .define('C', ModItems.BLUE_PAPER.get())
                .define('D', ModItems.BLUE_PAPER_BLOCK.get())
                .unlockedBy("has_amethyst_scissors", has(ModItems.AMETHYST_SCISSORS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.WOODEN_BARREL_BOOKSHELF_ORIGAMI.get())
                .pattern("AA ")
                .pattern("AB ")
                .pattern(" AA")
                .define('A', ModItems.BLUE_PAPER.get())
                .define('B', ModItems.COTTON_SERGE.get())
                .unlockedBy("has_amethyst_scissors", has(ModItems.AMETHYST_SCISSORS.get()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.WOODWORKING_TABLE_ORIGAMI.get())
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .define('A', ModItems.BLUE_PAPER.get())
                .define('B', ModItems.BLUE_PAPER_BLOCK.get())
                .unlockedBy("has_amethyst_scissors", has(ModItems.AMETHYST_SCISSORS.get()))
                .save(consumer);
    }
}