package com.kazi_cat.papercraft_magic_decoration.init;

import net.minecraft.world.food.FoodProperties;

public interface ModFoods {
    FoodProperties SMOKED_SALMON_HEAD = (new FoodProperties.Builder())
            .nutrition(5).saturationMod(0.6f)
            .meat().build();

    FoodProperties CHUNKY_SALMON = (new FoodProperties.Builder())
            .nutrition(4).saturationMod(0.3f)
            .meat().build();

    FoodProperties CHUNKY_SMOKED_SALMON = (new FoodProperties.Builder())
            .nutrition(10).saturationMod(0.6f)
            .meat().build();

    FoodProperties MANGA_MEAT = (new FoodProperties.Builder())
            .nutrition(15).saturationMod(0.6F)
            .meat().build();

    FoodProperties LARGE_STEAK = (new FoodProperties.Builder())
            .nutrition(12).saturationMod(0.7f)
            .meat().build();

    FoodProperties CUBED_SAUSAGE = (new FoodProperties.Builder())
            .nutrition(14).saturationMod(0.7f)
            .meat().build();
}
