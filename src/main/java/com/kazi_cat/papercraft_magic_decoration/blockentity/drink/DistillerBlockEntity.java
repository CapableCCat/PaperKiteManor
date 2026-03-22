package com.kazi_cat.papercraft_magic_decoration.blockentity.drink;

import com.google.common.collect.Lists;
import com.kazi_cat.papercraft_magic_decoration.block.drink.DistillerBlock;
import com.kazi_cat.papercraft_magic_decoration.blockentity.BaseBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.crafting.container.DistillerContainer;
import com.kazi_cat.papercraft_magic_decoration.crafting.recipe.DistillerRecipe;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import com.kazi_cat.papercraft_magic_decoration.init.ModRecipes;
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

public class DistillerBlockEntity extends BaseBlockEntity {
    protected static final String ITEMS = "Items";
    protected static final String RESULT = "Result";
    protected static final String CURRENT_TICK = "CurrentTick";

    private final RecipeManager.CachedCheck<DistillerContainer, DistillerRecipe> quickCheck = RecipeManager.createCheck(ModRecipes.DISTILLER_RECIPE);

    protected ItemStackHandler items = new ItemStackHandler(10){
        @Override
        public int getSlotLimit(int slot) { return 1; }
    };
    protected ItemStack result = ItemStack.EMPTY;
    protected int currentTick = 0;

    public DistillerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.DISTILLER_BE.get(), pos, state);
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
            this.onFinished(level);
        }
    }

    public boolean addWineBase(Level level, @Nullable LivingEntity user, ItemStack itemStack) {
        if (getStatus() != 0) return false;
        if (!getWineBase().isEmpty()) return false;
        if (!itemStack.is(ModItems.WHISKEY_RAW.get())) return false;

        items.setStackInSlot(0, itemStack.split(1));
        level.setBlockAndUpdate(this.worldPosition, this.getBlockState().setValue(DistillerBlock.STATUS, 1));
        level.playSound(null, this.worldPosition, SoundEvents.LANTERN_PLACE, SoundSource.BLOCKS, 1.0F, 0.5F);
        setChanged();
        return true;
    }

    public boolean removeWineBase(Level level, LivingEntity user) {
        if (getStatus() != 1) return false;

        ItemStack item = this.items.getStackInSlot(0);
        if (!item.isEmpty()) {
            this.items.setStackInSlot(0, ItemStack.EMPTY);
            ItemUtils.getItemToLivingEntity(user, item);
            level.setBlockAndUpdate(this.worldPosition, this.getBlockState().setValue(DistillerBlock.STATUS, 0));
            setChanged();
            return true;
        }

        return false;
    }

    public boolean addIngredient(Level level, @Nullable LivingEntity user, ItemStack itemStack) {
        if (getStatus() != 0 && getStatus() != 1) return false;

        for (int i = 1; i < this.items.getSlots(); i++) {
            ItemStack item = this.items.getStackInSlot(i);
            if (item.isEmpty()) {
                this.items.setStackInSlot(i, itemStack.split(1));
                level.playSound(null, this.worldPosition, SoundEvents.LANTERN_PLACE, SoundSource.BLOCKS, 1.0F, 0.5F);
                setChanged();
                return true;
            }
        }

        return false;
    }

    public boolean removeIngredient(Level level, LivingEntity user) {
        if (getStatus() != 0 && getStatus() != 1) return false;

        for (int i = this.items.getSlots() - 1; i >= 1; i--) {
            ItemStack stack = this.items.getStackInSlot(i);
            if (stack.isEmpty()) continue;

            this.items.setStackInSlot(i, ItemStack.EMPTY);
            ItemUtils.getItemToLivingEntity(user, stack);
            setChanged();
            return true;
        }
        return false;
    }

    public boolean takeOutResult(Level level, LivingEntity user) {
        if (getStatus() != 3) return false;

        if (!result.isEmpty()) {
            ItemUtils.getItemToLivingEntity(user, result.copyAndClear());
            level.setBlockAndUpdate(this.worldPosition, this.getBlockState().setValue(DistillerBlock.STATUS, 0));
            setChanged();
            return true;
        }

        return false;
    }

    public boolean startDistilling(Level level, @Nullable LivingEntity user, ItemStack itemStack) {
        if (this.currentTick > 0) return false;
        if (getStatus() != 1) return false;

        if (itemStack.is(Items.COAL) || itemStack.is(Items.CHARCOAL)) {
            itemStack.shrink(1);
            quickCheck.getRecipeFor(getContainer(), level).ifPresentOrElse(recipe -> {
                this.result = recipe.result().copy();
                this.currentTick = recipe.time();
            }, () -> {
                this.result = new ItemStack(Items.POTION);
                this.currentTick = 300;
            });
            level.setBlockAndUpdate(this.worldPosition, this.getBlockState().setValue(DistillerBlock.STATUS, 2));
            setChanged();
            return true;
        }

        return false;
    }

    public void onFinished(Level level) {
        for (int i = 0; i < items.getSlots(); i++) {
            items.setStackInSlot(i, ItemStack.EMPTY);
        }
        level.setBlockAndUpdate(this.worldPosition, this.getBlockState().setValue(DistillerBlock.STATUS, 3));
        setChanged();
    }

    protected DistillerContainer getContainer() {
        List<ItemStack> ingredients = Lists.newArrayList();
        for (int i = 1; i < items.getSlots(); i++) {
            ItemStack stack = items.getStackInSlot(i);
            if (!stack.isEmpty()) {
                ingredients.add(stack);
            }
        }

        return new DistillerContainer(ingredients, items.getStackInSlot(0));
    }

    public List<ItemStack> getDrops() {
        List<ItemStack> ans = Lists.newArrayList();
        if (getStatus() != 2) {
            for (int i = 0; i < items.getSlots(); i++) {
                ItemStack stack = items.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    ans.add(stack);
                }
            }
        }

        if (getStatus() == 3) {
            if (!result.isEmpty()) {
                ans.add(result);
            }
        }

        return ans;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(ITEMS, this.items.serializeNBT());
        tag.put(RESULT, this.result.save(new CompoundTag()));
        tag.putInt(CURRENT_TICK, this.currentTick);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(ITEMS)) {
            this.items = new ItemStackHandler(10);
            this.items.deserializeNBT(tag.getCompound(ITEMS));
        }
        if (tag.contains(RESULT)) {
            this.result = ItemStack.of(tag.getCompound(RESULT));
        }
        if (tag.contains(CURRENT_TICK)) {
            this.currentTick = tag.getInt(CURRENT_TICK);
        }
    }

    public ItemStackHandler getItems() { return this.items; }

    public ItemStack getWineBase() { return this.items.getStackInSlot(0); }

    public ItemStack getResult() { return this.result; }

    public int getStatus() { return this.getBlockState().getValue(DistillerBlock.STATUS); }

    public int getCurrentTick() { return this.currentTick; }
}
