package com.kazi_cat.papercraft_magic_decoration.datagen.lootable;

import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.HashSet;
import java.util.Set;

public class BlockLootTables extends BlockLootSubProvider {
    public final Set<Block> knownBlocks = new HashSet<>();

    public BlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        dropSelf(ModBlocks.BLAZE_WHISKEY.get());
        dropSelf(ModBlocks.FERRY_WHISKEY.get());
        dropSelf(ModBlocks.FLY_WHISKEY.get());
        dropSelf(ModBlocks.LAND_NO1.get());
        dropSelf(ModBlocks.LUCKY_CACTUS.get());
        dropSelf(ModBlocks.POISON_RUM.get());

        dropSelf(ModBlocks.WHITE_PAPER_BLOCK.get());
        dropSelf(ModBlocks.BLUE_PAPER_BLOCK.get());
        dropSelf(ModBlocks.BLACK_PAPER_BLOCK.get());
        dropSelf(ModBlocks.RED_PAPER_BLOCK.get());
        dropSelf(ModBlocks.YELLOW_PAPER_BLOCK.get());
        dropSelf(ModBlocks.DEWY_MEMBRANE_BLOCK.get());
        dropSelf(ModBlocks.COTTON_SERGE_BLOCK.get());

        dropSelf(ModBlocks.PAPER_CUTTING_TABLE.get());
        dropSelf(ModBlocks.COPPER_BARTENDER.get());
        dropSelf(ModBlocks.COPPER_STILL.get());
        dropSelf(ModBlocks.SPORES_COLLECTION_PLATE.get());

        dropSelf(ModBlocks.UNDERGROUND_PANELLING.get());
        dropSelf(ModBlocks.ROSES_IN_WATER_BOTTLE.get());
        dropSelf(ModBlocks.STAR_EMBELLISHED_CEILING.get());
        dropSelf(ModBlocks.LOUD_BUTTON.get());
        dropSelf(ModBlocks.LOW_CABINET_WITH_TABLECLOTH.get());
        dropSelf(ModBlocks.WOODEN_BARREL_BOOKSHELF.get());
        dropSelf(ModBlocks.WOODWORKING_TABLE.get());
        dropSelf(ModBlocks.LONG_STORAGE_TABLE.get());
        dropSelf(ModBlocks.EDGED_CHALKBOARD.get());
        dropSelf(ModBlocks.CUPBOARD.get());
        dropSelf(ModBlocks.FIREPLACE_DECORATION.get());

        dropSelf(ModBlocks.CANOPY_TREE_DROOPING_ROOT.get());
        dropSelf(ModBlocks.CANOPY_TREE_FERN.get());
        dropSelf(ModBlocks.CANOPY_TREE_FOLIAGE.get());
        dropSelf(ModBlocks.CANOPY_TREE_MUSHROOM.get());
        dropSelf(ModBlocks.CANOPY_TREE_TRUNK.get());
        dropSelf(ModBlocks.CANOPY_TREE_LIMB.get());

        dropSelf(ModBlocks.WINE_AROMA_RED_WALLPAPER_WALL.get());
        dropSelf(ModBlocks.WINE_AROMA_BLUE_WALLPAPER_WALL.get());
        dropSelf(ModBlocks.UNDERGROUND_WALLPAPER_WALL.get());
        dropSelf(ModBlocks.RUSTIC_BLUE_WALLPAPER_WALL.get());
        dropSelf(ModBlocks.BLACK_AND_WHITE_CHECKER_BOARD_TILE.get());
        dropSelf(ModBlocks.BLUE_AND_WHITE_CHECKER_BOARD_TILE.get());

        dropOther(ModBlocks.DIRT_HOLE.get(), Items.DIRT);
        dropOther(ModBlocks.COFFEE_PASTINACA_SATIVA.get(), ModItems.COFFEE_FRUIT.get());
        dropOther(ModBlocks.COFFEE_PASTINACA_SATIVA_CORE.get(), ModItems.COFFEE_PASTINACA_SATIVA_TUBER.get());
        dropOther(ModBlocks.COFFEE_PASTINACA_SATIVA_RIM.get(), ModItems.COFFEE_PASTINACA_SATIVA_TUBER.get());
    }

    @Override
    public void add(Block block, LootTable.Builder builder) {
        this.knownBlocks.add(block);
        super.add(block, builder);
    }

    @Override
    public Iterable<Block> getKnownBlocks() {
        return this.knownBlocks;
    }
}
