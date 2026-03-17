package com.kazi_cat.papercraft_magic_decoration.item;

import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Function;

public class StateBlockItem extends ItemNameBlockItem {
    protected final Function<BlockState, BlockState> stateFunction;

    public StateBlockItem(Block block, Properties properties, Function<BlockState, BlockState> stateFunction) {
        super(block, properties);
        this.stateFunction = stateFunction;
    }

    @Override
    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        return super.placeBlock(context, stateFunction.apply(state));
    }
}
