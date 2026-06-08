package com.kazi_cat.papercraft_magic_decoration.block.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class MultipartBlock extends Block {
    protected final IntegerProperty partProperty;

    public MultipartBlock(Properties properties, int parts) {
        super(properties);
        this.partProperty = IntegerProperty.create("part", 0, parts - 1);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        handleRemove(level, pos, state, player);
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        handleRemove(level, pos, state, null);
        super.onBlockExploded(state, level, pos, explosion);
    }

    @Override
    public @Nullable PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    public abstract List<BlockPos> getOrderedParts(BlockPos pos, BlockState state);

    public void handleRemove(Level level, BlockPos pos, BlockState state, @Nullable Player player) {
        boolean drop = !(player != null && player.isCreative());
        for (var part : getOrderedParts(pos, state)) {
            level.destroyBlock(part, drop);
        }
    }
}
