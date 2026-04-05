package com.kazi_cat.papercraft_magic_decoration.datagen.model;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.block.*;
import com.kazi_cat.papercraft_magic_decoration.block.decoration.TwoByOneBlock;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class BlockStateGenerator extends BlockStateProvider {
    public BlockStateGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, PaperKiteManor.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        drink(ModBlocks.BLAZE_WHISKEY.get(), "blaze_whiskey");
        drink(ModBlocks.FERRY_WHISKEY.get(), "ferry_whiskey");
        drink(ModBlocks.FLY_WHISKEY.get(), "fly_whiskey");
        drink(ModBlocks.LAND_NO1.get(), "land_no1");
        drink(ModBlocks.LUCKY_CACTUS.get(), "lucky_cactus");
        drink(ModBlocks.POISON_RUM.get(), "poison_rum");
        drink(ModBlocks.BLOODY_MARY.get(), "bloody_mary");
        drink(ModBlocks.DEVIL_MARGARITA.get(), "devil_margarita");
        drink(ModBlocks.DIPLOMATICO_COFFEE.get(), "diplomatico_coffee");
        drink(ModBlocks.NOCTURNAL_CAT_COFFEE.get(), "nocturnal_cat_coffee");
        drink(ModBlocks.GOLD_MEDAL_COFFEE.get(), "gold_medal_coffee");

        boxedDrink(ModBlocks.GUANG_S.get(), "guang_s");

        horizontalBlock(ModBlocks.BOTTLE_OF_BLAZE_WHISKEY.get(), new ModelFile.UncheckedModelFile(modLoc("block/bottle_of_blaze_whiskey")));
        horizontalBlock(ModBlocks.BOTTLE_OF_FERRY_WHISKEY.get(), new ModelFile.UncheckedModelFile(modLoc("block/bottle_of_ferry_whiskey")));
        horizontalBlock(ModBlocks.BOTTLE_OF_FLY_WHISKEY.get(), new ModelFile.UncheckedModelFile(modLoc("block/bottle_of_fly_whiskey")));
        horizontalBlock(ModBlocks.BOTTLE_OF_LAND_NO1.get(), new ModelFile.UncheckedModelFile(modLoc("block/bottle_of_land_no1")));
        horizontalBlock(ModBlocks.BOTTLE_OF_LUCKY_CACTUS.get(), new ModelFile.UncheckedModelFile(modLoc("block/bottle_of_lucky_cactus")));
        horizontalBlock(ModBlocks.BOTTLE_OF_POISON_RUM.get(), new ModelFile.UncheckedModelFile(modLoc("block/bottle_of_poison_rum")));
        horizontalBlock(ModBlocks.PAPER_CUTTING_TABLE.get(), new ModelFile.UncheckedModelFile(modLoc("block/paper_cutting_table")));
        horizontalBlock(ModBlocks.COPPER_BARTENDER.get(), new ModelFile.UncheckedModelFile(modLoc("block/copper_bartender")));
        horizontalBlock(ModBlocks.COFFEE_PASTINACA_SATIVA_FRUITING_STEM.get(), new ModelFile.UncheckedModelFile(modLoc("block/coffee_pastinaca_sativa_fruiting_stem")));
        horizontalBlock(ModBlocks.PALM_TREE_CROWN.get(), new ModelFile.UncheckedModelFile(modLoc("block/palm_tree_crown")));
        horizontalBlock(ModBlocks.UNDERGROUND_PANELLING.get(), new ModelFile.UncheckedModelFile(modLoc("block/underground_panelling")));
        horizontalBlock(ModBlocks.CANOPY_TREE_TRUNK.get(), new ModelFile.UncheckedModelFile(modLoc("block/canopy_tree_trunk")));
        horizontalBlock(ModBlocks.LOW_CABINET_WITH_TABLECLOTH.get(), new ModelFile.UncheckedModelFile(modLoc("block/low_cabinet_with_tablecloth")));
        horizontalBlock(ModBlocks.WOODEN_BARREL_BOOKSHELF.get(), new ModelFile.UncheckedModelFile(modLoc("block/wooden_barrel_bookshelf")));
        horizontalBlock(ModBlocks.EDGED_CHALKBOARD.get(), new ModelFile.UncheckedModelFile(modLoc("block/edged_chalkboard")));

        simpleBlock(ModBlocks.WHITE_PAPER_BLOCK.get());
        simpleBlock(ModBlocks.BLUE_PAPER_BLOCK.get());
        simpleBlock(ModBlocks.BLACK_PAPER_BLOCK.get());
        simpleBlock(ModBlocks.RED_PAPER_BLOCK.get());
        simpleBlock(ModBlocks.YELLOW_PAPER_BLOCK.get());
        simpleBlock(ModBlocks.DEWY_MEMBRANE_BLOCK.get());
        simpleBlock(ModBlocks.COTTON_SERGE_BLOCK.get());
        simpleBlock(ModBlocks.WINE_AROMA_RED_WALLPAPER_WALL.get());
        simpleBlock(ModBlocks.WINE_AROMA_BLUE_WALLPAPER_WALL.get());
        simpleBlock(ModBlocks.UNDERGROUND_WALLPAPER_WALL.get());
        simpleBlock(ModBlocks.RUSTIC_BLUE_WALLPAPER_WALL.get());
        simpleBlock(ModBlocks.DIRT_HOLE.get(), new ModelFile.UncheckedModelFile(modLoc("block/dirt_hole")));
        simpleBlock(ModBlocks.COFFEE_PASTINACA_SATIVA_CORE.get(), new ModelFile.UncheckedModelFile(modLoc("block/coffee_pastinaca_sativa_core")));
        simpleBlock(ModBlocks.COFFEE_PASTINACA_SATIVA_FLOWERS.get(), new ModelFile.UncheckedModelFile(modLoc("block/coffee_pastinaca_sativa_flowers")));
        simpleBlock(ModBlocks.PALM_TREE_TOP.get(), new ModelFile.UncheckedModelFile(modLoc("block/palm_tree_top")));
        simpleBlock(ModBlocks.PALM_TREE_TRUNK_TOP.get(), new ModelFile.UncheckedModelFile(modLoc("block/palm_tree_trunk_top")));
        simpleBlock(ModBlocks.PALM_TREE_TRUNK.get(), new ModelFile.UncheckedModelFile(modLoc("block/palm_tree_trunk")));
        simpleBlock(ModBlocks.ROUGH_PALM_TREE_TRUNK.get(), new ModelFile.UncheckedModelFile(modLoc("block/rough_palm_tree_trunk")));
        simpleBlock(ModBlocks.CANOPY_TREE_DROOPING_ROOT.get(), new ModelFile.UncheckedModelFile(modLoc("block/canopy_tree_drooping_root")));
        simpleBlock(ModBlocks.CANOPY_TREE_FERN.get(), new ModelFile.UncheckedModelFile(modLoc("block/canopy_tree_fern")));
        simpleBlock(ModBlocks.CANOPY_TREE_FOLIAGE.get(), new ModelFile.UncheckedModelFile(modLoc("block/canopy_tree_foliage")));
        simpleBlock(ModBlocks.CANOPY_TREE_MUSHROOM.get(), new ModelFile.UncheckedModelFile(modLoc("block/canopy_tree_mushroom")));
        simpleBlock(ModBlocks.BLACK_AND_WHITE_CHECKER_BOARD_TILE.get(), new ModelFile.UncheckedModelFile(modLoc("block/black_and_white_checker_board_tile")));
        simpleBlock(ModBlocks.BLUE_AND_WHITE_CHECKER_BOARD_TILE.get(), new ModelFile.UncheckedModelFile(modLoc("block/blue_and_white_checker_board_tile")));

        distiller(ModBlocks.COPPER_STILL.get(), "copper_still");

        crop(ModBlocks.COFFEE_PASTINACA_SATIVA, "coffee_pastinaca_sativa");

        twoPart(ModBlocks.COFFEE_PASTINACA_SATIVA_RIM.get(), "coffee_pastinaca_sativa_rim");
        twoPart(ModBlocks.ROSES_IN_WATER_BOTTLE.get(), "roses_in_water_bottle");

        getVariantBuilder(ModBlocks.SPORES_COLLECTION_PLATE.get()).forAllStates(state -> {
            boolean filled = state.getValue(SporesCollectionPlateBlock.FILLED);
            ResourceLocation file = modLoc("block/spores_collection_plate%s".formatted(filled ? "_filled" : ""));
            return ConfiguredModel.builder()
                    .modelFile(new ModelFile.UncheckedModelFile(file))
                    .build();
        });

        axisBlock((RotatedPillarBlock) ModBlocks.CANOPY_TREE_LIMB.get(),
                new ModelFile.UncheckedModelFile(modLoc("block/canopy_tree_limb")),
                new ModelFile.UncheckedModelFile(modLoc("block/canopy_tree_limb_horizontal")));
    }

    public void drink(Block block, String name) {
        if (block instanceof DrinkBlock drink) {
            horizontalBlock(block, blockState -> {
                int count = blockState.getValue(drink.getCountProperty());
                ResourceLocation file = modLoc("block/drink/%s/count%d".formatted(name, count));
                return new ModelFile.UncheckedModelFile(file);
            });
        }
    }

    public void boxedDrink(Block block, String name) {
        if (block instanceof BoxedDrinkBlock drink) {
            horizontalBlock(block, blockState -> {
                int count = blockState.getValue(drink.getCountProperty());
                boolean boxed = blockState.getValue(BoxedDrinkBlock.BOXED);
                ResourceLocation file = modLoc("block/drink/%s/count%d%s".formatted(name, count, boxed ? "_boxed" : ""));
                return new ModelFile.UncheckedModelFile(file);
            });
        }
    }

    public void distiller(Block block, String name) {
        if (block instanceof DistillerBlock) {
            horizontalBlock(block, blockState -> {
                int part = blockState.getValue(DistillerBlock.PART);
                int status = blockState.getValue(DistillerBlock.STATUS);
                ResourceLocation file = modLoc("block/distiller/%s/part%d_status%d".formatted(name, part, status));
                return new ModelFile.UncheckedModelFile(file);
            });
        }
    }

    public void crop(RegistryObject<Block> block, String name) {
        getVariantBuilder(block.get()).forAllStates(state -> {
            int age = state.getValue(CropBlock.AGE);
            ResourceLocation file = modLoc("block/crop/%s/stage%d".formatted(name, age));
            return ConfiguredModel.builder()
                    .modelFile(new ModelFile.UncheckedModelFile(file))
                    .build();
        });
    }

    public void twoPart(Block block, String name) {
        horizontalBlock(block, blockState -> {
            int part = blockState.getValue(TwoByOneBlock.PART);
            ResourceLocation file = modLoc("block/%s%d".formatted(name, part));
            return new ModelFile.UncheckedModelFile(file);
        });
    }
}
