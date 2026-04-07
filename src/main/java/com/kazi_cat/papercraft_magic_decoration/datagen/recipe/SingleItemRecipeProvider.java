package com.kazi_cat.papercraft_magic_decoration.datagen.recipe;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Consumer;

public class SingleItemRecipeProvider extends ModRecipeProvider {
    public SingleItemRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(ModItems.KEY_UNDER_THE_LAKE.get()),
                RecipeCategory.DECORATIONS,
                ModItems.BLACK_AND_WHITE_CHECKER_BOARD_TILE.get(),
                8)
                .unlockedBy("has_key_under_the_lake", has(ModItems.KEY_UNDER_THE_LAKE.get()))
                .save(consumer, stoneCuttingLoc(ModItems.BLACK_AND_WHITE_CHECKER_BOARD_TILE.get()));

        SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(ModItems.KEY_UNDER_THE_LAKE.get()),
                RecipeCategory.DECORATIONS,
                ModItems.BLUE_AND_WHITE_CHECKER_BOARD_TILE.get(),
                8)
                .unlockedBy("has_key_under_the_lake", has(ModItems.KEY_UNDER_THE_LAKE.get()))
                .save(consumer, stoneCuttingLoc(ModItems.BLUE_AND_WHITE_CHECKER_BOARD_TILE.get()));

        SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(ModItems.KEY_UNDER_THE_LAKE.get()),
                RecipeCategory.DECORATIONS,
                ModItems.EMERALD_BLUE_EARTH_TILE.get(),
                8)
                .unlockedBy("has_key_under_the_lake", has(ModItems.KEY_UNDER_THE_LAKE.get()))
                .save(consumer, stoneCuttingLoc(ModItems.EMERALD_BLUE_EARTH_TILE.get()));

        SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(ModItems.KEY_UNDER_THE_LAKE.get()),
                RecipeCategory.DECORATIONS,
                ModItems.WINE_AROMA_RED_WALLPAPER_WALL.get(),
                8)
                .unlockedBy("has_key_under_the_lake", has(ModItems.KEY_UNDER_THE_LAKE.get()))
                .save(consumer, stoneCuttingLoc(ModItems.WINE_AROMA_RED_WALLPAPER_WALL.get()));

        SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(ModItems.KEY_UNDER_THE_LAKE.get()),
                RecipeCategory.DECORATIONS,
                ModItems.WINE_AROMA_BLUE_WALLPAPER_WALL.get(),
                8)
                .unlockedBy("has_key_under_the_lake", has(ModItems.KEY_UNDER_THE_LAKE.get()))
                .save(consumer, stoneCuttingLoc(ModItems.WINE_AROMA_BLUE_WALLPAPER_WALL.get()));

        SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(ModItems.KEY_UNDER_THE_LAKE.get()),
                RecipeCategory.DECORATIONS,
                ModItems.UNDERGROUND_WALLPAPER_WALL.get(),
                8)
                .unlockedBy("has_key_under_the_lake", has(ModItems.KEY_UNDER_THE_LAKE.get()))
                .save(consumer, stoneCuttingLoc(ModItems.UNDERGROUND_WALLPAPER_WALL.get()));

        SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(ModItems.KEY_UNDER_THE_LAKE.get()),
                RecipeCategory.DECORATIONS,
                ModItems.RUSTIC_BLUE_WALLPAPER_WALL.get(),
                8)
                .unlockedBy("has_key_under_the_lake", has(ModItems.KEY_UNDER_THE_LAKE.get()))
                .save(consumer, stoneCuttingLoc(ModItems.RUSTIC_BLUE_WALLPAPER_WALL.get()));

        SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(ModItems.KEY_UNDER_THE_LAKE.get()),
                RecipeCategory.DECORATIONS,
                ModItems.RUSTIC_PANELLING.get(),
                8)
                .unlockedBy("has_key_under_the_lake", has(ModItems.KEY_UNDER_THE_LAKE.get()))
                .save(consumer, stoneCuttingLoc(ModItems.RUSTIC_PANELLING.get()));

        SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(ModItems.KEY_UNDER_THE_LAKE.get()),
                RecipeCategory.DECORATIONS,
                ModItems.UNDERGROUND_PANELLING.get(),
                8)
                .unlockedBy("has_key_under_the_lake", has(ModItems.KEY_UNDER_THE_LAKE.get()))
                .save(consumer, stoneCuttingLoc(ModItems.UNDERGROUND_PANELLING.get()));

        SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(ModItems.KEY_UNDER_THE_LAKE.get()),
                RecipeCategory.DECORATIONS,
                ModItems.UNDERGROUND_DOOR_FRAMES.get(),
                8)
                .unlockedBy("has_key_under_the_lake", has(ModItems.KEY_UNDER_THE_LAKE.get()))
                .save(consumer, stoneCuttingLoc(ModItems.UNDERGROUND_DOOR_FRAMES.get()));

        SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(ModItems.KEY_UNDER_THE_LAKE.get()),
                RecipeCategory.DECORATIONS,
                ModItems.STAR_EMBELLISHED_CEILING.get(),
                4)
                .unlockedBy("has_key_under_the_lake", has(ModItems.KEY_UNDER_THE_LAKE.get()))
                .save(consumer, stoneCuttingLoc(ModItems.STAR_EMBELLISHED_CEILING.get()));

        SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(ModItems.GIFT_FROM_KAZI_MANOR.get()),
                RecipeCategory.DECORATIONS,
                ModItems.KAZI_LUCKY_CAT.get())
                .unlockedBy("has_gift_from_kazi_manor", has(ModItems.GIFT_FROM_KAZI_MANOR.get()))
                .save(consumer, stoneCuttingLoc(ModItems.KAZI_LUCKY_CAT.get()));

        SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(ModItems.GIFT_FROM_KAZI_MANOR.get()),
                RecipeCategory.DECORATIONS,
                ModItems.KEY_UNDER_THE_LAKE.get(),
                8)
                .unlockedBy("has_gift_from_kazi_manor", has(ModItems.GIFT_FROM_KAZI_MANOR.get()))
                .save(consumer, stoneCuttingLoc(ModItems.KEY_UNDER_THE_LAKE.get()));

        SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(ModItems.GIFT_FROM_KAZI_MANOR.get()),
                RecipeCategory.DECORATIONS,
                ModItems.LOUD_BUTTON.get(),
                4)
                .unlockedBy("has_gift_from_kazi_manor", has(ModItems.GIFT_FROM_KAZI_MANOR.get()))
                .save(consumer, stoneCuttingLoc(ModItems.LOUD_BUTTON.get()));

        SingleItemRecipeBuilder.stonecutting(
                Ingredient.of(ModItems.GIFT_FROM_KAZI_MANOR.get()),
                RecipeCategory.DECORATIONS,
                ModItems.ROSES_IN_WATER_BOTTLE.get())
                .unlockedBy("has_gift_from_kazi_manor", has(ModItems.GIFT_FROM_KAZI_MANOR.get()))
                .save(consumer, stoneCuttingLoc(ModItems.ROSES_IN_WATER_BOTTLE.get()));
    }

    private ResourceLocation stoneCuttingLoc(Item item) {
        return PaperKiteManor.resourceLocation("stonecutting/%s".formatted(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath()));
    }
}
