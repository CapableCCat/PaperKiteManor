package com.kazi_cat.papercraft_magic_decoration.client.render;

import com.kazi_cat.papercraft_magic_decoration.client.model.BlockGeoModelManager;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.joml.Matrix4f;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class BaseGeoBlockRenderer<T extends BlockEntity & GeoBlockEntity> extends GeoBlockRenderer<T> {
    public BaseGeoBlockRenderer(BlockEntityType<T> blockEntityType) {
        super(blockEntityType);
    }

    @Override
    public GeoModel<T> getGeoModel() {
        T animatable = this.animatable;
        if (animatable != null && animatable.getLevel() != null) {
            GeoModel<T> geoModel = BlockGeoModelManager.getModel(animatable.getBlockState());
            if (geoModel != null) {
                return geoModel;
            }
        }
        return super.getGeoModel();
    }

    @Override
    public void actuallyRender(PoseStack poseStack, T animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        poseStack.pushPose();
        Matrix4f matrix4f = BlockGeoModelManager.getTransformation(animatable.getBlockState());
        if (matrix4f != null) {
            poseStack.mulPoseMatrix(matrix4f);
        }
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        poseStack.popPose();
    }
}
