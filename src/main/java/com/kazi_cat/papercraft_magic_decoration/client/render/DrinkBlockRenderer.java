package com.kazi_cat.papercraft_magic_decoration.client.render;

import com.kazi_cat.papercraft_magic_decoration.block.DrinkBlock;
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
import org.joml.Vector2i;

public class DrinkBlockRenderer implements BlockEntityRenderer<DrinkBlockEntity> {
    private final BlockRenderDispatcher blockRenderer;

    public DrinkBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(DrinkBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Level level = blockEntity.getLevel();
        if (level == null) {
            return;
        }

        BlockState state = blockEntity.getBlockState();
        BakedModel model = blockRenderer.getBlockModel(state);
        DrinkBlock block = (DrinkBlock) state.getBlock();

        poseStack.pushPose();

        double stepLength = block.getStepLength();
        Vector2i offset = blockEntity.getOffset();
        poseStack.translate(offset.x() * stepLength, 0, offset.y() * stepLength);

        // 渲染方块模型
        blockRenderer.getModelRenderer().tesselateBlock(
                level,
                model,
                state,
                blockEntity.getBlockPos(),
                poseStack,
                bufferSource.getBuffer(RenderType.cutout()),
                false,
                blockEntity.getLevel().getRandom(),
                state.getSeed(blockEntity.getBlockPos()),
                packedOverlay,
                blockEntity.getModelData(),
                RenderType.cutout()
        );

        poseStack.popPose();
    }
}
