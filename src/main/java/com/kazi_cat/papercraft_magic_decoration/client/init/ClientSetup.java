package com.kazi_cat.papercraft_magic_decoration.client.init;

import com.kazi_cat.papercraft_magic_decoration.client.render.block.GeoSmeltableBlockRenderer;
import com.kazi_cat.papercraft_magic_decoration.client.render.block.GlassDrinkBlockEntityRenderer;
import com.kazi_cat.papercraft_magic_decoration.client.render.block.PaperCuttingTableBlockEntityRenderer;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlocks.GLASS_DRINK_BE.get(), GlassDrinkBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlocks.PAPER_CUTTING_TABLE_BE.get(), context -> new PaperCuttingTableBlockEntityRenderer());
        event.registerBlockEntityRenderer(ModBlocks.GEO_SMELTABLE_BE.get(), context -> new GeoSmeltableBlockRenderer());
    }
}
