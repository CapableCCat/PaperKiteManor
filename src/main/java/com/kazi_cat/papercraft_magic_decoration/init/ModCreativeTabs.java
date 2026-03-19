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

                output.accept(ModItems.COPPER_BARTENDER.get());
                output.accept(ModItems.VITALITY_SPORES.get());
                output.accept(ModItems.WHISKEY_RAW.get());
                output.accept(ModItems.COFFEE_FRUIT.get());
                output.accept(ModItems.GOLDEN_COFFEE_FRUIT.get());
            }).build());

    RegistryObject<CreativeModeTab> MANOR_FOOD_TAB = TABS.register("manor_food", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group.papercraft_magic_decoration.manor_food.name"))
            .icon(ModItems.GLASS_OF_LAND_NO1.get()::getDefaultInstance)
            .displayItems((par, output) -> {
                output.accept(ModItems.BLAZE_WHISKEY.get());
                output.accept(ModItems.FERRY_WHISKEY.get());
                output.accept(ModItems.FLY_WHISKEY.get());
                output.accept(ModItems.LAND_NO1.get());
                output.accept(ModItems.LUCKY_CACTUS.get());
                output.accept(ModItems.POISON_RUM.get());

                output.accept(ModItems.GLASS_OF_BLAZE_WHISKEY.get());
                output.accept(ModItems.GLASS_OF_FERRY_WHISKEY.get());
                output.accept(ModItems.GLASS_OF_FLY_WHISKEY.get());
                output.accept(ModItems.GLASS_OF_LAND_NO1.get());
                output.accept(ModItems.GLASS_OF_LUCKY_CACTUS.get());
                output.accept(ModItems.GLASS_OF_POISON_RUM.get());
                output.accept(ModItems.BLOODY_MARY.get());
                output.accept(ModItems.DIPLOMAT_COFFEE.get());
                output.accept(ModItems.DEVIL_MARGARITA.get());
                output.accept(ModItems.PACK_OF_GUANG_S.get());
                output.accept(ModItems.GUANG_S.get());

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
                output.accept(ModItems.COFFEE_PASTINACA_SATIVA_TUBER.get());
            }).build());

    RegistryObject<CreativeModeTab> MANOR_DECORATION_TAB = TABS.register("manor_decoration", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group.papercraft_magic_decoration.manor_decoration.name"))
            .icon(ModItems.PAPER_CUTTING_TABLE.get()::getDefaultInstance)
            .displayItems((par, output) -> {
                output.accept(ModItems.PAPER_CUTTING_TABLE.get());

                output.accept(ModItems.LOUD_BUTTON.get());
                output.accept(ModItems.LOW_CABINET_WITH_TABLECLOTH.get());
                output.accept(ModItems.WOODEN_BARREL_BOOKSHELF.get());
                output.accept(ModItems.WOODWORKING_TABLE.get());
                output.accept(ModItems.LONG_STORAGE_TABLE.get());
            }).build());
}
