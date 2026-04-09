package com.kazi_cat.papercraft_magic_decoration.client.render.entity;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.client.model.FixedGeoModel;
import com.kazi_cat.papercraft_magic_decoration.entity.AirFlowCarpEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AirFlowCarpRenderer extends GeoEntityRenderer<AirFlowCarpEntity> {
    public AirFlowCarpRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FixedGeoModel<>(
                PaperKiteManor.resourceLocation("geo/air_flow_carp.geo.json"),
                PaperKiteManor.resourceLocation("textures/entities/air_flow_carp.png"),
                PaperKiteManor.resourceLocation("animations/air_flow_carp.animation.json")
        ));
        this.shadowRadius = 0.5f;
    }

    @Override
    public RenderType getRenderType(AirFlowCarpEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
