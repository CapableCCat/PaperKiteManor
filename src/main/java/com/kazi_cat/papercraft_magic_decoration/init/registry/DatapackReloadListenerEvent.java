package com.kazi_cat.papercraft_magic_decoration.init.registry;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.datamap.resources.SmeltableBlockDataReloadListener;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PaperKiteManor.MOD_ID)
public class DatapackReloadListenerEvent {
    @SubscribeEvent
    public static void onAddReloadListenerEvent(AddReloadListenerEvent event) {
        event.addListener(new SmeltableBlockDataReloadListener());
    }
}
