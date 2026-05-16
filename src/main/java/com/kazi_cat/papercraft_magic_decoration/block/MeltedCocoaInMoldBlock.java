package com.kazi_cat.papercraft_magic_decoration.block;

import com.kazi_cat.papercraft_magic_decoration.blockentity.MeltedCocoaInMoldBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class MeltedCocoaInMoldBlock extends HorizontalDirectionalBlock implements EntityBlock {
    private static final VoxelShape shape = Shapes.join(
            Shapes.or(
                    Block.box(2, 0, 2, 14, 10, 14),
                    Block.box(1, 10, 1, 15, 13, 15)
            ),
            Block.box(3, 9, 3, 13, 13, 13),
            BooleanOp.ONLY_FIRST
    );

    private final Supplier<Block> chocolate;
    private final int cooldownTime;

    public MeltedCocoaInMoldBlock(Properties properties, Supplier<Block> chocolate, int cooldownTime) {
        super(properties);
        this.chocolate = chocolate;
        this.cooldownTime = cooldownTime;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) { return new MeltedCocoaInMoldBlockEntity(pos, state); }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shape;
    }

    @Nullable
    @SuppressWarnings("all")
    protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(
            BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker) {
        return clientType == serverType ? (BlockEntityTicker<A>) ticker : null;
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlocks.MELTED_COCOA_IN_MOLD_BE.get(),
                (levelIn, blockPos, blockState, cocoa) -> cocoa.tick(levelIn));
    }

    public ChocolateInMoldBlock getChocolateBlock() { return (ChocolateInMoldBlock) chocolate.get(); }

    public int getCooldownTime() { return cooldownTime; }
}
