package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.item.ItemNameGeoBlockItem;
import com.kazi_cat.papercraft_magic_decoration.item.JumboSalmonItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModItems {
    DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PaperKiteManor.MOD_ID);

    RegistryObject<Item> SAUSAGE_MACE_WEAPON = ITEMS.register("sausage_mace_weapon", () -> new ItemNameGeoBlockItem(ModBlocks.SAUSAGE_MACE_WEAPON_BLOCK.get(), new Item.Properties().stacksTo(1)));
    RegistryObject<Item> RAW_SAUSAGE_MACE_WEAPON = ITEMS.register("raw_sausage_mace_weapon", () -> new ItemNameGeoBlockItem(ModBlocks.SAUSAGE_MACE_WEAPON_BLOCK.get(), new Item.Properties()));
    RegistryObject<Item> SMOKED_SALMON_HEAD = ITEMS.register("smoked_salmon_head", () -> new ItemNameBlockItem(ModBlocks.SALMON_HEAD.get(), new Item.Properties().food(ModFoods.SMOKED_SALMON_HEAD)));
    RegistryObject<Item> CHUNKY_SMOKED_SALMON = ITEMS.register("chunky_smoked_salmon", () -> new ItemNameBlockItem(ModBlocks.CHUNKY_SALMON.get(), new Item.Properties().food(ModFoods.CHUNKY_SMOKED_SALMON)));
    RegistryObject<Item> CHUNKY_SALMON = ITEMS.register("chunky_salmon", () -> new ItemNameBlockItem(ModBlocks.CHUNKY_SALMON.get(), new Item.Properties().food(ModFoods.CHUNKY_SALMON)));
    RegistryObject<Item> JUMBO_SALMON = ITEMS.register("jumbo_salmon", () -> new JumboSalmonItem(new Item.Properties()));
    RegistryObject<Item> MANGA_MEAT = ITEMS.register("manga_meat", () -> new ItemNameGeoBlockItem(ModBlocks.MANGA_MEAT.get(), new Item.Properties().food(ModFoods.MANGA_MEAT)));
    RegistryObject<Item> RAW_MANGA_MEAT = ITEMS.register("raw_manga_meat", () -> new ItemNameGeoBlockItem(ModBlocks.MANGA_MEAT.get(), new Item.Properties()));
}
