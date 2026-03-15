package com.kazi_cat.papercraft_magic_decoration.item;

import com.kazi_cat.papercraft_magic_decoration.block.food.TwoByThreeSmeltableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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
        List<BlockPos> ordered = TwoByThreeSmeltableBlock.getOrderedPos(context.getClickedPos(), direction);
        if (ordered.stream().anyMatch(pos -> !level.getBlockState(pos).canBeReplaced())) {
            return InteractionResult.FAIL;
        }

        List<BlockState> states = structure.get();
        for (int i = 0; i < 6; i++) {
            level.setBlockAndUpdate(ordered.get(i), states.get(i).trySetValue(HorizontalDirectionalBlock.FACING, direction.getOpposite()));
        }

        Player player = context.getPlayer();
        if (!(player != null && player.isCreative())) {
            context.getItemInHand().shrink(1);
        }

        return InteractionResult.SUCCESS;
    }
}
