package com.kazi_cat.papercraft_magic_decoration.client.render.block;

import com.kazi_cat.papercraft_magic_decoration.block.utility.CopperStillBlock;
import com.kazi_cat.papercraft_magic_decoration.blockentity.CopperStillBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Vec3i;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;

public class CopperStillBlockRenderer implements BlockEntityRenderer<CopperStillBlockEntity> {
    protected final ItemRenderer itemRenderer;

    public CopperStillBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(CopperStillBlockEntity blockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int packedOverlay) {
        Level level = blockEntity.getLevel();
        if (level == null) {
            return;
        }

        ItemStackHandler items = blockEntity.getItems();
        long seed = blockEntity.getBlockPos().asLong();
        Vec3i vec3i = blockEntity.getBlockState().getValue(CopperStillBlock.FACING).getCounterClockWise().getNormal();
        Vec3 vec3 = new Vec3(vec3i.getX() * 0.07, 0, vec3i.getZ() * 0.07);

        for (int i = 0; i < items.getSlots(); i++) {
            ItemStack stack = items.getStackInSlot(i);
            if (stack.isEmpty()) {
                continue;
            }
            poseStack.pushPose();

            float y = (float) (i / 3) * 0.045f + stableRandom(seed, i, 3) * 0.045f;
            long time = level.getGameTime() / 10;

            boolean distilling = blockEntity.getCurrentTick() > 0;
            float offsetX = distilling ? stableRandom(seed, (int) time, 9 + i) * 0.06F : 0;
            float offsetY = distilling ? stableRandom(seed, (int) time, 27 + i) * 0.06F : 0;
            float offsetZ = distilling ? stableRandom(seed, (int) time, 18 + i) * 0.06F : 0;

            float yRot = stableRandom(seed, i, 4) * 9 / 10f;
            float zRot = stableRandom(seed, i, 5) * 360f;
            Vec3 offset = vec3.yRot((float) Math.toRadians(120 * (i % 3))).multiply(
                            1 + stableRandom(seed, i, 2) * 0.015f,
                            1,
                            1 + stableRandom(seed, i, 2) * 0.015f)
                    .add(vec3i.getX() * 0.04, 0, vec3i.getZ() * 0.04)
                    .add(offsetX, offsetY, offsetZ);

            poseStack.translate(
                    0.5f + offset.x(),
                    1.105f + y + offsetY,
                    0.5f + offset.z()
            );
            poseStack.scale(0.4f, 0.4f, 0.4f);
            poseStack.mulPose(Axis.XN.rotationDegrees(90));

            poseStack.mulPose(Axis.YN.rotationDegrees(yRot));
            poseStack.mulPose(Axis.ZN.rotationDegrees(zRot));

            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, packedLight,
                    packedOverlay, poseStack, buffer, blockEntity.getLevel(), 0);
            poseStack.popPose();
        }
    }

    /**
     * 基于方块坐标、物品索引和通道号生成稳定的伪随机浮点数，范围 [-1, 1]。
     * <p>
     * 使用 64 位位混淆哈希（Splitmix64 变体），无对象分配，适合逐帧调用。
     *
     * @param posSeed 方块坐标的 long 表示，作为基础种子
     * @param index   物品在槽位中的索引，保证每个物品结果不同
     * @param channel 通道编号，保证同一物品的不同旋转轴结果不同
     * @return [-1, 1] 范围内的伪随机浮点数
     */
    public static float stableRandom(long posSeed, int index, int channel) {
        long h = posSeed ^ ((long) index * 0x9e3779b97f4a7c15L) ^ ((long) channel * 0x6c62272e07bb0142L);
        h = (h ^ (h >>> 30)) * 0xbf58476d1ce4e5b9L;
        h = (h ^ (h >>> 27)) * 0x94d049bb133111ebL;
        h ^= (h >>> 31);
        return (float) (int) h / (float) Integer.MAX_VALUE;
    }
}
