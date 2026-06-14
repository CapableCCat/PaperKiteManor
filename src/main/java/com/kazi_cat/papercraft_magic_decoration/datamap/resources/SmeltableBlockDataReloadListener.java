package com.kazi_cat.papercraft_magic_decoration.datamap.resources;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.datamap.data.SmeltableBlockData;
import com.mojang.serialization.JsonOps;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Block;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

// TODO 服务端向客户端主动同步
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SmeltableBlockDataReloadListener extends SimpleJsonResourceReloadListener {
    public static final Map<Block, SmeltableBlockData> INSTANCE = Maps.newHashMap();
    private static final Gson GSON = new GsonBuilder().create();

    public SmeltableBlockDataReloadListener() {
        super(GSON, "datamap/smeltable_block_data");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> resources, ResourceManager resourceManager, ProfilerFiller profiler) {
        INSTANCE.clear();
        for (var entry : resources.entrySet()) {
            var result = SmeltableBlockData.CODEC.parse(JsonOps.INSTANCE, entry.getValue());
            if (result.result().isPresent()) {
                SmeltableBlockData data = result.result().get();
                INSTANCE.put(data.block(), data);
            } else if (result.error().isPresent()) {
                PaperKiteManor.LOGGER.error("Failed to parse smeltable block data from '{}': {}", entry.getKey(), result.error().get().message());
            }
        }
        PaperKiteManor.LOGGER.info("Successfully loaded smeltable block data with {} entries", INSTANCE.size());
    }
}
