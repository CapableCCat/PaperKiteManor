package com.kazi_cat.papercraft_magic_decoration.mixin;

import com.kazi_cat.papercraft_magic_decoration.init.ModEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LivingEntity.class})
public abstract class LivingEntityMixin {
    @Inject(method = "getFrictionInfluencedSpeed", at = @At("RETURN"), cancellable = true)
    private void getFrictionInfluencedSpeed(float pFriction, CallbackInfoReturnable<Float> cir) {
        LivingEntity living = (LivingEntity) (Object) this;
        if (living.onGround() && living.hasEffect(ModEffects.SILKY_FEEL.get())) {
            float speed = living.getSpeed() * (0.21600002F / (0.941192F));
            cir.setReturnValue(speed);
        }
    }

    @Inject(method = "shouldDiscardFriction", at = @At("RETURN"), cancellable = true)
    public void shouldDiscardFriction(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity living = (LivingEntity) (Object) this;
        if (living.hasEffect(ModEffects.SILKY_FEEL.get())) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "updateWalkAnimation", at = @At("HEAD"), cancellable = true)
    public void updateWalkAnimation(float pPartialTick, CallbackInfo ci) {
        LivingEntity living = (LivingEntity) (Object) this;
        if (living.hasEffect(ModEffects.SILKY_FEEL.get())) {
            ci.cancel();
            living.walkAnimation.update(0, 0.4F);
        }
    }
}
