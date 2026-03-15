package com.kazi_cat.papercraft_magic_decoration.block.drink;

import com.kazi_cat.papercraft_magic_decoration.utils.VoxelShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class BottleDrinkBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected final EnumMap<Direction, VoxelShape> shapes;
    protected final Supplier<ItemStack> resultSupplier;

    public BottleDrinkBlock(Properties properties, VoxelShape northShape, Supplier<ItemStack> resultSupplier) {
        super(properties);
        this.shapes = VoxelShapeUtils.horizontalShapes(northShape);
        this.resultSupplier = resultSupplier;

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
    }

    public BottleDrinkBlock(VoxelShape northShape, Supplier<ItemStack> resultSupplier) {
        this(Properties.of()
                .noOcclusion()
                .instabreak()
                .pushReaction(PushReaction.DESTROY)
                .sound(SoundType.GLASS), northShape, resultSupplier);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        // 如果是空手，那么可以尝试取得产物
        if (!player.getItemInHand(hand).isEmpty()) {
            return super.use(state, level, pos, player, hand, hitResult);
        }

        ItemHandlerHelper.giveItemToPlayer(player, resultSupplier.get());
        level.playSound(null, pos, SoundEvents.GLASS_PLACE, SoundSource.BLOCKS);
        level.removeBlock(pos, false);

        return InteractionResult.SUCCESS;
    }

    @Override
    public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!level.isClientSide) {
            BlockPos pos = hit.getBlockPos();
            if (projectile.mayInteract(level, pos)) {
                level.removeBlock(pos, false);
                // 播放玻璃的粒子效果
                int id = Block.getId(this.defaultBlockState());
                level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, id);
                Vec3 dropPos = pos.getCenter();
                ItemEntity itemEntity = new ItemEntity(level, dropPos.x(), dropPos.y(), dropPos.z(), resultSupplier.get());
                itemEntity.setDefaultPickUpDelay();
                level.addFreshEntity(itemEntity);
            }
        }
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shapes.get(state.getValue(FACING));
    }
}
