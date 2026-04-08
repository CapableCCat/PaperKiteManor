package com.kazi_cat.papercraft_magic_decoration.client.render.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

@SuppressWarnings("unchecked")
public class EyesGlowLayer<T extends GeoEntity> extends GeoRenderLayer<T> {
    private final ResourceLocation glowLayer;

    public EyesGlowLayer(GeoRenderer<T> renderer, ResourceLocation glowLayer) {
        super(renderer);
        this.glowLayer = glowLayer;
    }

    @Override
    public void render(PoseStack poseStack, GeoEntity animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        RenderType glowRenderType = RenderType.eyes(glowLayer);
        getRenderer().reRender(getDefaultBakedModel((T) animatable), poseStack, bufferSource, (T) animatable, glowRenderType, bufferSource.getBuffer(glowRenderType), partialTick, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
    }
}
