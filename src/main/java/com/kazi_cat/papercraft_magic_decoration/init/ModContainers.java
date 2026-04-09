package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.inventory.container.*;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModContainers {
    DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, PaperKiteManor.MOD_ID);

    RegistryObject<MenuType<CopperBartenderContainer>> COPPER_BARTENDER_CONTAINER = CONTAINER_TYPES.register("copper_bartender_container", () -> CopperBartenderContainer.TYPE);
    RegistryObject<MenuType<DirtHoleContainer>> DIRT_HOLE_CONTAINER = CONTAINER_TYPES.register("dirt_hole_container", () -> DirtHoleContainer.TYPE);
    RegistryObject<MenuType<KaziLuckyCatContainer>> KAZI_LUCKY_CAT_CONTAINER = CONTAINER_TYPES.register("kazi_lucky_cat_container", () -> KaziLuckyCatContainer.TYPE);
    RegistryObject<MenuType<BunnySuitcaseContainer>> BUNNY_SUITCASE_CONTAINER = CONTAINER_TYPES.register("bunny_suitcase_container", () -> BunnySuitcaseContainer.TYPE);
    RegistryObject<MenuType<LobbyBoyBackpackContainer>> LOBBY_BOY_BACKPACK_CONTAINER = CONTAINER_TYPES.register("lobby_boy_backpack_container", () -> LobbyBoyBackpackContainer.TYPE);
}
