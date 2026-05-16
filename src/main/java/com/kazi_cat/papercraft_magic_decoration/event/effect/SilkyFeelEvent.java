package com.kazi_cat.papercraft_magic_decoration.event.effect;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.init.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PaperKiteManor.MOD_ID)
public class SilkyFeelEvent {
    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        MobEffectInstance effect = entity.getEffect(ModEffects.SILKY_FEEL.get());
        if (effect != null) {
            float targetFriction = 0.98F * (entity.onGround() ? 0.98F : 1);
            Vec3 delta = entity.getDeltaMovement();
            entity.setDeltaMovement(delta.x * targetFriction, delta.y, delta.z * targetFriction);
        }
    }
}
