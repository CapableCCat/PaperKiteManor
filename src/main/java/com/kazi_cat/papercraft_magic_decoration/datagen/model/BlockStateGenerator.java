package com.kazi_cat.papercraft_magic_decoration.datagen.model;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.block.BoxedDrinkBlock;
import com.kazi_cat.papercraft_magic_decoration.block.DistillerBlock;
import com.kazi_cat.papercraft_magic_decoration.block.DrinkBlock;
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

        simpleBlock(ModBlocks.WHITE_PAPER_BLOCK.get());
        simpleBlock(ModBlocks.BLUE_PAPER_BLOCK.get());
        simpleBlock(ModBlocks.BLACK_PAPER_BLOCK.get());
        simpleBlock(ModBlocks.RED_PAPER_BLOCK.get());
        simpleBlock(ModBlocks.YELLOW_PAPER_BLOCK.get());
        simpleBlock(ModBlocks.DEWY_MEMBRANE_BLOCK.get());
        simpleBlock(ModBlocks.COTTON_SERGE_BLOCK.get());

        distiller(ModBlocks.COPPER_STILL.get(), "copper_still");
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
}
