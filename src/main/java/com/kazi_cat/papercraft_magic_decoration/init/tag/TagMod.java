package com.kazi_cat.papercraft_magic_decoration.init.tag;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface TagMod {
    TagKey<Block> HEAT_SOURCE_WITHOUT_LIT = blockTag("heat_source_without_lit");

    static TagKey<Item> itemTag(String name) {
        return TagKey.create(Registries.ITEM, PaperKiteManor.resourceLocation(name));
    }

    static TagKey<Block> blockTag(String name) {
        return TagKey.create(Registries.BLOCK, PaperKiteManor.resourceLocation(name));
    }

    static TagKey<EntityType<?>> entityTag(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, PaperKiteManor.resourceLocation(name));
    }

    static TagKey<DamageType> damageTypeTag(String name) {
        return TagKey.create(Registries.DAMAGE_TYPE, PaperKiteManor.resourceLocation(name));
    }
}
