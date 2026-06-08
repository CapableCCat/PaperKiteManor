package com.kazi_cat.papercraft_magic_decoration.datagen.sound;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class SoundDefinitionsGenerator extends SoundDefinitionsProvider {
    public SoundDefinitionsGenerator(PackOutput output, ExistingFileHelper helper) {
        super(output, PaperKiteManor.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
    }

    protected static SoundDefinition.Sound sound(final String name) {
        return sound(new ResourceLocation(PaperKiteManor.MOD_ID, name));
    }

    protected static String subtitle(String subtitle) {
        return "subtitles.%s.%s".formatted(PaperKiteManor.MOD_ID, subtitle);
    }
}
