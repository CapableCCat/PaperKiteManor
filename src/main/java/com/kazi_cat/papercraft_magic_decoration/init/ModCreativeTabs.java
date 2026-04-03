package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public interface ModCreativeTabs {
    DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PaperKiteManor.MOD_ID);

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
            }).build());
}
