package com.kazi_cat.papercraft_magic_decoration.datagen.recipe;

import com.kazi_cat.papercraft_magic_decoration.datagen.builder.MixologyBuilder;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
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
                .addIngredient(ModItems.LUCKY_CACTUS.get())
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.NETHER_WART)
                .addIngredient(ModItems.LAND_NO1.get())
                .setResult(ModItems.DEVIL_MARGARITA.get())
                .save(consumer);

        MixologyBuilder.builder()
                .addIngredient(ModItems.VITALITY_SPORES.get())
                .addIngredient(Items.BEETROOT)
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(ModItems.WHISKEY_RAW.get())
                .setResult(ModItems.BLOODY_MARY.get())
                .save(consumer);

        MixologyBuilder.builder()
                .addIngredient(ModItems.POISON_RUM.get())
                .addIngredient(ModItems.NOCTURNAL_CAT_COFFEE.get())
                .addIngredient(Items.SUGAR)
                .addIngredient(Items.COCOA_BEANS)
                .setResult(ModItems.DIPLOMATICO_COFFEE.get())
                .save(consumer);
    }
}
