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
                        Ingredient.of(Items.APPLE)
                ))
                .setResult(ModItems.BOTTLE_OF_LAND_NO1.get())
                .setTime(1200)
                .save(consumer);

        DistillerBuilder.builder()
                .setWineBase(Ingredient.of(ModItems.WHISKEY_RAW.get()))
                .setIngredients(Lists.newArrayList(
                        Ingredient.of(Items.BLAZE_POWDER),
                        Ingredient.of(Items.BLAZE_POWDER),
                        Ingredient.of(Items.BLAZE_POWDER),
                        Ingredient.of(Items.BLAZE_POWDER)
                ))
                .setResult(ModItems.BOTTLE_OF_BLAZE_WHISKEY.get())
                .setTime(1200)
                .save(consumer);

        DistillerBuilder.builder()
                .setWineBase(Ingredient.of(ModItems.WHISKEY_RAW.get()))
                .setIngredients(Lists.newArrayList(
                        Ingredient.of(Items.SEA_PICKLE),
                        Ingredient.of(Items.SEA_PICKLE),
                        Ingredient.of(Items.SEA_PICKLE),
                        Ingredient.of(Items.SEA_PICKLE)
                ))
                .setResult(ModItems.BOTTLE_OF_FERRY_WHISKEY.get())
                .setTime(1200)
                .save(consumer);

        DistillerBuilder.builder()
                .setWineBase(Ingredient.of(ModItems.WHISKEY_RAW.get()))
                .setIngredients(Lists.newArrayList(
                        Ingredient.of(Items.WARPED_FUNGUS),
                        Ingredient.of(Items.WARPED_FUNGUS),
                        Ingredient.of(Items.WARPED_FUNGUS),
                        Ingredient.of(Items.WARPED_FUNGUS)
                ))
                .setResult(ModItems.BOTTLE_OF_FLY_WHISKEY.get())
                .setTime(1200)
                .save(consumer);

        DistillerBuilder.builder()
                .setWineBase(Ingredient.of(ModItems.WHISKEY_RAW.get()))
                .setIngredients(Lists.newArrayList(
                        Ingredient.of(Items.CACTUS),
                        Ingredient.of(Items.CACTUS),
                        Ingredient.of(Items.CACTUS),
                        Ingredient.of(Items.CACTUS)
                ))
                .setResult(ModItems.BOTTLE_OF_LUCKY_CACTUS.get())
                .setTime(1200)
                .save(consumer);

        DistillerBuilder.builder()
                .setWineBase(Ingredient.of(ModItems.WHISKEY_RAW.get()))
                .setIngredients(Lists.newArrayList(
                        Ingredient.of(Items.WITHER_ROSE),
                        Ingredient.of(Items.WITHER_ROSE),
                        Ingredient.of(Items.WITHER_ROSE),
                        Ingredient.of(Items.WITHER_ROSE)
                ))
                .setResult(ModItems.BOTTLE_OF_POISON_RUM.get())
                .setTime(1200)
                .save(consumer);

        DistillerBuilder.builder()
                .setWineBase(Ingredient.of(ModItems.WHISKEY_RAW.get()))
                .setIngredients(Lists.newArrayList(
                        Ingredient.of(Items.TORCHFLOWER),
                        Ingredient.of(Items.TORCHFLOWER),
                        Ingredient.of(Items.TORCHFLOWER),
                        Ingredient.of(Items.TORCHFLOWER)
                ))
                .setResult(ModItems.PACK_OF_GUANG_S.get())
                .setTime(1200)
                .save(consumer);
    }
}
