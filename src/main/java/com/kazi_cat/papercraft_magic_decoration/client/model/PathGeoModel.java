package com.kazi_cat.papercraft_magic_decoration.client.model;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

import java.util.function.Function;
import java.util.function.Supplier;

public class PathGeoModel<T extends GeoAnimatable> extends GeoModel<T> {
    private final Function<T, ResourceLocation> idMapper;
    private final String modelPrefix;
    private final String texturePrefix;
    private final String animationPrefix;
    private ResourceLocation model;
    private ResourceLocation texture;
    private ResourceLocation animation;

    public PathGeoModel(Function<T, ResourceLocation> idMapper, String modelPrefix, String texturePrefix, String animationPrefix) {
        this.idMapper = idMapper;
        this.modelPrefix = modelPrefix;
        this.texturePrefix = texturePrefix;
        this.animationPrefix = animationPrefix;
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        if (model == null) {
            ResourceLocation id = idMapper.apply(animatable);
            model = new ResourceLocation(id.getNamespace(), "geo/" + modelPrefix + id.getPath() + ".geo.json");
        }
        return model;
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        if (texture == null) {
            ResourceLocation id = idMapper.apply(animatable);
            texture = new ResourceLocation(id.getNamespace(), "textures/" + texturePrefix + id.getPath() + ".png");
        }
        return texture;
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        if (animation == null) {
            ResourceLocation id = idMapper.apply(animatable);
            animation = new ResourceLocation(id.getNamespace(), "animations/" + animationPrefix + id.getPath() + ".animation.json");
        }
        return animation;
    }

    public static <T extends GeoAnimatable> Builder<T> create(Class<T> type) {
        return new Builder<>();
    }

    public static class Builder<T extends GeoAnimatable> {
        private Function<T, ResourceLocation> idMapper;
        private String modelPrefix = "";
        private String texturePrefix = "";
        private String animationPrefix = "";

        public Builder<T> mapper(Function<T, ResourceLocation> idMapper) {
            this.idMapper = idMapper;
            return this;
        }

        public Builder<T> modelPrefix(String prefix) {
            this.modelPrefix = prefix;
            return this;
        }

        public Builder<T> texturePrefix(String prefix) {
            this.texturePrefix = prefix;
            return this;
        }

        public Builder<T> animationPrefix(String prefix) {
            this.animationPrefix = prefix;
            return this;
        }

        public Supplier<GeoModel<T>> build() {
            return () -> new PathGeoModel<>(idMapper, modelPrefix, texturePrefix, animationPrefix);
        }
    }
}
