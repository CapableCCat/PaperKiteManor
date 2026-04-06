package com.kazi_cat.papercraft_magic_decoration.effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class BloodThirstyDevil extends BaseEffect {
    public BloodThirstyDevil(int color) { super(color); }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // 为了避免卡顿，每半秒检查一次
        return duration % 10 == 0;
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        Level level = livingEntity.level();
        if (livingEntity.getHealth() >= livingEntity.getMaxHealth() / 2) {
            return;
        }

        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, new AABB(livingEntity.blockPosition()).inflate(4));
        targets.stream().filter(e -> e != livingEntity && e.getType().getCategory() == MobCategory.MONSTER).forEach(e -> {
            e.hurt(livingEntity.damageSources().magic(), 2 + amplifier);
            livingEntity.heal(2 + amplifier);
            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.CRIT,
                        e.getX(), e.getY() + e.getBbHeight(), e.getZ(),
                        5,
                        0.25, 0.2, 0.25,
                        0.02);
            }
        });
    }
}
