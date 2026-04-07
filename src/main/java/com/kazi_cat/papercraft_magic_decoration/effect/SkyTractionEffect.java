package com.kazi_cat.papercraft_magic_decoration.effect;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SkyTractionEffect extends BaseEffect {
    public SkyTractionEffect(int color) { super(color); }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        Level level = livingEntity.level();
        if (livingEntity.onGround()) {
            if (level.canSeeSkyFromBelowWater(livingEntity.blockPosition())) {
                level.playSound(null, livingEntity.blockPosition(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.PLAYERS);
                livingEntity.addDeltaMovement(new Vec3(0, 2, 0));

                if (!level.isClientSide()) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0));
                }
            }
        }
    }
}
