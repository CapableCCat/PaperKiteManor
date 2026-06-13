package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.item.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Tiers;
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

    // 工具
    RegistryObject<Item> AMETHYST_SCISSORS = ITEMS.register("amethyst_scissors", () -> new AmethystScissorsItem(new Item.Properties().durability(250)));
    RegistryObject<Item> GARDEN_TROWEL = ITEMS.register("garden_trowel", () -> new GardenTrowelItem(Tiers.IRON, 0, -2.4F, new Item.Properties().durability(50)));

    // 基础素材
    RegistryObject<Item> VITALITY_SPORES = ITEMS.register("vitality_spores", () -> new Item(new Item.Properties()));
    RegistryObject<Item> WHISKEY_RAW = ITEMS.register("whiskey_raw", () -> new Item(new Item.Properties()));
    RegistryObject<Item> COFFEE_FRUIT = ITEMS.register("coffee_fruit", () -> new CoffeeFruitItem(ModBlocks.COFFEE_PASTINACA_SATIVA.get(), new Item.Properties()));
    RegistryObject<Item> GOLDEN_COFFEE_FRUIT = ITEMS.register("golden_coffee_fruit", () -> new Item(new Item.Properties()));
    RegistryObject<Item> COFFEE_PASTINACA_SATIVA_TUBER = ITEMS.register("coffee_pastinaca_sativa_tuber", () -> new Item(new Item.Properties().food(ModFoods.COFFEE_PASTINACA_SATIVA_TUBER)));

    // 功能方块
    RegistryObject<Item> PAPER_CUTTING_TABLE = ITEMS.register("paper_cutting_table", () -> new GeoBlockItem(ModBlocks.PAPER_CUTTING_TABLE.get(), new Item.Properties()));
    RegistryObject<Item> COPPER_BARTENDER = ITEMS.register("copper_bartender", () -> new GeoBlockItem(ModBlocks.COPPER_BARTENDER.get(), new Item.Properties()));
    RegistryObject<Item> COPPER_STILL = ITEMS.register("copper_still", () -> new BlockItem(ModBlocks.COPPER_STILL.get(), new Item.Properties()));
    RegistryObject<Item> SPORES_COLLECTION_PLATE = ITEMS.register("spores_collection_plate", () -> new BlockItem(ModBlocks.SPORES_COLLECTION_PLATE.get(), new Item.Properties()));
    RegistryObject<Item> MOCHA_POT = ITEMS.register("mocha_pot", MochaPotItem::new);

    // 棕榈树
    RegistryObject<Item> MINI_PALM_TREE = ITEMS.register("mini_palm_tree", () -> new BlockItem(ModBlocks.MINI_PALM_TREE.get(), new Item.Properties()));
    RegistryObject<Item> PALM_TREE_CROWN = ITEMS.register("palm_tree_crown", () -> new BlockItem(ModBlocks.PALM_TREE_CROWN.get(), new Item.Properties()));
    RegistryObject<Item> PALM_TREE_TOP = ITEMS.register("palm_tree_top", () -> new BlockItem(ModBlocks.PALM_TREE_TOP.get(), new Item.Properties()));
    RegistryObject<Item> PALM_TREE_TRUNK_TOP = ITEMS.register("palm_tree_trunk_top", () -> new BlockItem(ModBlocks.PALM_TREE_TRUNK_TOP.get(), new Item.Properties()));
    RegistryObject<Item> PALM_TREE_TRUNK = ITEMS.register("palm_tree_trunk", () -> new BlockItem(ModBlocks.PALM_TREE_TRUNK.get(), new Item.Properties()));
    RegistryObject<Item> ROUGH_PALM_TREE_TRUNK = ITEMS.register("rough_palm_tree_trunk", () -> new BlockItem(ModBlocks.ROUGH_PALM_TREE_TRUNK.get(), new Item.Properties()));

    // 荫幕树
    RegistryObject<Item> GLOW_CASHEWS = ITEMS.register("glow_cashews", () -> new ItemNameBlockItem(ModBlocks.UMBRELLA_CASHEW.get(), new Item.Properties().food(ModFoods.GLOW_CASHEWS)));
    RegistryObject<Item> MINI_CANOPY_TREE = ITEMS.register("mini_canopy_tree", () -> new BlockItem(ModBlocks.MINI_CANOPY_TREE.get(), new Item.Properties()));
    RegistryObject<Item> CANOPY_TREE_LIMB = ITEMS.register("canopy_tree_limb", () -> new BlockItem(ModBlocks.CANOPY_TREE_LIMB.get(), new Item.Properties()));
    RegistryObject<Item> CANOPY_TREE_FOLIAGE = ITEMS.register("canopy_tree_foliage", () -> new BlockItem(ModBlocks.CANOPY_TREE_FOLIAGE.get(), new Item.Properties()));
    RegistryObject<Item> CANOPY_TREE_FERN = ITEMS.register("canopy_tree_fern", () -> new BlockItem(ModBlocks.CANOPY_TREE_FERN.get(), new Item.Properties()));
    RegistryObject<Item> CANOPY_TREE_DROOPING_ROOT = ITEMS.register("canopy_tree_drooping_root", () -> new BlockItem(ModBlocks.CANOPY_TREE_DROOPING_ROOT.get(), new Item.Properties()));
    RegistryObject<Item> CANOPY_TREE_TRUNK = ITEMS.register("canopy_tree_trunk", () -> new BlockItem(ModBlocks.CANOPY_TREE_TRUNK.get(), new Item.Properties()));
    RegistryObject<Item> CANOPY_TREE_MUSHROOM = ITEMS.register("canopy_tree_mushroom", () -> new BlockItem(ModBlocks.CANOPY_TREE_MUSHROOM.get(), new Item.Properties()));

    // 装饰方块
    RegistryObject<Item> KAZI_LUCKY_CAT = ITEMS.register("kazi_lucky_cat", () -> new GeoBlockItem(ModBlocks.KAZI_LUCKY_CAT.get(), new Item.Properties()));
    RegistryObject<Item> WINE_AROMA_RED_WALLPAPER_WALL = ITEMS.register("wine_aroma_red_wallpaper_wall", () -> new BlockItem(ModBlocks.WINE_AROMA_RED_WALLPAPER_WALL.get(), new Item.Properties()));
    RegistryObject<Item> WINE_AROMA_BLUE_WALLPAPER_WALL = ITEMS.register("wine_aroma_blue_wallpaper_wall", () -> new BlockItem(ModBlocks.WINE_AROMA_BLUE_WALLPAPER_WALL.get(), new Item.Properties()));
    RegistryObject<Item> BLACK_AND_WHITE_CHECKER_BOARD_TILE = ITEMS.register("black_and_white_checker_board_tile", () -> new BlockItem(ModBlocks.BLACK_AND_WHITE_CHECKER_BOARD_TILE.get(), new Item.Properties()));
    RegistryObject<Item> BLUE_AND_WHITE_CHECKER_BOARD_TILE = ITEMS.register("blue_and_white_checker_board_tile", () -> new BlockItem(ModBlocks.BLUE_AND_WHITE_CHECKER_BOARD_TILE.get(), new Item.Properties()));
    RegistryObject<Item> UNDERGROUND_WALLPAPER_WALL = ITEMS.register("underground_wallpaper_wall", () -> new BlockItem(ModBlocks.UNDERGROUND_WALLPAPER_WALL.get(), new Item.Properties()));
    RegistryObject<Item> RUSTIC_BLUE_WALLPAPER_WALL = ITEMS.register("rustic_blue_wallpaper_wall", () -> new BlockItem(ModBlocks.RUSTIC_BLUE_WALLPAPER_WALL.get(), new Item.Properties()));
    RegistryObject<Item> EMERALD_BLUE_EARTH_TILE = ITEMS.register("emerald_blue_earth_tile", () -> new BlockItem(ModBlocks.EMERALD_BLUE_EARTH_TILE.get(), new Item.Properties()));
    RegistryObject<Item> UNDERGROUND_PANELLING = ITEMS.register("underground_panelling", () -> new BlockItem(ModBlocks.UNDERGROUND_PANELLING.get(), new Item.Properties()));
    RegistryObject<Item> RUSTIC_PANELLING = ITEMS.register("rustic_panelling", () -> new BlockItem(ModBlocks.RUSTIC_PANELLING.get(), new Item.Properties()));
    RegistryObject<Item> UNDERGROUND_DOOR_FRAMES = ITEMS.register("underground_door_frames", () -> new BlockItem(ModBlocks.UNDERGROUND_DOOR_FRAMES.get(), new Item.Properties()));
    RegistryObject<Item> ROSES_IN_WATER_BOTTLE = ITEMS.register("roses_in_water_bottle", () -> new BlockItem(ModBlocks.ROSES_IN_WATER_BOTTLE.get(), new Item.Properties()));
    RegistryObject<Item> STAR_EMBELLISHED_CEILING = ITEMS.register("star_embellished_ceiling", () -> new BlockItem(ModBlocks.STAR_EMBELLISHED_CEILING.get(), new Item.Properties()));
    RegistryObject<Item> KNITTED_LEOPARD_RUG = ITEMS.register("knitted_leopard_rug", () -> new BlockItem(ModBlocks.KNITTED_LEOPARD_RUG.get(), new Item.Properties()));
    RegistryObject<Item> FOAM_BOX_WITH_DIRT = ITEMS.register("foam_box_with_dirt", () -> new BlockItem(ModBlocks.FOAM_BOX_WITH_DIRT.get(), new Item.Properties()));

    RegistryObject<Item> LOUD_BUTTON = ITEMS.register("loud_button", () -> new GeoBlockItem(ModBlocks.LOUD_BUTTON.get(), new Item.Properties()));
    RegistryObject<Item> GIFT_FROM_KAZI_MANOR = ITEMS.register("gift_from_kazi_manor", () -> new BlockItem(ModBlocks.GIFT_FROM_KAZI_MANOR.get(), new Item.Properties()));
    RegistryObject<Item> KEY_UNDER_THE_LAKE = ITEMS.register("key_under_the_lake", () -> new BlockItem(ModBlocks.KEY_UNDER_THE_LAKE.get(), new Item.Properties()));
    RegistryObject<Item> LOW_CABINET_WITH_TABLECLOTH = ITEMS.register("low_cabinet_with_tablecloth", () -> new GeoBlockItem(ModBlocks.LOW_CABINET_WITH_TABLECLOTH.get(), new Item.Properties()));
    RegistryObject<Item> WOODEN_BARREL_BOOKSHELF = ITEMS.register("wooden_barrel_bookshelf", () -> new GeoBlockItem(ModBlocks.WOODEN_BARREL_BOOKSHELF.get(), new Item.Properties()));
    RegistryObject<Item> WOODWORKING_TABLE = ITEMS.register("woodworking_table", () -> new GeoBlockItem(ModBlocks.WOODWORKING_TABLE.get(), new Item.Properties()));
    RegistryObject<Item> LONG_STORAGE_TABLE = ITEMS.register("long_storage_table", () -> new GeoBlockItem(ModBlocks.LONG_STORAGE_TABLE.get(), new Item.Properties()));
    RegistryObject<Item> EDGED_CHALKBOARD = ITEMS.register("edged_chalkboard", () -> new GeoBlockItem(ModBlocks.EDGED_CHALKBOARD.get(), new Item.Properties()));
    RegistryObject<Item> CUPBOARD = ITEMS.register("cupboard", () -> new BlockItem(ModBlocks.CUPBOARD.get(), new Item.Properties()));
    RegistryObject<Item> FIREPLACE_DECORATION = ITEMS.register("fireplace_decoration", () -> new BlockItem(ModBlocks.FIREPLACE_DECORATION.get(), new Item.Properties()));
    RegistryObject<Item> OLD_ORGAN = ITEMS.register("old_organ", () -> new GeoBlockItem(ModBlocks.OLD_ORGAN.get(), new Item.Properties()));
    RegistryObject<Item> LARGE_DINING_TABLE = ITEMS.register("large_dining_table", () -> new BlockItem(ModBlocks.LARGE_DINING_TABLE.get(), new Item.Properties()));
    RegistryObject<Item> RED_VELVET_CHAISE_LONGUE = ITEMS.register("red_velvet_chaise_longue", () -> new BlockItem(ModBlocks.RED_VELVET_CHAISE_LONGUE.get(), new Item.Properties()));

    // 仅用于投掷物
    RegistryObject<Item> VODKA = ITEMS.register("vodka", () -> new BlockItem(ModBlocks.VODKA.get(), new Item.Properties()));
    RegistryObject<Item> FIRE_WHISKEY = ITEMS.register("fire_whiskey", () -> new BlockItem(ModBlocks.FIRE_WHISKEY.get(), new Item.Properties()));
}
