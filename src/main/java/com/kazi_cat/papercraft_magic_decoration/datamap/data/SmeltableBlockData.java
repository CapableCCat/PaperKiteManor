package com.kazi_cat.papercraft_magic_decoration.datamap.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

public record SmeltableBlockData(Block block, int time, int flips, int cooldown, Item ingredient, Item result) {
    private static final Codec<Item> ITEM_CODEC = ResourceLocation.CODEC.comapFlatMap(id -> {
        Item item = ForgeRegistries.ITEMS.getValue(id);
        return item != null ? DataResult.success(item) : DataResult.error(() -> "Unknown item: " + id);
    }, ForgeRegistries.ITEMS::getKey);

    private static final Codec<Block> BLOCK_CODEC = ResourceLocation.CODEC.comapFlatMap(id -> {
        Block block = ForgeRegistries.BLOCKS.getValue(id);
        return block != null ? DataResult.success(block) : DataResult.error(() -> "Unknown item: " + id);
    }, ForgeRegistries.BLOCKS::getKey);

    public static final Codec<SmeltableBlockData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BLOCK_CODEC.fieldOf("block").forGetter(SmeltableBlockData::block),
            Codec.INT.fieldOf("time").forGetter(SmeltableBlockData::time),
            Codec.INT.fieldOf("flips").forGetter(SmeltableBlockData::flips),
            Codec.INT.fieldOf("cooldown").forGetter(SmeltableBlockData::cooldown),
            ITEM_CODEC.fieldOf("ingredient").forGetter(SmeltableBlockData::ingredient),
            ITEM_CODEC.fieldOf("result").forGetter(SmeltableBlockData::result)
    ).apply(instance, SmeltableBlockData::new));
}
