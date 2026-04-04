package com.kazi_cat.papercraft_magic_decoration.datagen.lootable;

import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
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
