package com.kazi_cat.papercraft_magic_decoration.datagen.model;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.api.block.SmeltableBlock;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStateGenerator extends BlockStateProvider {
    public BlockStateGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, PaperKiteManor.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        smeltable(ModBlocks.SAUSAGE_MACE_WEAPON_BLOCK.get(), "sausage_mace_weapon");
    }

    public void smeltable(Block block, String name) {
        horizontalBlock(block, state -> {
            boolean cooked = state.getValue(SmeltableBlock.COOKED);
            ResourceLocation file = modLoc("block/%s%s".formatted(cooked ? "" : "raw_", name));
            return new ModelFile.UncheckedModelFile(file);
        });
    }
}
