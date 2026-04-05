package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public interface ModCreativeTabs {
    DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PaperKiteManor.MOD_ID);

    RegistryObject<CreativeModeTab> MANOR_MAIN_TAB = TABS.register("manor_main", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group.papercraft_magic_decoration.manor_main.name"))
            .icon(ModItems.AMETHYST_SCISSORS.get()::getDefaultInstance)
            .displayItems((par, output) -> {
                output.accept(ModItems.AMETHYST_SCISSORS.get());
                output.accept(ModItems.GARDEN_TROWEL.get());

                output.accept(ModItems.COPPER_STILL.get());
                output.accept(ModItems.COPPER_BARTENDER.get());
                output.accept(ModItems.SPORES_COLLECTION_PLATE.get());
                output.accept(ModItems.PAPER_CUTTING_TABLE.get());

                output.accept(ModItems.BLACK_PAPER_BLOCK.get());
                output.accept(ModItems.BLUE_PAPER_BLOCK.get());
                output.accept(ModItems.COTTON_SERGE_BLOCK.get());
                output.accept(ModItems.DEWY_MEMBRANE_BLOCK.get());
                output.accept(ModItems.RED_PAPER_BLOCK.get());
                output.accept(ModItems.WHITE_PAPER_BLOCK.get());
                output.accept(ModItems.YELLOW_PAPER_BLOCK.get());

                output.accept(ModItems.BLACK_PAPER.get());
                output.accept(ModItems.BLUE_PAPER.get());
                output.accept(ModItems.COTTON_SERGE.get());
                output.accept(ModItems.DEWY_MEMBRANE.get());
                output.accept(ModItems.RED_PAPER.get());
                output.accept(ModItems.WHITE_PAPER.get());
                output.accept(ModItems.YELLOW_PAPER.get());

                output.accept(ModItems.VITALITY_SPORES.get());
                output.accept(ModItems.WHISKEY_RAW.get());
                output.accept(ModItems.COFFEE_FRUIT.get());
                output.accept(ModItems.GOLDEN_COFFEE_FRUIT.get());

                output.accept(ModItems.LOW_CABINET_WITH_TABLECLOTH_ORIGAMI.get());
                output.accept(ModItems.WOODEN_BARREL_BOOKSHELF_ORIGAMI.get());
                output.accept(ModItems.WOODWORKING_TABLE_ORIGAMI.get());
                output.accept(ModItems.LONG_STORAGE_TABLE_ORIGAMI.get());
                output.accept(ModItems.EDGED_CHALKBOARD_ORIGAMI.get());
                output.accept(ModItems.CUPBOARD_ORIGAMI.get());
                output.accept(ModItems.FIREPLACE_DECORATION_ORIGAMI.get());
                output.accept(ModItems.LARGE_DINING_TABLE_ORIGAMI.get());
                output.accept(ModItems.RED_VELVET_CHAISE_LONGUE_ORIGAMI.get());
                output.accept(ModItems.OLD_ORGAN_ORIGAMI.get());
            }).build());

    RegistryObject<CreativeModeTab> MANOR_FOOD_TAB = TABS.register("manor_food", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group.papercraft_magic_decoration.manor_food.name"))
            .icon(ModItems.LAND_NO1.get()::getDefaultInstance)
            .displayItems((par, output) -> {
                output.accept(ModItems.BLAZE_WHISKEY.get());
                output.accept(ModItems.FERRY_WHISKEY.get());
                output.accept(ModItems.FLY_WHISKEY.get());
                output.accept(ModItems.LAND_NO1.get());
                output.accept(ModItems.LUCKY_CACTUS.get());
                output.accept(ModItems.POISON_RUM.get());
                output.accept(ModItems.BLOODY_MARY.get());
                output.accept(ModItems.DEVIL_MARGARITA.get());
                output.accept(ModItems.DIPLOMATICO_COFFEE.get());
                output.accept(ModItems.NOCTURNAL_CAT_COFFEE.get());
                output.accept(ModItems.GOLD_MEDAL_COFFEE.get());
                output.accept(ModItems.GUANG_S.get());

                output.accept(ModItems.BOTTLE_OF_BLAZE_WHISKEY.get());
                output.accept(ModItems.BOTTLE_OF_FERRY_WHISKEY.get());
                output.accept(ModItems.BOTTLE_OF_FLY_WHISKEY.get());
                output.accept(ModItems.BOTTLE_OF_LAND_NO1.get());
                output.accept(ModItems.BOTTLE_OF_LUCKY_CACTUS.get());
                output.accept(ModItems.BOTTLE_OF_POISON_RUM.get());
                output.accept(ModItems.PACK_OF_GUANG_S.get());

                output.accept(ModItems.COFFEE_PASTINACA_SATIVA_TUBER.get());

                output.accept(ModItems.BREADED_RAW_CHICKEN.get());
                output.accept(ModItems.BUCKET_OF_FRIED_CHICKEN.get());
                output.accept(ModItems.FRIED_CHICKEN_LEG.get());

                output.accept(ModItems.RAW_SAUSAGE_MACE_WEAPON.get());
                output.accept(ModItems.SAUSAGE_MACE_WEAPON.get());
                output.accept(ModItems.CUBED_SAUSAGE.get());
                output.accept(ModItems.JUMBO_SALMON.get());
                output.accept(ModItems.CHUNKY_SALMON.get());
                output.accept(ModItems.CHUNKY_SMOKED_SALMON.get());
                output.accept(ModItems.SMOKED_SALMON_HEAD.get());
                output.accept(ModItems.RAW_MANGA_MEAT.get());
                output.accept(ModItems.MANGA_MEAT.get());
                output.accept(ModItems.MONSTER_STEAK.get());
                output.accept(ModItems.LARGE_STEAK.get());
            }).build());

    RegistryObject<CreativeModeTab> MANOR_DECORATION_TAB = TABS.register("manor_decoration", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group.papercraft_magic_decoration.manor_decoration.name"))
            .icon(ModItems.ROSES_IN_WATER_BOTTLE.get()::getDefaultInstance)
            .displayItems((par, output) -> {
                output.accept(ModItems.WINE_AROMA_RED_WALLPAPER_WALL.get());
                output.accept(ModItems.WINE_AROMA_BLUE_WALLPAPER_WALL.get());
                output.accept(ModItems.UNDERGROUND_WALLPAPER_WALL.get());
                output.accept(ModItems.RUSTIC_BLUE_WALLPAPER_WALL.get());
                output.accept(ModItems.BLACK_AND_WHITE_CHECKER_BOARD_TILE.get());
                output.accept(ModItems.BLUE_AND_WHITE_CHECKER_BOARD_TILE.get());

                output.accept(ModItems.UNDERGROUND_PANELLING.get());
                output.accept(ModItems.STAR_EMBELLISHED_CEILING.get());
                output.accept(ModItems.ROSES_IN_WATER_BOTTLE.get());
                output.accept(ModItems.LOW_CABINET_WITH_TABLECLOTH.get());
                output.accept(ModItems.WOODEN_BARREL_BOOKSHELF.get());
                output.accept(ModItems.WOODWORKING_TABLE.get());
                output.accept(ModItems.LONG_STORAGE_TABLE.get());
                output.accept(ModItems.EDGED_CHALKBOARD.get());
                output.accept(ModItems.CUPBOARD.get());
                output.accept(ModItems.FIREPLACE_DECORATION.get());
                output.accept(ModItems.LARGE_DINING_TABLE.get());

                output.accept(ModItems.LOUD_BUTTON.get());
                output.accept(ModItems.GIFT_FROM_KAZI_MANOR.get());
                output.accept(ModItems.KEY_UNDER_THE_LAKE.get());

                output.accept(ModItems.PALM_TREE_CROWN.get());
                output.accept(ModItems.PALM_TREE_TOP.get());
                output.accept(ModItems.PALM_TREE_TRUNK_TOP.get());
                output.accept(ModItems.PALM_TREE_TRUNK.get());
                output.accept(ModItems.ROUGH_PALM_TREE_TRUNK.get());

                output.accept(ModItems.CANOPY_TREE_FOLIAGE.get());
                output.accept(ModItems.CANOPY_TREE_FERN.get());
                output.accept(ModItems.CANOPY_TREE_DROOPING_ROOT.get());
                output.accept(ModItems.CANOPY_TREE_LIMB.get());
                output.accept(ModItems.CANOPY_TREE_TRUNK.get());
                output.accept(ModItems.CANOPY_TREE_MUSHROOM.get());
            }).build());
}
