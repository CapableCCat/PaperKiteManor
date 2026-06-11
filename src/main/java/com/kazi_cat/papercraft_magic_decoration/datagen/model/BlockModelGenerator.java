package com.kazi_cat.papercraft_magic_decoration.datagen.model;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.init.registry.DrinkRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
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
        cubeAll("paper_cutting_table", modLoc("block/paper_cutting_table"));
        cubeAll("copper_bartender", modLoc("block/copper_bartender"));
        cubeAll("kazi_lucky_cat", modLoc("block/kazi_lucky_cat"));

        DrinkRegistry.DRINK_DATA_MAP.forEach(((key, data) -> {
            for (int i = 1; i <= data.getMaxCount(); i++) {
                ResourceLocation parent = modLoc("block/drink/%s/count%d".formatted(key.getPath(), i));
                for (int x = 0; x <= 4; x++) {
                    for (int z = 0; z <= 4; z++) {
                        ResourceLocation file = modLoc("block/drink/%s/count%d_%d_%d".formatted(key.getPath(), i, x, z));
                        withExistingParent(file.toString(), parent.toString())
                                .renderType("cutout")
                                .rootTransforms()
                                .translation((x - 2) * -0.25F, 0, (z - 2) * -0.25F)
                                .end();
                        if (data.getBlockType() == DrinkRegistry.BlockType.BOXED) {
                            ResourceLocation boxedParent = modLoc("block/drink/%s/count%d_boxed".formatted(key.getPath(), i));
                            ResourceLocation boxedFile = modLoc("block/drink/%s/count%d_%d_%d_boxed".formatted(key.getPath(), i, x, z));
                            withExistingParent(boxedFile.toString(), boxedParent.toString())
                                    .renderType("cutout")
                                    .rootTransforms()
                                    .translation((x - 2) * -0.25F, 0, (z - 2) * -0.25F)
                                    .end();
                        }
                    }
                }
            }
        }));
    }
}