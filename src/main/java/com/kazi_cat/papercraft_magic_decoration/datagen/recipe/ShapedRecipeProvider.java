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
                .unlockedBy("hsa_wheat", has(Items.WHEAT))
                .save(consumer);
    }
}