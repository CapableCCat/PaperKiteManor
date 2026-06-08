package com.kazi_cat.papercraft_magic_decoration.blockentity;

import com.kazi_cat.papercraft_magic_decoration.api.block.ICustomRenderBoundingBox;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
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

public class AnimatedSmeltableBlockEntity extends SmeltableBlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public AnimatedSmeltableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.ANIMATED_SMELTABLE_BE.get(), pos, state);
    }

    @Override
    public boolean flip(Level level, @Nullable LivingEntity user) {
        if (super.flip(level, user)) {
            triggerAnim("main_controller", "animate");
            return true;
        }
        return false;
    }

    @Override
    public AABB getRenderBoundingBox() {
        if (this.getBlockState().getBlock() instanceof ICustomRenderBoundingBox provider) {
            return provider.getRenderBoundingBox(this.getBlockState(), this.worldPosition);
        }
        return super.getRenderBoundingBox();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "main_controller", 0, state -> {
            state.getController().setAnimation(RawAnimation.begin().thenLoop("0"));
            return PlayState.CONTINUE;
        }).triggerableAnim("animate", RawAnimation.begin().thenPlay("1")));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
