package com.kazi_cat.papercraft_magic_decoration.client.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class ItemGeoModelManager {
    public static final String NAMESPACE = "papercraft_magic_decoration";
    public static final String PATH = "geomodel/item_mappings";
    private static final ConcurrentHashMap<ResourceLocation, GeoModel<?>> map;

    @Nullable
    public static <T extends Item & GeoAnimatable> GeoModel<T> getModel(Item item) {
        ResourceLocation key = ForgeRegistries.ITEMS.getKey(item);
        return key != null ? (GeoModel<T>) map.getOrDefault(key, null) : null;
    }

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
            ResourceLocation itemLocation = new ResourceLocation(PaperKiteManor.MOD_ID, filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf(".")));

            try (InputStream is = resource.open(); InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                JsonObject root = GsonHelper.parse(reader);

                // 解析 model 字段
                String modelPath = getStringOrEmpty(root, "model");
                String texturePath = getStringOrEmpty(root, "texture");
                String animationPath = getStringOrEmpty(root, "animation");

                // 创建 ResourceLocation
                ResourceLocation modelLoc = ResourceLocation.tryParse(modelPath);
                ResourceLocation textureLoc = ResourceLocation.tryParse(texturePath);
                ResourceLocation animationLoc = ResourceLocation.tryParse(animationPath);

                if (modelLoc != null && textureLoc != null && animationLoc != null) {
                    map.put(itemLocation, new FixedGeoModel<>(modelLoc, textureLoc, animationLoc));
                    jsonLoaded++;
                }
            } catch (JsonParseException e) {
                System.err.println("解析 JSON 失败: " + key);
            } catch (IOException e) {
                System.err.println("IO 异常：" + key);
            }
        }

        PaperKiteManor.LOGGER.info("已加载完成Item -> GeoModel映射, 共成功加载 {} / {} 个 JSON 文件, {} 条映射", jsonLoaded, resources.size(), map.size());
    }

    private static String getStringOrEmpty(JsonObject obj, String key) {
        return obj.has(key) ? obj.get(key).getAsString() : "";
    }

    static {
        map = new ConcurrentHashMap<>();
    }
}
