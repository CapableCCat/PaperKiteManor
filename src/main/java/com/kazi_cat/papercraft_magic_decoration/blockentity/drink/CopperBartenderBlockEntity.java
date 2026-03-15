package com.kazi_cat.papercraft_magic_decoration.blockentity.drink;

import com.kazi_cat.papercraft_magic_decoration.blockentity.BaseBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class CopperBartenderBlockEntity extends BaseBlockEntity implements GeoBlockEntity {
    protected final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    protected final NonNullList<ItemStack> inputs = NonNullList.withSize(4, ItemStack.EMPTY);
    protected ItemStack result = ItemStack.EMPTY;

    public CopperBartenderBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlocks.COPPER_BARTENDER_BE.get(), pos, state);
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
}
