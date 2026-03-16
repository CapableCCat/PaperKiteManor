package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.block.food.ChunkySalmonBlock;
import com.kazi_cat.papercraft_magic_decoration.block.food.TwoByOneSmeltableBlock;
import com.kazi_cat.papercraft_magic_decoration.item.GeoBlockItem;
import com.kazi_cat.papercraft_magic_decoration.item.GlassDrinkBlockItem;
import com.kazi_cat.papercraft_magic_decoration.item.RenamedBlockItem;
import com.kazi_cat.papercraft_magic_decoration.item.TwoByThreeStructureBlockItem;
import com.kazi_cat.papercraft_magic_decoration.item.food.MultiEatGeoBlockItem;
import com.kazi_cat.papercraft_magic_decoration.item.tool.AmethystScissorsItem;
import com.kazi_cat.papercraft_magic_decoration.item.tool.GardenTrowelItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public interface ModItems {
    DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PaperKiteManor.MOD_ID);

    // 杯装酒
    RegistryObject<Item> GLASS_OF_BLAZE_WHISKEY = ITEMS.register("glass_of_blaze_whiskey",
            () -> new GlassDrinkBlockItem(ModBlocks.GLASS_OF_BLAZE_WHISKEY.get(), GlassDrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 6000, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 6000, 1), 1)
                    .build()));

    RegistryObject<Item> GLASS_OF_FERRY_WHISKEY = ITEMS.register("glass_of_ferry_whiskey",
            () -> new GlassDrinkBlockItem(ModBlocks.GLASS_OF_FERRY_WHISKEY.get(), GlassDrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 6000, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 6000, 1), 1)
                    .build()));

    RegistryObject<Item> GLASS_OF_FLY_WHISKEY = ITEMS.register("glass_of_fly_whiskey",
            () -> new GlassDrinkBlockItem(ModBlocks.GLASS_OF_FLY_WHISKEY.get(), GlassDrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 2400, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.LEVITATION, 1200, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 1400, 1), 1)
                    .build()));

    RegistryObject<Item> GLASS_OF_LAND_NO1 = ITEMS.register("glass_of_land_no1",
            () -> new GlassDrinkBlockItem(ModBlocks.GLASS_OF_LAND_NO1.get(), GlassDrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 120, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 6000, 3), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.HEALTH_BOOST, 6000, 2), 1)
                    .build()));

    RegistryObject<Item> GLASS_OF_LUCKY_CACTUS = ITEMS.register("glass_of_lucky_cactus",
            () -> new GlassDrinkBlockItem(ModBlocks.GLASS_OF_LUCKY_CACTUS.get(), GlassDrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 400, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.LUCK, 6000, 3), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.GLOWING, 6000, 1), 1)
                    .build()));

    RegistryObject<Item> GLASS_OF_POISON_RUM = ITEMS.register("glass_of_poison_rum",
            () -> new GlassDrinkBlockItem(ModBlocks.GLASS_OF_POISON_RUM.get(), GlassDrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.BLINDNESS, 120, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.WITHER, 120, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 3), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.POISON, 600, 1), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 1), 1)
                    .build()));

    RegistryObject<Item> BLOODY_MARY = ITEMS.register("bloody_mary",
            () -> new GlassDrinkBlockItem(ModBlocks.BLOODY_MARY.get(), GlassDrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 120, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.HEAL, 1, 2), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 6000, 0), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 1), 1)
                    .build()));

    RegistryObject<Item> DIPLOMAT_COFFEE = ITEMS.register("diplomat_coffee",
            () -> new GlassDrinkBlockItem(ModBlocks.DIPLOMAT_COFFEE.get(), GlassDrinkBlockItem.defaultFood.get()
                    .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600, 2), 1)
                    .effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0), 1)
                    .build()));

    RegistryObject<Item> DEVIL_MARGARITA = ITEMS.register("devil_margarita",
            () -> new GlassDrinkBlockItem(ModBlocks.DEVIL_MARGARITA.get(), GlassDrinkBlockItem.defaultFood.get().build()));

    // 大瓶酒
    RegistryObject<Item> BLAZE_WHISKEY = ITEMS.register("blaze_whiskey",
            () -> new BlockItem(ModBlocks.BLAZE_WHISKEY.get(), new Item.Properties().stacksTo(1)));

    RegistryObject<Item> FERRY_WHISKEY = ITEMS.register("ferry_whiskey",
            () -> new BlockItem(ModBlocks.FERRY_WHISKEY.get(), new Item.Properties().stacksTo(1)));

    RegistryObject<Item> FLY_WHISKEY = ITEMS.register("fly_whiskey",
            () -> new BlockItem(ModBlocks.FLY_WHISKEY.get(), new Item.Properties().stacksTo(1)));

    RegistryObject<Item> LAND_NO1 = ITEMS.register("land_no1",
            () -> new BlockItem(ModBlocks.LAND_NO1.get(), new Item.Properties().stacksTo(1)));

    RegistryObject<Item> LUCKY_CACTUS = ITEMS.register("lucky_cactus",
            () -> new BlockItem(ModBlocks.LUCKY_CACTUS.get(), new Item.Properties().stacksTo(1)));

    RegistryObject<Item> POISON_RUM = ITEMS.register("poison_rum",
            () -> new BlockItem(ModBlocks.POISON_RUM.get(), new Item.Properties().stacksTo(1)));

    // 紫水晶剪刀
    RegistryObject<Item> AMETHYST_SCISSORS = ITEMS.register("amethyst_scissors", AmethystScissorsItem::new);

    // 园丁铲
    RegistryObject<Item> GARDEN_TROWEL = ITEMS.register("garden_trowel", () -> new GardenTrowelItem(Tiers.IRON, 0, -2.4F,
            new Item.Properties().durability(50)));

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

    // 咖啡果
    RegistryObject<Item> COFFEE_FRUIT = ITEMS.register("coffee_fruit", () -> new ItemNameBlockItem(ModBlocks.COFFEE_PASTINACA_SATIVA.get(),
            new Item.Properties()));

    // 剪纸台
    RegistryObject<Item> PAPER_CUTTING_TABLE = ITEMS.register("paper_cutting_table", () ->
            new GeoBlockItem(ModBlocks.PAPER_CUTTING_TABLE.get(), new Item.Properties(), "paper_cutting_table"));

    // 铜酒保
    RegistryObject<Item> COPPER_BARTENDER = ITEMS.register("copper_bartender", () ->
            new GeoBlockItem(ModBlocks.COPPER_BARTENDER.get(), new Item.Properties(), "copper_bartender"));

    // 烤制食物
    RegistryObject<Item> RAW_SAUSAGE_MACE_WEAPON = ITEMS.register("raw_sausage_mace_weapon", () ->
            new GeoBlockItem(ModBlocks.SAUSAGE_MACE_WEAPON_BLOCK.get(), new Item.Properties(), "raw_sausage_mace_weapon"));

    RegistryObject<Item> SAUSAGE_MACE_WEAPON = ITEMS.register("sausage_mace_weapon", () ->
            new GeoBlockItem(ModBlocks.SAUSAGE_MACE_WEAPON_BLOCK.get(), new Item.Properties(), "sausage_mace_weapon"));

    RegistryObject<Item> CUBED_SAUSAGE = ITEMS.register("cubed_sausage", () ->
            new Item(new Item.Properties().food((new FoodProperties.Builder()).nutrition(14).saturationMod(0.7f).build())));

    RegistryObject<Item> CHUNKY_SALMON = ITEMS.register("chunky_salmon", () -> new RenamedBlockItem(ModBlocks.CHUNKY_SALMON.get(),
                    new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.3f).meat().build()),
                    "chunky_salmon"));

    RegistryObject<Item> CHUNKY_SMOKED_SALMON = ITEMS.register("chunky_smoked_salmon", () -> new RenamedBlockItem(ModBlocks.CHUNKY_SALMON.get(),
                    new Item.Properties().food(new FoodProperties.Builder().nutrition(10).saturationMod(0.6f).meat().build()),
                    "chunky_smoked_salmon"));

    RegistryObject<Item> SMOKED_SALMON_HEAD = ITEMS.register("smoked_salmon_head", () -> new RenamedBlockItem(ModBlocks.SALMON_HEAD.get(),
                    new Item.Properties().food(new FoodProperties.Builder().nutrition(5).saturationMod(0.3f).meat().build()),
                    "smoked_salmon_head"));

    RegistryObject<Item> JUMBO_SALMON = ITEMS.register("jumbo_salmon", () -> new TwoByThreeStructureBlockItem(new Item.Properties(), () -> List.of(
            ModBlocks.SALMON_HEAD.get().defaultBlockState().setValue(TwoByOneSmeltableBlock.POSITION, 0),
            ModBlocks.CHUNKY_SALMON.get().defaultBlockState().setValue(ChunkySalmonBlock.VARIANT, 1),
            ModBlocks.CHUNKY_SALMON.get().defaultBlockState().setValue(ChunkySalmonBlock.VARIANT, 2),
            ModBlocks.SALMON_HEAD.get().defaultBlockState().setValue(TwoByOneSmeltableBlock.POSITION, 1),
            ModBlocks.CHUNKY_SALMON.get().defaultBlockState().setValue(ChunkySalmonBlock.VARIANT, 3),
            ModBlocks.CHUNKY_SALMON.get().defaultBlockState().setValue(ChunkySalmonBlock.VARIANT, 4)
    ), "jumbo_salmon"));

    RegistryObject<Item> MANGA_MEAT = ITEMS.register("manga_meat", () ->
            new MultiEatGeoBlockItem(ModBlocks.MANGA_MEAT.get(),
                    new Item.Properties().food(new FoodProperties.Builder().nutrition(15).saturationMod(0.6F).meat().build())
                            .durability(2).fireResistant(),
                    "manga_meat", 100));

    RegistryObject<Item> RAW_MANGA_MEAT = ITEMS.register("raw_manga_meat", () ->
            new GeoBlockItem(ModBlocks.MANGA_MEAT.get(), new Item.Properties(), "raw_manga_meat"));

    RegistryObject<Item> MONSTER_STEAK = ITEMS.register("monster_steak", () -> new RenamedBlockItem(ModBlocks.MONSTER_STEAK.get(),
            new Item.Properties().stacksTo(1), "monster_steak"));

    RegistryObject<Item> LARGE_STEAK = ITEMS.register("large_steak", () -> new Item(new Item.Properties().food((new FoodProperties.Builder()).nutrition(12).saturationMod(0.7f).meat().build())));
}
