package com.kazi_cat.papercraft_magic_decoration.compat.ponder.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.resources.ResourceLocation;

public class ModPonderPlugin implements PonderPlugin {
    @Override
    public String getModId() {
        return PaperKiteManor.MOD_ID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        ModPonderScreen.register(helper);
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {

    }

    public static void init() {
        PonderIndex.addPlugin(new ModPonderPlugin());
    }
}
