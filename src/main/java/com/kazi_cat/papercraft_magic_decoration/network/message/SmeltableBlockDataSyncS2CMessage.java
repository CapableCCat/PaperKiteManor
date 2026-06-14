package com.kazi_cat.papercraft_magic_decoration.network.message;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.datamap.data.SmeltableBlockData;
import com.kazi_cat.papercraft_magic_decoration.datamap.resources.SmeltableBlockDataReloadListener;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SmeltableBlockDataSyncS2CMessage {
    private static final String ENTRIES = "entries";

    private final CompoundTag data;

    public SmeltableBlockDataSyncS2CMessage(CompoundTag data) {
        this.data = data;
    }

    public static SmeltableBlockDataSyncS2CMessage fromServer() {
        CompoundTag syncData = new CompoundTag();
        ListTag entries = new ListTag();
        for (SmeltableBlockData data : SmeltableBlockDataReloadListener.INSTANCE.values()) {
            var result = SmeltableBlockData.CODEC.encodeStart(NbtOps.INSTANCE, data);
            result.result().ifPresentOrElse(tag -> {
                if (tag instanceof CompoundTag compoundTag) {
                    entries.add(compoundTag);
                }
            }, () -> result.error().ifPresent(error -> PaperKiteManor.LOGGER.error(
                    "Failed to encode smeltable block data for sync: {}", error.message())));
        }
        syncData.put(ENTRIES, entries);
        return new SmeltableBlockDataSyncS2CMessage(syncData);
    }

    public static void encode(SmeltableBlockDataSyncS2CMessage message, FriendlyByteBuf buf) {
        buf.writeNbt(message.data);
    }

    public static SmeltableBlockDataSyncS2CMessage decode(FriendlyByteBuf buf) {
        CompoundTag data = buf.readNbt();
        return new SmeltableBlockDataSyncS2CMessage(data == null ? new CompoundTag() : data);
    }

    public static void handle(SmeltableBlockDataSyncS2CMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> onHandle(message));
        }
        context.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void onHandle(SmeltableBlockDataSyncS2CMessage message) {
        SmeltableBlockDataReloadListener.INSTANCE.clear();
        ListTag entries = message.data.getList(ENTRIES, Tag.TAG_COMPOUND);
        for (int i = 0; i < entries.size(); i++) {
            CompoundTag entry = entries.getCompound(i);
            var result = SmeltableBlockData.CODEC.parse(NbtOps.INSTANCE, entry);
            result.result().ifPresentOrElse(data -> SmeltableBlockDataReloadListener.INSTANCE.put(data.block(), data),
                    () -> result.error().ifPresent(error -> PaperKiteManor.LOGGER.error(
                            "Failed to decode synced smeltable block data: {}", error.message())));
        }
    }
}
