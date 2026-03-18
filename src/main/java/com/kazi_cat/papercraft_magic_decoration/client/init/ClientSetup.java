package com.kazi_cat.papercraft_magic_decoration.client.init;

import com.kazi_cat.papercraft_magic_decoration.client.gui.CopperBartenderScreen;
import com.kazi_cat.papercraft_magic_decoration.client.gui.DirtHoleScreen;
import com.kazi_cat.papercraft_magic_decoration.client.model.BlockGeoModelManager;
import com.kazi_cat.papercraft_magic_decoration.client.model.ItemGeoModelManager;
import com.kazi_cat.papercraft_magic_decoration.client.render.BaseGeoBlockRenderer;
import com.kazi_cat.papercraft_magic_decoration.client.render.GlassDrinkBlockRenderer;
import com.kazi_cat.papercraft_magic_decoration.client.render.PaperCuttingTableBlockRenderer;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.inventory.container.CopperBartenderContainer;
import com.kazi_cat.papercraft_magic_decoration.inventory.container.DirtHoleContainer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(BlockGeoModelManager::loadModelMappings);
        event.enqueueWork(ItemGeoModelManager::loadModelMappings);
        event.enqueueWork(() -> MenuScreens.register(CopperBartenderContainer.TYPE, CopperBartenderScreen::new));
        event.enqueueWork(() -> MenuScreens.register(DirtHoleContainer.TYPE, DirtHoleScreen::new));
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlocks.GLASS_DRINK_BE.get(), GlassDrinkBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlocks.ANIMATED_BE.get(), context -> new BaseGeoBlockRenderer<>(ModBlocks.ANIMATED_BE.get()));
        event.registerBlockEntityRenderer(ModBlocks.PAPER_CUTTING_TABLE_BE.get(), context -> new PaperCuttingTableBlockRenderer());
        event.registerBlockEntityRenderer(ModBlocks.ANIMATED_SMELTABLE_BE.get(), context -> new BaseGeoBlockRenderer<>(ModBlocks.ANIMATED_SMELTABLE_BE.get()));
        event.registerBlockEntityRenderer(ModBlocks.COPPER_BARTENDER_BE.get(), context -> new BaseGeoBlockRenderer<>(ModBlocks.COPPER_BARTENDER_BE.get()));
    }
}
