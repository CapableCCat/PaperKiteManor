package com.kazi_cat.papercraft_magic_decoration.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class ChocolateInMoldBlock extends HorizontalDirectionalBlock {
    private static final VoxelShape shape = Shapes.join(
            Shapes.or(
                    Block.box(2, 0, 2, 14, 10, 14),
                    Block.box(1, 10, 1, 15, 13, 15)
            ),
            Block.box(3, 9, 3, 13, 13, 13),
            BooleanOp.ONLY_FIRST
    );

    private final Supplier<Item> chocolate;

    public ChocolateInMoldBlock(Properties properties, Supplier<Item> chocolate) {
        super(properties);
        this.chocolate = chocolate;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        if (hand == InteractionHand.MAIN_HAND && player.getMainHandItem().isEmpty() && !player.isSecondaryUseActive()) {
            ItemHandlerHelper.giveItemToPlayer(player, Items.BUCKET.getDefaultInstance());
            ItemHandlerHelper.giveItemToPlayer(player, chocolate.get().getDefaultInstance());
            level.removeBlock(pos, false);
            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, hitResult);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder lootParamsBuilder) {
        return List.of(Items.BUCKET.getDefaultInstance(), chocolate.get().getDefaultInstance());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shape;
    }
}
