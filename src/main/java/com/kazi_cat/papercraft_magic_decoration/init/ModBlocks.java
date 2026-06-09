package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.block.decoration.PickableDecorationBlock;
import com.kazi_cat.papercraft_magic_decoration.block.decoration.VariantDecorationBlock;
import com.kazi_cat.papercraft_magic_decoration.block.drink.BottleDrinkBlock;
import com.kazi_cat.papercraft_magic_decoration.block.drink.BoxedDrinkBlock;
import com.kazi_cat.papercraft_magic_decoration.block.drink.DrinkBlock;
import com.kazi_cat.papercraft_magic_decoration.block.smeltable.*;
import com.kazi_cat.papercraft_magic_decoration.blockentity.AnimatedSmeltableBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.blockentity.DrinkBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.blockentity.SmeltableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collections;
import java.util.List;

@SuppressWarnings({"DataFlowIssue"})
public interface ModBlocks {
    DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PaperKiteManor.MOD_ID);
    DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, PaperKiteManor.MOD_ID);

    // 可烤制食物
    RegistryObject<Block> SAUSAGE_MACE_WEAPON_BLOCK = BLOCKS.register("sausage_mace_weapon", () -> new SausageMaceWeaponBlock(
            BlockBehaviour.Properties.of()
                    .sound(SoundType.SHROOMLIGHT)
                    .strength(1f, 4f)
                    .noOcclusion()));

    RegistryObject<Block> MANGA_MEAT = BLOCKS.register("manga_meat", () -> new MangaMeatBlock(
            BlockBehaviour.Properties.of()
                    .sound(SoundType.SHROOMLIGHT)
                    .strength(1f, 4f)
                    .noOcclusion()));

    RegistryObject<Block> SALMON_HEAD = BLOCKS.register("salmon_head", () -> new TwoByOneSmeltableBlock(
            BlockBehaviour.Properties.of()
                    .sound(SoundType.SHROOMLIGHT)
                    .strength(1f, 4f)
                    .noOcclusion(),
            Block.box(0, 0, 0, 16, 20, 16),
            Block.box(0, 0, 0, 16, 20, 16)));

    RegistryObject<Block> CHUNKY_SALMON = BLOCKS.register("chunky_salmon", () -> new ChunkySalmonBlock(
            BlockBehaviour.Properties.of()
                    .sound(SoundType.SHROOMLIGHT)
                    .strength(1f, 4f)
                    .noOcclusion()));

    RegistryObject<Block> MONSTER_STEAK = BLOCKS.register("monster_steak", () -> new MonsterSteakBlock(
            BlockBehaviour.Properties.of()
                    .sound(SoundType.SHROOMLIGHT)
                    .strength(1f, 4f)
                    .noOcclusion()));

    RegistryObject<BlockEntityType<SmeltableBlockEntity>> SMELTABLE_BE = BLOCK_ENTITIES.register(
            "smeltable_block", () -> BlockEntityType.Builder
                    .of(SmeltableBlockEntity::new,
                            SALMON_HEAD.get(),
                            CHUNKY_SALMON.get(),
                            MONSTER_STEAK.get()
                    ).build(null));

    RegistryObject<BlockEntityType<AnimatedSmeltableBlockEntity>> ANIMATED_SMELTABLE_BE = BLOCK_ENTITIES.register(
            "animated_smeltable_block", () -> BlockEntityType.Builder
                    .of(AnimatedSmeltableBlockEntity::new,
                            SAUSAGE_MACE_WEAPON_BLOCK.get(),
                            MANGA_MEAT.get()
                    ).build(null));

    // 炸鸡桶
    RegistryObject<Block> BUCKET_OF_FRIED_CHICKEN = BLOCKS.register("bucket_of_fried_chicken", () -> new PickableDecorationBlock(
            BlockBehaviour.Properties.of()
                    .sound(SoundType.SNOW)
                    .strength(1f, 4f)
                    .noOcclusion(),
            () -> List.of(new ItemStack(ModItems.FRIED_CHICKEN_LEG.get(), 4)),
            Block.box(2, 0, 2, 14, 14, 14)));

    // 饮品方块
    RegistryObject<Block> BLAZE_WHISKEY = BLOCKS.register("blaze_whiskey", () -> new DrinkBlock(3,
            Block.box(4, 0, 4, 12, 9, 12),
            Shapes.or(
                    Block.box(1, 0, 1, 15, 1, 15),
                    Block.box(2, 1, 2, 9, 10, 9),
                    Block.box(8.5, 1, 8.5, 13.5, 10, 13.5)
            ),
            Shapes.or(
                    Block.box(1, 0, 1, 15, 1, 15),
                    Block.box(5, 1, 1.5, 11.5, 10, 8),
                    Block.box(1.5, 1, 8, 8, 10, 14),
                    Block.box(9, 1, 9, 14, 10, 14)
            )));

    RegistryObject<Block> FERRY_WHISKEY = BLOCKS.register("ferry_whiskey", () -> new DrinkBlock(1,
            Block.box(4, 0, 4, 12, 10, 12)));

    RegistryObject<Block> FLY_WHISKEY = BLOCKS.register("fly_whiskey", () -> new DrinkBlock(1,
            Block.box(4, 0, 4, 12, 10, 12)));

    RegistryObject<Block> LAND_NO1 = BLOCKS.register("land_no1", () -> new DrinkBlock(4,
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)));

    RegistryObject<Block> LUCKY_CACTUS = BLOCKS.register("lucky_cactus", () -> new DrinkBlock(3,
            Block.box(4, 0, 4, 12, 10, 12),
            Shapes.or(
                    Block.box(1, 0, 1, 15, 1, 15),
                    Block.box(2, 1, 2, 9, 10, 9),
                    Block.box(8.5, 1, 8.5, 13.5, 10, 13.5)
            ),
            Shapes.or(
                    Block.box(1, 0, 1, 15, 1, 15),
                    Block.box(2, 1, 2, 14, 10, 14)
            )));

    RegistryObject<Block> POISON_RUM = BLOCKS.register("poison_rum", () -> new DrinkBlock(1,
            Block.box(4, 0, 4, 12, 10, 12)));

    RegistryObject<Block> BLOODY_MARY = BLOCKS.register("bloody_mary", () -> new DrinkBlock(1,
            Block.box(4, 0, 4, 12, 13, 12)));

    RegistryObject<Block> DIPLOMATICO_COFFEE = BLOCKS.register("diplomatico_coffee", () -> new DrinkBlock(1,
            Block.box(4, 0, 4, 12, 10, 12)));

    RegistryObject<Block> DEVIL_MARGARITA = BLOCKS.register("devil_margarita", () -> new DrinkBlock(1,
            Block.box(4, 0, 4, 12, 10, 12)));

    RegistryObject<Block> DIONYSUS = BLOCKS.register("dionysus", () -> new DrinkBlock(1,
            Block.box(4, 0, 4, 12, 10, 12)));

    RegistryObject<Block> KALEIDOSCOPE_WHISKEY_SOUR = BLOCKS.register("kaleidoscope_whiskey_sour", () -> new DrinkBlock(1,
            Block.box(4, 0, 4, 12, 8.5, 12)));

    RegistryObject<Block> LONG_ISLAND_POPSICLE_TEA = BLOCKS.register("long_island_popsicle_tea", () -> new DrinkBlock(1,
            Block.box(4, 0, 4, 12, 8.5, 12)));

    RegistryObject<Block> NOCTURNAL_CAT_COFFEE = BLOCKS.register("nocturnal_cat_coffee", () -> new DrinkBlock(
                    BlockBehaviour.Properties.of()
                            .noOcclusion()
                            .instabreak()
                            .sound(SoundType.WOOD)
                            .pushReaction(PushReaction.DESTROY),
            1, Block.box(4, 0, 4, 12, 6, 12)));

    RegistryObject<Block> GOLD_MEDAL_COFFEE = BLOCKS.register("gold_medal_coffee", () -> new DrinkBlock(
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .instabreak()
                    .sound(SoundType.WOOD)
                    .pushReaction(PushReaction.DESTROY),
            1, Block.box(4, 0, 4, 12, 6, 12)));

    RegistryObject<Block> WHITE_RABBIT_MOCHA = BLOCKS.register("white_rabbit_mocha", () -> new DrinkBlock(
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .instabreak()
                    .sound(SoundType.WOOD)
                    .pushReaction(PushReaction.DESTROY),
            1, Block.box(4, 0, 4, 12, 6, 12)));

    RegistryObject<Block> GUANG_S = BLOCKS.register("guang_s", () -> new BoxedDrinkBlock(
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .instabreak()
                    .pushReaction(PushReaction.DESTROY)
                    .sound(SoundType.LANTERN), 4,
            Block.box(5, 0, 5, 11, 11, 11),
            Block.box(2, 0, 5, 14, 11, 11),
            Shapes.or(
                    Block.box(2, 0, 9, 14, 11, 15),
                    Block.box(5, 0, 2, 11, 11, 15)
            ),
            Block.box(1, 0, 1, 15, 11, 15)));

    RegistryObject<BlockEntityType<DrinkBlockEntity>> DRINK_BE = BLOCK_ENTITIES.register(
            "drink", () -> BlockEntityType.Builder
                    .of(DrinkBlockEntity::new,
                            BLAZE_WHISKEY.get(),
                            FERRY_WHISKEY.get(),
                            FLY_WHISKEY.get(),
                            LAND_NO1.get(),
                            LUCKY_CACTUS.get(),
                            POISON_RUM.get(),
                            BLOODY_MARY.get(),
                            DIPLOMATICO_COFFEE.get(),
                            DEVIL_MARGARITA.get(),
                            DIONYSUS.get(),
                            KALEIDOSCOPE_WHISKEY_SOUR.get(),
                            LONG_ISLAND_POPSICLE_TEA.get(),
                            NOCTURNAL_CAT_COFFEE.get(),
                            GOLD_MEDAL_COFFEE.get(),
                            WHITE_RABBIT_MOCHA.get(),
                            GUANG_S.get()
                    ).build(null));

    // 大瓶酒方块
    RegistryObject<Block> BOTTLE_OF_BLAZE_WHISKEY = BLOCKS.register("bottle_of_blaze_whiskey", () -> new BottleDrinkBlock(
            () -> Collections.singletonList(new ItemStack(ModItems.BLAZE_WHISKEY.get(), 4)),
            Block.box(2, 0, 2, 14, 22, 14)));

    RegistryObject<Block> BOTTLE_OF_FERRY_WHISKEY = BLOCKS.register("bottle_of_ferry_whiskey", () -> new BottleDrinkBlock(
            () -> Collections.singletonList(new ItemStack(ModItems.FERRY_WHISKEY.get(), 4)),
            Block.box(2, 0, 2, 14, 20, 14)));

    RegistryObject<Block> BOTTLE_OF_FLY_WHISKEY = BLOCKS.register("bottle_of_fly_whiskey", () -> new BottleDrinkBlock(
            () -> Collections.singletonList(new ItemStack(ModItems.FLY_WHISKEY.get(), 4)),
            Block.box(2, 0, 2, 14, 20, 14)));

    RegistryObject<Block> BOTTLE_OF_LAND_NO1 = BLOCKS.register("bottle_of_land_no1", () -> new BottleDrinkBlock(
            () -> Collections.singletonList(new ItemStack(ModItems.LAND_NO1.get(), 4)),
            Block.box(2, 0, 2, 14, 20, 14)));

    RegistryObject<Block> BOTTLE_OF_LUCKY_CACTUS = BLOCKS.register("bottle_of_lucky_cactus", () -> new BottleDrinkBlock(
            () -> Collections.singletonList(new ItemStack(ModItems.LUCKY_CACTUS.get(), 4)),
            Block.box(2, 0, 2, 14, 20, 14)));

    RegistryObject<Block> BOTTLE_OF_POISON_RUM = BLOCKS.register("bottle_of_poison_rum", () -> new BottleDrinkBlock(
            () -> Collections.singletonList(new ItemStack(ModItems.POISON_RUM.get(), 4)),
            Block.box(2, 0, 2, 14, 20, 14)));

    // 纸块
    RegistryObject<Block> WHITE_PAPER_BLOCK = BLOCKS.register("white_paper_block", () -> new Block(
            BlockBehaviour.Properties.of()
                    .instabreak()
                    .ignitedByLava()
                    .sound(SoundType.METAL)
                    .mapColor(MapColor.SNOW)
                    .instrument(NoteBlockInstrument.BASS)));

    RegistryObject<Block> BLUE_PAPER_BLOCK = BLOCKS.register("blue_paper_block", () -> new Block(
            BlockBehaviour.Properties.of()
                    .ignitedByLava()
                    .instrument(NoteBlockInstrument.BASS)
                    .mapColor(MapColor.COLOR_BLUE)
                    .sound(SoundType.WOOD)
                    .instabreak()));

    RegistryObject<Block> BLACK_PAPER_BLOCK = BLOCKS.register("black_paper_block", () -> new Block(
            BlockBehaviour.Properties.of()
                    .ignitedByLava()
                    .instrument(NoteBlockInstrument.BASS)
                    .mapColor(MapColor.COLOR_BLACK)
                    .sound(SoundType.LILY_PAD)
                    .instabreak()));

    RegistryObject<Block> RED_PAPER_BLOCK = BLOCKS.register("red_paper_block", () -> new Block(
            BlockBehaviour.Properties.of()
                    .ignitedByLava()
                    .instrument(NoteBlockInstrument.BASS)
                    .mapColor(MapColor.COLOR_RED)
                    .sound(SoundType.AMETHYST)
                    .instabreak()
                    .lightLevel(s -> 6)
                    .hasPostProcess((bs, br, bp) -> true)
                    .emissiveRendering((bs, br, bp) -> true)));

    RegistryObject<Block> YELLOW_PAPER_BLOCK = BLOCKS.register("yellow_paper_block", () -> new Block(
            BlockBehaviour.Properties.of()
                    .ignitedByLava()
                    .instrument(NoteBlockInstrument.BASS)
                    .mapColor(MapColor.COLOR_YELLOW)
                    .sound(SoundType.SOUL_SAND)
                    .instabreak()
                    .lightLevel((s) -> 10)));

    RegistryObject<Block> DEWY_MEMBRANE_BLOCK = BLOCKS.register("dewy_membrane_block", () -> new GlassBlock(
            BlockBehaviour.Properties.of()
                    .instrument(NoteBlockInstrument.HAT)
                    .mapColor(MapColor.COLOR_GREEN)
                    .sound(SoundType.LILY_PAD)
                    .instabreak()
                    .noOcclusion()
                    .isRedstoneConductor(ModBlocks::never)
                    .isSuffocating(ModBlocks::never)
                    .isViewBlocking(ModBlocks::never)));

    RegistryObject<Block> COTTON_SERGE_BLOCK = BLOCKS.register("cotton_serge_block", () -> new Block(
            BlockBehaviour.Properties.of()
                    .ignitedByLava().
                    instrument(NoteBlockInstrument.BASS)
                    .mapColor(MapColor.SNOW)
                    .sound(SoundType.SNOW)
                    .instabreak()));

    // 装饰方块
    RegistryObject<Block> TRAY_BLOCK = BLOCKS.register("tray", () -> new VariantDecorationBlock(
            BlockBehaviour.Properties.of()
                    .strength(0.5f, 10f)
                    .noOcclusion()
                    .noLootTable(),
            Block.box(0, 0, 0, 16, 2, 16), 2));

    private static boolean never(BlockState state, BlockGetter getter, BlockPos pos) {
        return false;
    }
}
