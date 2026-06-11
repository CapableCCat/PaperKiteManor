package com.kazi_cat.papercraft_magic_decoration.blockentity;

import com.kazi_cat.papercraft_magic_decoration.crafting.recipe.MixologyRecipe;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.ModRecipes;
import com.kazi_cat.papercraft_magic_decoration.init.ModSounds;
import com.kazi_cat.papercraft_magic_decoration.inventory.container.CopperBartenderContainer;
import com.kazi_cat.papercraft_magic_decoration.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class CopperBartenderBlockEntity extends BaseBlockEntity implements GeoBlockEntity, MenuProvider {
    private static final String TAG_ITEMS = "items";
    private static final String TAG_CURRENT_TICK = "current_tick";

    protected final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    protected final RecipeManager.CachedCheck<SimpleContainer, MixologyRecipe> quickCheck = RecipeManager.createCheck(ModRecipes.MIXOLOGY_RECIPE);
    protected final ItemStackHandler items = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            refresh();
        }
    };
    protected int currentTick;

    public CopperBartenderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.COPPER_BARTENDER_BE.get(), pos, state);
    }

    public boolean tryShake(Level level) {
        return !isShaking() && !isInputEmpty() && quickCheck.getRecipeFor(getContainer(), level).map(recipe -> {
            currentTick = 87;
            triggerAnim("main_controller", "long_shake");
            level.playSound(null, worldPosition, ModSounds.COCKTAIL_SHAKING.get(), SoundSource.BLOCKS, 1, 1);
            refresh();
            return true;
        }).orElse(false);
    }

    public void tick(Level level) {
        if (!isShaking()) {
            return;
        }
        currentTick--;
        if (currentTick <= 0) {
            quickCheck.getRecipeFor(getContainer(), level).ifPresent(recipe -> {
                ItemStack result = recipe.assemble(getContainer(), level.registryAccess());
                tryPlaceResult(level, result);
                if (result.isEmpty()) {
                    return;
                }
                ItemStack remainder = items.insertItem(4, result, false);
                if (!remainder.isEmpty()) {
                    ItemUtils.spawnItemEntity(level, worldPosition.getCenter(), remainder);
                }
            });
            for (int i = 0; i < 4; i++) {
                this.items.extractItem(i, 1, false);
            }
            refresh();
        }
    }

    public void tryPlaceResult(Level level, ItemStack result) {
        if (result.getItem() instanceof BlockItem blockItem) {
            Direction direction = this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
            BlockPos frontPos = this.worldPosition.relative(direction);
            BlockState frontState = level.getBlockState(frontPos);
            ItemStack toInsert = result.copyWithCount(1);
            BlockPlaceContext context = new BlockPlaceContext(level, null, InteractionHand.MAIN_HAND, toInsert,
                    new BlockHitResult(
                            frontPos.getCenter().relative(Direction.DOWN, 0.5),
                            Direction.UP,
                            frontPos,
                            false
                    ));
            if (frontState.canBeReplaced(context) && blockItem.place(context).consumesAction()) {
                result.split(1);
                if (level instanceof ServerLevel serverLevel) {
                    Vec3 particlePos = frontPos.getCenter().relative(Direction.DOWN, 0.2);
                    serverLevel.sendParticles(ParticleTypes.GLOW,
                            particlePos.x(), particlePos.y(), particlePos.z(),
                            10, 0.2, 0.2, 0.2, 0);
                }
            }
        }
    }

    public SimpleContainer getContainer() {
        SimpleContainer container = new SimpleContainer(4);
        for (int i = 0; i < 4; i++) {
            ItemStack stack = this.items.getStackInSlot(i);
            if (!stack.isEmpty()) {
                container.setItem(3 - i, stack);
            }
        }
        return container;
    }

    public boolean isInputEmpty() {
        for (int i = 0; i < 4; i++) {
            if (!items.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public ItemStack getResult() { return this.items.getStackInSlot(4); }

    public void setResult(ItemStack itemStack) { this.items.setStackInSlot(4, itemStack); }

    public ItemStackHandler getItems() { return this.items; }

    @SuppressWarnings("all")
    public boolean isShaking() { return this.currentTick > 0; }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(TAG_ITEMS, this.items.serializeNBT());
        tag.putInt(TAG_CURRENT_TICK, this.currentTick);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(TAG_ITEMS, Tag.TAG_COMPOUND)) {
            this.items.deserializeNBT(tag.getCompound(TAG_ITEMS));
        }
        if (tag.contains(TAG_CURRENT_TICK)) {
            this.currentTick = tag.getInt(TAG_CURRENT_TICK);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "main_controller", 0, state -> {
            state.getController().setAnimation(RawAnimation.begin().thenLoop("0"));
            return PlayState.CONTINUE;
        }).triggerableAnim("short_shake", RawAnimation.begin().thenPlay("1"))
                .triggerableAnim("long_shake", RawAnimation.begin().thenPlay("2")));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new CopperBartenderContainer(i, inventory, this);
    }
}
