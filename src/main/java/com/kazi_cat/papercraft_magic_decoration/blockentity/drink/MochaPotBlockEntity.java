package com.kazi_cat.papercraft_magic_decoration.blockentity.drink;

import com.kazi_cat.papercraft_magic_decoration.block.drink.MochaPotBlock;
import com.kazi_cat.papercraft_magic_decoration.blockentity.BaseBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.crafting.recipe.MochaPotRecipe;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import com.kazi_cat.papercraft_magic_decoration.init.ModRecipes;
import com.kazi_cat.papercraft_magic_decoration.init.tag.TagMod;
import com.kazi_cat.papercraft_magic_decoration.inventory.container.MochaPotContainer;
import com.kazi_cat.papercraft_magic_decoration.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MochaPotBlockEntity extends BaseBlockEntity implements MenuProvider {
    protected static final int PUT_INGREDIENT = 0;
    protected static final int BOILING = 1;
    protected static final int TAKE_OUT_PRODUCT = 2;

    protected static final String ITEMS = "items";
    protected static final String CARRIER = "carrier";
    protected static final String RESULT = "result";
    protected static final String CURRENT_TICK = "currentTick";
    protected static final String STATUS = "status";

    private final RecipeManager.CachedCheck<SimpleContainer, MochaPotRecipe> quickCheck = RecipeManager.createCheck(ModRecipes.MOCHA_POT_RECIPE);

    protected ItemStackHandler items = new ItemStackHandler(4){
        @Override
        public int getSlotLimit(int slot) { return 1; }
    };
    protected ItemStack carrier = ItemStack.EMPTY;
    protected ItemStack result = ItemStack.EMPTY;
    protected int currentTick = 0;
    protected int status = 0;

    public MochaPotBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.MOCHA_POT_BE.get(), pos, state);
    }

    public void tick(Level level) {
        if (!hasHeatSource(level)) {
            return;
        }

        if (status == PUT_INGREDIENT && level.getGameTime() % 5 == 0 && !isAnyEmpty()) {
            Optional<MochaPotRecipe> recipeOpt = this.quickCheck.getRecipeFor(getContainer(), level);
            if (recipeOpt.isPresent()) {
                this.setRecipe(level, recipeOpt.orElseThrow());
                this.status = BOILING;
                this.refresh();
                return;
            }
        }

        if (status == BOILING) {
            spawnParticleBoiling(level);
            if (currentTick > 0) {
                currentTick--;
                return;
            }
            status = TAKE_OUT_PRODUCT;
            currentTick = 0;
            for (int i = 0; i < this.items.getSlots(); i++) {
               items.setStackInSlot(i, items.getStackInSlot(i).getCraftingRemainingItem());
            }
            if (level instanceof ServerLevel serverLevel) {
                Vec3 pos = worldPosition.getCenter();
                serverLevel.sendParticles(
                        ParticleTypes.GLOW,
                        pos.x(), pos.y(), pos.z(),
                        10,
                        0.2, 0.2, 0.2,
                        0
                );
            }
            level.setBlockAndUpdate(this.worldPosition, this.getBlockState().setValue(MochaPotBlock.BOILED, true));
            this.refresh();
        }
    }

    public boolean takeOutProduct(Level level, LivingEntity entity, ItemStack carrier) {
        if (this.status != TAKE_OUT_PRODUCT) {
            return false;
        }

        if (carrier.is(this.carrier.getItem()) && carrier.getCount() >= this.carrier.getCount()) {
            carrier.shrink(this.carrier.getCount());
            ItemUtils.getItemToLivingEntity(entity, this.result.copy());
            this.status = PUT_INGREDIENT;
            this.carrier = ItemStack.EMPTY;
            this.result = ItemStack.EMPTY;
            this.refresh();
            level.setBlockAndUpdate(this.worldPosition, this.getBlockState().setValue(MochaPotBlock.BOILED, false));
            return true;
        }

        return false;
    }

    private void spawnParticleBoiling(Level level) {
        if (level instanceof ServerLevel serverLevel && serverLevel.random.nextFloat() < 0.08F) {
            serverLevel.sendParticles(ParticleTypes.CLOUD,
                    worldPosition.getX() + 0.5 + (level.random.nextFloat() - 0.5F) * 0.2F,
                    worldPosition.getY() + 0.8 + level.random.nextDouble() / 3,
                    worldPosition.getZ() + 0.5 + (level.random.nextFloat() - 0.5F) * 0.2F,
                    2,
                    (level.random.nextFloat() - 0.5) * 0.05F,
                    0.1,
                    (level.random.nextFloat() - 0.5) * 0.05F,
                    0.005);
        }
    }

    public boolean hasHeatSource(Level level) {
        BlockState belowState = level.getBlockState(worldPosition.below());
        if (belowState.hasProperty(BlockStateProperties.LIT)) {
            return belowState.getValue(BlockStateProperties.LIT);
        }
        return belowState.is(TagMod.HEAT_SOURCE_WITHOUT_LIT);
    }

    public void setRecipe(Level level, MochaPotRecipe recipe) {
        this.carrier = recipe.carrier().copy();
        this.result = recipe.getResultItem(level.registryAccess());
        this.currentTick = recipe.time();
    }

    public SimpleContainer getContainer() {
        SimpleContainer container = new SimpleContainer(this.items.getSlots());
        for (int i = 0; i < this.items.getSlots(); i++) {
            container.setItem(i, this.items.getStackInSlot(i));
        }
        return container;
    }

    public ItemStack dropAsItem() {
        ItemStack result = ModItems.MOCHA_POT.get().getDefaultInstance();
        CompoundTag tag = new CompoundTag();
        tag.put(ITEMS, this.items.serializeNBT());
        tag.put(CARRIER, this.carrier.save(new CompoundTag()));
        tag.put(RESULT, this.result.save(new CompoundTag()));
        tag.putInt(STATUS, this.status);
        tag.putInt(CURRENT_TICK, this.currentTick);
        BlockItem.setBlockEntityData(result, this.getType(), tag);
        return result;
    }

    protected boolean isAnyEmpty() {
        for (int i = 0; i < this.items.getSlots(); i++) {
            if (this.items.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(ITEMS, this.items.serializeNBT());
        tag.put(CARRIER, this.carrier.save(new CompoundTag()));
        tag.put(RESULT, this.result.save(new CompoundTag()));
        tag.putInt(CURRENT_TICK, this.currentTick);
        tag.putInt(STATUS, this.status);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(ITEMS)) {
            this.items = new ItemStackHandler(4);
            this.items.deserializeNBT(tag.getCompound(ITEMS));
        }
        if (tag.contains(CARRIER)) {
            this.carrier = ItemStack.of(tag.getCompound(CARRIER));
        }
        if (tag.contains(RESULT)) {
            this.result = ItemStack.of(tag.getCompound(RESULT));
        }
        if (tag.contains(CURRENT_TICK)) {
            this.currentTick = tag.getInt(CURRENT_TICK);
        }
        if (tag.contains(STATUS)) {
            this.status = tag.getInt(STATUS);
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new MochaPotContainer(i, inventory, this);
    }

    public ItemStackHandler getItems() { return this.items; }

    public ItemStack getResult() { return this.result; }

    public int getStatus() { return this.status; }

    public int getCurrentTick() { return this.currentTick; }
}
