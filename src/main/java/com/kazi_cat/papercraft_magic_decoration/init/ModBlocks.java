package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.block.decoration.KaziLuckyCatBlock;
import com.kazi_cat.papercraft_magic_decoration.block.decoration.PaperCuttingTableBlock;
import com.kazi_cat.papercraft_magic_decoration.block.crop.CoffeePastinacaSativaCropBlock;
import com.kazi_cat.papercraft_magic_decoration.block.crop.CoffeePastinacaSativaFruitingStemBlock;
import com.kazi_cat.papercraft_magic_decoration.block.decoration.*;
import com.kazi_cat.papercraft_magic_decoration.block.decoration.TwoByOneBlock;
import com.kazi_cat.papercraft_magic_decoration.block.drink.*;
import com.kazi_cat.papercraft_magic_decoration.block.food.*;
import com.kazi_cat.papercraft_magic_decoration.blockentity.decoration.AnimatedBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.blockentity.decoration.DirtHoleBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.blockentity.decoration.KaziLuckyCatBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.blockentity.drink.CopperBartenderBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.blockentity.drink.DistillerBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.blockentity.food.AnimatedSmeltableBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.blockentity.drink.GlassDrinkBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.blockentity.decoration.PaperCuttingTableBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.blockentity.food.SmeltableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

