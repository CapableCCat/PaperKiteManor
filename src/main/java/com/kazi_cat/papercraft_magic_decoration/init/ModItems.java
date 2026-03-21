package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.block.food.ChunkySalmonBlock;
import com.kazi_cat.papercraft_magic_decoration.block.food.TwoByOneSmeltableBlock;
import com.kazi_cat.papercraft_magic_decoration.item.*;
import com.kazi_cat.papercraft_magic_decoration.item.food.BreadedRawChickenItem;
import com.kazi_cat.papercraft_magic_decoration.item.food.GlassDrinkBlockItem;
import com.kazi_cat.papercraft_magic_decoration.item.food.MultiEatGeoBlockItem;
import com.kazi_cat.papercraft_magic_decoration.item.food.PackOfGuangSItem;
import com.kazi_cat.papercraft_magic_decoration.item.tool.AmethystScissorsItem;
import com.kazi_cat.papercraft_magic_decoration.item.tool.GardenTrowelItem;
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

import java.util.List;

public interface ModItems {
    DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PaperKiteManor.MOD_ID);

    // 杯装酒
    RegistryObject<Item> GLASS_OF_BLAZE_WHISKEY = ITEMS.register("glass_of_blaze_whiskey",
            () -> new GlassDrinkBlockItem(ModBlocks.GLASS_OF_BLAZE_WHISKEY.get(), GlassDrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 6000, 1), 1)
                    .build()));

    RegistryObject<Item> GLASS_OF_FERRY_WHISKEY = ITEMS.register("glass_of_ferry_whiskey",
            () -> new GlassDrinkBlockItem(ModBlocks.GLASS_OF_FERRY_WHISKEY.get(), GlassDrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 6000, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 6000, 1), 1)
                    .build()));

    RegistryObject<Item> GLASS_OF_FLY_WHISKEY = ITEMS.register("glass_of_fly_whiskey",
            () -> new GlassDrinkBlockItem(ModBlocks.GLASS_OF_FLY_WHISKEY.get(), GlassDrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 2400, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.LEVITATION, 1200, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 1400, 1), 1)
                    .build()));

    RegistryObject<Item> GLASS_OF_LAND_NO1 = ITEMS.register("glass_of_land_no1",
            () -> new GlassDrinkBlockItem(ModBlocks.GLASS_OF_LAND_NO1.get(), GlassDrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 120, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 6000, 3), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 6000, 2), 1)
                    .build()));

    RegistryObject<Item> GLASS_OF_LUCKY_CACTUS = ITEMS.register("glass_of_lucky_cactus",
            () -> new GlassDrinkBlockItem(ModBlocks.GLASS_OF_LUCKY_CACTUS.get(), GlassDrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.LUCK, 6000, 3), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.GLOWING, 6000, 1), 1)
                    .build()));

    RegistryObject<Item> GLASS_OF_POISON_RUM = ITEMS.register("glass_of_poison_rum",
            () -> new GlassDrinkBlockItem(ModBlocks.GLASS_OF_POISON_RUM.get(), GlassDrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 120, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.WITHER, 120, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 3), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.POISON, 600, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 1), 1)
                    .build()));

    RegistryObject<Item> BLOODY_MARY = ITEMS.register("bloody_mary",
            () -> new GlassDrinkBlockItem(ModBlocks.BLOODY_MARY.get(), GlassDrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 120, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.HEAL, 1, 2), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 6000, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 1), 1)
                    .build()));

    RegistryObject<Item> DIPLOMAT_COFFEE = ITEMS.register("diplomat_coffee",
            () -> new GlassDrinkBlockItem(ModBlocks.DIPLOMAT_COFFEE.get(), GlassDrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600, 2), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0), 1)
                    .build()));

    RegistryObject<Item> DEVIL_MARGARITA = ITEMS.register("devil_margarita",
            () -> new GlassDrinkBlockItem(ModBlocks.DEVIL_MARGARITA.get(), GlassDrinkBlockItem.defaultFood.get().build()));

    RegistryObject<Item> GUANG_S = ITEMS.register("guang_s",
            () -> new GlassDrinkBlockItem(ModBlocks.GUANG_S.get(), GlassDrinkBlockItem.defaultFood.get().build()));

    RegistryObject<Item> PACK_OF_GUANG_S = ITEMS.register("pack_of_guang_s", () -> new PackOfGuangSItem(new Item.Properties()));

    // 大瓶酒
    RegistryObject<Item> BLAZE_WHISKEY = ITEMS.register("blaze_whiskey",
            () -> new BlockItem(ModBlocks.BLAZE_WHISKEY.get(), new Item.Properties().stacksTo(1)));

    RegistryObject<Item> FERRY_WHISKEY = ITEMS.register("ferry_whiskey",
            () -> new BlockItem(ModBlocks.FERRY_WHISKEY.get(), new Item.Properties().stacksTo(1)));

    RegistryObject<Item> FLY_WHISKEY = ITEMS.register("fly_whiskey",
            () -> new BlockItem(ModBlocks.FLY_WHISKEY.get(), new Item.Properties().stacksTo(1)));

    RegistryObject<Item> LAND_NO1 = ITEMS.register("land_no1",
            () -> new BlockItem(ModBlocks.LAND_NO1.get(), new Item.Properties().stacksTo(1)));

    RegistryObject<Item> LUCKY_CACTUS = ITEMS.register("lucky_cactus",
            () -> new BlockItem(ModBlocks.LUCKY_CACTUS.get(), new Item.Properties().stacksTo(1)));

    RegistryObject<Item> POISON_RUM = ITEMS.register("poison_rum",
            () -> new BlockItem(ModBlocks.POISON_RUM.get(), new Item.Properties().stacksTo(1)));

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
    RegistryObject<Item> COFFEE_FRUIT = ITEMS.register("coffee_fruit", () -> new ItemNameBlockItem(ModBlocks.COFFEE_PASTINACA_SATIVA.get(),
            new Item.Properties()));

    RegistryObject<Item> GOLDEN_COFFEE_FRUIT = ITEMS.register("golden_coffee_fruit", () -> new Item(new Item.Properties()));

    RegistryObject<Item> COFFEE_PASTINACA_SATIVA_TUBER = ITEMS.register("coffee_pastinaca_sativa_tuber", () ->
            new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.3f).build())));

    RegistryObject<Item> VITALITY_SPORES = ITEMS.register("vitality_spores", () -> new Item(new Item.Properties()));

    RegistryObject<Item> WHISKEY_RAW = ITEMS.register("whiskey_raw", () -> new Item(new Item.Properties()));

    // 剪纸台
    RegistryObject<Item> PAPER_CUTTING_TABLE = ITEMS.register("paper_cutting_table", () ->
            new GeoBlockItem(ModBlocks.PAPER_CUTTING_TABLE.get(), new Item.Properties()));

    // 铜酒保
    RegistryObject<Item> COPPER_BARTENDER = ITEMS.register("copper_bartender", () ->
            new GeoBlockItem(ModBlocks.COPPER_BARTENDER.get(), new Item.Properties()));

    // 蒸馏器
    RegistryObject<Item> COPPER_STILL = ITEMS.register("copper_still", () -> new BlockItem(ModBlocks.COPPER_STILL.get(), new Item.Properties()));

    // 烤制食物
    RegistryObject<Item> RAW_SAUSAGE_MACE_WEAPON = ITEMS.register("raw_sausage_mace_weapon", () ->
            new GeoBlockItem(ModBlocks.SAUSAGE_MACE_WEAPON_BLOCK.get(), new Item.Properties()));

    RegistryObject<Item> SAUSAGE_MACE_WEAPON = ITEMS.register("sausage_mace_weapon", () ->
            new GeoBlockItem(ModBlocks.SAUSAGE_MACE_WEAPON_BLOCK.get(), new Item.Properties()));

    RegistryObject<Item> CUBED_SAUSAGE = ITEMS.register("cubed_sausage", () ->
            new Item(new Item.Properties().food((new FoodProperties.Builder()).nutrition(14).saturationMod(0.7f).build())));

    RegistryObject<Item> CHUNKY_SALMON = ITEMS.register("chunky_salmon", () -> new ItemNameBlockItem(ModBlocks.CHUNKY_SALMON.get(),
                    new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3f).meat().build())));

    RegistryObject<Item> CHUNKY_SMOKED_SALMON = ITEMS.register("chunky_smoked_salmon", () -> new ItemNameBlockItem(ModBlocks.CHUNKY_SALMON.get(),
                    new Item.Properties().food(new FoodProperties.Builder().nutrition(10).saturationMod(0.6f).meat().build())));

    RegistryObject<Item> SMOKED_SALMON_HEAD = ITEMS.register("smoked_salmon_head", () -> new ItemNameBlockItem(ModBlocks.SALMON_HEAD.get(),
                    new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationMod(0.3f).meat().build())));

    RegistryObject<Item> JUMBO_SALMON = ITEMS.register("jumbo_salmon", () -> new TwoByThreeStructureBlockItem(new Item.Properties(), () -> List.of(
            ModBlocks.SALMON_HEAD.get().defaultBlockState().setValue(TwoByOneSmeltableBlock.POSITION, 0),
            ModBlocks.CHUNKY_SALMON.get().defaultBlockState().setValue(ChunkySalmonBlock.VARIANT, 1),
            ModBlocks.CHUNKY_SALMON.get().defaultBlockState().setValue(ChunkySalmonBlock.VARIANT, 2),
            ModBlocks.SALMON_HEAD.get().defaultBlockState().setValue(TwoByOneSmeltableBlock.POSITION, 1),
            ModBlocks.CHUNKY_SALMON.get().defaultBlockState().setValue(ChunkySalmonBlock.VARIANT, 3),
            ModBlocks.CHUNKY_SALMON.get().defaultBlockState().setValue(ChunkySalmonBlock.VARIANT, 4)
    )));

    RegistryObject<Item> MANGA_MEAT = ITEMS.register("manga_meat", () ->
            new MultiEatGeoBlockItem(ModBlocks.MANGA_MEAT.get(),
                    new Item.Properties().food(new FoodProperties.Builder().nutrition(15).saturationMod(0.6F).meat().build())
                            .durability(2).fireResistant(), 100));

    RegistryObject<Item> RAW_MANGA_MEAT = ITEMS.register("raw_manga_meat", () ->
            new GeoBlockItem(ModBlocks.MANGA_MEAT.get(), new Item.Properties()));

    RegistryObject<Item> MONSTER_STEAK = ITEMS.register("monster_steak", () -> new ItemNameBlockItem(ModBlocks.MONSTER_STEAK.get(),
            new Item.Properties().stacksTo(1)));

    RegistryObject<Item> LARGE_STEAK = ITEMS.register("large_steak", () -> new Item(new Item.Properties().food((new FoodProperties.Builder()).nutrition(12).saturationMod(0.7f).meat().build())));

    RegistryObject<Item> BREADED_RAW_CHICKEN = ITEMS.register("breaded_raw_chicken", () -> new BreadedRawChickenItem(new Item.Properties()));

    RegistryObject<Item> BUCKET_OF_FRIED_CHICKEN = ITEMS.register("bucket_of_fried_chicken", () -> new BlockItem(ModBlocks.BUCKET_OF_FRIED_CHICKEN.get(), new Item.Properties()));

    RegistryObject<Item> FRIED_CHICKEN_LEG = ITEMS.register("fried_chicken_leg", () ->
            new Item(new Item.Properties().food((new FoodProperties.Builder()).nutrition(8).saturationMod(0.6f).meat().build())));

    // 装饰方块
    RegistryObject<Item> LOUD_BUTTON = ITEMS.register("loud_button", () -> new GeoBlockItem(ModBlocks.LOUD_BUTTON.get(), new Item.Properties()));

    RegistryObject<Item> LOW_CABINET_WITH_TABLECLOTH = ITEMS.register("low_cabinet_with_tablecloth", () -> new GeoBlockItem(ModBlocks.LOW_CABINET_WITH_TABLECLOTH.get(), new Item.Properties()));

    RegistryObject<Item> WOODEN_BARREL_BOOKSHELF = ITEMS.register("wooden_barrel_bookshelf", () -> new GeoBlockItem(ModBlocks.WOODEN_BARREL_BOOKSHELF.get(), new Item.Properties()));

    RegistryObject<Item> WOODWORKING_TABLE = ITEMS.register("woodworking_table", () -> new GeoBlockItem(ModBlocks.WOODWORKING_TABLE.get(), new Item.Properties()));

    RegistryObject<Item> LONG_STORAGE_TABLE = ITEMS.register("long_storage_table", () -> new GeoBlockItem(ModBlocks.LONG_STORAGE_TABLE.get(), new Item.Properties()));

    RegistryObject<Item> EDGED_CHALKBOARD = ITEMS.register("edged_chalkboard", () -> new GeoBlockItem(ModBlocks.EDGED_CHALKBOARD.get(), new Item.Properties()));

    RegistryObject<Item> WINE_AROMA_RED_WALLPAPER_WALL = ITEMS.register("wine_aroma_red_wallpaper_wall", () ->
            new BlockItem(ModBlocks.WINE_AROMA_RED_WALLPAPER_WALL.get(), new Item.Properties()));

    RegistryObject<Item> WINE_AROMA_BLUE_WALLPAPER_WALL = ITEMS.register("wine_aroma_blue_wallpaper_wall", () ->
            new BlockItem(ModBlocks.WINE_AROMA_BLUE_WALLPAPER_WALL.get(), new Item.Properties()));

    RegistryObject<Item> BLACK_AND_WHITE_CHECKER_BOARD_TILE = ITEMS.register("black_and_white_checker_board_tile", () ->
            new BlockItem(ModBlocks.BLACK_AND_WHITE_CHECKER_BOARD_TILE.get(), new Item.Properties()));

    RegistryObject<Item> BLUE_AND_WHITE_CHECKER_BOARD_TILE = ITEMS.register("blue_and_white_checker_board_tile", () ->
            new BlockItem(ModBlocks.BLUE_AND_WHITE_CHECKER_BOARD_TILE.get(), new Item.Properties()));

    RegistryObject<Item> UNDERGROUND_WALLPAPER_WALL = ITEMS.register("underground_wallpaper_wall", () ->
            new BlockItem(ModBlocks.UNDERGROUND_WALLPAPER_WALL.get(), new Item.Properties()));

    RegistryObject<Item> RUSTIC_BLUE_WALLPAPER_WALL = ITEMS.register("rustic_blue_wallpaper_wall", () ->
            new BlockItem(ModBlocks.RUSTIC_BLUE_WALLPAPER_WALL.get(), new Item.Properties()));

    RegistryObject<Item> KAZI_LUCKY_CAT = ITEMS.register("kazi_lucky_cat", () -> new GeoBlockItem(ModBlocks.KAZI_LUCKY_CAT.get(), new Item.Properties()));

    RegistryObject<Item> CUPBOARD = ITEMS.register("cupboard", () -> new BlockItem(ModBlocks.CUPBOARD.get(), new Item.Properties()));

    RegistryObject<Item> LARGE_DINING_TABLE = ITEMS.register("large_dining_table", () -> new BlockItem(ModBlocks.LARGE_DINING_TABLE.get(), new Item.Properties()));

    RegistryObject<Item> CANOPY_TREE_LIMB = ITEMS.register("canopy_tree_limb", () -> new BlockItem(ModBlocks.CANOPY_TREE_LIMB.get(), new Item.Properties()));

    RegistryObject<Item> GIFT_FROM_KAZI_MANOR = ITEMS.register("gift_from_kazi_manor", () -> new BlockItem(ModBlocks.GIFT_FROM_KAZI_MANOR.get(), new Item.Properties()));

    RegistryObject<Item> KEY_UNDER_THE_LAKE = ITEMS.register("key_under_the_lake", () -> new BlockItem(ModBlocks.KEY_UNDER_THE_LAKE.get(), new Item.Properties()));

    RegistryObject<Item> FIREPLACE_DECORATION = ITEMS.register("fireplace_decoration", () -> new BlockItem(ModBlocks.FIREPLACE_DECORATION.get(), new Item.Properties()));

    RegistryObject<Item> CANOPY_TREE_FOLIAGE = ITEMS.register("canopy_tree_foliage", () -> new BlockItem(ModBlocks.CANOPY_TREE_FOLIAGE.get(), new Item.Properties()));

    RegistryObject<Item> CANOPY_TREE_FERN = ITEMS.register("canopy_tree_fern", () -> new BlockItem(ModBlocks.CANOPY_TREE_FERN.get(), new Item.Properties()));

    RegistryObject<Item> CANOPY_TREE_DROOPING_ROOT = ITEMS.register("canopy_tree_drooping_root", () -> new BlockItem(ModBlocks.CANOPY_TREE_DROOPING_ROOT.get(), new Item.Properties()));

    RegistryObject<Item> STAR_EMBELLISHED_CEILING = ITEMS.register("star_embellished_ceiling", () -> new BlockItem(ModBlocks.STAR_EMBELLISHED_CEILING.get(), new Item.Properties()));

    RegistryObject<Item> PALM_TREE_CROWN = ITEMS.register("palm_tree_crown", () -> new BlockItem(ModBlocks.PALM_TREE_CROWN.get(), new Item.Properties()));

    RegistryObject<Item> PALM_TREE_TOP = ITEMS.register("palm_tree_top", () -> new BlockItem(ModBlocks.PALM_TREE_TOP.get(), new Item.Properties()));

    RegistryObject<Item> PALM_TREE_TRUNK_TOP = ITEMS.register("palm_tree_trunk_top", () -> new BlockItem(ModBlocks.PALM_TREE_TRUNK_TOP.get(), new Item.Properties()));

    RegistryObject<Item> PALM_TREE_TRUNK = ITEMS.register("palm_tree_trunk", () -> new BlockItem(ModBlocks.PALM_TREE_TRUNK.get(), new Item.Properties()));

    RegistryObject<Item> ROUGH_PALM_TREE_TRUNK = ITEMS.register("rough_palm_tree_trunk", () -> new BlockItem(ModBlocks.ROUGH_PALM_TREE_TRUNK.get(), new Item.Properties()));

    RegistryObject<Item> UNDERGROUND_PANELLING = ITEMS.register("underground_panelling", () -> new BlockItem(ModBlocks.UNDERGROUND_PANELLING.get(), new Item.Properties()));

    // 折纸
    RegistryObject<Item> LOW_CABINET_WITH_TABLECLOTH_ORIGAMI = ITEMS.register("low_cabinet_with_tablecloth_origami", () -> new Item(new Item.Properties()));

    RegistryObject<Item> WOODEN_BARREL_BOOKSHELF_ORIGAMI = ITEMS.register("wooden_barrel_bookshelf_origami", () -> new Item(new Item.Properties()));

    RegistryObject<Item> WOODWORKING_TABLE_ORIGAMI = ITEMS.register("woodworking_table_origami", () -> new Item(new Item.Properties()));

    RegistryObject<Item> LONG_STORAGE_TABLE_ORIGAMI = ITEMS.register("long_storage_table_origami", () -> new Item(new Item.Properties()));

    RegistryObject<Item> EDGED_CHALKBOARD_ORIGAMI = ITEMS.register("edged_chalkboard_origami", () -> new Item(new Item.Properties()));

    RegistryObject<Item> CUPBOARD_ORIGAMI = ITEMS.register("cupboard_origami", () -> new Item(new Item.Properties()));

    RegistryObject<Item> FIREPLACE_DECORATION_ORIGAMI = ITEMS.register("fireplace_decoration_origami", () -> new Item(new Item.Properties()));

    RegistryObject<Item> LARGE_DINING_TABLE_ORIGAMI = ITEMS.register("large_dining_table_origami", () -> new Item(new Item.Properties()));

    RegistryObject<Item> RED_VELVET_CHAISE_LONGUE_ORIGAMI = ITEMS.register("red_velvet_chaise_longue_origami", () -> new Item(new Item.Properties()));

    RegistryObject<Item> OLD_ORGAN_ORIGAMI = ITEMS.register("old_organ_origami", () -> new Item(new Item.Properties()));
}
