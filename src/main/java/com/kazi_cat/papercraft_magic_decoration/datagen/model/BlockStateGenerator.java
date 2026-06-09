package com.kazi_cat.papercraft_magic_decoration.datagen.model;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.api.block.SmeltableBlock;
import com.kazi_cat.papercraft_magic_decoration.block.base.MultipartBlock;
import com.kazi_cat.papercraft_magic_decoration.block.decoration.VariantDecorationBlock;
import com.kazi_cat.papercraft_magic_decoration.block.drink.BoxedDrinkBlock;
import com.kazi_cat.papercraft_magic_decoration.block.drink.DrinkBlock;
import com.kazi_cat.papercraft_magic_decoration.block.smeltable.ChunkySalmonBlock;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
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
        drink(ModBlocks.DIONYSUS.get(), "dionysus");
        drink(ModBlocks.KALEIDOSCOPE_WHISKEY_SOUR.get(), "kaleidoscope_whiskey_sour");
        drink(ModBlocks.LONG_ISLAND_POPSICLE_TEA.get(), "long_island_popsicle_tea");
        drink(ModBlocks.NOCTURNAL_CAT_COFFEE.get(), "nocturnal_cat_coffee");
        drink(ModBlocks.GOLD_MEDAL_COFFEE.get(), "gold_medal_coffee");
        drink(ModBlocks.WHITE_RABBIT_MOCHA.get(), "white_rabbit_mocha");
        boxedDrink(ModBlocks.GUANG_S.get(), "guang_s");
        smeltable(ModBlocks.SAUSAGE_MACE_WEAPON_BLOCK.get(), "sausage_mace_weapon");
        smeltable(ModBlocks.MANGA_MEAT.get(), "manga_meat");
        multipartSmeltable(ModBlocks.SALMON_HEAD.get(), "salmon_head");
        multipartSmeltable(ModBlocks.CHUNKY_SALMON.get(), "chunky_salmon", ChunkySalmonBlock.VARIANT);
        multipartSmeltable(ModBlocks.MONSTER_STEAK.get(), "monster_steak");
        variant(ModBlocks.TRAY_BLOCK.get(), "tray", ((VariantDecorationBlock) ModBlocks.TRAY_BLOCK.get()).getVariantProperty());
        horizontalBlock(ModBlocks.BUCKET_OF_FRIED_CHICKEN.get(), new ModelFile.UncheckedModelFile(modLoc("block/bucket_of_fried_chicken")));
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
