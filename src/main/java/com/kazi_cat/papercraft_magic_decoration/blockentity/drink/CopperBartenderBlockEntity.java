package com.kazi_cat.papercraft_magic_decoration.blockentity.drink;

import com.kazi_cat.papercraft_magic_decoration.blockentity.BaseBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.crafting.recipe.MixologyRecipe;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.ModRecipes;
import com.kazi_cat.papercraft_magic_decoration.inventory.container.CopperBartenderContainer;
import com.kazi_cat.papercraft_magic_decoration.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
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

import java.util.ArrayList;
import java.util.List;

public class CopperBartenderBlockEntity extends BaseBlockEntity implements GeoBlockEntity, MenuProvider {
    private static final String ITEMS = "items";
    private static final String SHAKING_TICK = "shaking_tick";

    protected final RecipeManager.CachedCheck<SimpleContainer, MixologyRecipe> quickCheck = RecipeManager.createCheck(ModRecipes.MIXOLOGY_RECIPE);
    protected final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    protected ItemStackHandler items = new ItemStackHandler(5);
    protected int shakingTick;

    public CopperBartenderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.COPPER_BARTENDER_BE.get(), pos, state);
    }

    public boolean tryShake(Level level) {
        return !isShaking() && !isInputEmpty() && quickCheck.getRecipeFor(getContainer(), level).map(recipe -> {
            shakingTick = 90;
            triggerShakeAnim(false);
            refresh();
            return true;
        }).orElse(false);
    }

    public void tick(Level level) {
        if (!isShaking()) return;
        shakingTick--;
        if (shakingTick <= 0) {
            onFinished(level);
            return;
        }
        if (level.getGameTime() % 5 == 0) {
            setChanged();
        }
    }

    public void onFinished(Level level) {
        quickCheck.getRecipeFor(getContainer(), level).ifPresent(recipe -> {
            ItemStack result = recipe.result().copy();
            ItemStack current = getResult();
            if (current.isEmpty()) {
                setResult(result);
            } else if (ItemStack.isSameItemSameTags(result, current)) {
                int total = result.getCount() + current.getCount();
                int maxSize = result.getMaxStackSize();
                if (total <= maxSize) {
                    setResult(result.copyWithCount(total));
                } else {
                    setResult(result.copyWithCount(maxSize));
                    ItemUtils.spawnItemEntity(level, worldPosition.getCenter(), result.copyWithCount(total - maxSize), Vec3.ZERO);
                }
            } else {
                ItemUtils.spawnItemEntity(level, worldPosition.getCenter(), result, Vec3.ZERO);
            }
        });
        for (int i = 0; i < 4; i++) {
            this.items.extractItem(i, 1, false);
        }
        refresh();
    }

    public void triggerShakeAnim(boolean isShort) {
        triggerAnim("animate_controller", isShort ? "short_shake" : "long_shake");
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "main_controller", 0, state -> {
            state.getController().setAnimation(RawAnimation.begin().thenLoop("0"));
            return PlayState.CONTINUE;
        }));

        data.add(new AnimationController<>(this, "animate_controller", 0, state -> PlayState.STOP)
                .triggerableAnim("short_shake", RawAnimation.begin().thenPlay("1"))
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

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(ITEMS, this.items.serializeNBT());
        tag.putInt(SHAKING_TICK, this.shakingTick);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(ITEMS, Tag.TAG_COMPOUND)) {
            this.items = new ItemStackHandler();
            this.items.deserializeNBT(tag.getCompound(ITEMS));
        }
        if (tag.contains(SHAKING_TICK)) {
            this.shakingTick = tag.getInt(SHAKING_TICK);
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

    public List<ItemStack> getDrops() {
        List<ItemStack> drops = new ArrayList<>();
        for (int i = 0; i < this.items.getSlots(); i++) {
            ItemStack itemStack = this.items.getStackInSlot(i);
            if (!itemStack.isEmpty()) {
                drops.add(itemStack);
            }
        }

        return drops;
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

    public boolean isShaking() { return this.shakingTick > 0; }
}
