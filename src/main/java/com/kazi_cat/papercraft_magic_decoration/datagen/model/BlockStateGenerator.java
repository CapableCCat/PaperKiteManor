package com.kazi_cat.papercraft_magic_decoration.datagen.model;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.api.block.SmeltableBlock;
import com.kazi_cat.papercraft_magic_decoration.block.base.MultipartBlock;
import com.kazi_cat.papercraft_magic_decoration.block.chocolate.ChocolateInMoldBlock;
import com.kazi_cat.papercraft_magic_decoration.block.chocolate.OversizedBoxOfChocolatesBlock;
import com.kazi_cat.papercraft_magic_decoration.block.decoration.VariantDecorationBlock;
import com.kazi_cat.papercraft_magic_decoration.block.drink.BoxedDrinkBlock;
import com.kazi_cat.papercraft_magic_decoration.block.drink.DrinkBlock;
import com.kazi_cat.papercraft_magic_decoration.block.smeltable.ChunkySalmonBlock;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.registry.DrinkRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

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
        simpleBlock(ModBlocks.DEWY_MEMBRANE_BLOCK.get(), new ModelFile.UncheckedModelFile(modLoc("block/dewy_membrane_block")));
        simpleBlock(ModBlocks.DIRT_HOLE.get(), new ModelFile.UncheckedModelFile(modLoc("block/dirt_hole")));

        smeltable(ModBlocks.SAUSAGE_MACE_WEAPON_BLOCK.get(), "sausage_mace_weapon");
        smeltable(ModBlocks.MANGA_MEAT.get(), "manga_meat");

        multipartSmeltable(ModBlocks.SALMON_HEAD.get(), "salmon_head");
        multipartSmeltable(ModBlocks.CHUNKY_SALMON.get(), "chunky_salmon", ChunkySalmonBlock.VARIANT);
        multipartSmeltable(ModBlocks.MONSTER_STEAK.get(), "monster_steak");

        variant(ModBlocks.TRAY_BLOCK.get(), "tray", ((VariantDecorationBlock) ModBlocks.TRAY_BLOCK.get()).getVariantProperty());

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

    public void variant(Block block, String name, IntegerProperty property) {
        horizontalBlock(block, state -> {
            int part = state.getValue(property);
            ResourceLocation file = modLoc("block/%s_%s".formatted(name, part));
            return new ModelFile.UncheckedModelFile(file);
        });
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
