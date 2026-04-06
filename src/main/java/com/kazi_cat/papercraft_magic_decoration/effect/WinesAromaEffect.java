package com.kazi_cat.papercraft_magic_decoration.effect;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class WinesAromaEffect extends BaseEffect {
    public WinesAromaEffect(int color) { super(color); }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        // 为了避免卡顿，每秒检查一次
        return duration % 20 == 0;
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        Level level = livingEntity.level();
        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, new AABB(livingEntity.blockPosition()).inflate(32));
        targets.stream().filter(e -> e != livingEntity && e.getType().getCategory() == MobCategory.MONSTER).forEach(e -> {
            e.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, amplifier));
            e.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 30, amplifier));
        });
    }
}
