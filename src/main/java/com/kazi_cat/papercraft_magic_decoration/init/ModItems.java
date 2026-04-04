package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.item.AmethystScissorsItem;
import com.kazi_cat.papercraft_magic_decoration.item.DrinkBlockItem;
import com.kazi_cat.papercraft_magic_decoration.item.GeoBlockItem;
import com.kazi_cat.papercraft_magic_decoration.item.PackOfGuangSItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModItems {
    DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PaperKiteManor.MOD_ID);

    RegistryObject<Item> BLAZE_WHISKEY = ITEMS.register("blaze_whiskey",
            () -> new DrinkBlockItem(ModBlocks.BLAZE_WHISKEY.get(), DrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 6000, 1), 1)
                    .build()));

    RegistryObject<Item> FERRY_WHISKEY = ITEMS.register("ferry_whiskey",
            () -> new DrinkBlockItem(ModBlocks.FERRY_WHISKEY.get(), DrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 6000, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 6000, 1), 1)
                    .build()));

    RegistryObject<Item> FLY_WHISKEY = ITEMS.register("fly_whiskey",
            () -> new DrinkBlockItem(ModBlocks.FLY_WHISKEY.get(), DrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 2400, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.LEVITATION, 1200, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 1400, 1), 1)
                    .build()));

    RegistryObject<Item> LAND_NO1 = ITEMS.register("land_no1",
            () -> new DrinkBlockItem(ModBlocks.LAND_NO1.get(), DrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 120, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 6000, 3), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 6000, 2), 1)
                    .build()));

    RegistryObject<Item> LUCKY_CACTUS = ITEMS.register("lucky_cactus",
            () -> new DrinkBlockItem(ModBlocks.LUCKY_CACTUS.get(), DrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.LUCK, 6000, 3), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.GLOWING, 6000, 1), 1)
                    .build()));

    RegistryObject<Item> POISON_RUM = ITEMS.register("poison_rum",
            () -> new DrinkBlockItem(ModBlocks.POISON_RUM.get(), DrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 120, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.WITHER, 120, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 3), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.POISON, 600, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 1), 1)
                    .build()));

    RegistryObject<Item> BLOODY_MARY = ITEMS.register("bloody_mary",
            () -> new DrinkBlockItem(ModBlocks.BLOODY_MARY.get(), DrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 120, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.HEAL, 1, 2), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 6000, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 1), 1)
                    .build()));

    RegistryObject<Item> DIPLOMATICO_COFFEE = ITEMS.register("diplomatico_coffee",
            () -> new DrinkBlockItem(ModBlocks.DIPLOMATICO_COFFEE.get(), DrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600, 2), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0), 1)
                    .build()));

    RegistryObject<Item> DEVIL_MARGARITA = ITEMS.register("devil_margarita",
            () -> new DrinkBlockItem(ModBlocks.DEVIL_MARGARITA.get(), DrinkBlockItem.defaultFood.get().build()));

    RegistryObject<Item> NOCTURNAL_CAT_COFFEE = ITEMS.register("nocturnal_cat_coffee",
            () -> new DrinkBlockItem(ModBlocks.NOCTURNAL_CAT_COFFEE.get(), DrinkBlockItem.defaultFood.get().build()));

    RegistryObject<Item> GOLD_MEDAL_COFFEE = ITEMS.register("gold_medal_coffee",
            () -> new DrinkBlockItem(ModBlocks.GOLD_MEDAL_COFFEE.get(), DrinkBlockItem.defaultFood.get().build()));

    RegistryObject<Item> GUANG_S = ITEMS.register("guang_s",
            () -> new DrinkBlockItem(ModBlocks.GUANG_S.get(), DrinkBlockItem.defaultFood.get().build()));

    RegistryObject<Item> PACK_OF_GUANG_S = ITEMS.register("pack_of_guang_s", PackOfGuangSItem::new);

    // 大瓶酒
    RegistryObject<Item> BOTTLE_OF_BLAZE_WHISKEY = ITEMS.register("bottle_of_blaze_whiskey",
            () -> new BlockItem(ModBlocks.BOTTLE_OF_BLAZE_WHISKEY.get(), new Item.Properties().stacksTo(16)));

    RegistryObject<Item> BOTTLE_OF_FERRY_WHISKEY = ITEMS.register("bottle_of_ferry_whiskey",
            () -> new BlockItem(ModBlocks.BOTTLE_OF_FERRY_WHISKEY.get(), new Item.Properties().stacksTo(16)));

    RegistryObject<Item> BOTTLE_OF_FLY_WHISKEY = ITEMS.register("bottle_of_fly_whiskey",
            () -> new BlockItem(ModBlocks.BOTTLE_OF_FLY_WHISKEY.get(), new Item.Properties().stacksTo(16)));

    RegistryObject<Item> BOTTLE_OF_LAND_NO1 = ITEMS.register("bottle_of_land_no1",
            () -> new BlockItem(ModBlocks.BOTTLE_OF_LAND_NO1.get(), new Item.Properties().stacksTo(16)));

    RegistryObject<Item> BOTTLE_OF_LUCKY_CACTUS = ITEMS.register("bottle_of_lucky_cactus",
            () -> new BlockItem(ModBlocks.BOTTLE_OF_LUCKY_CACTUS.get(), new Item.Properties().stacksTo(16)));

    RegistryObject<Item> BOTTLE_OF_POISON_RUM = ITEMS.register("bottle_of_poison_rum",
            () -> new BlockItem(ModBlocks.BOTTLE_OF_POISON_RUM.get(), new Item.Properties().stacksTo(16)));

    // 紫水晶剪刀
    RegistryObject<Item> AMETHYST_SCISSORS = ITEMS.register("amethyst_scissors", AmethystScissorsItem::new);

    // 纸块
    RegistryObject<Item> WHITE_PAPER_BLOCK = ITEMS.register("white_paper_block", () -> new BlockItem(ModBlocks.WHITE_PAPER_BLOCK.get(), new Item.Properties()));
    RegistryObject<Item> BLUE_PAPER_BLOCK = ITEMS.register("blue_paper_block", () -> new BlockItem(ModBlocks.BLUE_PAPER_BLOCK.get(), new Item.Properties()));
    RegistryObject<Item> BLACK_PAPER_BLOCK = ITEMS.register("black_paper_block", () -> new BlockItem(ModBlocks.BLACK_PAPER_BLOCK.get(), new Item.Properties()));
    RegistryObject<Item> RED_PAPER_BLOCK = ITEMS.register("red_paper_block", () -> new BlockItem(ModBlocks.RED_PAPER_BLOCK.get(), new Item.Properties()));
    RegistryObject<Item> YELLOW_PAPER_BLOCK = ITEMS.register("yellow_paper_block", () -> new BlockItem(ModBlocks.YELLOW_PAPER_BLOCK.get(), new Item.Properties()));
    RegistryObject<Item> DEWY_MEMBRANE_BLOCK = ITEMS.register("dewy_membrane_block", () -> new BlockItem(ModBlocks.DEWY_MEMBRANE_BLOCK.get(), new Item.Properties()));
    RegistryObject<Item> COTTON_SERGE_BLOCK = ITEMS.register("cotton_serge_block", () -> new BlockItem(ModBlocks.COTTON_SERGE_BLOCK.get(), new Item.Properties()));

    // 纸
    RegistryObject<Item> WHITE_PAPER = ITEMS.register("white_paper", () -> new Item(new Item.Properties()));
    RegistryObject<Item> BLUE_PAPER = ITEMS.register("blue_paper", () -> new Item(new Item.Properties()));
    RegistryObject<Item> BLACK_PAPER = ITEMS.register("black_paper", () -> new Item(new Item.Properties()));
    RegistryObject<Item> RED_PAPER = ITEMS.register("red_paper", () -> new Item(new Item.Properties()));
    RegistryObject<Item> YELLOW_PAPER = ITEMS.register("yellow_paper", () -> new Item(new Item.Properties()));
    RegistryObject<Item> DEWY_MEMBRANE = ITEMS.register("dewy_membrane", () -> new Item(new Item.Properties()));
    RegistryObject<Item> COTTON_SERGE = ITEMS.register("cotton_serge", () -> new Item(new Item.Properties()));

    // 剪纸台
    RegistryObject<Item> PAPER_CUTTING_TABLE = ITEMS.register("paper_cutting_table", () -> new GeoBlockItem(ModBlocks.PAPER_CUTTING_TABLE.get(), new Item.Properties()));

    // 铜酒保
    RegistryObject<Item> COPPER_BARTENDER = ITEMS.register("copper_bartender", () -> new GeoBlockItem(ModBlocks.COPPER_BARTENDER.get(), new Item.Properties()));
}
