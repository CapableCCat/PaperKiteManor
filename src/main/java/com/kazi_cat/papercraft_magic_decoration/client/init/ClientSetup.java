package com.kazi_cat.papercraft_magic_decoration.client.init;

import com.kazi_cat.papercraft_magic_decoration.client.model.BlockGeoModelManager;
import com.kazi_cat.papercraft_magic_decoration.client.model.ItemGeoModelManager;
import com.kazi_cat.papercraft_magic_decoration.client.render.block.BaseGeoBlockRenderer;
import com.kazi_cat.papercraft_magic_decoration.client.render.block.PaperCuttingTableBlockRenderer;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
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
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlocks.ANIMATED_SMELTABLE_BE.get(), context -> new BaseGeoBlockRenderer<>(ModBlocks.ANIMATED_SMELTABLE_BE.get()));
        event.registerBlockEntityRenderer(ModBlocks.PAPER_CUTTING_TABLE_BE.get(), context -> new PaperCuttingTableBlockRenderer());
    }
}
