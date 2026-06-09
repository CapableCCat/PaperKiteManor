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
            .icon(ModItems.DEWY_MEMBRANE.get()::getDefaultInstance)
            .displayItems((par, output) -> {
                output.accept(ModItems.BLACK_PAPER.get());
                output.accept(ModItems.BLUE_PAPER.get());
                output.accept(ModItems.COTTON_SERGE.get());
                output.accept(ModItems.DEWY_MEMBRANE.get());
                output.accept(ModItems.RED_PAPER.get());
                output.accept(ModItems.WHITE_PAPER.get());
                output.accept(ModItems.YELLOW_PAPER.get());

                output.accept(ModItems.BLACK_PAPER_BLOCK.get());
                output.accept(ModItems.BLUE_PAPER_BLOCK.get());
                output.accept(ModItems.COTTON_SERGE_BLOCK.get());
                output.accept(ModItems.DEWY_MEMBRANE_BLOCK.get());
                output.accept(ModItems.RED_PAPER_BLOCK.get());
                output.accept(ModItems.WHITE_PAPER_BLOCK.get());
                output.accept(ModItems.YELLOW_PAPER_BLOCK.get());
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
                output.accept(ModItems.DIONYSUS.get());
                output.accept(ModItems.KALEIDOSCOPE_WHISKEY_SOUR.get());
                output.accept(ModItems.LONG_ISLAND_POPSICLE_TEA.get());
                output.accept(ModItems.NOCTURNAL_CAT_COFFEE.get());
                output.accept(ModItems.GOLD_MEDAL_COFFEE.get());
                output.accept(ModItems.WHITE_RABBIT_MOCHA.get());
                output.accept(ModItems.GUANG_S.get());

                output.accept(ModItems.BOTTLE_OF_BLAZE_WHISKEY.get());
                output.accept(ModItems.BOTTLE_OF_FERRY_WHISKEY.get());
                output.accept(ModItems.BOTTLE_OF_FLY_WHISKEY.get());
                output.accept(ModItems.BOTTLE_OF_LAND_NO1.get());
                output.accept(ModItems.BOTTLE_OF_LUCKY_CACTUS.get());
                output.accept(ModItems.BOTTLE_OF_POISON_RUM.get());
                output.accept(ModItems.PACK_OF_GUANG_S.get());

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

//    RegistryObject<CreativeModeTab> MANOR_DECORATION_TAB = TABS.register("manor_decoration", () -> CreativeModeTab.builder()
//            .title(Component.translatable("item_group.papercraft_magic_decoration.manor_decoration.name"))
//            .icon(ModItems.ROSES_IN_WATER_BOTTLE.get()::getDefaultInstance)
//            .displayItems((par, output) -> {
//
//            }).build());
}
