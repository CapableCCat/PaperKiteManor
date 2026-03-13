package com.kazi_cat.papercraft_magic_decoration.blockentity.food.smeltable;

import com.kazi_cat.papercraft_magic_decoration.api.IBlockGeoModelProvider;
import com.kazi_cat.papercraft_magic_decoration.block.food.smeltable.SmeltableBlock;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.util.GeckoLibUtil;

@SuppressWarnings("unchecked")
public class GeoSmeltableBlockEntity extends SmeltableBlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public GeoSmeltableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.GEO_SMELTABLE_BE.get(), pos, state);
    }

    @Override
    public void onFlip(Level level, LivingEntity user) {
        if (isInFlipCooldown(level)) return;
        lastFlippedTime = level.getGameTime();
        int maxFlipCount = getMaxFlipCount();
        if (maxFlipCount <= 0) return;
        flipCount = Mth.clamp(flipCount + 1, 0, maxFlipCount);
        triggerAnim();
    }

    public @Nullable GeoModel<GeoSmeltableBlockEntity> getModel() {
        SmeltableBlock block = getBlock();
        if (block instanceof IBlockGeoModelProvider provider) {
            return (GeoModel<GeoSmeltableBlockEntity>) provider.getModel(this);
        }
        return null;
    }

    public void triggerAnim() {
        triggerAnim("animate_controller", "animate");
    }

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
}
