package com.kazi_cat.papercraft_magic_decoration.datagen.loot_table;

import com.kazi_cat.papercraft_magic_decoration.api.block.SmeltableBlock;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BlockLootTables extends BlockLootSubProvider {
    public final Set<Block> knownBlocks = new HashSet<>();

    public BlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        dropSmeltable(ModBlocks.SAUSAGE_MACE_WEAPON_BLOCK.get(), List.of(ModItems.RAW_SAUSAGE_MACE_WEAPON.get()), List.of(ModItems.SAUSAGE_MACE_WEAPON.get()));
        dropSmeltable(ModBlocks.MANGA_MEAT.get(), List.of(ModItems.RAW_MANGA_MEAT.get()), List.of(ModItems.MANGA_MEAT.get()));
        dropSmeltable(ModBlocks.SALMON_HEAD.get(), List.of(Items.SALMON), List.of(ModItems.SMOKED_SALMON_HEAD.get()));
        dropSmeltable(ModBlocks.CHUNKY_SALMON.get(), List.of(ModItems.CHUNKY_SALMON.get()), List.of(ModItems.CHUNKY_SMOKED_SALMON.get()));
        dropSmeltable(ModBlocks.MONSTER_STEAK.get(), List.of(ModItems.MONSTER_STEAK.get()), List.of(ModItems.LARGE_STEAK.get()));
    }

    public void dropSmeltable(Block block, List<ItemLike> rawLoot, List<ItemLike> cookedLoot) {
        ConstantValue exactly = ConstantValue.exactly(1);
        StatePropertiesPredicate.Builder cooked = StatePropertiesPredicate.Builder.properties().hasProperty(SmeltableBlock.COOKED, true);
        LootItemCondition.Builder builder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(cooked);

        LootTable.Builder lootTable = LootTable.lootTable();
        for (int i = 0; i < cookedLoot.size(); i++) {
            ItemLike cookedItem = cookedLoot.get(i);
            LootPool.Builder rolls = LootPool.lootPool().setRolls(exactly).when(ExplosionCondition.survivesExplosion());
            if (i < rawLoot.size()) {
                ItemLike rawItem = rawLoot.get(i);
                rolls.add(LootItem.lootTableItem(cookedItem).when(builder).otherwise(LootItem.lootTableItem(rawItem)));
            } else {
                rolls.add(LootItem.lootTableItem(cookedItem).when(builder).otherwise(EmptyLootItem.emptyItem()));
            }
            lootTable.withPool(rolls);
        }

        if (cookedLoot.size() < rawLoot.size()) {
            for (int i = cookedLoot.size(); i < rawLoot.size(); i++) {
                ItemLike rawItem = rawLoot.get(i);
                LootPool.Builder rolls = LootPool.lootPool().setRolls(exactly).when(ExplosionCondition.survivesExplosion());
                rolls.add(EmptyLootItem.emptyItem().when(builder).otherwise(LootItem.lootTableItem(rawItem)));
                lootTable.withPool(rolls);
            }
        }

        this.add(block, lootTable);
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
