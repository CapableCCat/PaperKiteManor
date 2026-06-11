package com.kazi_cat.papercraft_magic_decoration.blockentity;

import com.kazi_cat.papercraft_magic_decoration.api.block.ICustomRenderBoundingBox;
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

public class SimpleAnimatedBlockEntity extends BaseBlockEntity implements GeoBlockEntity {
    protected final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public SimpleAnimatedBlockEntity(BlockEntityType<?> entityType, BlockPos pos, BlockState state) {
        super(entityType, pos, state);
    }

//    public SimpleAnimatedBlockEntity(BlockPos pos, BlockState state) {
//        super(ModBlocks.ANIMATED_BE.get(), pos, state);
//    }

    public void triggerAnimation() {
        triggerAnim("main_controller", "animate");
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
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.cache; }
}
