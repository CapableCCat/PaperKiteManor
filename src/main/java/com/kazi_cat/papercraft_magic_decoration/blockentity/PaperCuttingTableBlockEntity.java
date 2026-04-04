package com.kazi_cat.papercraft_magic_decoration.blockentity;

import com.kazi_cat.papercraft_magic_decoration.crafting.recipe.PaperCuttingRecipe;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.ModRecipes;
import com.kazi_cat.papercraft_magic_decoration.item.ScissorsItem;
import com.kazi_cat.papercraft_magic_decoration.utils.AABBUtils;
import com.kazi_cat.papercraft_magic_decoration.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class PaperCuttingTableBlockEntity extends BaseBlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final RecipeManager.CachedCheck<Container, PaperCuttingRecipe> quickCheck = RecipeManager.createCheck(ModRecipes.PAPERCUTTING_RECIPE);
    private ItemStack content = ItemStack.EMPTY;

    public PaperCuttingTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.PAPER_CUTTING_TABLE_BE.get(), pos, state);
    }

    public boolean cut(LivingEntity user, ItemStack scissors, InteractionHand hand) {
        if (content.isEmpty() || !(scissors.getItem() instanceof ScissorsItem)) {
            return false;
        }

        return quickCheck.getRecipeFor(new SimpleContainer(content), user.level()).map(recipe -> {
            triggerAnim("animate_controller", "animate");
            if (user.level() instanceof ServerLevel serverLevel) {
                ItemParticleOption option = new ItemParticleOption(ParticleTypes.ITEM, content.copy());
                serverLevel.sendParticles(option,
                        worldPosition.getX() + 0.5,
                        worldPosition.getY() + 1.25,
                        worldPosition.getZ() + 0.5,
                        10, 0.25, 0.2, 0.25, 0.05);
            }
            scissors.hurtAndBreak(1, user, (p) -> p.broadcastBreakEvent(hand));
            content.shrink(1);
            ItemEntity itemEntity = new ItemEntity(user.level(),
                    worldPosition.getX() + 0.5,
                    worldPosition.getY() + 1.25,
                    worldPosition.getZ() + 0.5,
                    recipe.getResult().copy());
            itemEntity.setDefaultPickUpDelay();
            user.level().addFreshEntity(itemEntity);
            refresh();
            return true;
        }).orElse(false);
    }

    public boolean putIngredient(Level level, @Nullable LivingEntity user, ItemStack itemStack) {
        if (content.isEmpty()) {
            content = itemStack.split(1);
            level.playSound(null, this.worldPosition, SoundEvents.ITEM_FRAME_PLACE, SoundSource.BLOCKS, 1.0F, 0.5F);
            refresh();
            return true;
        }

        return false;
    }

    public boolean takeIngredient(Level level, LivingEntity user) {
        if (!content.isEmpty()) {
            ItemUtils.getItemToLivingEntity(user, content.split(1));
            refresh();
            return true;
        }

        return false;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("content")) {
            content = ItemStack.of(tag.getCompound("content"));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("content", content.save(new CompoundTag()));
    }

    public ItemStack getContent() { return this.content; }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "main_controller", 0, state -> {
            state.getController().setAnimation(RawAnimation.begin().thenLoop("0"));
            return PlayState.CONTINUE;
        }));

        data.add(new AnimationController<>(this, "animate_controller", 0, state -> PlayState.STOP)
                .triggerableAnim("animate", RawAnimation.begin().thenPlay("1")));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public AABB getRenderBoundingBox() {
        return AABBUtils.fromTo(this.worldPosition, this.worldPosition.above());
    }
}
