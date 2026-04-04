package com.kazi_cat.papercraft_magic_decoration.client.init;

import com.kazi_cat.papercraft_magic_decoration.client.gui.CopperBartenderScreen;
import com.kazi_cat.papercraft_magic_decoration.client.model.BlockGeoModelManager;
import com.kazi_cat.papercraft_magic_decoration.client.model.ItemGeoModelManager;
import com.kazi_cat.papercraft_magic_decoration.client.render.block.BaseGeoBlockRenderer;
import com.kazi_cat.papercraft_magic_decoration.client.render.block.DistillerBlockRenderer;
import com.kazi_cat.papercraft_magic_decoration.client.render.block.DrinkBlockRenderer;
import com.kazi_cat.papercraft_magic_decoration.client.render.block.PaperCuttingTableBlockRenderer;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.inventory.container.CopperBartenderContainer;
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
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlocks.DRINK_BE.get(), DrinkBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlocks.DISTILLER_BE.get(), DistillerBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlocks.PAPER_CUTTING_TABLE_BE.get(), context -> new PaperCuttingTableBlockRenderer());
        event.registerBlockEntityRenderer(ModBlocks.COPPER_BARTENDER_BE.get(), context -> new BaseGeoBlockRenderer<>(ModBlocks.COPPER_BARTENDER_BE.get()));
    }
}
