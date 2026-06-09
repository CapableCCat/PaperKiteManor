package com.kazi_cat.papercraft_magic_decoration.block.drink;

import com.kazi_cat.papercraft_magic_decoration.blockentity.DrinkBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.utils.VoxelShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemHandlerHelper;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;

@SuppressWarnings({"unchecked","deprecation"})
public class DrinkBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock, EntityBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final double STEP_LENGTH = 0.25D;

    protected final int maxCount;
    protected final IntegerProperty countProperty;
    protected final EnumMap<Direction, VoxelShape>[][][] shapes;

    public DrinkBlock(Properties properties, int maxCount, VoxelShape... shapes) {
        super(properties);
        this.maxCount = maxCount;
        this.countProperty = IntegerProperty.create("count", 0, maxCount);
        this.shapes = new EnumMap[shapes.length][5][5];
        for (int i = 0; i < shapes.length; i++) {
            for (int x = 0; x < 5; x++) {
                for (int y = 0; y <5; y++) {
                    Vec3 offset = new Vec3((x - 2) * STEP_LENGTH, 0, (y - 2) * STEP_LENGTH);
                    this.shapes[i][x][y] = VoxelShapeUtils.horizontalShapes(shapes[i], offset);
                }
            }
        }

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.overrideBlockStateDefinition(builder);
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(countProperty, 1)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
    }

    public DrinkBlock(int maxCount, VoxelShape... shapes) {
        this(Properties.of()
                .noOcclusion()
                .instabreak()
                .pushReaction(PushReaction.DESTROY)
                .sound(SoundType.GLASS), maxCount, shapes);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        if (hand != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }
        ItemStack itemInHand = player.getItemInHand(hand);

        if (itemInHand.is(this.asItem())) {
            int count = state.getValue(this.countProperty);
            if (count < this.maxCount) {
                level.setBlockAndUpdate(pos, state.cycle(this.countProperty));
                SoundType soundType = state.getSoundType(level, pos, player);
                SoundEvent sound = soundType.getPlaceSound();
                level.playSound(
                        player, pos, sound, SoundSource.BLOCKS,
                        (soundType.getVolume() + 1) / 2f,
                        soundType.getPitch() * 0.8f
                );
                if (!player.isCreative()) {
                    itemInHand.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
        }

        if (itemInHand.isEmpty()) {
            if (player.isSecondaryUseActive() && hitResult.getDirection().getAxis().isHorizontal()) {
                if (level.getBlockEntity(pos) instanceof DrinkBlockEntity be) {
                    Direction direction = hitResult.getDirection().getOpposite();
                    int x = Mth.clamp(be.getXOffset() + direction.getStepX(), -2, 2);
                    int y = Mth.clamp(be.getYOffset() + direction.getStepZ(), -2, 2);
                    be.setOffset(x, y);
                    return InteractionResult.SUCCESS;
                }
            }

            int count = state.getValue(this.countProperty);
            if (count > 1) {
                // 如果数量大于 1，那么就减少数量
                level.setBlockAndUpdate(pos, state.setValue(this.countProperty, count - 1));
            } else {
                // 否则就直接破坏
                level.removeBlock(pos, false);
            }
            ItemHandlerHelper.giveItemToPlayer(player, this.asItem().getDefaultInstance());
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    protected void overrideBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, countProperty);
    }

    public IntegerProperty getCountProperty() {
        return countProperty;
    }

    public int getMaxCount() {
        return maxCount;
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
    public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!level.isClientSide) {
            BlockPos pos = hit.getBlockPos();
            if (projectile.mayInteract(level, pos)) {
                level.removeBlock(pos, false);
                int id = Block.getId(this.defaultBlockState());
                level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, id);
            }
        }
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DrinkBlockEntity(pos, state);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        int count = state.getValue(this.countProperty);
        if (this.shapes.length == 0 || count == 0) {
            return super.getShape(state, level, pos, context);
        }
        if (count > this.shapes.length) {
            count = this.shapes.length;
        }
        Direction direction = state.getValue(FACING);
        int x = 2,y = 2;
        if (level.getBlockEntity(pos) instanceof DrinkBlockEntity be) {
            x = Mth.clamp(be.getXOffset() + 2, 0, 4);
            y = Mth.clamp(be.getYOffset() + 2, 0, 4);
        }
        return this.shapes[count - 1][x][y].getOrDefault(direction, super.getShape(state, level, pos, context));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    public VoxelShape getVisualShape(BlockState pState, BlockGetter pReader, BlockPos pPos, CollisionContext pContext) {
        return Shapes.empty();
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder lootParamsBuilder) {
        List<ItemStack> stacks = Lists.newArrayList();
        int count = state.getValue(this.countProperty);
        stacks.add(new ItemStack(this.asItem(), count));
        return stacks;
    }
}
