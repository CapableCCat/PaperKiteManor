package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.item.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModItems {
    DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PaperKiteManor.MOD_ID);

    // 可烤制食物
    RegistryObject<Item> RAW_SAUSAGE_MACE_WEAPON = ITEMS.register("raw_sausage_mace_weapon", () -> new ItemNameGeoBlockItem(ModBlocks.SAUSAGE_MACE_WEAPON_BLOCK.get(), new Item.Properties()));
    RegistryObject<Item> SAUSAGE_MACE_WEAPON = ITEMS.register("sausage_mace_weapon", () -> new ItemNameGeoBlockItem(ModBlocks.SAUSAGE_MACE_WEAPON_BLOCK.get(), new Item.Properties().stacksTo(1)));
    RegistryObject<Item> CUBED_SAUSAGE = ITEMS.register("cubed_sausage", () -> new Item(new Item.Properties().food(ModFoods.CUBED_SAUSAGE)));
    RegistryObject<Item> SMOKED_SALMON_HEAD = ITEMS.register("smoked_salmon_head", () -> new ItemNameBlockItem(ModBlocks.SALMON_HEAD.get(), new Item.Properties().food(ModFoods.SMOKED_SALMON_HEAD)));
    RegistryObject<Item> CHUNKY_SALMON = ITEMS.register("chunky_salmon", () -> new ItemNameBlockItem(ModBlocks.CHUNKY_SALMON.get(), new Item.Properties().food(ModFoods.CHUNKY_SALMON)));
    RegistryObject<Item> CHUNKY_SMOKED_SALMON = ITEMS.register("chunky_smoked_salmon", () -> new ItemNameBlockItem(ModBlocks.CHUNKY_SALMON.get(), new Item.Properties().food(ModFoods.CHUNKY_SMOKED_SALMON)));
    RegistryObject<Item> JUMBO_SALMON = ITEMS.register("jumbo_salmon", () -> new JumboSalmonItem(new Item.Properties()));
    RegistryObject<Item> RAW_MANGA_MEAT = ITEMS.register("raw_manga_meat", () -> new ItemNameGeoBlockItem(ModBlocks.MANGA_MEAT.get(), new Item.Properties()));
    RegistryObject<Item> MANGA_MEAT = ITEMS.register("manga_meat", () -> new MangaMeatItem(ModBlocks.MANGA_MEAT.get(), new Item.Properties().food(ModFoods.MANGA_MEAT).durability(2).fireResistant()));
    RegistryObject<Item> MONSTER_STEAK = ITEMS.register("monster_steak", () -> new ItemNameBlockItem(ModBlocks.MONSTER_STEAK.get(), new Item.Properties().stacksTo(1)));
    RegistryObject<Item> LARGE_STEAK = ITEMS.register("large_steak", () -> new Item(new Item.Properties().food(ModFoods.LARGE_STEAK)));

    // 炸鸡桶
    RegistryObject<Item> BREADED_RAW_CHICKEN = ITEMS.register("breaded_raw_chicken", () -> new Item(new Item.Properties()));
    RegistryObject<Item> BUCKET_OF_FRIED_CHICKEN = ITEMS.register("bucket_of_fried_chicken", () -> new BlockItem(ModBlocks.BUCKET_OF_FRIED_CHICKEN.get(), new Item.Properties()));
    RegistryObject<Item> FRIED_CHICKEN_LEG = ITEMS.register("fried_chicken_leg", () -> new Item(new Item.Properties().food(ModFoods.FRIED_CHICKEN_LEG)));

    // 杯装酒
    RegistryObject<Item> BLAZE_WHISKEY = ITEMS.register("blaze_whiskey", () -> new DrinkBlockItem(ModBlocks.BLAZE_WHISKEY.get(), ModFoods.BLAZE_WHISKEY));
    RegistryObject<Item> FERRY_WHISKEY = ITEMS.register("ferry_whiskey", () -> new DrinkBlockItem(ModBlocks.FERRY_WHISKEY.get(), ModFoods.FERRY_WHISKEY));
    RegistryObject<Item> FLY_WHISKEY = ITEMS.register("fly_whiskey", () -> new DrinkBlockItem(ModBlocks.FLY_WHISKEY.get(), ModFoods.FLY_WHISKEY));
    RegistryObject<Item> LAND_NO1 = ITEMS.register("land_no1", () -> new DrinkBlockItem(ModBlocks.LAND_NO1.get(), ModFoods.LAND_NO1));
    RegistryObject<Item> LUCKY_CACTUS = ITEMS.register("lucky_cactus", () -> new DrinkBlockItem(ModBlocks.LUCKY_CACTUS.get(), ModFoods.LUCKY_CACTUS));
    RegistryObject<Item> POISON_RUM = ITEMS.register("poison_rum", () -> new DrinkBlockItem(ModBlocks.POISON_RUM.get(), ModFoods.POISON_RUM));
    RegistryObject<Item> BLOODY_MARY = ITEMS.register("bloody_mary", () -> new DrinkBlockItem(ModBlocks.BLOODY_MARY.get(), ModFoods.BLOODY_MARY));
    RegistryObject<Item> DIPLOMATICO_COFFEE = ITEMS.register("diplomatico_coffee", () -> new DrinkBlockItem(ModBlocks.DIPLOMATICO_COFFEE.get(), ModFoods.DIPLOMATICO_COFFEE));
    RegistryObject<Item> DEVIL_MARGARITA = ITEMS.register("devil_margarita", () -> new DrinkBlockItem(ModBlocks.DEVIL_MARGARITA.get(), ModFoods.DEVIL_MARGARITA));
    RegistryObject<Item> DIONYSUS = ITEMS.register("dionysus", () -> new DrinkBlockItem(ModBlocks.DIONYSUS.get(), ModFoods.DIONYSUS));
    RegistryObject<Item> KALEIDOSCOPE_WHISKEY_SOUR = ITEMS.register("kaleidoscope_whiskey_sour", () -> new DrinkBlockItem(ModBlocks.KALEIDOSCOPE_WHISKEY_SOUR.get(), ModFoods.KALEIDOSCOPE_WHISKEY_SOUR));
    RegistryObject<Item> LONG_ISLAND_POPSICLE_TEA = ITEMS.register("long_island_popsicle_tea", () -> new DrinkBlockItem(ModBlocks.LONG_ISLAND_POPSICLE_TEA.get(), ModFoods.LONG_ISLAND_POPSICLE_TEA));
    RegistryObject<Item> NOCTURNAL_CAT_COFFEE = ITEMS.register("nocturnal_cat_coffee", () -> new DrinkBlockItem(ModBlocks.NOCTURNAL_CAT_COFFEE.get(), ModFoods.NOCTURNAL_CAT_COFFEE));
    RegistryObject<Item> GOLD_MEDAL_COFFEE = ITEMS.register("gold_medal_coffee", () -> new DrinkBlockItem(ModBlocks.GOLD_MEDAL_COFFEE.get(), ModFoods.GOLD_MEDAL_COFFEE));
    RegistryObject<Item> WHITE_RABBIT_MOCHA = ITEMS.register("white_rabbit_mocha", () -> new DrinkBlockItem(ModBlocks.WHITE_RABBIT_MOCHA.get(), ModFoods.WHITE_RABBIT_MOCHA));
    RegistryObject<Item> GUANG_S = ITEMS.register("guang_s", () -> new DrinkBlockItem(ModBlocks.GUANG_S.get(), ModFoods.GUANG_S));

    // 瓶装酒
    RegistryObject<Item> PACK_OF_GUANG_S = ITEMS.register("pack_of_guang_s", () -> new PackOfGuangSItem(new Item.Properties()));
}
