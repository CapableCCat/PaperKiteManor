package com.kazi_cat.papercraft_magic_decoration.datagen.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import net.minecraft.Util;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ItemGeoModelGenerator implements DataProvider {
    private final Map<String, ModelData> data = Maps.newLinkedHashMap();
    private final PackOutput output;

    public ItemGeoModelGenerator(PackOutput output) {
        this.output = output;
    }

    private void addMappings() {
        simple(ModItems.SAUSAGE_MACE_WEAPON);
        ModelData rawSausageMaceWeapon = ModelData.simple("sausage_mace_weapon", "raw_sausage_mace_weapon", "sausage_mace_weapon");
        add(ModItems.RAW_SAUSAGE_MACE_WEAPON, rawSausageMaceWeapon);
    }

    public void simple(RegistryObject<Item> key) {
        var itemKey = ForgeRegistries.ITEMS.getKey(key.get());
        if (itemKey == null) {
            throw new IllegalArgumentException("Item not registered: " + key.getId());
        }
        this.add(itemKey.getPath(), ModelData.simple(itemKey.getPath()));
    }

    /**
     * 使用方块注册名的 path 部分作为文件名
     */
    public final void add(RegistryObject<Item> key, ModelData value) {
        var itemKey = ForgeRegistries.ITEMS.getKey(key.get());
        if (itemKey == null) {
            throw new IllegalArgumentException("Item not registered: " + key.getId());
        }
        this.add(itemKey.getPath(), value);
    }

    public void add(String fileName, ModelData value) { this.data.put(fileName, value); }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        this.addMappings();

        List<CompletableFuture<?>> futures = Lists.newArrayList();
        var pathProvider = this.output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "geomodel/item_mappings");

        for (var entry : data.entrySet()) {
            var filePath = pathProvider.json(new ResourceLocation(PaperKiteManor.MOD_ID, entry.getKey()));
            var future = this.saveStable(cache, entry.getValue().toJson(), filePath);
            futures.add(future);
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
                    GsonHelper.writeValue(writer, json, null);
                }

                output.writeIfNeeded(path, stream.toByteArray(), hashing.hash());
            } catch (IOException ioexception) {
                LOGGER.error("Failed to save file to {}", path, ioexception);
            }
        }, Util.backgroundExecutor());
    }

    @Override
    public String getName() {
        return "ItemGeoModel";
    }

    public record ModelData(ResourceLocation model, ResourceLocation texture, ResourceLocation animation) {
        public JsonObject toJson() {
            JsonObject json = new JsonObject();
            json.addProperty("model", model.toString());
            json.addProperty("texture", texture.toString());
            json.addProperty("animation", animation.toString());
            return json;
        }

        public static ModelData simple(String path) {
            return simple(path, path, path);
        }

        public static ModelData simple(String model, String texture, String animation) {
            return new ModelData(
                    PaperKiteManor.modLoc("geo/%s.geo.json".formatted(model)),
                    PaperKiteManor.modLoc("textures/block/%s.png".formatted(texture)),
                    PaperKiteManor.modLoc("%s.animation.json".formatted(animation))
            );
        }
    }
}
