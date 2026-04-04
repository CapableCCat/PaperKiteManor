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
        cubeAll("white_paper_block", modLoc("block/white_paper_block"));
        cubeAll("blue_paper_block", modLoc("block/blue_paper_block"));
        cubeAll("black_paper_block", modLoc("block/black_paper_block"));
        cubeAll("red_paper_block", modLoc("block/red_paper_block"));
        cubeAll("yellow_paper_block", modLoc("block/yellow_paper_block"));
        cubeAll("dewy_membrane_block", modLoc("block/dewy_membrane_block"));
        cubeAll("cotton_serge_block", modLoc("block/cotton_serge_block"));
        cubeAll("paper_cutting_table", modLoc("block/paper_cutting_table"));
        cubeAll("copper_bartender", modLoc("block/copper_bartender"));
        cubeAll("wine_aroma_red_wallpaper_wall", modLoc("block/wine_aroma_red_wallpaper_wall"));
        cubeAll("wine_aroma_blue_wallpaper_wall", modLoc("block/wine_aroma_blue_wallpaper_wall"));
        cubeAll("underground_wallpaper_wall", modLoc("block/underground_wallpaper_wall"));
        cubeAll("rustic_blue_wallpaper_wall", modLoc("block/rustic_blue_wallpaper_wall"));
        cubeAll("edged_chalkboard", modLoc("block/edged_chalkboard"));

        cubeBottomTop("coffee_pastinaca_sativa_core",
                modLoc("block/coffee_pastinaca_sativa_side"),
                modLoc("block/coffee_pastinaca_sativa_bottom"),
                modLoc("block/coffee_pastinaca_sativa_top"));

        cubeColumn("canopy_tree_limb",
                modLoc("block/canopy_tree_limb_side"),
                modLoc("block/canopy_tree_limb_top"));

        cubeColumnHorizontal("canopy_tree_limb_horizontal",
                modLoc("block/canopy_tree_limb_side"),
                modLoc("block/canopy_tree_limb_top"));
    }
}
