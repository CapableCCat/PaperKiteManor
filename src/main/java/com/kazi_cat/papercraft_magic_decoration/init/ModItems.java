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

    // 瓶装酒
    RegistryObject<Item> BOTTLE_OF_BLAZE_WHISKEY = ITEMS.register("bottle_of_blaze_whiskey", () -> new BlockItem(ModBlocks.BOTTLE_OF_BLAZE_WHISKEY.get(), new Item.Properties().stacksTo(16)));
    RegistryObject<Item> BOTTLE_OF_FERRY_WHISKEY = ITEMS.register("bottle_of_ferry_whiskey", () -> new BlockItem(ModBlocks.BOTTLE_OF_FERRY_WHISKEY.get(), new Item.Properties().stacksTo(16)));
    RegistryObject<Item> BOTTLE_OF_FLY_WHISKEY = ITEMS.register("bottle_of_fly_whiskey", () -> new BlockItem(ModBlocks.BOTTLE_OF_FLY_WHISKEY.get(), new Item.Properties().stacksTo(16)));
    RegistryObject<Item> BOTTLE_OF_LAND_NO1 = ITEMS.register("bottle_of_land_no1", () -> new BlockItem(ModBlocks.BOTTLE_OF_LAND_NO1.get(), new Item.Properties().stacksTo(16)));
    RegistryObject<Item> BOTTLE_OF_LUCKY_CACTUS = ITEMS.register("bottle_of_lucky_cactus", () -> new BlockItem(ModBlocks.BOTTLE_OF_LUCKY_CACTUS.get(), new Item.Properties().stacksTo(16)));
    RegistryObject<Item> BOTTLE_OF_POISON_RUM = ITEMS.register("bottle_of_poison_rum", () -> new BlockItem(ModBlocks.BOTTLE_OF_POISON_RUM.get(), new Item.Properties().stacksTo(16)));
    RegistryObject<Item> PACK_OF_GUANG_S = ITEMS.register("pack_of_guang_s", () -> new PackOfGuangSItem(new Item.Properties()));

    // 巧克力
    RegistryObject<Item> TRUFFLE_CHOCOLATE = ITEMS.register("truffle_chocolate", () -> new BlockItem(ModBlocks.TRUFFLE_CHOCOLATE.get(), new Item.Properties().food(ModFoods.TRUFFLE_CHOCOLATE)));
    RegistryObject<Item> MILK_CHOCOLATE = ITEMS.register("milk_chocolate", () -> new MilkChocolateItem(ModBlocks.MILK_CHOCOLATE.get(), new Item.Properties().food(ModFoods.MILK_CHOCOLATE)));
    RegistryObject<Item> PRALINE_CHOCOLATE = ITEMS.register("praline_chocolate", () -> new BlockItem(ModBlocks.PRALINE_CHOCOLATE.get(), new Item.Properties().food(ModFoods.PRALINE_CHOCOLATE)));
    RegistryObject<Item> DARK_COCOA_IN_MOLD = ITEMS.register("dark_cocoa_in_mold", () -> new Item(new Item.Properties()));
    RegistryObject<Item> MILK_COCOA_IN_MOLD = ITEMS.register("milk_cocoa_in_mold", () -> new Item(new Item.Properties()));
    RegistryObject<Item> PRALINE_COCOA_IN_MOLD = ITEMS.register("praline_cocoa_in_mold", () -> new Item(new Item.Properties()));
    RegistryObject<Item> MELTED_DARK_COCOA_IN_MOLD = ITEMS.register("melted_dark_cocoa_in_mold", () -> new MeltedCocoaInMoldBlockItem(ModBlocks.TRUFFLE_CHOCOLATE_IN_MOLD.get(), new Item.Properties(), 200));
    RegistryObject<Item> MELTED_MILK_COCOA_IN_MOLD = ITEMS.register("melted_milk_cocoa_in_mold", () -> new MeltedCocoaInMoldBlockItem(ModBlocks.MILK_CHOCOLATE_IN_MOLD.get(), new Item.Properties(), 200));
    RegistryObject<Item> MELTED_PRALINE_COCOA_IN_MOLD = ITEMS.register("melted_praline_cocoa_in_mold", () -> new MeltedCocoaInMoldBlockItem(ModBlocks.PRALINE_CHOCOLATE_IN_MOLD.get(), new Item.Properties(), 200));
    RegistryObject<Item> OVERSIZED_BOX_OF_CHOCOLATES = ITEMS.register("oversized_box_of_chocolates", () -> new BlockItem(ModBlocks.OVERSIZED_BOX_OF_CHOCOLATES.get(), new Item.Properties().stacksTo(1)));

    // 纸块
    RegistryObject<Item> WHITE_PAPER_BLOCK = ITEMS.register("white_paper_block", () -> new BlockItem(ModBlocks.WHITE_PAPER_BLOCK.get(), new Item.Properties()));
    RegistryObject<Item> BLUE_PAPER_BLOCK = ITEMS.register("blue_paper_block", () -> new BlockItem(ModBlocks.BLUE_PAPER_BLOCK.get(), new Item.Properties()));
    RegistryObject<Item> BLACK_PAPER_BLOCK = ITEMS.register("black_paper_block", () -> new BlockItem(ModBlocks.BLACK_PAPER_BLOCK.get(), new Item.Properties()));
    RegistryObject<Item> RED_PAPER_BLOCK = ITEMS.register("red_paper_block", () -> new BlockItem(ModBlocks.RED_PAPER_BLOCK.get(), new Item.Properties()));
    RegistryObject<Item> YELLOW_PAPER_BLOCK = ITEMS.register("yellow_paper_block", () -> new BlockItem(ModBlocks.YELLOW_PAPER_BLOCK.get(), new Item.Properties()));
    RegistryObject<Item> DEWY_MEMBRANE_BLOCK = ITEMS.register("dewy_membrane_block", () -> new BlockItem(ModBlocks.DEWY_MEMBRANE_BLOCK.get(), new Item.Properties()));
    RegistryObject<Item> COTTON_SERGE_BLOCK = ITEMS.register("cotton_serge_block", () -> new BlockItem(ModBlocks.COTTON_SERGE_BLOCK.get(), new Item.Properties()));

    // 纸
    RegistryObject<Item> WHITE_PAPER = ITEMS.register("white_paper", () -> new Item(new Item.Properties()));
    RegistryObject<Item> BLUE_PAPER = ITEMS.register("blue_paper", () -> new Item(new Item.Properties()));
    RegistryObject<Item> BLACK_PAPER = ITEMS.register("black_paper", () -> new Item(new Item.Properties()));
    RegistryObject<Item> RED_PAPER = ITEMS.register("red_paper", () -> new Item(new Item.Properties()));
    RegistryObject<Item> YELLOW_PAPER = ITEMS.register("yellow_paper", () -> new Item(new Item.Properties()));
    RegistryObject<Item> DEWY_MEMBRANE = ITEMS.register("dewy_membrane", () -> new Item(new Item.Properties()));
    RegistryObject<Item> COTTON_SERGE = ITEMS.register("cotton_serge", () -> new Item(new Item.Properties()));

    // 紫水晶剪刀
    RegistryObject<Item> AMETHYST_SCISSORS = ITEMS.register("amethyst_scissors", () -> new AmethystScissorsItem(new Item.Properties().durability(250)));

    // 剪纸台
    RegistryObject<Item> PAPER_CUTTING_TABLE = ITEMS.register("paper_cutting_table", () -> new GeoBlockItem(ModBlocks.PAPER_CUTTING_TABLE.get(), new Item.Properties()));
}
