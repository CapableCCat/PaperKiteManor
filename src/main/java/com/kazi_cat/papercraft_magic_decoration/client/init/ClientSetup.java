package com.kazi_cat.papercraft_magic_decoration.client.init;

import com.kazi_cat.papercraft_magic_decoration.client.gui.*;
import com.kazi_cat.papercraft_magic_decoration.client.model.BlockGeoModelManager;
import com.kazi_cat.papercraft_magic_decoration.client.model.ItemGeoModelManager;
import com.kazi_cat.papercraft_magic_decoration.client.render.block.BaseGeoBlockRenderer;
import com.kazi_cat.papercraft_magic_decoration.client.render.block.CopperStillBlockRenderer;
import com.kazi_cat.papercraft_magic_decoration.client.render.block.PaperCuttingTableBlockRenderer;
import com.kazi_cat.papercraft_magic_decoration.client.render.entity.*;
import com.kazi_cat.papercraft_magic_decoration.compat.ponder.PonderCompat;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.ModEntities;
import com.kazi_cat.papercraft_magic_decoration.inventory.container.*;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
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
        event.enqueueWork(PonderCompat::init);
        event.enqueueWork(() -> MenuScreens.register(CopperBartenderContainer.TYPE, CopperBartenderScreen::new));
        event.enqueueWork(() -> MenuScreens.register(DirtHoleContainer.TYPE, DirtHoleScreen::new));
        event.enqueueWork(() -> MenuScreens.register(KaziLuckyCatContainer.TYPE, KaziLuckyCatScreen::new));
        event.enqueueWork(() -> MenuScreens.register(BunnySuitcaseContainer.TYPE, BunnySuitcaseScreen::new));
        event.enqueueWork(() -> MenuScreens.register(LobbyBoyBackpackContainer.TYPE, LobbyBoyBackpackScreen::new));
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlocks.COPPER_STILL_BE.get(), CopperStillBlockRenderer::new);
        event.registerBlockEntityRenderer(ModBlocks.SIMPLE_ANIMATED_BE.get(), context -> new BaseGeoBlockRenderer<>(ModBlocks.SIMPLE_ANIMATED_BE.get()));
        event.registerBlockEntityRenderer(ModBlocks.ANIMATED_SMELTABLE_BE.get(), context -> new BaseGeoBlockRenderer<>(ModBlocks.ANIMATED_SMELTABLE_BE.get()));
        event.registerBlockEntityRenderer(ModBlocks.PAPER_CUTTING_TABLE_BE.get(), context -> new PaperCuttingTableBlockRenderer());
        event.registerBlockEntityRenderer(ModBlocks.COPPER_BARTENDER_BE.get(), context -> new BaseGeoBlockRenderer<>(ModBlocks.COPPER_BARTENDER_BE.get()));
        event.registerBlockEntityRenderer(ModBlocks.KAZI_LUCKY_CAT_BE.get(), context -> new BaseGeoBlockRenderer<>(ModBlocks.KAZI_LUCKY_CAT_BE.get()));
        event.registerBlockEntityRenderer(ModBlocks.OLD_ORGAN_BE.get(), context -> new BaseGeoBlockRenderer<>(ModBlocks.OLD_ORGAN_BE.get()));

        event.registerEntityRenderer(ModEntities.AOAO.get(), AoaoRenderer::new);
        event.registerEntityRenderer(ModEntities.WHITE_RABBIT_MAID.get(), WhiteRabbitMaidRenderer::new);
        event.registerEntityRenderer(ModEntities.BLACK_CAT_LOBBY_BOY.get(), BlackCatLobbyBoyRenderer::new);
        event.registerEntityRenderer(ModEntities.AIR_FLOW_CARP.get(), AirFlowCarpRenderer::new);
        event.registerEntityRenderer(ModEntities.PAPER_TIGER.get(), PaperTigerRenderer::new);
        event.registerEntityRenderer(ModEntities.FLYING_CHICKEN.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.THROWN_VODKA.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.THROWN_FIRE_WHISKEY.get(), ThrownItemRenderer::new);
    }
}
