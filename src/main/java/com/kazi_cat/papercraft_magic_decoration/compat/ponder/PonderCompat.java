package com.kazi_cat.papercraft_magic_decoration.compat.ponder;

import com.kazi_cat.papercraft_magic_decoration.compat.ponder.init.ModPonderPlugin;
import net.minecraftforge.fml.ModList;

public class PonderCompat {
    public static final String ID = "ponder";

    public static boolean PONDER_LOADED = false;

    public static void init() {
        if (ModList.get().isLoaded(ID)) {
            PONDER_LOADED = true;
            ModPonderPlugin.init();
        }
    }
}