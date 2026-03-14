package com.kazi_cat.papercraft_magic_decoration.client.model;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class CommonGeoModel <T extends GeoAnimatable> extends GeoModel<T> {
    protected final ResourceLocation model;
    protected final ResourceLocation texture;
    protected final ResourceLocation animation;

    public CommonGeoModel(ResourceLocation model, ResourceLocation texture, ResourceLocation animation) {
        this.model = model;
        this.texture = texture;
        this.animation = animation;
    }

    @Override
    public ResourceLocation getModelResource(T t) {
        return model;
    }

    @Override
    public ResourceLocation getTextureResource(T t) {
        return texture;
    }

    @Override
    public ResourceLocation getAnimationResource(T t) {
        return animation;
    }
}
