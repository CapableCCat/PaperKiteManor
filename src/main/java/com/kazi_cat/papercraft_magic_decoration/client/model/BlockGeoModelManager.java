package com.kazi_cat.papercraft_magic_decoration.client.model;

import com.google.gson.*;
import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.model.GeoModel;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class BlockGeoModelManager {
    public static final String NAMESPACE = "papercraft_magic_decoration";
    public static final String PATH = "geckolib/blockstate_mappings";
    private static final ConcurrentHashMap<ModelResourceLocation, GeoModel<?>> map;

    @Nullable
    public static <T extends BlockEntity & GeoBlockEntity> GeoModel<T> getModel(BlockState blockState) {
        return (GeoModel<T>) map.getOrDefault(BlockModelShaper.stateToModelLocation(blockState), null);
    }

    /**
     * 加载BlockState -> GeoModel映射
     */
    public static void loadModelMappings() {
        map.clear();
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        Map<ResourceLocation, Resource> resources = resourceManager.listResources(
                PATH,
                location -> location.getNamespace().equals(NAMESPACE) && location.getPath().endsWith(".json")
        );

        int jsonLoaded = 0;
        for (ResourceLocation key : resources.keySet()) {
            Resource resource = resources.get(key);
            String filePath = key.getPath();
            ResourceLocation blockLocation = new ResourceLocation(PaperKiteManor.MOD_ID, filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf(".")));

            try (InputStream is = resource.open(); InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                JsonObject root = GsonHelper.parse(reader);
                Map<ModelResourceLocation, String> variantToId = new HashMap<>();
                Map<String, FixedGeoModel<?>> idToModel = new HashMap<>();

                if (root.has("variants") && root.get("variants").isJsonObject()) {
                    JsonObject variantsObj = root.getAsJsonObject("variants");
                    for (Map.Entry<String, JsonElement> entry : variantsObj.entrySet()) {
                        ModelResourceLocation variant = convertToModelLocation(blockLocation, entry.getKey());
                        String id = entry.getValue().getAsString();
                        if (variant != null) {
                            variantToId.put(variant, id);
                        }
                    }
                } else {
                    throw new JsonParseException("JSON 缺少 'variants' 字段或格式错误");
                }

                if (root.has("models") && root.get("models").isJsonObject()) {
                    JsonObject modelsObj = root.getAsJsonObject("models");
                    for (Map.Entry<String, JsonElement> entry : modelsObj.entrySet()) {
                        String id = entry.getKey(); // 如 "0"
                        JsonObject modelObj = entry.getValue().getAsJsonObject();

                        // 解析 model 字段
                        String modelPath = getStringOrEmpty(modelObj, "model");
                        String texturePath = getStringOrEmpty(modelObj, "texture");
                        String animationPath = getStringOrEmpty(modelObj, "animation");

                        // 创建 ResourceLocation
                        ResourceLocation modelLoc = ResourceLocation.tryParse(modelPath);
                        ResourceLocation textureLoc = ResourceLocation.tryParse(texturePath);
                        ResourceLocation animationLoc = ResourceLocation.tryParse(animationPath);
                        if (modelLoc == null || textureLoc == null || animationLoc == null) continue;

                        idToModel.put(id, new FixedGeoModel<>(modelLoc, textureLoc, animationLoc));
                    }
                } else {
                    throw new JsonParseException("JSON 缺少 'models' 字段或格式错误");
                }

                for (Map.Entry<ModelResourceLocation, String> entry : variantToId.entrySet()) {
                    if (idToModel.containsKey(entry.getValue())) {
                        map.put(entry.getKey(), idToModel.get(entry.getValue()));
                    }
                }
                jsonLoaded++;
            } catch (JsonParseException e) {
                System.err.println("解析 JSON 失败: " + key);
            } catch (IOException e) {
                System.err.println("IO 异常：" + key);
            }
        }

        PaperKiteManor.LOGGER.info("已加载完成BlockState -> GeoModel映射, 共成功加载 {} / {} 个 JSON 文件, {} 条映射", jsonLoaded, resources.size(), map.size());
    }

    /**
     * 将无序的BlockState变种字符串转换为ModelResourceLocation
     */
    public static @Nullable ModelResourceLocation convertToModelLocation(ResourceLocation blockLocation, String stateString) {
        Map<String, String> propertyMap = parseStateString(stateString);
        if (propertyMap.isEmpty()) {
            return null;
        }

        String sortedVariant = propertyMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(","));

        return new ModelResourceLocation(blockLocation, sortedVariant);
    }

    /**
     * 将无序的状态字符串解析为 Map
     */
    private static Map<String, String> parseStateString(String stateString) {
        Map<String, String> map = new HashMap<>();
        if (stateString.isEmpty()) {
            return map;
        }

        String[] pairs = stateString.split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                map.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }
        return map;
    }

    private static String getStringOrEmpty(JsonObject obj, String key) {
        return obj.has(key) ? obj.get(key).getAsString() : "";
    }

    static {
        map = new ConcurrentHashMap<>();
    }
}
