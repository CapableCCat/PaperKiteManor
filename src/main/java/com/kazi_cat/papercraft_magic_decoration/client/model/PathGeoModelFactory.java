package com.kazi_cat.papercraft_magic_decoration.client.model;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public class PathGeoModelFactory {
    private static final ConcurrentHashMap<Class<?>, ConcurrentHashMap<ResourceLocation, ConcurrentHashMap<String, GeoModel<?>>>> map;

    public static <T extends BlockEntity & GeoBlockEntity> Function<T, ResourceLocation> blockMapper() {
        return (be) -> ForgeRegistries.BLOCKS.getKey(be.getBlockState().getBlock());
    }

    public static <T extends BlockEntity & GeoBlockEntity> Function<T, ResourceLocation> prefixedBlockMapper(String prefix) {
        return  (be) -> {
            ResourceLocation id = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(be.getBlockState().getBlock()));
            return new ResourceLocation(id.getNamespace(), prefix + id.getPath());
        };
    }

    public static <T extends GeoAnimatable> void put(Class<T> tClass, ResourceLocation uid, String state,  GeoModel<T> model) {
        map.computeIfAbsent(tClass, (k) -> new ConcurrentHashMap<>())
                .computeIfAbsent(uid, (s) -> new ConcurrentHashMap<>())
                .put(state, model);
    }

    public static @Nullable <T extends GeoAnimatable> GeoModel<T> get(Class<T> tClass, ResourceLocation uid, String state) {
        if (map.containsKey(tClass) && map.get(tClass).containsKey(uid)) {
            return (GeoModel<T>) map.get(tClass).get(uid).getOrDefault(state, null);
        }
        return null;
    }

    static {
        map = new ConcurrentHashMap<>();
    }
}
