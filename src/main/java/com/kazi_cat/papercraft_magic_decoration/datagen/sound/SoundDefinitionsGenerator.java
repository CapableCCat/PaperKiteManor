package com.kazi_cat.papercraft_magic_decoration.datagen.sound;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.init.ModSounds;
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
        SoundDefinition aoaoIdle = definition().subtitle(subtitle("entity.aoao_idle"))
                .with(sound("entity/aoao_idle_01"))
                .with(sound("entity/aoao_idle_02"))
                .with(sound("entity/aoao_idle_03"))
                .with(sound("entity/aoao_idle_04"))
                .with(sound("entity/aoao_idle_05"))
                .with(sound("entity/aoao_idle_06"))
                .with(sound("entity/aoao_idle_07"));
        this.add(ModSounds.AOAO_IDLE, aoaoIdle);

        SoundDefinition aoaoHurt = definition().subtitle(subtitle("entity.aoao_hurt"))
                .with(sound("entity/aoao_hurt_01"))
                .with(sound("entity/aoao_hurt_02"))
                .with(sound("entity/aoao_hurt_03"))
                .with(sound("entity/aoao_hurt_04"));
        this.add(ModSounds.AOAO_HURT, aoaoHurt);

        SoundDefinition aoaoDeath = definition().subtitle(subtitle("entity.aoao_death"))
                .with(sound("entity/aoao_death_01"))
                .with(sound("entity/aoao_death_02"));
        this.add(ModSounds.AOAO_DEATH, aoaoDeath);

        SoundDefinition paperTigerIdle = definition().subtitle("entity.paper_tiger_idle")
                .with(sound("entity/paper_tiger_idle_01"))
                .with(sound("entity/paper_tiger_idle_02"))
                .with(sound("entity/paper_tiger_idle_03"))
                .with(sound("entity/paper_tiger_idle_04"));
        this.add(ModSounds.PAPER_TIGER_IDLE, paperTigerIdle);

        SoundDefinition paperTigerDeath = definition().subtitle("entity.paper_tiger_death")
                .with(sound("entity/paper_tiger_death"));
        this.add(ModSounds.PAPER_TIGER_DEATH, paperTigerDeath);

        SoundDefinition loudButtonPressed = definition().subtitle("block.loud_button_pressed")
                .with(sound("block/loud_button"));
        this.add(ModSounds.LOUD_BUTTON_PRESSED, loudButtonPressed);

        SoundDefinition cocktailShaking = definition().subtitle("block.cocktail_shaking")
                .with(sound("block/cocktail_shaking"));
        this.add(ModSounds.COCKTAIL_SHAKING, cocktailShaking);

        SoundDefinition organ = definition().subtitle("block.organ")
                .with(sound("block/organ"));
        this.add(ModSounds.ORGAN, organ);

        SoundDefinition kaziStar = definition().subtitle("kazi_star")
                .with(sound("kazi_star"));
        this.add(ModSounds.KAZI_STAR, kaziStar);
    }

    protected static SoundDefinition.Sound sound(final String name) {
        return sound(new ResourceLocation(PaperKiteManor.MOD_ID, name));
    }

    protected static String subtitle(String subtitle) {
        return "subtitles.%s.%s".formatted(PaperKiteManor.MOD_ID, subtitle);
    }
}
