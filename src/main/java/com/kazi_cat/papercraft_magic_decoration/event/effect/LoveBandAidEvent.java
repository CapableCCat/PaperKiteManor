package com.kazi_cat.papercraft_magic_decoration.event.effect;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.init.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = PaperKiteManor.MOD_ID)
public class LoveBandAidEvent {
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity target = event.getEntity();
        Level level = target.level();
        if (!(target.hasEffect(ModEffects.LOVE_BAND_AID.get())) || level.isClientSide()) {
            return;
        }

        int amplifier = Objects.requireNonNull(target.getEffect(ModEffects.LOVE_BAND_AID.get())).getAmplifier();
        target.heal(amplifier + 1);
        target.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 300, amplifier));
    }
}
