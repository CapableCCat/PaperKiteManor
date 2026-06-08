package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.item.ItemNameGeoBlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModItems {
    DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PaperKiteManor.MOD_ID);

    RegistryObject<Item> RAW_SAUSAGE_MACE_WEAPON = ITEMS.register("raw_sausage_mace_weapon", () -> new ItemNameGeoBlockItem(ModBlocks.SAUSAGE_MACE_WEAPON_BLOCK.get(), new Item.Properties()));
    RegistryObject<Item> SAUSAGE_MACE_WEAPON = ITEMS.register("sausage_mace_weapon", () -> new ItemNameGeoBlockItem(ModBlocks.SAUSAGE_MACE_WEAPON_BLOCK.get(), new Item.Properties().stacksTo(1)));
}
