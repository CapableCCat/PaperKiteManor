package com.kazi_cat.papercraft_magic_decoration.compat.ponder.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.compat.ponder.scenes.CopperStillScenes;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class ModPonderScreen {
    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        helper.forComponents(new ResourceLocation(PaperKiteManor.MOD_ID, "copper_still"))
                .addStoryBoard("copper_still/introduction", CopperStillScenes::introduction);
    }
}
