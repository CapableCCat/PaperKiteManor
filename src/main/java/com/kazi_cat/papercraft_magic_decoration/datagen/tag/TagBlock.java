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

        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(ModBlocks.CANOPY_TREE_FERN.get(), ModBlocks.CANOPY_TREE_MUSHROOM.get());

        tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.DIRT_HOLE.get(), ModBlocks.FOAM_BOX_WITH_DIRT.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.COPPER_BARTENDER.get(), ModBlocks.COPPER_STILL.get(),
                        ModBlocks.MOCHA_POT.get(), ModBlocks.BLACK_AND_WHITE_CHECKER_BOARD_TILE.get(),
                        ModBlocks.BLUE_AND_WHITE_CHECKER_BOARD_TILE.get(), ModBlocks.EMERALD_BLUE_EARTH_TILE.get());

        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.PAPER_CUTTING_TABLE.get(), ModBlocks.COFFEE_PASTINACA_SATIVA_CORE.get(),
                        ModBlocks.COFFEE_PASTINACA_SATIVA_RIM.get(), ModBlocks.SPORES_COLLECTION_PLATE.get(),
                        ModBlocks.TRAY_BLOCK.get(), ModBlocks.PALM_TREE_TRUNK.get(),
                        ModBlocks.ROUGH_PALM_TREE_TRUNK.get(), ModBlocks.PALM_TREE_TOP.get(),
                        ModBlocks.PALM_TREE_TRUNK_TOP.get(), ModBlocks.CANOPY_TREE_LIMB.get(),
                        ModBlocks.CANOPY_TREE_DROOPING_ROOT.get(), ModBlocks.CANOPY_TREE_TRUNK.get(),
                        ModBlocks.WINE_AROMA_RED_WALLPAPER_WALL.get(), ModBlocks.WINE_AROMA_BLUE_WALLPAPER_WALL.get(),
                        ModBlocks.UNDERGROUND_WALLPAPER_WALL.get(), ModBlocks.RUSTIC_BLUE_WALLPAPER_WALL.get(),
                        ModBlocks.UNDERGROUND_PANELLING.get(), ModBlocks.RUSTIC_PANELLING.get(),
                        ModBlocks.UNDERGROUND_DOOR_FRAMES.get(), ModBlocks.STAR_EMBELLISHED_CEILING.get(),
                        ModBlocks.LOW_CABINET_WITH_TABLECLOTH.get(), ModBlocks.WOODEN_BARREL_BOOKSHELF.get(),
                        ModBlocks.WOODWORKING_TABLE.get(), ModBlocks.LONG_STORAGE_TABLE.get(),
                        ModBlocks.EDGED_CHALKBOARD.get(), ModBlocks.CUPBOARD.get(),
                        ModBlocks.FIREPLACE_DECORATION.get());
    }
}
