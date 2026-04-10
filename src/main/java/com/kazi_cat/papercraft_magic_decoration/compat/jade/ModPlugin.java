package com.kazi_cat.papercraft_magic_decoration.compat.jade;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.block.MochaPotBlock;
import com.kazi_cat.papercraft_magic_decoration.block.PaperCuttingTableBlock;
import com.kazi_cat.papercraft_magic_decoration.block.SporesCollectionPlateBlock;
import com.kazi_cat.papercraft_magic_decoration.blockentity.CopperBartenderBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.blockentity.KaziLuckyCatBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.compat.jade.block.*;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class ModPlugin implements IWailaPlugin {
    public static final ResourceLocation PAPERCUTTING_TABLE = PaperKiteManor.resourceLocation("papercutting_table");
    public static final ResourceLocation COPPER_BARTENDER = PaperKiteManor.resourceLocation("copper_bartender");
    public static final ResourceLocation SPORES_COLLECTION_PLATE = PaperKiteManor.resourceLocation("spores_collection_plate");
    public static final ResourceLocation MOCHA_POT = PaperKiteManor.resourceLocation("mocha_pot");
    public static final ResourceLocation KAZI_LUCKY_CAT = PaperKiteManor.resourceLocation("kazi_lucky_cat");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerItemStorage(CopperBartenderComponentProvider.INSTANCE, CopperBartenderBlockEntity.class);
        registration.registerItemStorage(KaziLuckyCatComponentProvider.INSTANCE, KaziLuckyCatBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(PaperCuttingTableComponentProvider.INSTANCE, PaperCuttingTableBlock.class);
        registration.registerBlockComponent(SporesCollectionPlateComponentProvider.INSTANCE, SporesCollectionPlateBlock.class);
        registration.registerBlockComponent(MochaPotComponentProvider.INSTANCE, MochaPotBlock.class);

        registration.registerItemStorageClient(CopperBartenderComponentProvider.INSTANCE);
        registration.registerItemStorageClient(KaziLuckyCatComponentProvider.INSTANCE);
    }
}
