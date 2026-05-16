package com.kazi_cat.papercraft_magic_decoration.item;

import com.kazi_cat.papercraft_magic_decoration.block.food.MonsterSteakBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.function.Supplier;

public class TwoByThreeStructureItem extends Item {
    protected final Supplier<List<BlockState>> structure;

    public TwoByThreeStructureItem(Item.Properties properties, Supplier<List<BlockState>> structure) {
        super(properties);
        this.structure = structure;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
        Direction direction = context.getHorizontalDirection();

        List<BlockPos> ordered = MonsterSteakBlock.getOrderedPos(pos, direction);
        if (ordered.stream().anyMatch(p -> !level.getBlockState(p).canBeReplaced())) {
            return InteractionResult.FAIL;
        }

        if (!level.isClientSide()) {
            List<BlockState> states = structure.get();
            for (int i = 0; i < 6; i++) {
                level.setBlockAndUpdate(ordered.get(i), states.get(i).trySetValue(HorizontalDirectionalBlock.FACING, direction.getOpposite()));
            }

            Player player = context.getPlayer();
            if (!(player != null && player.isCreative())) {
                context.getItemInHand().shrink(1);
            }
        }

        return InteractionResult.SUCCESS;
    }
}
