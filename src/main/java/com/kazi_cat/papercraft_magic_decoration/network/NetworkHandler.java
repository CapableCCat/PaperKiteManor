package com.kazi_cat.papercraft_magic_decoration.network;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.network.message.OffersSyncMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = PaperKiteManor.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetworkHandler {
    private static final String VERSION = "1.0.0";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(PaperKiteManor.resourceLocation("network"), () -> "1.0.0", (it) -> it.equals("1.0.0"), (it) -> it.equals("1.0.0"));

    public static void init() {
        CHANNEL.registerMessage(28, OffersSyncMessage.class, OffersSyncMessage::encode, OffersSyncMessage::decode, OffersSyncMessage::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    public static void sendToClientPlayer(Object message, Player player) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)player), message);
    }

    public static void sendToTrackingEntity(Object message, Entity centerEntity) {
        CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> centerEntity), message);
    }

    public static void sendToServer(Object message) {
        CHANNEL.sendToServer(message);
    }

    @SubscribeEvent
    public static void onSetupEvent(FMLCommonSetupEvent event) {
        event.enqueueWork(NetworkHandler::init);
    }
}
