package com.kazi_cat.papercraft_magic_decoration.compat.jade;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.block.chocolate.ChocolateInMoldBlock;
import com.kazi_cat.papercraft_magic_decoration.block.smeltable.*;
import com.kazi_cat.papercraft_magic_decoration.block.utility.MochaPotBlock;
import com.kazi_cat.papercraft_magic_decoration.block.utility.PaperCuttingTableBlock;
import com.kazi_cat.papercraft_magic_decoration.block.utility.SporesCollectionPlateBlock;
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
    public static final ResourceLocation SMELTABLE_BLOCK = PaperKiteManor.modLoc("smeltable_block");
    public static final ResourceLocation PAPERCUTTING_TABLE = PaperKiteManor.modLoc("papercutting_table");
    public static final ResourceLocation MOCHA_POT = PaperKiteManor.modLoc("mocha_pot");
    public static final ResourceLocation KAZI_LUCKY_CAT = PaperKiteManor.modLoc("kazi_lucky_cat");
    public static final ResourceLocation SPORES_COLLECTION_PLATE = PaperKiteManor.modLoc("spores_collection_plate");
    public static final ResourceLocation COPPER_BARTENDER = PaperKiteManor.modLoc("copper_bartender");
    public static final ResourceLocation CHOCOLATE_IN_MOLD = PaperKiteManor.modLoc("chocolate_in_mold");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerItemStorage(KaziLuckyCatComponentProvider.INSTANCE, KaziLuckyCatBlockEntity.class);
        registration.registerItemStorage(CopperBartenderComponentProvider.INSTANCE, CopperBartenderBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(SmeltableBlockComponent.INSTANCE, ChunkySalmonBlock.class);
        registration.registerBlockComponent(SmeltableBlockComponent.INSTANCE, MangaMeatBlock.class);
        registration.registerBlockComponent(SmeltableBlockComponent.INSTANCE, MonsterSteakBlock.class);
        registration.registerBlockComponent(SmeltableBlockComponent.INSTANCE, SausageMaceWeaponBlock.class);
        registration.registerBlockComponent(SmeltableBlockComponent.INSTANCE, TwoByOneSmeltableBlock.class);
        registration.registerBlockComponent(PaperCuttingTableComponentProvider.INSTANCE, PaperCuttingTableBlock.class);
        registration.registerBlockComponent(MochaPotComponentProvider.INSTANCE, MochaPotBlock.class);
        registration.registerBlockComponent(SporesCollectionPlateComponentProvider.INSTANCE, SporesCollectionPlateBlock.class);
        registration.registerBlockComponent(ChocolateInMoldComponentProvider.INSTANCE, ChocolateInMoldBlock.class);

        registration.registerBlockIcon(SmeltableBlockComponent.INSTANCE, ChunkySalmonBlock.class);
        registration.registerBlockIcon(SmeltableBlockComponent.INSTANCE, MangaMeatBlock.class);
        registration.registerBlockIcon(SmeltableBlockComponent.INSTANCE, MonsterSteakBlock.class);
        registration.registerBlockIcon(SmeltableBlockComponent.INSTANCE, SausageMaceWeaponBlock.class);
        registration.registerBlockIcon(SmeltableBlockComponent.INSTANCE, TwoByOneSmeltableBlock.class);

        registration.registerItemStorageClient(KaziLuckyCatComponentProvider.INSTANCE);
        registration.registerItemStorageClient(CopperBartenderComponentProvider.INSTANCE);
    }
}
