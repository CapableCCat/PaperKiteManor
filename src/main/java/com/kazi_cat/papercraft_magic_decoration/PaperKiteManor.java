package com.kazi_cat.papercraft_magic_decoration;

import com.kazi_cat.papercraft_magic_decoration.init.*;
import com.kazi_cat.papercraft_magic_decoration.init.registry.DrinkRegistry;
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
        DrinkRegistry.init();

        ModBlocks.BLOCKS.register(modEventBus);
        ModBlocks.BLOCK_ENTITIES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModEffects.EFFECTS.register(modEventBus);
        ModCreativeTabs.TABS.register(modEventBus);
        ModRecipes.RECIPE_SERIALIZERS.register(modEventBus);
        ModContainers.CONTAINER_TYPES.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);
        ModSounds.SOUND_EVENTS.register(modEventBus);
    }
}
