package com.kazi_cat.papercraft_magic_decoration.block.drink;

import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class GuangSBlock extends GlassDrinkBlock {
    public static final BooleanProperty BOXED = BooleanProperty.create("boxed");

    public GuangSBlock(Properties properties, int maxCount, Int2ObjectArrayMap<Double> offsets, VoxelShape... shapes) {
        super(properties, maxCount, offsets, shapes);

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.createBoxedBlockStateDefinition(builder);
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(countProperty, 1)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
                .setValue(BOXED, false));
    }

    protected void createBoxedBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, countProperty, BOXED, WATERLOGGED);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        ItemStack itemInHand = player.getItemInHand(hand);
        if (itemInHand.is(Items.PAPER)) {
            if (state.getValue(BOXED)) {
                level.setBlockAndUpdate(pos, state.setValue(BOXED, false));
            } else {
                level.setBlockAndUpdate(pos, state.setValue(BOXED, true));
            }
            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, hitResult);
    }

    public Item asItem() {
        return ModItems.GUANG_S.get();
    }

    public static class Builder extends GlassDrinkBlock.Builder {
        @Override
        public Supplier<? extends Block> build(Properties properties) {
            return () -> new GuangSBlock(properties, maxCount, offsets, shapes);
        }
    }
}
