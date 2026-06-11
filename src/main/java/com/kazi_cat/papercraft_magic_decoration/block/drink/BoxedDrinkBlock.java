package com.kazi_cat.papercraft_magic_decoration.block.drink;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BoxedDrinkBlock extends DrinkBlock {
    public static final BooleanProperty BOXED = BooleanProperty.create("boxed");

    public BoxedDrinkBlock(Properties properties, int maxCount, VoxelShape... shapes) {
        super(properties, maxCount, shapes);

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.createBoxedBlockStateDefinition(builder);
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(countProperty, 1)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
                .setValue(X_OFFSET, 2)
                .setValue(Z_OFFSET, 2)
                .setValue(BOXED, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        ItemStack itemInHand = player.getItemInHand(hand);
        if (hand == InteractionHand.MAIN_HAND && itemInHand.is(Items.PAPER)) {
            level.setBlockAndUpdate(pos, state.setValue(BOXED, !state.getValue(BOXED)));
            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, hitResult);
    }

    protected void createBoxedBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, BOXED, WATERLOGGED, X_OFFSET, Z_OFFSET, countProperty);
    }
}
