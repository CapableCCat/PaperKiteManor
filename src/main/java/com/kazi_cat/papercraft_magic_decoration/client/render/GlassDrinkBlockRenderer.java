package com.kazi_cat.papercraft_magic_decoration.client.render;

import com.kazi_cat.papercraft_magic_decoration.block.drink.GlassDrinkBlock;
import com.kazi_cat.papercraft_magic_decoration.blockentity.drink.GlassDrinkBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class GlassDrinkBlockRenderer implements BlockEntityRenderer<GlassDrinkBlockEntity> {
    private final BlockRenderDispatcher blockRenderer;

    public GlassDrinkBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderer = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(GlassDrinkBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Level level = blockEntity.getLevel();
        if (level == null) {
            return;
        }

        BlockState state = blockEntity.getBlockState();
        BakedModel model = blockRenderer.getBlockModel(state);
        GlassDrinkBlock block = (GlassDrinkBlock) state.getBlock();

        poseStack.pushPose();

        Vec3 offset = block.getOffset(blockEntity.getOffset());
        poseStack.translate(offset.x(), offset.y(), offset.z());

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
