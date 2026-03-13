package com.kazi_cat.papercraft_magic_decoration.block.food;

import com.kazi_cat.papercraft_magic_decoration.blockentity.food.GlassDrinkBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.utils.VoxelShapeUtils;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

import java.util.EnumMap;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class GlassDrinkBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock, EntityBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected final IntegerProperty countProperty;
    protected final int maxCount;
    protected final EnumMap<Direction, VoxelShape>[] shapes;
    protected final Int2ObjectArrayMap<Double> offsets;

    private GlassDrinkBlock(int maxCount, Int2ObjectArrayMap<Double> offsets, VoxelShape... shapes) {
        super(Properties.of().noOcclusion().instabreak().pushReaction(PushReaction.DESTROY).sound(SoundType.GLASS));
        this.maxCount = maxCount;
        this.countProperty = IntegerProperty.create("count", 0, maxCount);
        this.offsets = offsets;
        this.shapes = new EnumMap[shapes.length];
        for (int i = 0; i < shapes.length; i++) {
            this.shapes[i] = VoxelShapeUtils.horizontalShapes(shapes[i]);
        }

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.createCountBlockStateDefinition(builder);
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(countProperty, 1)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
    }

    public boolean tryIncreaseCount(Level level, BlockPos pos, BlockState state, ItemStack stack) {
        int count = state.getValue(this.countProperty);
        if (count < this.maxCount) {
            if (level.getBlockEntity(pos) instanceof GlassDrinkBlockEntity be) {
                if (be.addItem(stack)) {
                    be.refresh();
                }
            }
            level.setBlockAndUpdate(pos, state.cycle(this.countProperty));
            return true;
        }
        return false;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        // 如果是空手，那么可以尝试取回
        if (!player.getItemInHand(hand).isEmpty()) {
            return super.use(state, level, pos, player, hand, hitResult);
        }

        if (player.isSecondaryUseActive()) {
            if (level.getBlockEntity(pos) instanceof GlassDrinkBlockEntity be) {
                Vector2i vector2i = be.getOffset();
                Direction direction = hitResult.getDirection().getOpposite();
                int newX = vector2i.x() + direction.getStepX();
                int newY = vector2i.y() + direction.getStepZ();
                if (Math.abs(newX) <= offsets.size() && Math.abs(newY) <= offsets.size()) {
                    vector2i.set(newX, newY);
                    be.refresh();
                }
                return InteractionResult.SUCCESS;
            }
        }

        // 尝试给玩家物品
        if (level.getBlockEntity(pos) instanceof GlassDrinkBlockEntity be) {
            ItemStack stack = be.removeItem();
            if (!stack.isEmpty()) {
                be.refresh();
                ItemHandlerHelper.giveItemToPlayer(player, stack);
                // 播放放置的音效
                level.playSound(null, pos, SoundEvents.GLASS_PLACE, SoundSource.BLOCKS);
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
            }
        }
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GlassDrinkBlockEntity(pos, state);
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

    protected void createCountBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, countProperty, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (this.shapes.length == 0) {
            return super.getShape(state, level, pos, context);
        }
        int count = state.getValue(this.countProperty);
        if (count == 0) return super.getShape(state, level, pos, context);
        if (count > this.shapes.length) {
            count = this.shapes.length;
        }
        Direction direction = state.getValue(FACING);
        VoxelShape shape = this.shapes[count - 1].getOrDefault(direction, super.getShape(state, level, pos, context));
        if (level.getBlockEntity(pos) instanceof GlassDrinkBlockEntity glass) {
            Vec3 offset = getOffset(glass.getOffset());
            shape = shape.move(offset.x(), offset.y(), offset.z());
        }
        return shape;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    public IntegerProperty getCountProperty() {
        return countProperty;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public Vec3 getOffset(Vector2i vector2i) {
        return new Vec3(
                sign(vector2i.x()) * offsets.get(Math.abs(vector2i.x())),
                0,
                sign(vector2i.y()) * offsets.get(Math.abs(vector2i.y()))
        );
    }

    protected int sign(int a) {
        if (a == 0) return 0;
        return a > 0 ? 1 : -1;
    }

    public static Builder create() { return new Builder(); }

    public static class Builder {
        private int maxCount;
        private VoxelShape[] shapes;
        private final Int2ObjectArrayMap<Double> offsets;

        public Builder() {
            offsets = new Int2ObjectArrayMap<>();
            offsets.defaultReturnValue(0D);
        }

        public Builder maxCount(int maxCount) {
            this.maxCount = maxCount;
            return this;
        }

        public Builder shapes(VoxelShape... shapes) {
            this.shapes = shapes;
            return this;
        }

        public Builder offset(int step, Double offset) {
            this.offsets.put(step, offset);
            return this;
        }

        public Supplier<? extends Block> build() {
            return () -> new GlassDrinkBlock(maxCount, offsets, shapes);
        }
    }
}
