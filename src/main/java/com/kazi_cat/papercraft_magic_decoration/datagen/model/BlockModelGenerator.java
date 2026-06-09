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
        cubeAll("sausage_mace_weapon", modLoc("block/sausage_mace_weapon_break"));
        cubeAll("raw_sausage_mace_weapon", modLoc("block/raw_sausage_mace_weapon_break"));
        cubeAll("manga_meat", modLoc("block/manga_meat_break"));
        cubeAll("raw_manga_meat", modLoc("block/raw_manga_meat_break"));
        cubeAll("white_paper_block", modLoc("block/white_paper_block"));
        cubeAll("blue_paper_block", modLoc("block/blue_paper_block"));
        cubeAll("black_paper_block", modLoc("block/black_paper_block"));
        cubeAll("red_paper_block", modLoc("block/red_paper_block"));
        cubeAll("yellow_paper_block", modLoc("block/yellow_paper_block"));
        cubeAll("cotton_serge_block", modLoc("block/cotton_serge_block"));
    }
}