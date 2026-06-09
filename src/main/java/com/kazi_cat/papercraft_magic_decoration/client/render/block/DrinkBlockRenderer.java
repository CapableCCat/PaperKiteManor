package com.kazi_cat.papercraft_magic_decoration.client.render.block;

import com.kazi_cat.papercraft_magic_decoration.block.drink.DrinkBlock;
import com.kazi_cat.papercraft_magic_decoration.blockentity.DrinkBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class DrinkBlockRenderer implements BlockEntityRenderer<DrinkBlockEntity> {
    private final BlockRenderDispatcher blockRenderer;

    public DrinkBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(DrinkBlockEntity be, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Level level = be.getLevel();
        if (level == null) {
            return;
        }

        BlockState state = be.getBlockState();
        BakedModel model = blockRenderer.getBlockModel(state);

        poseStack.pushPose();
        poseStack.translate(be.getXOffset() * DrinkBlock.STEP_LENGTH, 0, be.getYOffset() * DrinkBlock.STEP_LENGTH);

        // 渲染方块模型
        blockRenderer.getModelRenderer().tesselateBlock(
                level,
                model,
                state,
                be.getBlockPos(),
                poseStack,
                bufferSource.getBuffer(RenderType.cutout()),
                false,
                be.getLevel().getRandom(),
                state.getSeed(be.getBlockPos()),
                packedOverlay,
                be.getModelData(),
                RenderType.cutout()
        );
        poseStack.popPose();
    }
}
