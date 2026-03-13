package com.kazi_cat.papercraft_magic_decoration.item.food;

import com.kazi_cat.papercraft_magic_decoration.item.RenamedBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.function.Supplier;

public class TwoByThreeStructureBlockItem extends RenamedBlockItem {
    protected final Supplier<List<BlockState>> structure;

    public TwoByThreeStructureBlockItem(Properties properties, Supplier<List<BlockState>> structure, String descriptionId) {
        super(Blocks.AIR, properties, descriptionId);
        this.structure = structure;
    }

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        Level level = context.getLevel();
        Direction direction = context.getHorizontalDirection();
        BlockPos middleDown = context.getClickedPos();
        BlockPos middleUp = middleDown.relative(direction);
        BlockPos leftDown = middleDown.relative(direction.getCounterClockWise());
        BlockPos leftUp = leftDown.relative(direction);
        BlockPos rightDown = middleDown.relative(direction.getClockWise());
        BlockPos rightUp = rightDown.relative(direction);
        List<BlockPos> ordered = List.of(leftDown, middleDown, rightDown, leftUp, middleUp, rightUp);
        if (ordered.stream().anyMatch(pos -> !level.getBlockState(pos).canBeReplaced())) {
            return InteractionResult.FAIL;
        }

        List<BlockState> states = structure.get();
        for (int i = 0; i < 6; i++) {
            level.setBlockAndUpdate(ordered.get(i), states.get(i).trySetValue(HorizontalDirectionalBlock.FACING, direction.getOpposite()));
        }

        return InteractionResult.SUCCESS;
    }
}
