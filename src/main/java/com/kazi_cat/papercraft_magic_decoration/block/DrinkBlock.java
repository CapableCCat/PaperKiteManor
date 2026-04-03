package com.kazi_cat.papercraft_magic_decoration.block;

import com.kazi_cat.papercraft_magic_decoration.blockentity.DrinkBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.utils.VoxelShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings({"unchecked","deprecation"})
public class DrinkBlock extends HorizontalDirectionalBlock implements SimpleWaterloggedBlock, EntityBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected final IntegerProperty countProperty;
    protected final int maxCount;
    protected final int maxOffset;
    protected final double stepLength;
    protected final EnumMap<Direction, VoxelShape>[] shapes;

    public DrinkBlock(Properties properties, int maxCount, int maxOffset, double stepLength, VoxelShape... shapes) {
        super(properties);
        this.maxCount = maxCount;
        this.maxOffset = maxOffset;
        this.stepLength = stepLength;
        this.shapes = new EnumMap[shapes.length];
        for (int i = 0; i < shapes.length; i++) {
            this.shapes[i] = VoxelShapeUtils.horizontalShapes(shapes[i]);
        }
        this.countProperty = IntegerProperty.create("count", 0, maxCount);

        StateDefinition.Builder<Block, BlockState> builder = new StateDefinition.Builder<>(this);
        this.createCountBlockStateDefinition(builder);
        this.stateDefinition = builder.create(Block::defaultBlockState, BlockState::new);

        this.registerDefaultState(this.stateDefinition.any()
                .setValue(countProperty, 1)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false));
    }

    public DrinkBlock(int maxCount, int maxOffset, double stepLength, VoxelShape... shapes) {
        this(Properties.of().noOcclusion().instabreak().pushReaction(PushReaction.DESTROY).sound(SoundType.GLASS), maxCount, maxOffset, stepLength, shapes);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    protected void createCountBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, countProperty, WATERLOGGED);
    }

    public boolean tryIncreaseCount(Level level, BlockPos pos, BlockState state, ItemStack stack) {
        int count = state.getValue(this.countProperty);
        if (count < this.maxCount) {
            if (level.getBlockEntity(pos) instanceof DrinkBlockEntity be && be.addItem(stack)) {
                be.refresh();
            }
            level.setBlockAndUpdate(pos, state.cycle(this.countProperty));
            return true;
        }
        return false;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        // 如果是空手，那么可以尝试取回或偏移
        if (!player.getMainHandItem().isEmpty()) {
            return super.use(state, level, pos, player, hand, hitResult);
        }

        if (player.isSecondaryUseActive() && (hitResult.getDirection() != Direction.UP || hitResult.getDirection() != Direction.DOWN)) {
            if (level.getBlockEntity(pos) instanceof DrinkBlockEntity be) {
                Vector2i vector2i = be.getOffset();
                Direction direction = hitResult.getDirection().getOpposite();
                vector2i.set(Mth.clamp(vector2i.x() + direction.getStepX(), -maxOffset, maxOffset), Mth.clamp(vector2i.y() + direction.getStepZ(), -maxOffset, maxOffset));
                return InteractionResult.SUCCESS;
            }
        }

        // 尝试给玩家物品
        if (level.getBlockEntity(pos) instanceof DrinkBlockEntity be) {
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
                int id = Block.getId(this.defaultBlockState());
                level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, id);
            }
        }
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DrinkBlockEntity(pos, state);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder lootParamsBuilder) {
        List<ItemStack> stacks = super.getDrops(state, lootParamsBuilder);
        BlockEntity parameter = lootParamsBuilder.getParameter(LootContextParams.BLOCK_ENTITY);
        if (parameter instanceof DrinkBlockEntity glass) {
            glass.getItems().stream().filter(s -> !s.isEmpty()).forEach(stacks::add);
        }
        return stacks;
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
        if (level.getBlockEntity(pos) instanceof DrinkBlockEntity glass) {
            Vector2i vector2i = glass.getOffset();
            return shape.move(vector2i.x() * stepLength, 0, vector2i.y() * stepLength);
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

    public int getMaxOffset() { return maxOffset; }

    public double getStepLength() { return stepLength; }

    public static Builder create() { return new Builder(); }

    public static class Builder {
        protected int maxCount;
        protected int maxOffset = 2;
        protected double stepLength = 0.25;
        protected VoxelShape[] shapes;

        public Builder maxCount(int maxCount) {
            this.maxCount = maxCount;
            return this;
        }

        public Builder maxOffset(int maxOffset) {
            this.maxOffset = maxOffset;
            return this;
        }

        public Builder stepLength(double stepLength) {
            this.stepLength = stepLength;
            return this;
        }

        public Builder shapes(VoxelShape... shapes) {
            this.shapes = shapes;
            return this;
        }

        public Supplier<? extends Block> build() {
            return () -> new DrinkBlock(maxCount, maxOffset, stepLength, shapes);
        }

        public Supplier<? extends Block> build(Properties properties) {
            return () -> new DrinkBlock(properties, maxCount, maxOffset, stepLength, shapes);
        }
    }
}
