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
import com.kazi_cat.papercraft_magic_decoration.api.block.SmeltableBlock;
import com.kazi_cat.papercraft_magic_decoration.block.smeltable.MangaMeatBlock;
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
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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

public class BlockGeoModelGenerator implements DataProvider {
    private static final ToIntFunction<String> ORDER_FIELDS = Util.make(new Object2IntOpenHashMap<>(), map -> {
        map.put("variants", 0);
        map.defaultReturnValue(1);
    });

    private final Map<String, MappingBuilder> data = Maps.newLinkedHashMap();
    private final PackOutput output;

    public BlockGeoModelGenerator(PackOutput output) {
        this.output = output;
    }

    private void addMappings() {
        simple(ModBlocks.PAPER_CUTTING_TABLE);
        simple(ModBlocks.COPPER_BARTENDER);
        simple(ModBlocks.KAZI_LUCKY_CAT);
        simple(ModBlocks.GIFT_FROM_KAZI_MANOR);
        simple(ModBlocks.KEY_UNDER_THE_LAKE);
        simple(ModBlocks.LOW_CABINET_WITH_TABLECLOTH);
        simple(ModBlocks.WOODEN_BARREL_BOOKSHELF);
        simple(ModBlocks.WOODWORKING_TABLE);
        simple(ModBlocks.LONG_STORAGE_TABLE);
        simple(ModBlocks.EDGED_CHALKBOARD);

        // 狼牙棒火腿肠
        ModelData sausage = ModelData.simple("0", "sausage_mace_weapon");
        ModelData rawSausage = ModelData.simple("1", "sausage_mace_weapon", "raw_sausage_mace_weapon", "sausage_mace_weapon");
        ModelData sausageFloor = ModelData.simple("2", "sausage_mace_weapon_floor", "sausage_mace_weapon", "sausage_mace_weapon");
        ModelData rawSausageFloor = ModelData.simple("3", "sausage_mace_weapon_floor", "raw_sausage_mace_weapon", "sausage_mace_weapon");
        ModelData sausageCeiling = ModelData.simple("4", "sausage_mace_weapon_ceiling", "sausage_mace_weapon", "sausage_mace_weapon");
        ModelData rawSausageCeiling = ModelData.simple("5", "sausage_mace_weapon_ceiling", "raw_sausage_mace_weapon", "sausage_mace_weapon");
        this.add("sausage_mace_weapon", new MappingBuilder(ModBlocks.SAUSAGE_MACE_WEAPON_BLOCK.get()).forAllStates(state -> {
            boolean cooked = state.getValue(SmeltableBlock.COOKED);
            AttachFace face = state.getValue(BlockStateProperties.ATTACH_FACE);
            return switch (face) {
                case WALL -> cooked ? sausage : rawSausage;
                case FLOOR -> cooked ? sausageFloor : rawSausageFloor;
                case CEILING -> cooked ? sausageCeiling : rawSausageCeiling;
            };
        }));

        // 漫画肉
        ModelData rawMangaMeat = ModelData.simple("0", "manga_meat", "raw_manga_meat_grill", "manga_meat_grill");
        ModelData mangaMeat = ModelData.simple("1", "manga_meat", "manga_meat_grill", "manga_meat_grill");
        ModelData rawMangaMeatGrill = ModelData.simple("2", "manga_meat_grill", "raw_manga_meat_grill", "manga_meat_grill");
        ModelData mangaMeatGrill = ModelData.simple("3", "manga_meat_grill", "manga_meat_grill", "manga_meat_grill");
        this.add("manga_meat", new MappingBuilder(ModBlocks.MANGA_MEAT.get()).forAllStates(state -> {
            boolean cooked = state.getValue(SmeltableBlock.COOKED);
            boolean hasBase = state.getValue(MangaMeatBlock.HAS_BASE);
            return cooked ? (hasBase ? mangaMeatGrill : mangaMeat) : (hasBase ? rawMangaMeatGrill : rawMangaMeat);
        }));
    }

    public void simple(RegistryObject<Block> block) {
        var blockKey = ForgeRegistries.BLOCKS.getKey(block.get());
        if (blockKey == null) {
            throw new IllegalArgumentException("Block not registered: " + block.getId());
        }
        MappingBuilder builder = MappingBuilder.simple(block.get(), ModelData.simple("0", blockKey.getPath()));
        this.add(blockKey.getPath(), builder);
    }

    public void smeltable(RegistryObject<Block> block, ModelData raw, ModelData cooked) {
        var blockKey = ForgeRegistries.BLOCKS.getKey(block.get());
        if (blockKey == null) {
            throw new IllegalArgumentException("Block not registered: " + block.getId());
        }
        MappingBuilder builder = new MappingBuilder(block.get()).forAllStates(state -> state.getValue(SmeltableBlock.COOKED) ? cooked : raw);
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
        return "BlockGeoModel";
    }

    public static class MappingBuilder {
        private final Block owner;
        private final Map<BlockState, ModelData> mappings = new LinkedHashMap<>();
        private final Set<ModelData> models = new LinkedHashSet<>();
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

        public MappingBuilder addModel(Predicate<BlockState> state, ModelData model) {
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

        public MappingBuilder forAllStates(Function<BlockState, ModelData> mapper) {
            return forAllStatesExcept(mapper, state -> false);
        }

        public MappingBuilder forAllStatesExcept(Function<BlockState, ModelData> mapper, Predicate<BlockState> except) {
            for (BlockState fullState : owner.getStateDefinition().getPossibleStates()) {
                if (except.test(fullState)) continue;
                coveredStates.add(fullState);
                mappings.put(fullState, mapper.apply(fullState));
                models.add(mapper.apply(fullState));
            }
            return this;
        }

        public static MappingBuilder simple(Block block, ModelData modelData) {
            MappingBuilder builder = new MappingBuilder(block);
            return builder.forAllStates(state -> modelData);
        }
    }

    public static class ModelData {
        public String name;
        public ResourceLocation model;
        public ResourceLocation texture;
        public ResourceLocation animation;
        public Vector3d translation;
        public Vector3d rotation;
        public Vector3d scale;

        public ModelData(String name, ResourceLocation model, ResourceLocation texture, ResourceLocation animation) {
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

        public static ModelData simple(String name, String path) {
            return simple(name, path, path, path);
        }

        public static ModelData simple(String name, String model, String texture, String animation) {
            return new ModelData(
                    name,
                    PaperKiteManor.modLoc("geo/%s.geo.json".formatted(model)),
                    PaperKiteManor.modLoc("textures/block/%s.png".formatted(texture)),
                    PaperKiteManor.modLoc("animations/%s.animation.json".formatted(animation))
            );
        }

        private static JsonArray toArray(Vector3d vector3d) {
            JsonArray array = new JsonArray();
            array.add(vector3d.x());
            array.add(vector3d.y());
            array.add(vector3d.z());
            return array;
        }

        public ModelData copy() {
            ModelData modelData = new ModelData(name, model, texture, animation);
            modelData.translation = translation;
            modelData.rotation = rotation;
            modelData.scale = scale;
            return modelData;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ModelData that = (ModelData) o;
            return Objects.equals(name, that.name)
                    && Objects.equals(model, that.model)
                    && Objects.equals(texture, that.texture)
                    && Objects.equals(animation, that.animation)
                    && Objects.equals(translation, that.translation)
                    && Objects.equals(rotation, that.rotation)
                    && Objects.equals(scale, that.scale);
        }
    }
}
