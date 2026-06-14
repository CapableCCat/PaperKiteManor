package com.kazi_cat.papercraft_magic_decoration.blockentity;

import com.google.common.collect.Lists;
import com.kazi_cat.papercraft_magic_decoration.block.utility.CopperStillBlock;
import com.kazi_cat.papercraft_magic_decoration.crafting.container.DistillationContainer;
import com.kazi_cat.papercraft_magic_decoration.crafting.recipe.DistillationRecipe;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.ModRecipes;
import com.kazi_cat.papercraft_magic_decoration.init.tag.TagMod;
import com.kazi_cat.papercraft_magic_decoration.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;

public class CopperStillBlockEntity extends BaseBlockEntity {
    protected static final String TAG_ITEMS = "items";
    protected static final String TAG_WINE_BASE = "wine_base";
    protected static final String TAG_RESULT = "result";
    protected static final String TAG_CURRENT_TICK = "current_tick";

    protected final RecipeManager.CachedCheck<DistillationContainer, DistillationRecipe> quickCheck = RecipeManager.createCheck(ModRecipes.DISTILLATION_RECIPE);
    protected final ItemStackHandler items = new ItemStackHandler(9) {
        @Override
        protected void onContentsChanged(int slot) { refresh(); }
        @Override
        protected int getStackLimit(int slot, ItemStack stack) { return 1; }
    };
    protected ItemStack wineBase = ItemStack.EMPTY;
    protected ItemStack result = ItemStack.EMPTY;
    protected int currentTick = 0;

    public CopperStillBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.COPPER_STILL_BE.get(), pos, state);
    }

    public void tick(Level level) {
        RandomSource random = level.random;
        if (currentTick > 0) {
            this.currentTick--;

            if (this.currentTick % 10 == 0 && level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.CLOUD,
                        worldPosition.getX() + 0.5,
                        worldPosition.getY() + 1.25,
                        worldPosition.getZ() + 0.5,
                        2, 0.1, 0.08, 0.1, 0.02);
            }

            // 播放炼药台音效
            if (this.currentTick % 20 == 0) {
                level.playSound(null, this.worldPosition,
                        SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS,
                        0.5f + random.nextFloat() / 0.5f,
                        0.8f + random.nextFloat() / 0.5f);
            }
        }

        if (currentTick == 0) {
            for (int i = 0; i < items.getSlots(); i++) {
                items.setStackInSlot(i, ItemStack.EMPTY);
            }
            wineBase = ItemStack.EMPTY;
            CopperStillBlock.updateStatus(level, worldPosition, getBlockState(), 3);
            refresh();
        }
    }

    public boolean addWineBase(Level level, @Nullable LivingEntity user, ItemStack itemStack) {
        if (getStatus() != 0 || !wineBase.isEmpty() || !itemStack.is(TagMod.WINE_BASE)) {
            return false;
        }

        this.wineBase = itemStack.split(1);
        CopperStillBlock.updateStatus(level, worldPosition, getBlockState(), 1);
        level.playSound(null, worldPosition, SoundEvents.LANTERN_PLACE, SoundSource.BLOCKS, 1.0F, 0.5F);
        refresh();
        return true;
    }

    public boolean removeWineBase(Level level, LivingEntity user) {
        if (getStatus() != 1 || wineBase.isEmpty()) {
            return false;
        }

        ItemUtils.getItemToLivingEntity(user, wineBase.copyAndClear());
        CopperStillBlock.updateStatus(level, worldPosition, getBlockState(), 0);
        refresh();
        return true;
    }

    public boolean addIngredient(Level level, @Nullable LivingEntity user, ItemStack itemStack) {
        if (getStatus() != 0 && getStatus() != 1) {
            return false;
        }

        for (int i = 0; i < this.items.getSlots(); i++) {
            ItemStack item = this.items.getStackInSlot(i);
            if (item.isEmpty()) {
                this.items.setStackInSlot(i, itemStack.split(1));
                level.playSound(null, this.worldPosition, SoundEvents.LANTERN_PLACE, SoundSource.BLOCKS, 1.0F, 0.5F);
                refresh();
                return true;
            }
        }
        return false;
    }

    public boolean removeIngredient(Level level, LivingEntity user) {
        if (getStatus() != 0 && getStatus() != 1) {
            return false;
        }

        for (int i = this.items.getSlots() - 1; i >= 0; i--) {
            ItemStack stack = this.items.getStackInSlot(i);
            if (stack.isEmpty()) {
                continue;
            }
            ItemUtils.getItemToLivingEntity(user, this.items.extractItem(i, 1, false));
            refresh();
            return true;
        }
        return false;
    }

    public boolean takeOutResult(Level level, LivingEntity user) {
        if (getStatus() != 3 || result.isEmpty()) {
            return false;
        }

        ItemUtils.getItemToLivingEntity(user, result.copyAndClear());
        CopperStillBlock.updateStatus(level, worldPosition, getBlockState(), 0);
        refresh();
        return true;
    }

    public boolean addFuel(Level level, @Nullable LivingEntity user, ItemStack itemStack) {
        if (this.currentTick > 0 || getStatus() != 1) {
            return false;
        }

        // TODO 使用tag来匹配燃料
        if (itemStack.is(Items.COAL) || itemStack.is(Items.CHARCOAL)) {
            itemStack.shrink(1);
            quickCheck.getRecipeFor(getContainer(), level).ifPresentOrElse(recipe -> {
                this.result = recipe.result().copy();
                this.currentTick = recipe.time();
            }, () -> {
                this.result = new ItemStack(Items.POTION);
                this.currentTick = 300;
            });
            CopperStillBlock.updateStatus(level, worldPosition, getBlockState(), 2);
            refresh();
            return true;
        }

        return false;
    }

    protected DistillationContainer getContainer() {
        List<ItemStack> ingredients = Lists.newArrayList();
        for (int i = 0; i < items.getSlots(); i++) {
            ItemStack stack = items.getStackInSlot(i);
            if (!stack.isEmpty()) {
                ingredients.add(stack);
            }
        }
        return new DistillationContainer(ingredients, wineBase);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(TAG_ITEMS, this.items.serializeNBT());
        tag.put(TAG_WINE_BASE, this.wineBase.save(new CompoundTag()));
        tag.put(TAG_RESULT, this.result.save(new CompoundTag()));
        tag.putInt(TAG_CURRENT_TICK, this.currentTick);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(TAG_ITEMS)) {
            this.items.deserializeNBT(tag.getCompound(TAG_ITEMS));
        }
        if (tag.contains(TAG_WINE_BASE)) {
            this.wineBase = ItemStack.of(tag.getCompound(TAG_WINE_BASE));
        }
        if (tag.contains(TAG_RESULT)) {
            this.result = ItemStack.of(tag.getCompound(TAG_RESULT));
        }
        if (tag.contains(TAG_CURRENT_TICK)) {
            this.currentTick = tag.getInt(TAG_CURRENT_TICK);
        }
    }

    public ItemStackHandler getItems() { return this.items; }

    public ItemStack getWineBase() { return this.wineBase; }

    public ItemStack getResult() { return this.result; }

    public int getCurrentTick() { return this.currentTick; }

    public int getStatus() { return this.getBlockState().getValue(CopperStillBlock.STATUS); }
}
