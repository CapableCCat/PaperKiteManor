package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.item.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModItems {
    DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PaperKiteManor.MOD_ID);

    RegistryObject<Item> BLAZE_WHISKEY = ITEMS.register("blaze_whiskey",
            () -> new DrinkBlockItem(ModBlocks.BLAZE_WHISKEY.get(), DrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 6000, 1), 1)
                    .build()));

    RegistryObject<Item> FERRY_WHISKEY = ITEMS.register("ferry_whiskey",
            () -> new DrinkBlockItem(ModBlocks.FERRY_WHISKEY.get(), DrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 6000, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 6000, 1), 1)
                    .build()));

    RegistryObject<Item> FLY_WHISKEY = ITEMS.register("fly_whiskey",
            () -> new DrinkBlockItem(ModBlocks.FLY_WHISKEY.get(), DrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 2400, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.LEVITATION, 1200, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 1400, 1), 1)
                    .build()));

    RegistryObject<Item> LAND_NO1 = ITEMS.register("land_no1",
            () -> new DrinkBlockItem(ModBlocks.LAND_NO1.get(), DrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 120, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 6000, 3), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 6000, 2), 1)
                    .build()));

    RegistryObject<Item> LUCKY_CACTUS = ITEMS.register("lucky_cactus",
            () -> new DrinkBlockItem(ModBlocks.LUCKY_CACTUS.get(), DrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.LUCK, 6000, 3), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.GLOWING, 6000, 1), 1)
                    .build()));

    RegistryObject<Item> POISON_RUM = ITEMS.register("poison_rum",
            () -> new DrinkBlockItem(ModBlocks.POISON_RUM.get(), DrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 120, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.WITHER, 120, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 3), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.POISON, 600, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 1), 1)
                    .build()));

    RegistryObject<Item> BLOODY_MARY = ITEMS.register("bloody_mary",
            () -> new DrinkBlockItem(ModBlocks.BLOODY_MARY.get(), DrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 120, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.HEAL, 1, 2), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 6000, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 1), 1)
                    .build()));

    RegistryObject<Item> DIPLOMATICO_COFFEE = ITEMS.register("diplomatico_coffee",
            () -> new DrinkBlockItem(ModBlocks.DIPLOMATICO_COFFEE.get(), DrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600, 2), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0), 1)
                    .build()));

    RegistryObject<Item> DEVIL_MARGARITA = ITEMS.register("devil_margarita",
            () -> new DrinkBlockItem(ModBlocks.DEVIL_MARGARITA.get(), DrinkBlockItem.defaultFood.get().build()));

    RegistryObject<Item> NOCTURNAL_CAT_COFFEE = ITEMS.register("nocturnal_cat_coffee",
            () -> new DrinkBlockItem(ModBlocks.NOCTURNAL_CAT_COFFEE.get(), DrinkBlockItem.defaultFood.get().build()));

    RegistryObject<Item> GOLD_MEDAL_COFFEE = ITEMS.register("gold_medal_coffee",
            () -> new DrinkBlockItem(ModBlocks.GOLD_MEDAL_COFFEE.get(), DrinkBlockItem.defaultFood.get().build()));

    RegistryObject<Item> GUANG_S = ITEMS.register("guang_s",
            () -> new DrinkBlockItem(ModBlocks.GUANG_S.get(), DrinkBlockItem.defaultFood.get().build()));

    RegistryObject<Item> PACK_OF_GUANG_S = ITEMS.register("pack_of_guang_s", PackOfGuangSItem::new);

    // 大瓶酒
    RegistryObject<Item> BOTTLE_OF_BLAZE_WHISKEY = ITEMS.register("bottle_of_blaze_whiskey",
            () -> new BlockItem(ModBlocks.BOTTLE_OF_BLAZE_WHISKEY.get(), new Item.Properties().stacksTo(16)));

    RegistryObject<Item> BOTTLE_OF_FERRY_WHISKEY = ITEMS.register("bottle_of_ferry_whiskey",
            () -> new BlockItem(ModBlocks.BOTTLE_OF_FERRY_WHISKEY.get(), new Item.Properties().stacksTo(16)));

    RegistryObject<Item> BOTTLE_OF_FLY_WHISKEY = ITEMS.register("bottle_of_fly_whiskey",
            () -> new BlockItem(ModBlocks.BOTTLE_OF_FLY_WHISKEY.get(), new Item.Properties().stacksTo(16)));

    RegistryObject<Item> BOTTLE_OF_LAND_NO1 = ITEMS.register("bottle_of_land_no1",
            () -> new BlockItem(ModBlocks.BOTTLE_OF_LAND_NO1.get(), new Item.Properties().stacksTo(16)));

    RegistryObject<Item> BOTTLE_OF_LUCKY_CACTUS = ITEMS.register("bottle_of_lucky_cactus",
            () -> new BlockItem(ModBlocks.BOTTLE_OF_LUCKY_CACTUS.get(), new Item.Properties().stacksTo(16)));

    RegistryObject<Item> BOTTLE_OF_POISON_RUM = ITEMS.register("bottle_of_poison_rum",
            () -> new BlockItem(ModBlocks.BOTTLE_OF_POISON_RUM.get(), new Item.Properties().stacksTo(16)));

    // 紫水晶剪刀
    RegistryObject<Item> AMETHYST_SCISSORS = ITEMS.register("amethyst_scissors", AmethystScissorsItem::new);

    // 园丁铲
    RegistryObject<Item> GARDEN_TROWEL = ITEMS.register("garden_trowel", () -> new GardenTrowelItem(Tiers.IRON, 0, -2.4F,
            new Item.Properties().durability(50)));

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

    // 基础材料
    RegistryObject<Item> COFFEE_FRUIT = ITEMS.register("coffee_fruit", () -> new ItemNameBlockItem(ModBlocks.COFFEE_PASTINACA_SATIVA.get(), new Item.Properties()));
    RegistryObject<Item> GOLDEN_COFFEE_FRUIT = ITEMS.register("golden_coffee_fruit", () -> new Item(new Item.Properties()));
    RegistryObject<Item> COFFEE_PASTINACA_SATIVA_TUBER = ITEMS.register("coffee_pastinaca_sativa_tuber", () -> new Item(new Item.Properties()
            .food(new FoodProperties.Builder().nutrition(6).saturationMod(0.3f).build())));
    RegistryObject<Item> VITALITY_SPORES = ITEMS.register("vitality_spores", () -> new Item(new Item.Properties()));
    RegistryObject<Item> WHISKEY_RAW = ITEMS.register("whiskey_raw", () -> new Item(new Item.Properties()));

    // 剪纸台
    RegistryObject<Item> PAPER_CUTTING_TABLE = ITEMS.register("paper_cutting_table", () -> new GeoBlockItem(ModBlocks.PAPER_CUTTING_TABLE.get(), new Item.Properties()));

    // 铜酒保
    RegistryObject<Item> COPPER_BARTENDER = ITEMS.register("copper_bartender", () -> new GeoBlockItem(ModBlocks.COPPER_BARTENDER.get(), new Item.Properties()));

    // 蒸馏器
    RegistryObject<Item> COPPER_STILL = ITEMS.register("copper_still", () -> new BlockItem(ModBlocks.COPPER_STILL.get(), new Item.Properties()));

    // 孢子收集盆
    RegistryObject<Item> SPORES_COLLECTION_PLATE = ITEMS.register("spores_collection_plate", () -> new BlockItem(ModBlocks.SPORES_COLLECTION_PLATE.get(), new Item.Properties()));

    // 棕榈树
    RegistryObject<Item> PALM_TREE_CROWN = ITEMS.register("palm_tree_crown", () -> new BlockItem(ModBlocks.PALM_TREE_CROWN.get(), new Item.Properties()));
    RegistryObject<Item> PALM_TREE_TOP = ITEMS.register("palm_tree_top", () -> new BlockItem(ModBlocks.PALM_TREE_TOP.get(), new Item.Properties()));
    RegistryObject<Item> PALM_TREE_TRUNK_TOP = ITEMS.register("palm_tree_trunk_top", () -> new BlockItem(ModBlocks.PALM_TREE_TRUNK_TOP.get(), new Item.Properties()));
    RegistryObject<Item> PALM_TREE_TRUNK = ITEMS.register("palm_tree_trunk", () -> new BlockItem(ModBlocks.PALM_TREE_TRUNK.get(), new Item.Properties()));
    RegistryObject<Item> ROUGH_PALM_TREE_TRUNK = ITEMS.register("rough_palm_tree_trunk", () -> new BlockItem(ModBlocks.ROUGH_PALM_TREE_TRUNK.get(), new Item.Properties()));

    // 荫幕树
    RegistryObject<Item> CANOPY_TREE_LIMB = ITEMS.register("canopy_tree_limb", () -> new BlockItem(ModBlocks.CANOPY_TREE_LIMB.get(), new Item.Properties()));
    RegistryObject<Item> CANOPY_TREE_FOLIAGE = ITEMS.register("canopy_tree_foliage", () -> new BlockItem(ModBlocks.CANOPY_TREE_FOLIAGE.get(), new Item.Properties()));
    RegistryObject<Item> CANOPY_TREE_FERN = ITEMS.register("canopy_tree_fern", () -> new BlockItem(ModBlocks.CANOPY_TREE_FERN.get(), new Item.Properties()));
    RegistryObject<Item> CANOPY_TREE_DROOPING_ROOT = ITEMS.register("canopy_tree_drooping_root", () -> new BlockItem(ModBlocks.CANOPY_TREE_DROOPING_ROOT.get(), new Item.Properties()));
    RegistryObject<Item> CANOPY_TREE_TRUNK = ITEMS.register("canopy_tree_trunk", () -> new BlockItem(ModBlocks.CANOPY_TREE_TRUNK.get(), new Item.Properties()));
    RegistryObject<Item> CANOPY_TREE_MUSHROOM = ITEMS.register("canopy_tree_mushroom", () -> new BlockItem(ModBlocks.CANOPY_TREE_MUSHROOM.get(), new Item.Properties()));

    // 装饰方块
    RegistryObject<Item> WINE_AROMA_RED_WALLPAPER_WALL = ITEMS.register("wine_aroma_red_wallpaper_wall", () -> new BlockItem(ModBlocks.WINE_AROMA_RED_WALLPAPER_WALL.get(), new Item.Properties()));
    RegistryObject<Item> WINE_AROMA_BLUE_WALLPAPER_WALL = ITEMS.register("wine_aroma_blue_wallpaper_wall", () -> new BlockItem(ModBlocks.WINE_AROMA_BLUE_WALLPAPER_WALL.get(), new Item.Properties()));
    RegistryObject<Item> BLACK_AND_WHITE_CHECKER_BOARD_TILE = ITEMS.register("black_and_white_checker_board_tile", () -> new BlockItem(ModBlocks.BLACK_AND_WHITE_CHECKER_BOARD_TILE.get(), new Item.Properties()));
    RegistryObject<Item> BLUE_AND_WHITE_CHECKER_BOARD_TILE = ITEMS.register("blue_and_white_checker_board_tile", () -> new BlockItem(ModBlocks.BLUE_AND_WHITE_CHECKER_BOARD_TILE.get(), new Item.Properties()));
    RegistryObject<Item> UNDERGROUND_WALLPAPER_WALL = ITEMS.register("underground_wallpaper_wall", () -> new BlockItem(ModBlocks.UNDERGROUND_WALLPAPER_WALL.get(), new Item.Properties()));
    RegistryObject<Item> RUSTIC_BLUE_WALLPAPER_WALL = ITEMS.register("rustic_blue_wallpaper_wall", () -> new BlockItem(ModBlocks.RUSTIC_BLUE_WALLPAPER_WALL.get(), new Item.Properties()));
    RegistryObject<Item> UNDERGROUND_PANELLING = ITEMS.register("underground_panelling", () -> new BlockItem(ModBlocks.UNDERGROUND_PANELLING.get(), new Item.Properties()));
    RegistryObject<Item> ROSES_IN_WATER_BOTTLE = ITEMS.register("roses_in_water_bottle", () -> new BlockItem(ModBlocks.ROSES_IN_WATER_BOTTLE.get(), new Item.Properties()));
    RegistryObject<Item> STAR_EMBELLISHED_CEILING = ITEMS.register("star_embellished_ceiling", () -> new BlockItem(ModBlocks.STAR_EMBELLISHED_CEILING.get(), new Item.Properties()));

    RegistryObject<Item> LOUD_BUTTON = ITEMS.register("loud_button", () -> new GeoBlockItem(ModBlocks.LOUD_BUTTON.get(), new Item.Properties()));
    RegistryObject<Item> LOW_CABINET_WITH_TABLECLOTH = ITEMS.register("low_cabinet_with_tablecloth", () -> new GeoBlockItem(ModBlocks.LOW_CABINET_WITH_TABLECLOTH.get(), new Item.Properties()));
    RegistryObject<Item> WOODEN_BARREL_BOOKSHELF = ITEMS.register("wooden_barrel_bookshelf", () -> new GeoBlockItem(ModBlocks.WOODEN_BARREL_BOOKSHELF.get(), new Item.Properties()));
    RegistryObject<Item> WOODWORKING_TABLE = ITEMS.register("woodworking_table", () -> new GeoBlockItem(ModBlocks.WOODWORKING_TABLE.get(), new Item.Properties()));
    RegistryObject<Item> LONG_STORAGE_TABLE = ITEMS.register("long_storage_table", () -> new GeoBlockItem(ModBlocks.LONG_STORAGE_TABLE.get(), new Item.Properties()));
    RegistryObject<Item> EDGED_CHALKBOARD = ITEMS.register("edged_chalkboard", () -> new GeoBlockItem(ModBlocks.EDGED_CHALKBOARD.get(), new Item.Properties()));
    RegistryObject<Item> CUPBOARD = ITEMS.register("cupboard", () -> new BlockItem(ModBlocks.CUPBOARD.get(), new Item.Properties()));
    RegistryObject<Item> FIREPLACE_DECORATION = ITEMS.register("fireplace_decoration", () -> new BlockItem(ModBlocks.FIREPLACE_DECORATION.get(), new Item.Properties()));
}
