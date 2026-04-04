package com.kazi_cat.papercraft_magic_decoration.blockentity;

import com.kazi_cat.papercraft_magic_decoration.api.block.IRenderBoundingBoxProvider;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AnimatedBlockEntity extends BaseBlockEntity implements GeoBlockEntity {
    protected final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public AnimatedBlockEntity(BlockEntityType<?> entityType, BlockPos pos, BlockState state) {
        super(entityType, pos, state);
    }

    public AnimatedBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.ANIMATED_BE.get(), pos, state);
    }

    @Override
    public AABB getRenderBoundingBox() {
        if (this.getBlockState().getBlock() instanceof IRenderBoundingBoxProvider provider) {
            return provider.getRenderBoundingBox(this.getBlockState(), this.worldPosition);
        }

        return super.getRenderBoundingBox();
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
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.cache; }
}