@SuppressWarnings({"deprecation","DataFlowIssue"})
public interface ModBlocks {
    DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PaperKiteManor.MOD_ID);
    DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, PaperKiteManor.MOD_ID);

    // 杯装酒方块
    RegistryObject<Block> GLASS_OF_BLAZE_WHISKEY = BLOCKS.register("glass_of_blaze_whiskey", GlassDrinkBlock.create().maxCount(1).shapes(
            Block.box(4, 0, 4, 12, 9, 12)
    ).offset(1, 0.25).offset(2, 0.5).build());

    RegistryObject<Block> GLASS_OF_FERRY_WHISKEY = BLOCKS.register("glass_of_ferry_whiskey", GlassDrinkBlock.create().maxCount(1).shapes(
            Block.box(4, 0, 4, 12, 10, 12)
    ).offset(1, 0.25).offset(2, 0.5).build());

    RegistryObject<Block> GLASS_OF_FLY_WHISKEY = BLOCKS.register("glass_of_fly_whiskey", GlassDrinkBlock.create().maxCount(1).shapes(
            Block.box(4, 0, 4, 12, 10, 12)
    ).offset(1, 0.25).offset(2, 0.5).build());

    RegistryObject<Block> GLASS_OF_LAND_NO1 = BLOCKS.register("glass_of_land_no1", GlassDrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).offset(1, 0.25).offset(2, 0.5).build());

    RegistryObject<Block> GLASS_OF_LUCKY_CACTUS = BLOCKS.register("glass_of_lucky_cactus", GlassDrinkBlock.create().maxCount(1).shapes(
            Block.box(4, 0, 4, 12, 10, 12)
    ).offset(1, 0.25).offset(2, 0.5).build());

    RegistryObject<Block> GLASS_OF_POISON_RUM = BLOCKS.register("glass_of_poison_rum", GlassDrinkBlock.create().maxCount(1).shapes(
            Block.box(4, 0, 4, 12, 10, 12)
    ).offset(1, 0.25).offset(2, 0.5).build());

    RegistryObject<Block> BLOODY_MARY = BLOCKS.register("bloody_mary", GlassDrinkBlock.create().maxCount(1).shapes(
            Block.box(4, 0, 4, 12, 13, 12)
    ).offset(1, 0.25).offset(2, 0.5).build());

    RegistryObject<Block> DIPLOMAT_COFFEE = BLOCKS.register("diplomat_coffee", GlassDrinkBlock.create().maxCount(1).shapes(
            Block.box(4, 0, 4, 12, 10, 12)
    ).offset(1, 0.25).offset(2, 0.5).build());

    RegistryObject<Block> DEVIL_MARGARITA = BLOCKS.register("devil_margarita", GlassDrinkBlock.create().maxCount(1).shapes(
            Block.box(4, 0, 4, 12, 10, 12)
    ).offset(1, 0.25).offset(2, 0.5).build());

    RegistryObject<Block> GUANG_S = BLOCKS.register("guang_s", new GuangSBlock.Builder().maxCount(4).shapes(
            Block.box(5, 0, 5, 11, 11, 11),
            Block.box(2, 0, 5, 14, 11, 11),
            Shapes.or(
                    Block.box(2, 0, 9, 14, 11, 15),
                    Block.box(5, 0, 2, 11, 11, 15)
            ),
            Block.box(1, 0, 1, 15, 11, 15)
    ).offset(1, 0.25).offset(2, 0.5).build(
            BlockBehaviour.Properties.of().noOcclusion().instabreak().pushReaction(PushReaction.DESTROY).sound(SoundType.LANTERN)
    ));

    // 大瓶酒方块
    RegistryObject<Block> BLAZE_WHISKEY = BLOCKS.register("blaze_whiskey", () -> new BottleDrinkBlock(
            Block.box(2, 0, 2, 14, 22, 14),
            () -> new ItemStack(ModItems.GLASS_OF_BLAZE_WHISKEY.get(), 4)
    ));

    RegistryObject<Block> FERRY_WHISKEY = BLOCKS.register("ferry_whiskey", () -> new BottleDrinkBlock(
            Block.box(2, 0, 2, 14, 20, 14),
            () -> new ItemStack(ModItems.GLASS_OF_FERRY_WHISKEY.get(), 4)
    ));

    RegistryObject<Block> FLY_WHISKEY = BLOCKS.register("fly_whiskey", () -> new BottleDrinkBlock(
            Block.box(2, 0, 2, 14, 20, 14),
            () -> new ItemStack(ModItems.GLASS_OF_FLY_WHISKEY.get(), 4)
    ));

    RegistryObject<Block> LAND_NO1 = BLOCKS.register("land_no1", () -> new BottleDrinkBlock(
            Block.box(2, 0, 2, 14, 20, 14),
            () -> new ItemStack(ModItems.GLASS_OF_LAND_NO1.get(), 4)
    ));

    RegistryObject<Block> LUCKY_CACTUS = BLOCKS.register("lucky_cactus", () -> new BottleDrinkBlock(
            Block.box(2, 0, 2, 14, 20, 14),
            () -> new ItemStack(ModItems.GLASS_OF_LUCKY_CACTUS.get(), 4)
    ));

    RegistryObject<Block> POISON_RUM = BLOCKS.register("poison_rum", () -> new BottleDrinkBlock(
            Block.box(2, 0, 2, 14, 20, 14),
            () -> new ItemStack(ModItems.GLASS_OF_POISON_RUM.get(), 4)
    ));

    // 纸块
    RegistryObject<Block> WHITE_PAPER_BLOCK = BLOCKS.register("white_paper_block", () -> new Block(BlockBehaviour.Properties.of()
            .ignitedByLava()
            .instrument(NoteBlockInstrument.BASS)
            .mapColor(MapColor.SNOW)
            .sound(SoundType.METAL)
            .instabreak())
    );

    RegistryObject<Block> BLUE_PAPER_BLOCK = BLOCKS.register("blue_paper_block", () -> new Block(BlockBehaviour.Properties.of()
            .ignitedByLava()
            .instrument(NoteBlockInstrument.BASS)
            .mapColor(MapColor.COLOR_BLUE)
            .sound(SoundType.WOOD)
            .instabreak())
    );

    RegistryObject<Block> BLACK_PAPER_BLOCK = BLOCKS.register("black_paper_block", () -> new Block(BlockBehaviour.Properties.of()
            .ignitedByLava()
            .instrument(NoteBlockInstrument.BASS)
            .mapColor(MapColor.COLOR_BLACK)
            .sound(SoundType.LILY_PAD)
            .instabreak())
    );

    RegistryObject<Block> RED_PAPER_BLOCK = BLOCKS.register("red_paper_block", () -> new Block(BlockBehaviour.Properties.of()
            .ignitedByLava()
            .instrument(NoteBlockInstrument.BASS)
            .mapColor(MapColor.COLOR_RED)
            .sound(SoundType.AMETHYST)
            .instabreak()
            .lightLevel(s -> 6)
            .hasPostProcess((bs, br, bp) -> true)
            .emissiveRendering((bs, br, bp) -> true))
    );

    RegistryObject<Block> YELLOW_PAPER_BLOCK = BLOCKS.register("yellow_paper_block", () -> new Block(BlockBehaviour.Properties.of()
            .ignitedByLava()
            .instrument(NoteBlockInstrument.BASS)
            .mapColor(MapColor.COLOR_YELLOW)
            .sound(SoundType.SOUL_SAND)
            .instabreak()
            .lightLevel((s) -> 10))
    );

    RegistryObject<Block> DEWY_MEMBRANE_BLOCK = BLOCKS.register("dewy_membrane_block", () -> new Block(BlockBehaviour.Properties.of()
            .instrument(NoteBlockInstrument.HAT)
            .mapColor(MapColor.COLOR_GREEN)
            .sound(SoundType.LILY_PAD)
            .instabreak()
            .noOcclusion()
            .isRedstoneConductor((bs, br, bp) -> false))
    );

    RegistryObject<Block> COTTON_SERGE_BLOCK = BLOCKS.register("cotton_serge_block", () -> new Block(BlockBehaviour.Properties.of()
            .ignitedByLava()
            .instrument(NoteBlockInstrument.BASS)
            .mapColor(MapColor.SNOW)
            .sound(SoundType.SNOW)
            .instabreak())
    );

    // 剪纸台方块
    RegistryObject<Block> PAPER_CUTTING_TABLE = BLOCKS.register("paper_cutting_table", PaperCuttingTableBlock::new);

    // 铜酒保方块
    RegistryObject<Block> COPPER_BARTENDER = BLOCKS.register("copper_bartender", CopperBartenderBlock::new);

    // 蒸馏器方块
    RegistryObject<Block> COPPER_STILL = BLOCKS.register("copper_still", DistillerBlock::new);

    // 可烤制方块
    RegistryObject<Block> SAUSAGE_MACE_WEAPON_BLOCK = BLOCKS.register("sausage_mace_weapon", () ->
            new AnimatedTwoByOneSmeltableBlock(
                    BlockBehaviour.Properties.of().sound(SoundType.SHROOMLIGHT).strength(1f, 4f).noOcclusion(),
                    Shapes.or(
                            Block.box(1, 1, 1, 15, 15, 15),
                            Block.box(6, 6, -16, 10, 10, 0)
                    ),
                    Block.box(1, 1, 0, 15, 15, 15),
                    100,
                    2,
                    21,
                    () -> ModItems.RAW_SAUSAGE_MACE_WEAPON.get().getDefaultInstance(),
                    () -> ModItems.SAUSAGE_MACE_WEAPON.get().getDefaultInstance()
            ));

    RegistryObject<Block> CHUNKY_SALMON = BLOCKS.register("chunky_salmon", () ->
            new ChunkySalmonBlock(
                    BlockBehaviour.Properties.of().sound(SoundType.SHROOMLIGHT).strength(1f, 4f).noOcclusion(),
                    Block.box(0, 0, 0, 16, 10, 16),
                    100,
                    0,
                    0,
                    () -> ModItems.CHUNKY_SALMON.get().getDefaultInstance(),
                    () -> ModItems.CHUNKY_SMOKED_SALMON.get().getDefaultInstance()
            ));

    RegistryObject<Block> SALMON_HEAD = BLOCKS.register("salmon_head", () ->
            new TwoByOneSmeltableBlock(
                    BlockBehaviour.Properties.of().sound(SoundType.SHROOMLIGHT).strength(1f, 4f).noOcclusion(),
                    Block.box(0, 0, 0, 16, 20, 16),
                    Block.box(0, 0, 0, 16, 20, 16),
                    100,
                    0,
                    0,
                    Items.SALMON::getDefaultInstance,
                    () -> ModItems.SMOKED_SALMON_HEAD.get().getDefaultInstance()
            ));

    RegistryObject<Block> MANGA_MEAT = BLOCKS.register("manga_meat", () ->
            new MangaMeatBlock(
                    BlockBehaviour.Properties.of().sound(SoundType.SHROOMLIGHT).strength(1f, 4f).noOcclusion(),
                    Block.box(1, 0, 0, 15, 15, 16),
                    200,
                    4,
                    16,
                    () -> new ItemStack(ModItems.RAW_MANGA_MEAT.get()),
                    () -> new ItemStack(ModItems.MANGA_MEAT.get())
            ));

    RegistryObject<Block> LARGE_STEAK = BLOCKS.register("large_steak", () ->
            new LargeSteakBlock(BlockBehaviour.Properties.of().sound(SoundType.SHROOMLIGHT).strength(1f, 4f).noOcclusion()));

    RegistryObject<Block> MONSTER_STEAK = BLOCKS.register("monster_steak", () ->
            new TwoByThreeSmeltableBlock(
                    BlockBehaviour.Properties.of().sound(SoundType.SHROOMLIGHT).strength(1f, 4f).noOcclusion(),
                    Block.box(0, 0, 0, 16, 12, 16),
                    100,
                    0,
                    0,
                    () -> new ItemStack(ModItems.MONSTER_STEAK.get()),
                    () -> ItemStack.EMPTY,
                    () -> List.of(
                            LARGE_STEAK.get().defaultBlockState().setValue(LargeSteakBlock.VARIANT, 1),
                            LARGE_STEAK.get().defaultBlockState().setValue(LargeSteakBlock.VARIANT, 2),
                            LARGE_STEAK.get().defaultBlockState().setValue(LargeSteakBlock.VARIANT, 3),
                            LARGE_STEAK.get().defaultBlockState().setValue(LargeSteakBlock.VARIANT, 4),
                            LARGE_STEAK.get().defaultBlockState().setValue(LargeSteakBlock.VARIANT, 5),
                            LARGE_STEAK.get().defaultBlockState().setValue(LargeSteakBlock.VARIANT, 6)
                    )
            ));

    RegistryObject<Block> TRAY_BLOCK = BLOCKS.register("tray", TrayBlock::new);

    RegistryObject<Block> BUCKET_OF_FRIED_CHICKEN = BLOCKS.register("bucket_of_fried_chicken", () -> new BucketOfFriedChickenBlock(
            BlockBehaviour.Properties.of().sound(SoundType.SNOW).strength(1f, 4f).noOcclusion(),
            Block.box(2, 0, 2, 14, 14, 14)));

    // 土坑方块
    RegistryObject<Block> DIRT_HOLE = BLOCKS.register("dirt_hole", DirtHoleBlock::new);

    // 作物方块
    RegistryObject<Block> COFFEE_PASTINACA_SATIVA = BLOCKS.register("coffee_pastinaca_sativa", CoffeePastinacaSativaCropBlock::new);

    RegistryObject<Block> COFFEE_PASTINACA_SATIVA_FRUITING_STEM = BLOCKS.register("coffee_pastinaca_sativa_fruiting_stem", () ->
            new CoffeePastinacaSativaFruitingStemBlock(BlockBehaviour.Properties.of()
                    .ignitedByLava()
                    .instabreak()
                    .sound(SoundType.LILY_PAD)
                    .noCollission()
                    .pushReaction(PushReaction.DESTROY)
                    .replaceable()));

    RegistryObject<Block> COFFEE_PASTINACA_SATIVA_CORE = BLOCKS.register("coffee_pastinaca_sativa_core", () ->
            new Block(BlockBehaviour.Properties.of().ignitedByLava().strength(2f, 10f).forceSolidOn()));

    RegistryObject<Block> COFFEE_PASTINACA_SATIVA_RIM = BLOCKS.register("coffee_pastinaca_sativa_rim", ()->
            new TwoByOneBlock(BlockBehaviour.Properties.of().ignitedByLava().strength(2f, 10f),
                    Block.box(0, 0, 0, 8, 16, 16),
                    Block.box(0, 0, 0, 8, 16, 8)));

    RegistryObject<Block> COFFEE_PASTINACA_SATIVA_FLOWERS = BLOCKS.register("coffee_pastinaca_sativa_flowers", () ->
            new Block(BlockBehaviour.Properties.of()
                    .ignitedByLava()
                    .instabreak()
                    .sound(SoundType.LILY_PAD)
                    .noCollission()
                    .pushReaction(PushReaction.DESTROY)
                    .replaceable()));

    // 装饰方块
    RegistryObject<Block> LOUD_BUTTON = BLOCKS.register("loud_button", LoudButtonBlock::new);

    RegistryObject<Block> LOW_CABINET_WITH_TABLECLOTH = BLOCKS.register("low_cabinet_with_tablecloth",
            () -> new SimpleAnimatedBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED).sound(SoundType.WOOD).strength(2f, 10f).noOcclusion(),
                    Block.box(0, 0, 0, 16, 15, 16)));

    RegistryObject<Block> WOODEN_BARREL_BOOKSHELF = BLOCKS.register("wooden_barrel_bookshelf",
            () -> new SimpleAnimatedBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN).sound(SoundType.WOOD).strength(2f, 10f).noOcclusion(),
                    Block.box(0, 0, 0, 14, 16, 16)));

    RegistryObject<Block> WOODWORKING_TABLE = BLOCKS.register("woodworking_table",
            () -> new OneByTwoAnimatedBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN).sound(SoundType.WOOD).strength(2f, 10f).noOcclusion(),
                    Block.box(1, 0, 0, 16, 16, 16),
                    Block.box(0, 0, 0, 15, 16, 16)));

    RegistryObject<Block> LONG_STORAGE_TABLE = BLOCKS.register("long_storage_table",
            () -> new OneByThreeAnimatedBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN).sound(SoundType.WOOD).strength(2f, 10f).noOcclusion(),
                    Block.box(1, 0, 0, 16, 15, 16),
                    Block.box(0, 0, 0, 16, 15, 16),
                    Block.box(0, 0, 0, 15, 15, 16)));

    RegistryObject<Block> EDGED_CHALKBOARD = BLOCKS.register("edged_chalkboard",
            () -> new VerticalTwoByThreeAnimatedBlock(BlockBehaviour.Properties.of()
                    .sound(SoundType.WOOD).strength(2f, 10f).noCollission().noOcclusion(),
                    Block.box(0, 0, 13, 16, 16, 16)));

    RegistryObject<Block> WINE_AROMA_RED_WALLPAPER_WALL = BLOCKS.register("wine_aroma_red_wallpaper_wall", () -> new Block(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.COLOR_RED).sound(SoundType.WOOD).strength(2f, 6f)));

    RegistryObject<Block> WINE_AROMA_BLUE_WALLPAPER_WALL = BLOCKS.register("wine_aroma_blue_wallpaper_wall", () -> new Block(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.COLOR_BLUE).sound(SoundType.WOOD).strength(2f, 6f)));

    RegistryObject<Block> BLACK_AND_WHITE_CHECKER_BOARD_TILE = BLOCKS.register("black_and_white_checker_board_tile", () -> new Block(BlockBehaviour.Properties.of()
            .instrument(NoteBlockInstrument.BASEDRUM).mapColor(MapColor.COLOR_BLACK).sound(SoundType.STONE).strength(3f, 15f).requiresCorrectToolForDrops()));

    RegistryObject<Block> BLUE_AND_WHITE_CHECKER_BOARD_TILE = BLOCKS.register("blue_and_white_checker_board_tile", () -> new Block(BlockBehaviour.Properties.of()
            .instrument(NoteBlockInstrument.BASEDRUM).mapColor(MapColor.TERRACOTTA_WHITE).sound(SoundType.STONE).strength(3f, 15f).requiresCorrectToolForDrops()));

    RegistryObject<Block> UNDERGROUND_WALLPAPER_WALL = BLOCKS.register("underground_wallpaper_wall", () -> new Block(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.COLOR_GREEN).sound(SoundType.WOOD).strength(2f, 3f)));

    RegistryObject<Block> RUSTIC_BLUE_WALLPAPER_WALL = BLOCKS.register("rustic_blue_wallpaper_wall", () -> new Block(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.COLOR_BLUE).sound(SoundType.WOOD).strength(2f, 3f)));

    RegistryObject<Block> KAZI_LUCKY_CAT = BLOCKS.register("kazi_lucky_cat", () -> new KaziLuckyCatBlock(
            Block.box(0, 0, 1, 16, 16, 15),
            Block.box(0, 0, 1, 16, 16, 15)
    ));

    RegistryObject<Block> CUPBOARD = BLOCKS.register("cupboard", () -> new VerticalTwoByThreeBlock(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.COLOR_BROWN).sound(SoundType.WOOD).strength(2f, 10f).noOcclusion(),
            Block.box(0, 0, 12, 16, 16, 16)));

    RegistryObject<Block> LARGE_DINING_TABLE = BLOCKS.register("large_dining_table", () -> new ThreeByThreeBlock(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 10f).noOcclusion(),
            Block.box(0, 0, 0, 16, 15, 16)));

    RegistryObject<Block> GIFT_FROM_KAZI_MANOR = BLOCKS.register("gift_from_kazi_manor", () -> new SimpleAnimatedBlock(
            BlockBehaviour.Properties.of().sound(SoundType.SNOW).strength(1f, 5f).noOcclusion(),
            Block.box(2, 0, 2, 14, 10, 14)));

    RegistryObject<Block> KEY_UNDER_THE_LAKE = BLOCKS.register("key_under_the_lake", () -> new SimpleAnimatedBlock(
            BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(1f, 5f).noOcclusion(),
            Block.box(1, 0, 1, 15, 7, 15)));

    RegistryObject<Block> FIREPLACE_DECORATION = BLOCKS.register("fireplace_decoration", () -> new VerticalTwoByThreeBlock(BlockBehaviour.Properties.of()
            .instrument(NoteBlockInstrument.BASEDRUM).sound(SoundType.WOOD).strength(3f, 15f).noOcclusion(),
            Block.box(0, 0, 9, 15, 16, 16),
            Block.box(0, 0, 0, 0, 0, 0),
            Block.box(1, 0, 9, 16, 16, 16),
            Shapes.or(
                    Block.box(0, 0, 9, 15, 16, 16),
                    Block.box(0, 10, 2, 16, 16, 16)
            ),
            Shapes.or(
                    Block.box(0, 0, 9, 16, 16, 16),
                    Block.box(0, 10, 2, 16, 16, 16)
            ),
            Shapes.or(
                    Block.box(1, 0, 9, 16, 16, 16),
                    Block.box(0, 10, 2, 16, 16, 16)
            )));

    RegistryObject<Block> CANOPY_TREE_LIMB = BLOCKS.register("canopy_tree_limb", () -> log(MapColor.COLOR_BROWN, MapColor.COLOR_BROWN));

    RegistryObject<Block> CANOPY_TREE_FOLIAGE = BLOCKS.register("canopy_tree_foliage", () -> leaves(SoundType.GRASS));

    RegistryObject<Block> CANOPY_TREE_FERN = BLOCKS.register("canopy_tree_fern", () -> new MossBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_GREEN).strength(0.1F).sound(SoundType.MOSS).pushReaction(PushReaction.DESTROY)){
        @Override
        public boolean canSustainPlant(BlockState state, BlockGetter level, BlockPos pos,
                                       Direction facing, IPlantable plantable) {
            PlantType plantType = plantable.getPlantType(level, pos.relative(facing));

            if (plantType == null) {
                return false;
            }

            if (plantType == PlantType.PLAINS || plantType == PlantType.CAVE) {
                return true;
            } else if (plantType == PlantType.BEACH) {
                return level.getFluidState(pos.east()).getType() == Fluids.WATER
                        || level.getFluidState(pos.west()).getType() == Fluids.WATER
                        || level.getFluidState(pos.north()).getType() == Fluids.WATER
                        || level.getFluidState(pos.south()).getType() == Fluids.WATER;
            }
            return false;
        }
    });

    RegistryObject<Block> CANOPY_TREE_TRUNK = BLOCKS.register("canopy_tree_trunk", () -> new SimpleDecorationBlock(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 10f).noOcclusion(),
            Block.box(0, 0, 4, 12, 16, 16)));

    RegistryObject<Block> CANOPY_TREE_MUSHROOM = BLOCKS.register("canopy_tree_mushroom", () -> new WaterLoggedOnlyBlock(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.SHROOMLIGHT).strength(0.5f, 0.2f).lightLevel(s -> 5).noOcclusion(),
            Shapes.or(Block.box(-4, 8, -4, 20, 16, 20), Block.box(-10, 0, -10, 26, 8, 26))));

    RegistryObject<Block> CANOPY_TREE_DROOPING_ROOT = BLOCKS.register("canopy_tree_drooping_root", () -> new Block(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(0.5f, 10f)));

    RegistryObject<Block> STAR_EMBELLISHED_CEILING = BLOCKS.register("star_embellished_ceiling", () -> new FaceAttachedHorizontalDirectionalBlock(
            BlockBehaviour.Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 10f).noOcclusion()){
        @Override
        public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
            return true;
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            builder.add(FACING, FACE);
        }
    });

    RegistryObject<Block> PALM_TREE_CROWN = BLOCKS.register("palm_tree_crown", () -> new SimpleDecorationBlock(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.PLANT).sound(SoundType.AZALEA_LEAVES).instabreak().noCollission().noOcclusion(),
            Block.box(-16, -16, -16, 16, 32, 16)));

    RegistryObject<Block> PALM_TREE_TOP = BLOCKS.register("palm_tree_top", () -> new WaterLoggedOnlyBlock(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.PLANT).sound(SoundType.WOOD).instabreak().noOcclusion(),
            Block.box(1, 0, 1, 15, 16, 15)));

    RegistryObject<Block> PALM_TREE_TRUNK_TOP = BLOCKS.register("palm_tree_trunk_top", () -> new WaterLoggedOnlyBlock(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(2f, 10f).noOcclusion(),
            Block.box(1, 0, 1, 15, 16, 15)));

    RegistryObject<Block> PALM_TREE_TRUNK = BLOCKS.register("palm_tree_trunk", () -> new WaterLoggedOnlyBlock(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(2f, 10f).noOcclusion(),
            Block.box(3, 0, 3, 13, 16, 13)));

    RegistryObject<Block> ROUGH_PALM_TREE_TRUNK = BLOCKS.register("rough_palm_tree_trunk", () -> new WaterLoggedOnlyBlock(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(2f, 10f).noOcclusion(),
            Block.box(2, 0, 2, 14, 16, 14)));

    RegistryObject<Block> UNDERGROUND_PANELLING = BLOCKS.register("underground_panelling", () -> new HorizontalDirectionalOnlyBlock(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(2f, 10f).noOcclusion(),
            Block.box(0, 0, 1, 16, 16, 16)){
        @Override
        public boolean propagatesSkylightDown(BlockState state, BlockGetter getter, BlockPos pos) {
            return true;
        }
    });

    RegistryObject<Block> ROSES_IN_WATER_BOTTLE = BLOCKS.register("roses_in_water_bottle", () -> new VerticalTwoByOneBlock(BlockBehaviour.Properties.of()
            .noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.GLASS),
            Block.box(5.5, 0, 5.5, 10.5, 16, 10.5),
            Block.box(0, 0, 0, 16, 8, 16)){
        @Override
        public VoxelShape getCollisionShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
            if (state.getValue(POSITION) == 1) return Shapes.empty();
            return getShape(state, blockGetter, pos, context);
        }
    });

    // 杯装酒方块实体
    RegistryObject<BlockEntityType<GlassDrinkBlockEntity>> GLASS_DRINK_BE = BLOCK_ENTITIES.register(
            "glass_drink", () -> BlockEntityType.Builder
                    .of(GlassDrinkBlockEntity::new,
                            GLASS_OF_BLAZE_WHISKEY.get(),
                            GLASS_OF_FERRY_WHISKEY.get(),
                            GLASS_OF_FLY_WHISKEY.get(),
                            GLASS_OF_LAND_NO1.get(),
                            GLASS_OF_LUCKY_CACTUS.get(),
                            GLASS_OF_POISON_RUM.get(),
                            BLOODY_MARY.get(),
                            DIPLOMAT_COFFEE.get(),
                            DEVIL_MARGARITA.get(),
                            GUANG_S.get()
                    ).build(null)
    );

    // 剪纸台方块实体
    RegistryObject<BlockEntityType<PaperCuttingTableBlockEntity>> PAPER_CUTTING_TABLE_BE = BLOCK_ENTITIES.register(
            "paper_cutting_table", () -> BlockEntityType.Builder
                    .of(PaperCuttingTableBlockEntity::new,
                            PAPER_CUTTING_TABLE.get()
                    ).build(null)
    );

    // 可烤制方块实体
    RegistryObject<BlockEntityType<SmeltableBlockEntity>> SMELTABLE_BE = BLOCK_ENTITIES.register(
            "smeltable_block", () -> BlockEntityType.Builder
                    .of(SmeltableBlockEntity::new,
                            CHUNKY_SALMON.get(),
                            SALMON_HEAD.get(),
                            MONSTER_STEAK.get()
                    ).build(null)
    );

    // 使用 GeckoLib 模型的可烤制方块
    RegistryObject<BlockEntityType<AnimatedSmeltableBlockEntity>> ANIMATED_SMELTABLE_BE = BLOCK_ENTITIES.register(
            "geo_smeltable_block", () -> BlockEntityType.Builder
                    .of(AnimatedSmeltableBlockEntity::new,
                            SAUSAGE_MACE_WEAPON_BLOCK.get(),
                            MANGA_MEAT.get()
                    ).build(null)
    );

    // 铜酒保方块实体
    RegistryObject<BlockEntityType<CopperBartenderBlockEntity>> COPPER_BARTENDER_BE = BLOCK_ENTITIES.register(
            "copper_bartender_block", () -> BlockEntityType.Builder
                    .of(CopperBartenderBlockEntity::new,
                            COPPER_BARTENDER.get()
                    ).build(null)
    );

    // 蒸馏器方块实体
    RegistryObject<BlockEntityType<DistillerBlockEntity>> DISTILLER_BE = BLOCK_ENTITIES.register(
            "distiller", () -> BlockEntityType.Builder
                    .of(DistillerBlockEntity::new,
                            COPPER_STILL.get()
                    ).build(null)
    );

    // 土坑方块实体
    RegistryObject<BlockEntityType<DirtHoleBlockEntity>> DIRT_HOLE_BE = BLOCK_ENTITIES.register(
            "dirt_hole", () -> BlockEntityType.Builder
                    .of(DirtHoleBlockEntity::new,
                            DIRT_HOLE.get()
                    ).build(null)
    );

    RegistryObject<BlockEntityType<KaziLuckyCatBlockEntity>> KAZI_LUCKY_CAT_BE = BLOCK_ENTITIES.register(
            "kazi_lucky_cat", () -> BlockEntityType.Builder
                    .of(KaziLuckyCatBlockEntity::new,
                            KAZI_LUCKY_CAT.get()
                    ).build(null)
    );

    // 使用geckolib动画的通用方块实体
    RegistryObject<BlockEntityType<AnimatedBlockEntity>> ANIMATED_BE = BLOCK_ENTITIES.register(
            "animated", () -> BlockEntityType.Builder
                    .of(AnimatedBlockEntity::new,
                            LOUD_BUTTON.get(),
                            LOW_CABINET_WITH_TABLECLOTH.get(),
                            WOODEN_BARREL_BOOKSHELF.get(),
                            WOODWORKING_TABLE.get(),
                            LONG_STORAGE_TABLE.get(),
                            EDGED_CHALKBOARD.get(),
                            GIFT_FROM_KAZI_MANOR.get(),
                            KEY_UNDER_THE_LAKE.get()
                    ).build(null)
    );

    private static RotatedPillarBlock log(MapColor topColor, MapColor sideColor) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(
                (state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : sideColor)
                .instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    }

    private static LeavesBlock leaves(SoundType soundType) {
        return new LeavesBlock(BlockBehaviour.Properties.of()
                .mapColor(MapColor.PLANT)
                .strength(0.2F)
                .randomTicks()
                .sound(soundType)
                .noOcclusion()
                .isValidSpawn(ModBlocks::ocelotOrParrot)
                .isSuffocating(ModBlocks::never)
                .isViewBlocking(ModBlocks::never)
                .ignitedByLava()
                .pushReaction(PushReaction.DESTROY)
                .isRedstoneConductor(ModBlocks::never));
    }

    private static boolean never(BlockState state, BlockGetter getter, BlockPos pos) {
        return false;
    }

    private static Boolean ocelotOrParrot(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> entityType) {
        return (entityType == EntityType.OCELOT || entityType == EntityType.PARROT);
    }
}
