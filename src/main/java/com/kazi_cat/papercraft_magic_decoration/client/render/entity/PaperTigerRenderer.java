package com.kazi_cat.papercraft_magic_decoration.client.render.entity;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.client.model.FixedGeoModel;
import com.kazi_cat.papercraft_magic_decoration.entity.PaperTigerEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PaperTigerRenderer extends GeoEntityRenderer<PaperTigerEntity> {
    public PaperTigerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FixedGeoModel<>(
                PaperKiteManor.resourceLocation("geo/paper_tiger.geo.json"),
                PaperKiteManor.resourceLocation("textures/entities/paper_tiger.png"),
                PaperKiteManor.resourceLocation("animations/paper_tiger.animation.json")
        ));
        this.shadowRadius = 1f;
    }

    @Override
    public RenderType getRenderType(PaperTigerEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}