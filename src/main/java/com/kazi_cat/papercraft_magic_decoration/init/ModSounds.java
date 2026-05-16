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
    RegistryObject<SoundEvent> PAPER_TIGER_IDLE = SOUND_EVENTS.register("entity.paper_tiger_idle", () -> SoundEvent.createVariableRangeEvent(PaperKiteManor.resourceLocation("entity.paper_tiger_idle")));
    RegistryObject<SoundEvent> PAPER_TIGER_DEATH = SOUND_EVENTS.register("entity.paper_tiger_death", () -> SoundEvent.createVariableRangeEvent(PaperKiteManor.resourceLocation("entity.paper_tiger_death")));
    RegistryObject<SoundEvent> LOUD_BUTTON_PRESSED = SOUND_EVENTS.register("block.loud_button_pressed", () -> SoundEvent.createVariableRangeEvent(PaperKiteManor.resourceLocation("block.loud_button_pressed")));
    RegistryObject<SoundEvent> COCKTAIL_SHAKING = SOUND_EVENTS.register("block.cocktail_shaking", () -> SoundEvent.createVariableRangeEvent(PaperKiteManor.resourceLocation("block.cocktail_shaking")));
    RegistryObject<SoundEvent> ORGAN = SOUND_EVENTS.register("block.organ", () -> SoundEvent.createVariableRangeEvent(PaperKiteManor.resourceLocation("block.organ")));
}
