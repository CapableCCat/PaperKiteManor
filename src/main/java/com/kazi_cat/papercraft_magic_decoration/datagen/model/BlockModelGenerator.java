package com.kazi_cat.papercraft_magic_decoration.datagen.model;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockModelGenerator extends BlockModelProvider {
    public BlockModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PaperKiteManor.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        cubeAll("sausage_mace_weapon", modLoc("block/sausage_mace_weapon"));
        cubeAll("raw_sausage_mace_weapon", modLoc("block/raw_sausage_mace_weapon"));
        cubeAll("manga_meat", modLoc("block/manga_meat_grill"))
                .texture("particle", modLoc("block/manga_meat_particle"));
        cubeAll("raw_manga_meat", modLoc("block/raw_manga_meat_grill"));
    }
}
