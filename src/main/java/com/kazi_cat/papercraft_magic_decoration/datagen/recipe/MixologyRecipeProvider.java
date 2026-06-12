package com.kazi_cat.papercraft_magic_decoration.datagen.recipe;

import com.kazi_cat.papercraft_magic_decoration.datagen.builder.MixologyBuilder;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import com.kazi_cat.papercraft_magic_decoration.init.registry.DrinkRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class MixologyRecipeProvider extends ModRecipeProvider {
    public MixologyRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
        MixologyBuilder.builder()
                .addIngredient(DrinkRegistry.getItem(DrinkRegistry.LUCKY_CACTUS))
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.NETHER_WART)
                .addIngredient(DrinkRegistry.getItem(DrinkRegistry.LAND_NO1))
                .setResult(DrinkRegistry.getItem(DrinkRegistry.DEVIL_MARGARITA))
                .save(consumer);

        MixologyBuilder.builder()
                .addIngredient(ModItems.VITALITY_SPORES.get())
                .addIngredient(Items.BEETROOT)
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(ModItems.WHISKEY_RAW.get())
                .setResult(DrinkRegistry.getItem(DrinkRegistry.BLOODY_MARY))
                .save(consumer);

        MixologyBuilder.builder()
                .addIngredient(DrinkRegistry.getItem(DrinkRegistry.POISON_RUM))
                .addIngredient(DrinkRegistry.getItem(DrinkRegistry.NOCTURNAL_CAT_COFFEE))
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.COCOA_BEANS)
                .setResult(DrinkRegistry.getItem(DrinkRegistry.DIPLOMATICO_COFFEE))
                .save(consumer);
    }
}
