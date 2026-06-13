package com.kazi_cat.papercraft_magic_decoration.client.render.entity;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.client.model.RecordGeoModel;
import com.kazi_cat.papercraft_magic_decoration.client.render.entity.layer.EyesGlowLayer;
import com.kazi_cat.papercraft_magic_decoration.entity.AoaoEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AoaoRenderer extends GeoEntityRenderer<AoaoEntity> {
    public AoaoRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new RecordGeoModel<>(
                PaperKiteManor.modLoc("geo/aoao.geo.json"),
                PaperKiteManor.modLoc("textures/entities/aoao.png"),
                PaperKiteManor.modLoc("animations/aoao.animation.json")
        ) {
            @Override
            public void setCustomAnimations(AoaoEntity animatable, long instanceId, AnimationState<AoaoEntity> animationState) {
                CoreGeoBone head = getAnimationProcessor().getBone("head");
                if (head != null) {
                    EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
                    head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
                    head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
                }
            }
        });
        this.shadowRadius = 0.5f;
        this.addRenderLayer(new EyesGlowLayer<>(this, PaperKiteManor.modLoc("textures/entities/aoao_glow.png")));
    }

    @Override
    public RenderType getRenderType(AoaoEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}