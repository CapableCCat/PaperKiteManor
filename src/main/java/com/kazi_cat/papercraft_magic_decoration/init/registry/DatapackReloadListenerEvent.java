package com.kazi_cat.papercraft_magic_decoration.init.registry;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.datamap.resources.SmeltableBlockDataReloadListener;
import com.kazi_cat.papercraft_magic_decoration.network.NetworkHandler;
import com.kazi_cat.papercraft_magic_decoration.network.message.SmeltableBlockDataSyncS2CMessage;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PaperKiteManor.MOD_ID)
public class DatapackReloadListenerEvent {
    @SubscribeEvent
    public static void onAddReloadListenerEvent(AddReloadListenerEvent event) {
        event.addListener(new SmeltableBlockDataReloadListener());
    }

    @SubscribeEvent
    public static void onDatapackSyncEvent(OnDatapackSyncEvent event) {
        SmeltableBlockDataSyncS2CMessage message = SmeltableBlockDataSyncS2CMessage.fromServer();
        event.getPlayers().forEach(player -> NetworkHandler.sendToClientPlayer(message, player));
    }
}