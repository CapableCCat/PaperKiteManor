package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModSounds {
    DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, PaperKiteManor.MOD_ID);

    RegistryObject<SoundEvent> AOAO_IDLE = SOUND_EVENTS.register("entity.aoao_idle", () -> SoundEvent.createVariableRangeEvent(PaperKiteManor.resourceLocation("entity.aoao_idle")));
    RegistryObject<SoundEvent> AOAO_HURT = SOUND_EVENTS.register("entity.aoao_hurt", () -> SoundEvent.createVariableRangeEvent(PaperKiteManor.resourceLocation("entity.aoao_hurt")));
    RegistryObject<SoundEvent> AOAO_DEATH = SOUND_EVENTS.register("entity.aoao_death", () -> SoundEvent.createVariableRangeEvent(PaperKiteManor.resourceLocation("entity.aoao_death")));
}
