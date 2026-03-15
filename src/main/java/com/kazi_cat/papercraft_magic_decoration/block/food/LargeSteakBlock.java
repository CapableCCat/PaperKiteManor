package com.kazi_cat.papercraft_magic_decoration.block.food;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class LargeSteakBlock extends HorizontalDirectionalBlock {
    public static final IntegerProperty VARIANT = IntegerProperty.create("variant", 1, 6);
    protected static final VoxelShape BOUNDING_BOX = Block.box(0, 0, 0, 16, 12, 16);

    public LargeSteakBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        if (player.getItemInHand(hand).isEmpty() && player.isSecondaryUseActive()) {
            int variant = state.getValue(VARIANT) + 1;
            level.setBlockAndUpdate(pos, state.setValue(VARIANT, variant > 6 ? 1 : variant));
        }

        return super.use(state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, VARIANT);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return BOUNDING_BOX;
    }
}
