package com.kazi_cat.papercraft_magic_decoration.item;

import com.kazi_cat.papercraft_magic_decoration.block.smeltable.ChunkySalmonBlock;
import com.kazi_cat.papercraft_magic_decoration.block.smeltable.TwoByOneSmeltableBlock;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

public class JumboSalmonItem extends Item {
    public JumboSalmonItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
        Direction direction = context.getHorizontalDirection();

        List<BlockPos> parts = getOrderedParts(pos, direction);
        if (parts.stream().anyMatch(p -> !level.getBlockState(p).canBeReplaced())) {
            return InteractionResult.FAIL;
        }

        if (!level.isClientSide()) {
            TwoByOneSmeltableBlock salmonHead = (TwoByOneSmeltableBlock) ModBlocks.SALMON_HEAD.get();
            ChunkySalmonBlock chunkySalmon = (ChunkySalmonBlock) ModBlocks.CHUNKY_SALMON.get();
            for (int i = 0; i < 6; i++) {
                BlockPos part = parts.get(i);
                FluidState fluidState = level.getFluidState(part);
                BlockState state = switch (i) {
                    case 0 -> salmonHead.defaultBlockState().setValue(salmonHead.getPartProperty(), 0);
                    case 1 -> chunkySalmon.defaultBlockState().setValue(ChunkySalmonBlock.VARIANT, 0);
                    case 2 -> chunkySalmon.defaultBlockState().setValue(ChunkySalmonBlock.VARIANT, 1);
                    case 3 -> salmonHead.defaultBlockState().setValue(salmonHead.getPartProperty(), 1);
                    case 4 -> chunkySalmon.defaultBlockState().setValue(ChunkySalmonBlock.VARIANT, 2);
                    case 5 -> chunkySalmon.defaultBlockState().setValue(ChunkySalmonBlock.VARIANT, 3);
                    default -> Blocks.AIR.defaultBlockState();
                };
                level.setBlockAndUpdate(part, state
                        .setValue(BlockStateProperties.HORIZONTAL_FACING, direction.getOpposite())
                        .setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER));
            }

            Player player = context.getPlayer();
            if (!(player != null && player.isCreative())) {
                context.getItemInHand().shrink(1);
            }
        }

        return InteractionResult.SUCCESS;
    }

    public static List<BlockPos> getOrderedParts(BlockPos pos, Direction direction) {
        return List.of(
                pos.relative(direction.getCounterClockWise()),
                pos,
                pos.relative(direction.getClockWise()),
                pos.relative(direction.getCounterClockWise()).relative(direction),
                pos.relative(direction),
                pos.relative(direction.getClockWise()).relative(direction)
        );
    }
}
