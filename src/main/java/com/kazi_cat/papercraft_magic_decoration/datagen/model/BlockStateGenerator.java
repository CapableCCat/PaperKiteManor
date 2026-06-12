package com.kazi_cat.papercraft_magic_decoration.datagen.model;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.api.block.SmeltableBlock;
import com.kazi_cat.papercraft_magic_decoration.block.base.MultipartBlock;
import com.kazi_cat.papercraft_magic_decoration.block.chocolate.ChocolateInMoldBlock;
import com.kazi_cat.papercraft_magic_decoration.block.chocolate.OversizedBoxOfChocolatesBlock;
import com.kazi_cat.papercraft_magic_decoration.block.decoration.VariantDecorationBlock;
import com.kazi_cat.papercraft_magic_decoration.block.drink.BoxedDrinkBlock;
import com.kazi_cat.papercraft_magic_decoration.block.drink.DrinkBlock;
import com.kazi_cat.papercraft_magic_decoration.block.misc.UmbrellaCashewBlock;
import com.kazi_cat.papercraft_magic_decoration.block.smeltable.ChunkySalmonBlock;
import com.kazi_cat.papercraft_magic_decoration.block.utility.CopperStillBlock;
import com.kazi_cat.papercraft_magic_decoration.block.utility.MochaPotBlock;
import com.kazi_cat.papercraft_magic_decoration.block.utility.SporesCollectionPlateBlock;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.registry.DrinkRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockStateGenerator extends BlockStateProvider {
    public BlockStateGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, PaperKiteManor.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.WHITE_PAPER_BLOCK.get());
        simpleBlock(ModBlocks.BLUE_PAPER_BLOCK.get());
        simpleBlock(ModBlocks.BLACK_PAPER_BLOCK.get());
        simpleBlock(ModBlocks.RED_PAPER_BLOCK.get());
        simpleBlock(ModBlocks.YELLOW_PAPER_BLOCK.get());
        simpleBlock(ModBlocks.COTTON_SERGE_BLOCK.get());
        simpleBlock(ModBlocks.WINE_AROMA_RED_WALLPAPER_WALL.get());
        simpleBlock(ModBlocks.WINE_AROMA_BLUE_WALLPAPER_WALL.get());
        simpleBlock(ModBlocks.UNDERGROUND_WALLPAPER_WALL.get());
        simpleBlock(ModBlocks.RUSTIC_BLUE_WALLPAPER_WALL.get());
        simpleBlock(ModBlocks.DEWY_MEMBRANE_BLOCK.get(), new ModelFile.UncheckedModelFile(modLoc("block/dewy_membrane_block")));
        simpleBlock(ModBlocks.DIRT_HOLE.get(), new ModelFile.UncheckedModelFile(modLoc("block/dirt_hole")));
        simpleBlock(ModBlocks.COFFEE_PASTINACA_SATIVA_CORE.get(), new ModelFile.UncheckedModelFile(modLoc("block/coffee_pastinaca_sativa_core")));
        simpleBlock(ModBlocks.COFFEE_PASTINACA_SATIVA_FLOWERS.get(), new ModelFile.UncheckedModelFile(modLoc("block/coffee_pastinaca_sativa_flowers")));
        simpleBlock(ModBlocks.PALM_TREE_TOP.get(), new ModelFile.UncheckedModelFile(modLoc("block/palm_tree_top")));
        simpleBlock(ModBlocks.PALM_TREE_TRUNK_TOP.get(), new ModelFile.UncheckedModelFile(modLoc("block/palm_tree_trunk_top")));
        simpleBlock(ModBlocks.PALM_TREE_TRUNK.get(), new ModelFile.UncheckedModelFile(modLoc("block/palm_tree_trunk")));
        simpleBlock(ModBlocks.ROUGH_PALM_TREE_TRUNK.get(), new ModelFile.UncheckedModelFile(modLoc("block/rough_palm_tree_trunk")));
        simpleBlock(ModBlocks.MINI_PALM_TREE.get(), new ModelFile.UncheckedModelFile(modLoc("block/mini_palm_tree")));
        simpleBlock(ModBlocks.CANOPY_TREE_DROOPING_ROOT.get(), new ModelFile.UncheckedModelFile(modLoc("block/canopy_tree_drooping_root")));
        simpleBlock(ModBlocks.CANOPY_TREE_FERN.get(), new ModelFile.UncheckedModelFile(modLoc("block/canopy_tree_fern")));
        simpleBlock(ModBlocks.CANOPY_TREE_FOLIAGE.get(), new ModelFile.UncheckedModelFile(modLoc("block/canopy_tree_foliage")));
        simpleBlock(ModBlocks.CANOPY_TREE_MUSHROOM.get(), new ModelFile.UncheckedModelFile(modLoc("block/canopy_tree_mushroom")));
        simpleBlock(ModBlocks.MINI_CANOPY_TREE.get(), new ModelFile.UncheckedModelFile(modLoc("block/mini_canopy_tree")));
        simpleBlock(ModBlocks.BLACK_AND_WHITE_CHECKER_BOARD_TILE.get(), new ModelFile.UncheckedModelFile(modLoc("block/black_and_white_checker_board_tile")));
        simpleBlock(ModBlocks.BLUE_AND_WHITE_CHECKER_BOARD_TILE.get(), new ModelFile.UncheckedModelFile(modLoc("block/blue_and_white_checker_board_tile")));
        simpleBlock(ModBlocks.EMERALD_BLUE_EARTH_TILE.get(), new ModelFile.UncheckedModelFile(modLoc("block/emerald_blue_earth_tile")));
        simpleBlock(ModBlocks.FOAM_BOX_WITH_DIRT.get(), new ModelFile.UncheckedModelFile(modLoc("block/foam_box_with_dirt")));

        crop(ModBlocks.COFFEE_PASTINACA_SATIVA, "coffee_pastinaca_sativa");

        smeltable(ModBlocks.SAUSAGE_MACE_WEAPON_BLOCK.get(), "sausage_mace_weapon");
        smeltable(ModBlocks.MANGA_MEAT.get(), "manga_meat");

        multipartSmeltable(ModBlocks.SALMON_HEAD.get(), "salmon_head");
        multipartSmeltable(ModBlocks.CHUNKY_SALMON.get(), "chunky_salmon", ChunkySalmonBlock.VARIANT);
        multipartSmeltable(ModBlocks.MONSTER_STEAK.get(), "monster_steak");

        variant(ModBlocks.TRAY_BLOCK.get(), "tray", ((VariantDecorationBlock) ModBlocks.TRAY_BLOCK.get()).getVariantProperty());
        variant(ModBlocks.KNITTED_LEOPARD_RUG.get(), "knitted_leopard_rug", ((VariantDecorationBlock) ModBlocks.KNITTED_LEOPARD_RUG.get()).getVariantProperty());

        multipart(ModBlocks.COFFEE_PASTINACA_SATIVA_RIM.get(), "coffee_pastinaca_sativa_rim");
        multipart(ModBlocks.ROSES_IN_WATER_BOTTLE.get(), "roses_in_water_bottle");
        multipart(ModBlocks.CUPBOARD.get(), "cupboard");
        multipart(ModBlocks.FIREPLACE_DECORATION.get(), "fireplace_decoration");

        horizontalBlock(ModBlocks.WOODWORKING_TABLE.get(), new ModelFile.UncheckedModelFile(modLoc("block/woodworking_table")));
        horizontalBlock(ModBlocks.LONG_STORAGE_TABLE.get(), new ModelFile.UncheckedModelFile(modLoc("block/long_storage_table")));
        horizontalBlock(ModBlocks.EDGED_CHALKBOARD.get(), new ModelFile.UncheckedModelFile(modLoc("block/edged_chalkboard")));
        horizontalBlock(ModBlocks.GIFT_FROM_KAZI_MANOR.get(), new ModelFile.UncheckedModelFile(modLoc("block/gift_from_kazi_manor")));
        horizontalBlock(ModBlocks.KEY_UNDER_THE_LAKE.get(), new ModelFile.UncheckedModelFile(modLoc("block/key_under_the_lake")));
        horizontalBlock(ModBlocks.LOW_CABINET_WITH_TABLECLOTH.get(), new ModelFile.UncheckedModelFile(modLoc("block/low_cabinet_with_tablecloth")));
        horizontalBlock(ModBlocks.WOODEN_BARREL_BOOKSHELF.get(), new ModelFile.UncheckedModelFile(modLoc("block/wooden_barrel_bookshelf")));
        horizontalBlock(ModBlocks.UNDERGROUND_DOOR_FRAMES.get(), new ModelFile.UncheckedModelFile(modLoc("block/underground_door_frames")));
        horizontalBlock(ModBlocks.UNDERGROUND_PANELLING.get(), new ModelFile.UncheckedModelFile(modLoc("block/underground_panelling")));
        horizontalBlock(ModBlocks.RUSTIC_PANELLING.get(), new ModelFile.UncheckedModelFile(modLoc("block/rustic_panelling")));
        horizontalBlock(ModBlocks.CANOPY_TREE_TRUNK.get(), new ModelFile.UncheckedModelFile(modLoc("block/canopy_tree_trunk")));
        horizontalBlock(ModBlocks.PALM_TREE_CROWN.get(), new ModelFile.UncheckedModelFile(modLoc("block/palm_tree_crown")));
        horizontalBlock(ModBlocks.COFFEE_PASTINACA_SATIVA_FRUITING_STEM.get(), new ModelFile.UncheckedModelFile(modLoc("block/coffee_pastinaca_sativa_fruiting_stem")));
        horizontalBlock(ModBlocks.KAZI_LUCKY_CAT.get(), new ModelFile.UncheckedModelFile(modLoc("block/kazi_lucky_cat")));
        horizontalBlock(ModBlocks.PAPER_CUTTING_TABLE.get(), new ModelFile.UncheckedModelFile(modLoc("block/paper_cutting_table")));
        horizontalBlock(ModBlocks.COPPER_BARTENDER.get(), new ModelFile.UncheckedModelFile(modLoc("block/copper_bartender")));
        horizontalBlock(ModBlocks.BUCKET_OF_FRIED_CHICKEN.get(), new ModelFile.UncheckedModelFile(modLoc("block/bucket_of_fried_chicken")));
        horizontalBlock(ModBlocks.BOTTLE_OF_BLAZE_WHISKEY.get(), new ModelFile.UncheckedModelFile(modLoc("block/bottle_of_blaze_whiskey")));
        horizontalBlock(ModBlocks.BOTTLE_OF_FERRY_WHISKEY.get(), new ModelFile.UncheckedModelFile(modLoc("block/bottle_of_ferry_whiskey")));
        horizontalBlock(ModBlocks.BOTTLE_OF_FLY_WHISKEY.get(), new ModelFile.UncheckedModelFile(modLoc("block/bottle_of_fly_whiskey")));
        horizontalBlock(ModBlocks.BOTTLE_OF_LAND_NO1.get(), new ModelFile.UncheckedModelFile(modLoc("block/bottle_of_land_no1")));
        horizontalBlock(ModBlocks.BOTTLE_OF_LUCKY_CACTUS.get(), new ModelFile.UncheckedModelFile(modLoc("block/bottle_of_lucky_cactus")));
        horizontalBlock(ModBlocks.BOTTLE_OF_POISON_RUM.get(), new ModelFile.UncheckedModelFile(modLoc("block/bottle_of_poison_rum")));
        horizontalBlock(ModBlocks.TRUFFLE_CHOCOLATE.get(), new ModelFile.UncheckedModelFile(modLoc("block/truffle_chocolate")));
        horizontalBlock(ModBlocks.MILK_CHOCOLATE.get(), new ModelFile.UncheckedModelFile(modLoc("block/milk_chocolate")));
        horizontalBlock(ModBlocks.PRALINE_CHOCOLATE.get(), new ModelFile.UncheckedModelFile(modLoc("block/praline_chocolate")));
        horizontalBlock(ModBlocks.TRUFFLE_CHOCOLATE_IN_MOLD.get(), state -> state.getValue(ChocolateInMoldBlock.MELTED) ? new ModelFile.UncheckedModelFile(modLoc("block/melted_dark_cocoa_in_mold")) : new ModelFile.UncheckedModelFile(modLoc("block/truffle_chocolate_in_mold")));
        horizontalBlock(ModBlocks.MILK_CHOCOLATE_IN_MOLD.get(), state -> state.getValue(ChocolateInMoldBlock.MELTED) ? new ModelFile.UncheckedModelFile(modLoc("block/melted_milk_cocoa_in_mold")) : new ModelFile.UncheckedModelFile(modLoc("block/milk_chocolate_in_mold")));
        horizontalBlock(ModBlocks.PRALINE_CHOCOLATE_IN_MOLD.get(), state -> state.getValue(ChocolateInMoldBlock.MELTED) ? new ModelFile.UncheckedModelFile(modLoc("block/melted_praline_cocoa_in_mold")) : new ModelFile.UncheckedModelFile(modLoc("block/praline_chocolate_in_mold")));
        horizontalBlock(ModBlocks.OVERSIZED_BOX_OF_CHOCOLATES.get(), state -> {
            boolean opened = state.getValue(OversizedBoxOfChocolatesBlock.OPENED);
            int content = state.getValue(OversizedBoxOfChocolatesBlock.CONTENT);
            int part = state.getValue(((MultipartBlock) ModBlocks.OVERSIZED_BOX_OF_CHOCOLATES.get()).getPartProperty());
            String name;
            if (opened) {
                name = switch (content) {
                    case 0 -> "empty";
                    case 1 -> "truffle";
                    case 2 -> "milk";
                    case 3 -> "praline";
                    default -> "";
                };
            } else {
                name = "unopened";
            }
            ResourceLocation file = modLoc("block/oversized_box_of_chocolates/%s_%d".formatted(name, part));
            return new ModelFile.UncheckedModelFile(file);
        });
        horizontalBlock(ModBlocks.COPPER_STILL.get(), state -> {
            int part = state.getValue(((MultipartBlock) ModBlocks.COPPER_STILL.get()).getPartProperty());
            int status = state.getValue(CopperStillBlock.STATUS);
            ResourceLocation file = modLoc("block/%s/part%d_status%d".formatted("copper_still", part, status));
            return new ModelFile.UncheckedModelFile(file);
        });
        horizontalBlock(ModBlocks.MOCHA_POT.get(), state -> {
            boolean boiled = state.getValue(MochaPotBlock.BOILED);
            ResourceLocation file = modLoc("block/mocha_pot%s".formatted(boiled ? "_boiled" : ""));
            return new ModelFile.UncheckedModelFile(file);
        });
        horizontalBlock(ModBlocks.UMBRELLA_CASHEW.get(), state -> {
            boolean mature = state.getValue(UmbrellaCashewBlock.MATURE);
            ResourceLocation file = modLoc("block/umbrella_cashew%s".formatted(mature ? "_mature" : ""));
            return new ModelFile.UncheckedModelFile(file);
        });

        getVariantBuilder(ModBlocks.SPORES_COLLECTION_PLATE.get()).forAllStates(state -> {
            boolean filled = state.getValue(SporesCollectionPlateBlock.FILLED);
            ResourceLocation file = modLoc("block/spores_collection_plate%s".formatted(filled ? "_filled" : ""));
            return ConfiguredModel.builder().modelFile(new ModelFile.UncheckedModelFile(file)).build();
        });

        axisBlock((RotatedPillarBlock) ModBlocks.CANOPY_TREE_LIMB.get(), new ModelFile.UncheckedModelFile(modLoc("block/canopy_tree_limb")), new ModelFile.UncheckedModelFile(modLoc("block/canopy_tree_limb_horizontal")));

        buttonBlock((ButtonBlock) ModBlocks.LOUD_BUTTON.get(), new ModelFile.UncheckedModelFile(modLoc("block/loud_button")), new ModelFile.UncheckedModelFile(modLoc("block/loud_button")));

        DrinkRegistry.DRINK_DATA_MAP.forEach((key, data) -> {
            Block block = ForgeRegistries.BLOCKS.getValue(key);
            if (block != null) {
                switch (data.getBlockType()) {
                    case SIMPLE -> drink(block, key.getPath());
                    case BOXED -> boxedDrink(block, key.getPath());
                }
            }
        });
    }

    public void drink(Block block, String name) {
        if (block instanceof DrinkBlock drink) {
            ResourceLocation air = new ResourceLocation("minecraft:air");
            horizontalBlock(block, state -> {
                int count = state.getValue(drink.getCountProperty());
                int x = state.getValue(DrinkBlock.X_OFFSET);
                int z = state.getValue(DrinkBlock.Z_OFFSET);
                ResourceLocation file = count == 0 ? air : modLoc("block/drink/%s/count%d_%d_%d".formatted(name, count, x, z));
                return new ModelFile.UncheckedModelFile(file);
            });
        }
    }

    public void boxedDrink(Block block, String name) {
        if (block instanceof BoxedDrinkBlock drink) {
            ResourceLocation air = new ResourceLocation("minecraft:air");
            horizontalBlock(block, state -> {
                int count = state.getValue(drink.getCountProperty());
                int x = state.getValue(DrinkBlock.X_OFFSET);
                int z = state.getValue(DrinkBlock.Z_OFFSET);
                boolean boxed = state.getValue(BoxedDrinkBlock.BOXED);
                ResourceLocation file = count == 0 ? air : modLoc("block/drink/%s/count%d_%d_%d%s".formatted(name, count, x, z, boxed ? "_boxed" : ""));
                return new ModelFile.UncheckedModelFile(file);
            });
        }
    }

    public void crop(RegistryObject<Block> block, String name) {
        getVariantBuilder(block.get()).forAllStates(state -> {
            int age = state.getValue(CropBlock.AGE);
            ResourceLocation file = modLoc("block/crop/%s/age%d".formatted(name, age));
            return ConfiguredModel.builder()
                    .modelFile(new ModelFile.UncheckedModelFile(file))
                    .build();
        });
    }

    public void variant(Block block, String name, IntegerProperty property) {
        horizontalBlock(block, state -> {
            int part = state.getValue(property);
            ResourceLocation file = modLoc("block/%s_%s".formatted(name, part));
            return new ModelFile.UncheckedModelFile(file);
        });
    }

    public void multipart(Block block, String name) {
        if (!(block instanceof MultipartBlock multipart)) {
            return;
        }
       variant(block, name, multipart.getPartProperty());
    }

    public void smeltable(Block block, String name) {
        horizontalBlock(block, state -> {
            boolean cooked = state.getValue(SmeltableBlock.COOKED);
            ResourceLocation file = modLoc("block/%s%s".formatted(cooked ? "" : "raw_", name));
            return new ModelFile.UncheckedModelFile(file);
        });
    }

    public void multipartSmeltable(Block block, String name, IntegerProperty property) {
        horizontalBlock(block, state -> {
            int part = state.getValue(property);
            boolean cooked = state.getValue(SmeltableBlock.COOKED);
            ResourceLocation file = modLoc("block/%s%s_%s".formatted(cooked ? "" : "raw_", name, part));
            return new ModelFile.UncheckedModelFile(file);
        });
    }

    public void multipartSmeltable(Block block, String name) {
        if (!(block instanceof MultipartBlock multipart)) {
            return;
        }
        multipartSmeltable(block, name, multipart.getPartProperty());
    }
}
