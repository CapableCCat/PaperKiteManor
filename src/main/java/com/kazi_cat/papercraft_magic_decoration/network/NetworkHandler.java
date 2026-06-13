package com.kazi_cat.papercraft_magic_decoration.network;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.network.message.SyncOffersMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class NetworkHandler {
    private static final String VERSION = "1.0.0";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(PaperKiteManor.modLoc("network"), () -> "1.0.0", (it) -> it.equals("1.0.0"), (it) -> it.equals("1.0.0"));

    public static void init() {
        CHANNEL.registerMessage(0, SyncOffersMessage.class, SyncOffersMessage::encode, SyncOffersMessage::decode, SyncOffersMessage::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
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

}
