package com.kazi_cat.papercraft_magic_decoration.blockentity;

import com.kazi_cat.papercraft_magic_decoration.block.utility.MochaPotBlock;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import com.kazi_cat.papercraft_magic_decoration.init.tag.TagMod;
import com.kazi_cat.papercraft_magic_decoration.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public class MochaPotBlockEntity extends BaseBlockEntity {
    protected static final String TAG_RESULT = "result";
    protected static final String TAG_CURRENT_TICK = "currentTick";

    protected ItemStack result = ItemStack.EMPTY;
    protected int currentTick = -1;
    protected boolean heated;

    public MochaPotBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.MOCHA_POT_BE.get(), pos, state);
    }

    public void tick(Level level) {
        if (level.getGameTime() % 5 == 0) {
            heated = hasHeatSource(level);
        }

        if (!heated) {
            return;
        }

        if (currentTick > 0) {
            spawnParticleBoiling(level);
            currentTick--;
            return;
        }

        if (currentTick == 0) {
            if (level instanceof ServerLevel serverLevel) {
                Vec3 pos = worldPosition.getCenter();
                serverLevel.sendParticles(ParticleTypes.GLOW, pos.x(), pos.y(), pos.z(),
                        10, 0.2, 0.2, 0.2, 0);
            }
            level.setBlockAndUpdate(this.worldPosition, this.getBlockState().setValue(MochaPotBlock.BOILED, true));
            this.refresh();
            currentTick = -1;
        }
    }

    public boolean hasHeatSource(Level level) {
        BlockState belowState = level.getBlockState(worldPosition.below());
        if (belowState.hasProperty(BlockStateProperties.LIT)) {
            return belowState.getValue(BlockStateProperties.LIT);
        }
        return belowState.is(TagMod.HEAT_SOURCE_WITHOUT_LIT);
    }

    private void spawnParticleBoiling(Level level) {
        if (level instanceof ServerLevel serverLevel && serverLevel.random.nextFloat() < 0.08F) {
            serverLevel.sendParticles(ParticleTypes.CLOUD,
                    worldPosition.getX() + 0.5 + (level.random.nextFloat() - 0.5F) * 0.2F,
                    worldPosition.getY() + 0.8 + level.random.nextDouble() / 3,
                    worldPosition.getZ() + 0.5 + (level.random.nextFloat() - 0.5F) * 0.2F,
                    2, (level.random.nextFloat() - 0.5) * 0.05F, 0.1, (level.random.nextFloat() - 0.5) * 0.05F, 0.005);
        }
    }

    public boolean takeOutProduct(Level level, LivingEntity entity, ItemStack itemStack) {
        if (this.result.isEmpty() || this.currentTick > 0) {
            return false;
        }

        ItemStack carrier = this.result.getCraftingRemainingItem();
        if (itemStack.is(carrier.getItem()) && itemStack.getCount() >= this.result.getCount()) {
            itemStack.shrink(this.result.getCount());
            ItemUtils.getItemToLivingEntity(entity, this.result.copyAndClear());
            this.refresh();
            level.setBlockAndUpdate(this.worldPosition, this.getBlockState().setValue(MochaPotBlock.BOILED, false));
            return true;
        }

        return false;
    }

    public ItemStack dropAsItem() {
        ItemStack result = ModItems.MOCHA_POT.get().getDefaultInstance();
        if (!this.result.isEmpty()) {
            CompoundTag tag = new CompoundTag();
            tag.put(TAG_RESULT, this.result.save(new CompoundTag()));
            tag.putInt(TAG_CURRENT_TICK, this.currentTick);
            tag.putBoolean("boiled", getBlockState().getValue(MochaPotBlock.BOILED));
            BlockItem.setBlockEntityData(result, this.getType(), tag);
        }
        return result;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(TAG_RESULT, this.result.save(new CompoundTag()));
        tag.putInt(TAG_CURRENT_TICK, this.currentTick);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(TAG_RESULT)) {
            this.result = ItemStack.of(tag.getCompound(TAG_RESULT));
        }
        if (tag.contains(TAG_CURRENT_TICK)) {
            this.currentTick = tag.getInt(TAG_CURRENT_TICK);
        }
    }

    public ItemStack getResult() { return result; }

    public int getCurrentTick() { return currentTick; }
}
