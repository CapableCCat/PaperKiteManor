package com.kazi_cat.papercraft_magic_decoration.client.render.entity;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.client.model.FixedGeoModel;
import com.kazi_cat.papercraft_magic_decoration.client.render.entity.layer.EyesGlowLayer;
import com.kazi_cat.papercraft_magic_decoration.entity.BlackCatLobbyBoyEntity;
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

public class BlackCatLobbyBoyRenderer extends GeoEntityRenderer<BlackCatLobbyBoyEntity> {
    public BlackCatLobbyBoyRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FixedGeoModel<>(
                PaperKiteManor.resourceLocation("geo/black_cat_lobby_boy.geo.json"),
                PaperKiteManor.resourceLocation("textures/entities/black_cat_lobby_boy.png"),
                PaperKiteManor.resourceLocation("animations/black_cat_lobby_boy.animation.json")
        ) {
            @Override
            public void setCustomAnimations(BlackCatLobbyBoyEntity animatable, long instanceId, AnimationState<BlackCatLobbyBoyEntity> animationState) {
                CoreGeoBone head = getAnimationProcessor().getBone("head");
                if (head != null) {
                    EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
                    head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
                    head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
                }
            }
        });
        this.shadowRadius = 0.5f;
        this.addRenderLayer(new EyesGlowLayer<>(this, PaperKiteManor.resourceLocation("textures/entities/black_cat_lobby_boy_glow.png")));
    }

    @Override
    public RenderType getRenderType(BlackCatLobbyBoyEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}
