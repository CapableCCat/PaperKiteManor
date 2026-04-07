package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.effect.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public interface ModEffects {
    DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, PaperKiteManor.MOD_ID);

    RegistryObject<MobEffect> WINES_AROMA = EFFECTS.register("wines_aroma", () -> new WinesAromaEffect(0x8B5DF0));
    RegistryObject<MobEffect> BLOODTHIRSTY_DEVIL = EFFECTS.register("bloodthirsty_devil", () -> new BloodThirstyDevilEffect(0xCD0F32));
    RegistryObject<MobEffect> CAT_EYE = EFFECTS.register("cat_eye", () -> new CatEyeEffect(0x161231));
    RegistryObject<MobEffect> ACID_JAZZ = EFFECTS.register("acid_jazz", () -> new BaseEffect(0xFF831A));
    RegistryObject<MobEffect> SKY_TRACTION = EFFECTS.register("sky_traction", () -> new SkyTractionEffect(0x8ABBCB));
}
