package com.kazi_cat.papercraft_magic_decoration;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(PaperKiteManor.MOD_ID)
public class PaperKiteManor {
    public static final String MOD_ID = "papercraft_magic_decoration";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation modLoc(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public PaperKiteManor() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    }
}
