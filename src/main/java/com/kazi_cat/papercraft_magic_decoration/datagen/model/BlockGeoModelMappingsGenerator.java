package com.kazi_cat.papercraft_magic_decoration.datagen.model;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.Util;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3d;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

public class BlockGeoModelMappingsGenerator implements DataProvider {
    private static final ToIntFunction<String> ORDER_FIELDS = Util.make(new Object2IntOpenHashMap<>(), map -> {
        map.put("variants", 0);
        map.defaultReturnValue(1);
    });

    private final Map<String, MappingBuilder> data = Maps.newLinkedHashMap();
    private final PackOutput output;

    public BlockGeoModelMappingsGenerator(PackOutput output) {
        this.output = output;
    }

    private void addMappings() {
        simple(ModBlocks.PAPER_CUTTING_TABLE);
        simple(ModBlocks.COPPER_BARTENDER);
        simple(ModBlocks.EDGED_CHALKBOARD);
    }

    public void simple(RegistryObject<Block> block) {
        var blockKey = ForgeRegistries.BLOCKS.getKey(block.get());
        if (blockKey == null) {
            throw new IllegalArgumentException("Block not registered: " + block.getId());
        }
        MappingBuilder builder = MappingBuilder.simple(block.get(), GeoModelData.simple("0", blockKey.getPath()));
        this.add(blockKey.getPath(), builder);
    }

    /**
     * 使用方块注册名的 path 部分作为文件名
     */
    public final void add(RegistryObject<Block> key, MappingBuilder value) {
        var blockKey = ForgeRegistries.BLOCKS.getKey(key.get());
        if (blockKey == null) {
            throw new IllegalArgumentException("Block not registered: " + key.getId());
        }
        this.add(blockKey.getPath(), value);
    }

    public void add(String fileName, MappingBuilder value) {
        this.data.put(fileName, value);
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        this.addMappings();

        List<CompletableFuture<?>> futures = Lists.newArrayList();
        var pathProvider = this.output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "geomodel/blockstate_mappings");

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
                    GsonHelper.writeValue(writer, json, Comparator.comparingInt(ORDER_FIELDS));
                }

                output.writeIfNeeded(path, stream.toByteArray(), hashing.hash());
            } catch (IOException ioexception) {
                LOGGER.error("Failed to save file to {}", path, ioexception);
            }
        }, Util.backgroundExecutor());
    }

    @Override
    public String getName() {
        return "BlockGeoModelMappings";
    }

    public static class MappingBuilder {
        private final Block owner;
        private final Map<BlockState, GeoModelData> mappings = new LinkedHashMap<>();
        private final Set<GeoModelData> models = new LinkedHashSet<>();
        private final Set<BlockState> coveredStates = new HashSet<>();

        public MappingBuilder(Block owner) {
            this.owner = owner;
        }

        public JsonObject toJson() {
            List<BlockState> missingStates = Lists.newArrayList(owner.getStateDefinition().getPossibleStates());
            missingStates.removeAll(coveredStates);
            Preconditions.checkState(missingStates.isEmpty(), "Blockstate for block %s does not cover all states. Missing: %s", owner, missingStates);
            JsonObject variantsJson = new JsonObject();
            mappings.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey(Comparator.comparing(MappingBuilder::stateToVariantString)))
                    .forEach(entry -> variantsJson.addProperty(stateToVariantString(entry.getKey()), entry.getValue().name));

            JsonObject modelsJson = new JsonObject();
            models.forEach(model -> modelsJson.add(model.name, model.toJson()));

            JsonObject main = new JsonObject();
            main.add("variants", variantsJson);
            main.add("models", modelsJson);
            return main;
        }

        public MappingBuilder addModel(Predicate<BlockState> state, GeoModelData model) {
            Preconditions.checkArgument(disjointToAll(state), "Cannot set models for a state for which a partial match has already been configured");
            this.models.add(model);
            for (BlockState fullState : owner.getStateDefinition().getPossibleStates()) {
                if (state.test(fullState)) {
                    mappings.put(fullState, model);
                    coveredStates.add(fullState);
                }
            }
            return this;
        }

        private boolean disjointToAll(Predicate<BlockState> newState) {
            return coveredStates.stream().noneMatch(newState);
        }

        private static String stateToVariantString(BlockState state) {
            return BlockModelShaper.stateToModelLocation(state).getVariant();
        }

        public MappingBuilder forAllStates(Function<BlockState, GeoModelData> mapper) {
            return forAllStatesExcept(mapper, state -> false);
        }

        public MappingBuilder forAllStatesExcept(Function<BlockState, GeoModelData> mapper, Predicate<BlockState> except) {
            for (BlockState fullState : owner.getStateDefinition().getPossibleStates()) {
                if (except.test(fullState)) continue;
                coveredStates.add(fullState);
                mappings.put(fullState, mapper.apply(fullState));
                models.add(mapper.apply(fullState));
            }
            return this;
        }

        public static MappingBuilder simple(Block block, GeoModelData modelData) {
            MappingBuilder builder = new MappingBuilder(block);
            return builder.forAllStates(state -> modelData);
        }
    }

    public static class GeoModelData {
        public final String name;
        public final ResourceLocation model;
        public final ResourceLocation texture;
        public final ResourceLocation animation;
        public Vector3d translation;
        public Vector3d rotation;
        public Vector3d scale;

        public GeoModelData(String name, ResourceLocation model, ResourceLocation texture, ResourceLocation animation) {
            this.name = name;
            this.model = model;
            this.texture = texture;
            this.animation = animation;
        }

        public JsonObject toJson() {
            JsonObject json = new JsonObject();
            json.addProperty("model", model.toString());
            json.addProperty("texture", texture.toString());
            json.addProperty("animation", animation.toString());
            if (translation != null) {
                json.add("translation", toArray(translation));
            }
            if (rotation != null) {
                json.add("rotation", toArray(rotation));
            }
            if (scale != null) {
                json.add("scale", toArray(scale));
            }
            return json;
        }

        public static GeoModelData simple(String name, String path) {
            return new GeoModelData(
                    name,
                    new ResourceLocation("papercraft_magic_decoration:geo/%s.geo.json".formatted(path)),
                    new ResourceLocation("papercraft_magic_decoration:textures/block/%s.png".formatted(path)),
                    new ResourceLocation("papercraft_magic_decoration:animations/%s.animation.json".formatted(path))
            );
        }

        private static JsonArray toArray(Vector3d vector3d) {
            JsonArray array = new JsonArray();
            array.add(vector3d.x());
            array.add(vector3d.y());
            array.add(vector3d.z());
            return array;
        }
    }
}
