package com.kazi_cat.papercraft_magic_decoration.client.render;

import com.kazi_cat.papercraft_magic_decoration.blockentity.decoration.PaperCuttingTableBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;

public class PaperCuttingTableBlockRenderer extends BaseGeoBlockRenderer<PaperCuttingTableBlockEntity> {
    public PaperCuttingTableBlockRenderer() {
        super(ModBlocks.PAPER_CUTTING_TABLE_BE.get());
    }

    @Override
    public void postRender(PoseStack poseStack, PaperCuttingTableBlockEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.postRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

        ItemStack content = animatable.getContent().copy();
        if (content.isEmpty()) return;

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        boolean isGui3d = itemRenderer.getModel(content, animatable.getLevel(), null, 0).isGui3d();
        poseStack.pushPose();
        poseStack.translate(0.15, 0.96, -0.10);
        if (isGui3d) {
            poseStack.translate(0, 0.18F, -0.05);
        }
        poseStack.mulPose(Axis.XP.rotationDegrees(90));
        poseStack.mulPose(Axis.ZP.rotationDegrees(30));
        if (isGui3d) {
            poseStack.scale(0.75F, 0.75F, 0.75F);
        } else {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }

        itemRenderer.renderStatic(
                content,
                ItemDisplayContext.GUI,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                bufferSource,
                animatable.getLevel(),
                0
        );

        poseStack.popPose();
    }
}
