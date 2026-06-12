package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.block.chocolate.OversizedBoxOfChocolatesBlock;
import com.kazi_cat.papercraft_magic_decoration.init.registry.DrinkRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModCreativeTabs {
    DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PaperKiteManor.MOD_ID);

    RegistryObject<CreativeModeTab> MANOR_MAIN_TAB = TABS.register("manor_main", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group.papercraft_magic_decoration.manor_main.name"))
            .icon(ModItems.AMETHYST_SCISSORS.get()::getDefaultInstance)
            .displayItems((par, output) -> {
                output.accept(ModItems.AMETHYST_SCISSORS.get());
                output.accept(ModItems.GARDEN_TROWEL.get());

                output.accept(ModItems.PAPER_CUTTING_TABLE.get());
                output.accept(ModItems.COPPER_BARTENDER.get());
                output.accept(ModItems.COPPER_STILL.get());

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
            .icon(DrinkRegistry.getItem(DrinkRegistry.LAND_NO1)::getDefaultInstance)
            .displayItems((par, output) -> {
                DrinkRegistry.DRINK_DATA_MAP.keySet().forEach(key -> {
                    Item item = ForgeRegistries.ITEMS.getValue(key);
                    if (item != null) {
                        output.accept(item);
                    }
                });

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

                output.accept(ModItems.DARK_COCOA_IN_MOLD.get());
                output.accept(ModItems.MILK_COCOA_IN_MOLD.get());
                output.accept(ModItems.PRALINE_COCOA_IN_MOLD.get());
                output.accept(ModItems.MELTED_DARK_COCOA_IN_MOLD.get());
                output.accept(ModItems.MELTED_MILK_COCOA_IN_MOLD.get());
                output.accept(ModItems.MELTED_PRALINE_COCOA_IN_MOLD.get());
                output.accept(ModItems.TRUFFLE_CHOCOLATE.get());
                output.accept(ModItems.MILK_CHOCOLATE.get());
                output.accept(ModItems.PRALINE_CHOCOLATE.get());

                ItemStack chocolateBox = ModItems.OVERSIZED_BOX_OF_CHOCOLATES.get().getDefaultInstance();
                OversizedBoxOfChocolatesBlock.setContent(chocolateBox, new int[] {1,2,3,2,3,1});
                output.accept(chocolateBox);
            }).build());

    RegistryObject<CreativeModeTab> MANOR_DECORATION_TAB = TABS.register("manor_decoration", () -> CreativeModeTab.builder()
            .title(Component.translatable("item_group.papercraft_magic_decoration.manor_decoration.name"))
            .icon(ModItems.KAZI_LUCKY_CAT.get()::getDefaultInstance)
            .displayItems((par, output) -> {
                output.accept(ModItems.KAZI_LUCKY_CAT.get());
            }).build());
}
