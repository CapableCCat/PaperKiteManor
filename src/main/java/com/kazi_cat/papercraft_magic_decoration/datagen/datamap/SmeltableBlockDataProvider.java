package com.kazi_cat.papercraft_magic_decoration.datagen.datamap;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;
import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.datamap.data.SmeltableBlockData;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import com.mojang.serialization.JsonOps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.ToIntFunction;


@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SmeltableBlockDataProvider implements DataProvider {
    private static final ToIntFunction<String> ORDER_FIELDS = Util.make(new Object2IntOpenHashMap<>(), map -> {
        map.put("block", 0);
        map.defaultReturnValue(99);
    });

    private final Map<String, SmeltableBlockData> data = Maps.newLinkedHashMap();
    private final PackOutput output;

    public SmeltableBlockDataProvider(PackOutput output) {
        this.output = output;
    }

    private void addEntry() {
        this.add(ModBlocks.SAUSAGE_MACE_WEAPON_BLOCK.get(), new SmeltableBlockData(
                ModBlocks.SAUSAGE_MACE_WEAPON_BLOCK.get(),
                100, 2, 20,
                ModItems.SAUSAGE_MACE_WEAPON.get()
        ));
    }

    /**
     * 使用物品注册名的 path 部分作为文件名
     */
    public void add(Block key, SmeltableBlockData data) {
        var blockKey = ForgeRegistries.BLOCKS.getKey(key);
        if (blockKey == null) {
            throw new IllegalArgumentException("Block not registered: " + key.getName());
        }
        this.add(blockKey.getPath(), data);
    }

    /**
     * 使用自定义文件名
     */
    public final void add(String fileName, SmeltableBlockData data) {
        this.data.put(fileName, data);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        this.addEntry();

        List<CompletableFuture<?>> futures = Lists.newArrayList();
        var pathProvider = this.output.createPathProvider(PackOutput.Target.DATA_PACK, "datamap/smeltable_block_data");

        for (var entry : data.entrySet()) {
            SmeltableBlockData.CODEC
                    .encodeStart(JsonOps.INSTANCE, entry.getValue())
                    .resultOrPartial(PaperKiteManor.LOGGER::error)
                    .ifPresent(json -> {
                        var filePath = pathProvider.json(PaperKiteManor.modLoc(entry.getKey()));
                        var future = this.saveStable(cache, json, filePath);
                        futures.add(future);
                    });
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    @SuppressWarnings("all")
    private CompletableFuture<?> saveStable(CachedOutput output, JsonElement json, Path path) {
        return CompletableFuture.runAsync(() -> {
            try {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                HashingOutputStream hashing = new HashingOutputStream(Hashing.sha1(), stream);

                try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(hashing, StandardCharsets.UTF_8))) {
                    writer.setSerializeNulls(false);
                    writer.setIndent("  ");
                    GsonHelper.writeValue(writer, json, Comparator.comparingInt(ORDER_FIELDS));
                }

                output.writeIfNeeded(path, stream.toByteArray(), hashing.hash());
            } catch (IOException ioexception) {
                PaperKiteManor.LOGGER.error("Failed to save file to {}", path, ioexception);
            }
        }, Util.backgroundExecutor());
    }

    @Override
    public String getName() {
        return "Drink Effect Data";
    }
}
