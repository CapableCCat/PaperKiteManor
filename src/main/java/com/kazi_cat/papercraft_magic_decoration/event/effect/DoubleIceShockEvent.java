package com.kazi_cat.papercraft_magic_decoration.event.effect;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.init.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = PaperKiteManor.MOD_ID)
public class DoubleIceShockEvent {
    @SubscribeEvent
    public static void onCriticalHit(CriticalHitEvent event) {
        if (event.isVanillaCritical() || event.getResult() == CriticalHitEvent.Result.ALLOW) {
            if (event.getTarget().level().isClientSide() || !(event.getTarget() instanceof LivingEntity living)) return;
            if (!event.getEntity().hasEffect(ModEffects.DOUBLE_ICE_SHOCK.get())) return;
            MobEffectInstance effectInstance = Objects.requireNonNull(event.getEntity().getEffect(ModEffects.DOUBLE_ICE_SHOCK.get()));

            event.setDamageModifier(event.getDamageModifier() * (effectInstance.getAmplifier() + 2));
            event.setResult(CriticalHitEvent.Result.ALLOW);

            living.addEffect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SLOWDOWN,
                    60,
                    effectInstance.getAmplifier()
            ));
        }
    }
}
