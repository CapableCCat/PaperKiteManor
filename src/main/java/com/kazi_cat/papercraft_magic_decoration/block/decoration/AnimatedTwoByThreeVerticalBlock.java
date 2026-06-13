package com.kazi_cat.papercraft_magic_decoration.block.decoration;

import com.kazi_cat.papercraft_magic_decoration.api.block.ICustomRenderBoundingBox;
import com.kazi_cat.papercraft_magic_decoration.blockentity.SimpleAnimatedBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.utils.AABBUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class AnimatedTwoByThreeVerticalBlock extends TwoByThreeVerticalDecorationBlock implements EntityBlock, ICustomRenderBoundingBox {
    public AnimatedTwoByThreeVerticalBlock(Properties properties, VoxelShape... shapes) {
        super(properties, shapes);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {
        if (player.getItemInHand(hand).isEmpty()) {
            Direction facing = state.getValue(FACING);
            Direction left = facing.getClockWise();
            Direction right = facing.getCounterClockWise();
            BlockPos ep = switch (state.getValue(partProperty)) {
                case 0 -> pos.relative(right);
                case 1 -> pos;
                case 2 -> pos.relative(left);
                case 3 -> pos.relative(right).below();
                case 4 -> pos.below();
                case 5 -> pos.relative(left).below();
                default -> BlockPos.ZERO;
            };
            if (level.getBlockEntity(ep) instanceof SimpleAnimatedBlockEntity be) {
                if (be.triggerAnimation()) {
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(partProperty) == 1 ? new SimpleAnimatedBlockEntity(pos, state) : null;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public AABB getRenderBoundingBox(BlockState state, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        Direction left = facing.getClockWise();
        Direction right = facing.getCounterClockWise();
        return AABBUtils.fromTo(pos.relative(left), pos.relative(right).above());
    }
}
