package com.kazi_cat.papercraft_magic_decoration.datagen.tag;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.tag.TagMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TagBlock extends BlockTagsProvider {
    public TagBlock(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, PaperKiteManor.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(TagMod.HEAT_SOURCE_WITHOUT_LIT)
                .add(Blocks.MAGMA_BLOCK);

        tag(BlockTags.CROPS)
                .add(ModBlocks.COFFEE_PASTINACA_SATIVA.get());

        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.DIRT_HOLE.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.COPPER_BARTENDER.get());

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.PAPER_CUTTING_TABLE.get(), ModBlocks.COFFEE_PASTINACA_SATIVA_CORE.get(),
                        ModBlocks.COFFEE_PASTINACA_SATIVA_RIM.get());
    }
}
