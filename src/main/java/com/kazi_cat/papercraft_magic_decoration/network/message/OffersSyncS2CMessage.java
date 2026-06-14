package com.kazi_cat.papercraft_magic_decoration.network.message;

import com.kazi_cat.papercraft_magic_decoration.entity.BlackCatLobbyBoyEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public record OffersSyncS2CMessage(int entityId, List<BlackCatLobbyBoyEntity.Offer> offers) {
    public static void encode(OffersSyncS2CMessage message, FriendlyByteBuf buf) {
        buf.writeVarInt(message.entityId);
        buf.writeVarInt(message.offers.size());
        message.offers.forEach(o -> buf.writeJsonWithCodec(CompoundTag.CODEC, o.serializeNBT()));
    }

    public static OffersSyncS2CMessage decode(FriendlyByteBuf buf) {
        int entityId = buf.readVarInt();
        List<BlackCatLobbyBoyEntity.Offer> offers = new ArrayList<>();
        int length = buf.readVarInt();
        for (int i = 0; i < length; i++) {
            offers.add(BlackCatLobbyBoyEntity.Offer.of(buf.readJsonWithCodec(CompoundTag.CODEC)));
        }
        return new OffersSyncS2CMessage(entityId, offers);
    }

    public static void handle(OffersSyncS2CMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> write(message));
        }

        context.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void write(OffersSyncS2CMessage message) {
        Level level = Minecraft.getInstance().level;
        if (level == null) {
            return;
        }
        if (level.getEntity(message.entityId) instanceof BlackCatLobbyBoyEntity blackCat) {
            blackCat.setOffers(message.offers());
        }
    }
}
