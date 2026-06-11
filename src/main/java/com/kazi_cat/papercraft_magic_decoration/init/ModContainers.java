package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.inventory.container.CopperBartenderContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModContainers {
    DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, PaperKiteManor.MOD_ID);

    RegistryObject<MenuType<CopperBartenderContainer>> COPPER_BARTENDER_CONTAINER = CONTAINER_TYPES.register("copper_bartender_container", () -> CopperBartenderContainer.TYPE);
}
