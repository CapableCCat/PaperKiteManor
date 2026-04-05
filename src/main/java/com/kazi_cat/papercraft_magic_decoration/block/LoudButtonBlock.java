package com.kazi_cat.papercraft_magic_decoration.block;

import com.kazi_cat.papercraft_magic_decoration.blockentity.AnimatedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class LoudButtonBlock extends ButtonBlock implements EntityBlock {
    protected static final VoxelShape CEILING_AABB = Block.box(4.5, 9, 4.5, 11.5, 16, 11.5);
    protected static final VoxelShape FLOOR_AABB = Block.box(4.5, 0, 4.5, 11.5, 7, 11.5);
    protected static final VoxelShape NORTH_AABB = Block.box(4.5, 4.5, 9, 11.5, 11.5, 16);
    protected static final VoxelShape SOUTH_AABB = Block.box(4.5, 4.5, 0, 11.5, 11.5, 7);
    protected static final VoxelShape WEST_AABB = Block.box(9, 4.5, 4.5, 16, 11.5, 11.5);
    protected static final VoxelShape EAST_AABB = Block.box(0, 4.5, 4.5, 7, 11.5, 11.5);
    protected static final VoxelShape PRESSED_CEILING_AABB = Block.box(4.5, 11, 4.5, 11.5, 16, 11.5);
    protected static final VoxelShape PRESSED_FLOOR_AABB = Block.box(4.5, 0, 4.5, 11.5, 5, 11.5);
    protected static final VoxelShape PRESSED_NORTH_AABB = Block.box(4.5, 4.5, 11, 11.5, 11.5, 16);
    protected static final VoxelShape PRESSED_SOUTH_AABB = Block.box(4.5, 4.5, 0, 11.5, 11.5, 5);
    protected static final VoxelShape PRESSED_WEST_AABB = Block.box(11, 4.5, 4.5, 16, 11.5, 11.5);
    protected static final VoxelShape PRESSED_EAST_AABB = Block.box(0, 4.5, 4.5, 5, 11.5, 11.5);

    public LoudButtonBlock() {
        super(BlockBehaviour.Properties.of()
                        .sound(SoundType.LANTERN)
                        .strength(1f, 10f)
                        .lightLevel(s -> 5)
                        .noOcclusion()
                        .hasPostProcess((bs, br, bp) -> true)
                        .emissiveRendering((bs, br, bp) -> true),
                BlockSetType.WARPED, 20, true);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AnimatedBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    public void press(BlockState state, Level level, BlockPos pos) {
        super.press(state, level, pos);
        if (level.getBlockEntity(pos) instanceof AnimatedBlockEntity animated) {
            animated.triggerAnim();
        }
    }

    public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        boolean flag = state.getValue(POWERED);
        return switch (state.getValue(FACE)) {
            case FLOOR -> flag ? PRESSED_FLOOR_AABB : FLOOR_AABB;
            case WALL -> switch (direction) {
                case EAST -> flag ? PRESSED_EAST_AABB : EAST_AABB;
                case WEST -> flag ? PRESSED_WEST_AABB : WEST_AABB;
                case SOUTH -> flag ? PRESSED_SOUTH_AABB : SOUTH_AABB;
                case NORTH, UP, DOWN -> flag ? PRESSED_NORTH_AABB : NORTH_AABB;
            };
            default -> flag ? PRESSED_CEILING_AABB : CEILING_AABB;
        };
    }
}
