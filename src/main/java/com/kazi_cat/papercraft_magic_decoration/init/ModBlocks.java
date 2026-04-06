package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.block.*;
import com.kazi_cat.papercraft_magic_decoration.block.decoration.*;
import com.kazi_cat.papercraft_magic_decoration.block.food.ChunkySalmonBlock;
import com.kazi_cat.papercraft_magic_decoration.block.food.MangaMeatBlock;
import com.kazi_cat.papercraft_magic_decoration.block.food.MonsterSteakBlock;
import com.kazi_cat.papercraft_magic_decoration.block.food.TwoByOneSmeltableBlock;
import com.kazi_cat.papercraft_magic_decoration.blockentity.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings({"DataFlowIssue","deprecation"})
public interface ModBlocks {
    DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PaperKiteManor.MOD_ID);
    DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, PaperKiteManor.MOD_ID);

    // 饮品方块
    RegistryObject<Block> BLAZE_WHISKEY = BLOCKS.register("blaze_whiskey", DrinkBlock.create().maxCount(3).shapes(
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
            )
    ).build());

    RegistryObject<Block> FERRY_WHISKEY = BLOCKS.register("ferry_whiskey", DrinkBlock.create().maxCount(1).shapes(
            Block.box(4, 0, 4, 12, 10, 12)
    ).build());

    RegistryObject<Block> FLY_WHISKEY = BLOCKS.register("fly_whiskey", DrinkBlock.create().maxCount(1).shapes(
            Block.box(4, 0, 4, 12, 10, 12)
    ).build());

    RegistryObject<Block> LAND_NO1 = BLOCKS.register("land_no1", DrinkBlock.create().maxCount(4).shapes(
            Block.box(6, 0, 6, 10, 16, 10),
            Block.box(2, 0, 6, 14, 16, 10),
            Shapes.or(
                    Block.box(2, 0, 10, 14, 16, 14),
                    Block.box(6, 0, 2, 10, 16, 14)
            ),
            Block.box(2, 0, 2, 14, 16, 14)
    ).build());

    RegistryObject<Block> LUCKY_CACTUS = BLOCKS.register("lucky_cactus", DrinkBlock.create().maxCount(1).shapes(
            Block.box(4, 0, 4, 12, 10, 12)
    ).build());

    RegistryObject<Block> POISON_RUM = BLOCKS.register("poison_rum", DrinkBlock.create().maxCount(1).shapes(
            Block.box(4, 0, 4, 12, 10, 12)
    ).build());

    RegistryObject<Block> BLOODY_MARY = BLOCKS.register("bloody_mary", DrinkBlock.create().maxCount(1).shapes(
            Block.box(4, 0, 4, 12, 13, 12)
    ).build());

    RegistryObject<Block> DIPLOMATICO_COFFEE = BLOCKS.register("diplomatico_coffee", DrinkBlock.create().maxCount(1).shapes(
            Block.box(4, 0, 4, 12, 10, 12)
    ).build());

    RegistryObject<Block> DEVIL_MARGARITA = BLOCKS.register("devil_margarita", DrinkBlock.create().maxCount(1).shapes(
            Block.box(4, 0, 4, 12, 10, 12)
    ).build());

    RegistryObject<Block> DIONYSUS = BLOCKS.register("dionysus", DrinkBlock.create().maxCount(1).shapes(
            Block.box(4, 0, 4, 12, 10, 12)
    ).build());

    RegistryObject<Block> KALEIDOSCOPE_WHISKEY_SOUR = BLOCKS.register("kaleidoscope_whiskey_sour", DrinkBlock.create().maxCount(1).shapes(
            Block.box(4, 0, 4, 12, 8.5, 12)
    ).build());

    RegistryObject<Block> NOCTURNAL_CAT_COFFEE = BLOCKS.register("nocturnal_cat_coffee", DrinkBlock.create().maxCount(1).shapes(
            Block.box(4, 0, 4, 12, 6, 12)
    ).build(BlockBehaviour.Properties.of().noOcclusion().instabreak().pushReaction(PushReaction.DESTROY).sound(SoundType.WOOD)));

    RegistryObject<Block> GOLD_MEDAL_COFFEE = BLOCKS.register("gold_medal_coffee", DrinkBlock.create().maxCount(1).shapes(
            Block.box(4, 0, 4, 12, 6, 12)
    ).build(BlockBehaviour.Properties.of().noOcclusion().instabreak().pushReaction(PushReaction.DESTROY).sound(SoundType.WOOD)));

    RegistryObject<Block> GUANG_S = BLOCKS.register("guang_s", () -> new BoxedDrinkBlock(BlockBehaviour.Properties.of()
                    .noOcclusion().instabreak().pushReaction(PushReaction.DESTROY).sound(SoundType.LANTERN), 4, 2, 0.25,
                    Block.box(5, 0, 5, 11, 11, 11),
                    Block.box(2, 0, 5, 14, 11, 11),
                    Shapes.or(
                            Block.box(2, 0, 9, 14, 11, 15),
                            Block.box(5, 0, 2, 11, 11, 15)
                    ),
                    Block.box(1, 0, 1, 15, 11, 15)) {
        @Override
        public Item asItem() { return ModItems.GUANG_S.get(); }
    });

    // 饮品方块实体
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
                            NOCTURNAL_CAT_COFFEE.get(),
                            GOLD_MEDAL_COFFEE.get(),
                            GUANG_S.get()
                    ).build(null)
    );

    // 大瓶酒方块
    RegistryObject<Block> BOTTLE_OF_BLAZE_WHISKEY = BLOCKS.register("bottle_of_blaze_whiskey", () -> new BottleDrinkBlock(
            Block.box(2, 0, 2, 14, 22, 14),
            () -> new ItemStack(ModItems.BLAZE_WHISKEY.get(), 4)
    ));

    RegistryObject<Block> BOTTLE_OF_FERRY_WHISKEY = BLOCKS.register("bottle_of_ferry_whiskey", () -> new BottleDrinkBlock(
            Block.box(2, 0, 2, 14, 20, 14),
            () -> new ItemStack(ModItems.FERRY_WHISKEY.get(), 4)
    ));

    RegistryObject<Block> BOTTLE_OF_FLY_WHISKEY = BLOCKS.register("bottle_of_fly_whiskey", () -> new BottleDrinkBlock(
            Block.box(2, 0, 2, 14, 20, 14),
            () -> new ItemStack(ModItems.FLY_WHISKEY.get(), 4)
    ));

    RegistryObject<Block> BOTTLE_OF_LAND_NO1 = BLOCKS.register("bottle_of_land_no1", () -> new BottleDrinkBlock(
            Block.box(2, 0, 2, 14, 20, 14),
            () -> new ItemStack(ModItems.LAND_NO1.get(), 4)
    ));

    RegistryObject<Block> BOTTLE_OF_LUCKY_CACTUS = BLOCKS.register("bottle_of_lucky_cactus", () -> new BottleDrinkBlock(
            Block.box(2, 0, 2, 14, 20, 14),
            () -> new ItemStack(ModItems.LUCKY_CACTUS.get(), 4)
    ));

    RegistryObject<Block> BOTTLE_OF_POISON_RUM = BLOCKS.register("bottle_of_poison_rum", () -> new BottleDrinkBlock(
            Block.box(2, 0, 2, 14, 20, 14),
            () -> new ItemStack(ModItems.POISON_RUM.get(), 4)
    ));

    // 纸块
    RegistryObject<Block> WHITE_PAPER_BLOCK = BLOCKS.register("white_paper_block", () -> new Block(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.SNOW).sound(SoundType.METAL).instabreak()));

    RegistryObject<Block> BLUE_PAPER_BLOCK = BLOCKS.register("blue_paper_block", () -> new Block(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.COLOR_BLUE).sound(SoundType.WOOD).instabreak()));

    RegistryObject<Block> BLACK_PAPER_BLOCK = BLOCKS.register("black_paper_block", () -> new Block(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.COLOR_BLACK).sound(SoundType.LILY_PAD).instabreak()));

    RegistryObject<Block> RED_PAPER_BLOCK = BLOCKS.register("red_paper_block", () -> new Block(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.COLOR_RED).sound(SoundType.AMETHYST).instabreak().lightLevel(s -> 6).hasPostProcess((bs, br, bp) -> true).emissiveRendering((bs, br, bp) -> true)));

    RegistryObject<Block> YELLOW_PAPER_BLOCK = BLOCKS.register("yellow_paper_block", () -> new Block(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.COLOR_YELLOW).sound(SoundType.SOUL_SAND).instabreak().lightLevel((s) -> 10)));

    RegistryObject<Block> DEWY_MEMBRANE_BLOCK = BLOCKS.register("dewy_membrane_block", () -> new Block(BlockBehaviour.Properties.of()
            .instrument(NoteBlockInstrument.HAT).mapColor(MapColor.COLOR_GREEN).sound(SoundType.LILY_PAD).instabreak().noOcclusion().isRedstoneConductor((bs, br, bp) -> false)));

    RegistryObject<Block> COTTON_SERGE_BLOCK = BLOCKS.register("cotton_serge_block", () -> new Block(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.SNOW).sound(SoundType.SNOW).instabreak()));

    // 剪纸台方块
    RegistryObject<Block> PAPER_CUTTING_TABLE = BLOCKS.register("paper_cutting_table", PaperCuttingTableBlock::new);

    // 剪纸台方块实体
    RegistryObject<BlockEntityType<PaperCuttingTableBlockEntity>> PAPER_CUTTING_TABLE_BE = BLOCK_ENTITIES.register("paper_cutting_table",
            () -> BlockEntityType.Builder.of(PaperCuttingTableBlockEntity::new, PAPER_CUTTING_TABLE.get()).build(null));

    // 铜酒保方块
    RegistryObject<Block> COPPER_BARTENDER = BLOCKS.register("copper_bartender", CopperBartenderBlock::new);

    // 铜酒保方块实体
    RegistryObject<BlockEntityType<CopperBartenderBlockEntity>> COPPER_BARTENDER_BE = BLOCK_ENTITIES.register("copper_bartender_block",
            () -> BlockEntityType.Builder.of(CopperBartenderBlockEntity::new, COPPER_BARTENDER.get()).build(null));

    // 蒸馏器方块
    RegistryObject<Block> COPPER_STILL = BLOCKS.register("copper_still", DistillerBlock::new);

    // 蒸馏器方块实体
    RegistryObject<BlockEntityType<DistillerBlockEntity>> DISTILLER_BE = BLOCK_ENTITIES.register("distiller",
            () -> BlockEntityType.Builder.of(DistillerBlockEntity::new, COPPER_STILL.get()).build(null));

    // 摩卡壶方块
    RegistryObject<Block> MOCHA_POT = BLOCKS.register("mocha_pot", MochaPotBlock::new);

    // 摩卡壶方块实体
    RegistryObject<BlockEntityType<MochaPotBlockEntity>> MOCHA_POT_BE = BLOCK_ENTITIES.register("mocha_pot",
            () -> BlockEntityType.Builder.of(MochaPotBlockEntity::new, MOCHA_POT.get()).build(null));

    // 土坑方块
    RegistryObject<Block> DIRT_HOLE = BLOCKS.register("dirt_hole", DirtHoleBlock::new);

    // 土坑方块实体
    RegistryObject<BlockEntityType<DirtHoleBlockEntity>> DIRT_HOLE_BE = BLOCK_ENTITIES.register("dirt_hole",
            () -> BlockEntityType.Builder.of(DirtHoleBlockEntity::new, DIRT_HOLE.get()).build(null));

    // 孢子收集盆
    RegistryObject<Block> SPORES_COLLECTION_PLATE = BLOCKS.register("spores_collection_plate", SporesCollectionPlateBlock::new);

    // 咖啡欧防风
    RegistryObject<Block> COFFEE_PASTINACA_SATIVA = BLOCKS.register("coffee_pastinaca_sativa", CoffeePastinacaSativaCropBlock::new);

    RegistryObject<Block> COFFEE_PASTINACA_SATIVA_FRUITING_STEM = BLOCKS.register("coffee_pastinaca_sativa_fruiting_stem", () -> new DecorationBlock.HorizontalDirectional.Waterlogged(
            BlockBehaviour.Properties.of().ignitedByLava().instabreak().sound(SoundType.LILY_PAD).noCollission().pushReaction(PushReaction.DESTROY).replaceable(),
            Block.box(0, 0, 0, 16, 16, 16)));

    RegistryObject<Block> COFFEE_PASTINACA_SATIVA_CORE = BLOCKS.register("coffee_pastinaca_sativa_core", () -> new Block(
            BlockBehaviour.Properties.of().ignitedByLava().strength(2f, 10f).forceSolidOn()));

    RegistryObject<Block> COFFEE_PASTINACA_SATIVA_RIM = BLOCKS.register("coffee_pastinaca_sativa_rim", ()->
            new TwoByOneBlock.Waterlogged(BlockBehaviour.Properties.of().ignitedByLava().strength(2f, 10f),
                    Block.box(0, 0, 0, 8, 16, 16),
                    Block.box(0, 0, 0, 8, 16, 8)));

    RegistryObject<Block> COFFEE_PASTINACA_SATIVA_FLOWERS = BLOCKS.register("coffee_pastinaca_sativa_flowers", () -> new DecorationBlock.Waterlogged(
            BlockBehaviour.Properties.of().ignitedByLava().instabreak().sound(SoundType.LILY_PAD).noCollission().pushReaction(PushReaction.DESTROY).replaceable()));

    // 棕榈树
    RegistryObject<Block> PALM_TREE_CROWN = BLOCKS.register("palm_tree_crown", () -> new DecorationBlock.HorizontalDirectional.Waterlogged(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.PLANT).sound(SoundType.AZALEA_LEAVES).instabreak().noCollission().noOcclusion(),
            Block.box(-16, -16, -16, 16, 32, 16)));

    RegistryObject<Block> PALM_TREE_TOP = BLOCKS.register("palm_tree_top", () -> new DecorationBlock.Waterlogged(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.PLANT).sound(SoundType.WOOD).instabreak().noOcclusion(),
            Block.box(1, 0, 1, 15, 16, 15)));

    RegistryObject<Block> PALM_TREE_TRUNK_TOP = BLOCKS.register("palm_tree_trunk_top", () -> new DecorationBlock.Waterlogged(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(2f, 10f).noOcclusion(),
            Block.box(1, 0, 1, 15, 16, 15)));

    RegistryObject<Block> PALM_TREE_TRUNK = BLOCKS.register("palm_tree_trunk", () -> new DecorationBlock.Waterlogged(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(2f, 10f).noOcclusion(),
            Block.box(3, 0, 3, 13, 16, 13)));

    RegistryObject<Block> ROUGH_PALM_TREE_TRUNK = BLOCKS.register("rough_palm_tree_trunk", () -> new DecorationBlock.Waterlogged(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(2f, 10f).noOcclusion(),
            Block.box(2, 0, 2, 14, 16, 14)));

    // 荫幕树
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

    RegistryObject<Block> CANOPY_TREE_TRUNK = BLOCKS.register("canopy_tree_trunk", () -> new DecorationBlock.HorizontalDirectional.Waterlogged(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 10f).noOcclusion(),
            Block.box(0, 0, 4, 12, 16, 16)));

    RegistryObject<Block> CANOPY_TREE_MUSHROOM = BLOCKS.register("canopy_tree_mushroom", () -> new DecorationBlock.Waterlogged(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.SHROOMLIGHT).strength(0.5f, 0.2f).lightLevel(s -> 5).noOcclusion(),
            Shapes.or(Block.box(-4, 8, -4, 20, 16, 20), Block.box(-10, 0, -10, 26, 8, 26))));

    RegistryObject<Block> CANOPY_TREE_DROOPING_ROOT = BLOCKS.register("canopy_tree_drooping_root", () -> new Block(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(0.5f, 10f)));

    // 装饰方块
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

    RegistryObject<Block> EMERALD_BLUE_EARTH_TILE = BLOCKS.register("emerald_blue_earth_tile", () -> new Block(BlockBehaviour.Properties.of()
            .instrument(NoteBlockInstrument.BASEDRUM).mapColor(MapColor.TERRACOTTA_WHITE).sound(SoundType.STONE).strength(1.5f, 10f).requiresCorrectToolForDrops()));

    RegistryObject<Block> UNDERGROUND_PANELLING = BLOCKS.register("underground_panelling", () -> new DecorationBlock.HorizontalDirectional(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(2f, 10f).noOcclusion(),
            Block.box(0, 0, 1, 16, 16, 16)));

    RegistryObject<Block> RUSTIC_PANELLING = BLOCKS.register("rustic_panelling", () -> new DecorationBlock.HorizontalDirectional(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(1f, 3f),
            Block.box(0, 0, 1, 16, 16, 16)));

    RegistryObject<Block> UNDERGROUND_DOOR_FRAMES = BLOCKS.register("underground_door_frames", () -> new DecorationBlock.HorizontalDirectional.Waterlogged(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(1f, 3f).noCollission().noOcclusion(),
            Block.box(-7, 8, 12, 23, 32, 16)));

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

    RegistryObject<Block> ROSES_IN_WATER_BOTTLE = BLOCKS.register("roses_in_water_bottle", () -> new TwoByOneVerticalBlock.Waterlogged(BlockBehaviour.Properties.of()
            .noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.GLASS),
            Block.box(5.5, 0, 5.5, 10.5, 16, 10.5),
            Block.box(0, 0, 0, 16, 8, 16)){
        @Override
        public VoxelShape getCollisionShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context) {
            if (state.getValue(PART) == 1) return Shapes.empty();
            return getShape(state, blockGetter, pos, context);
        }
    });

    RegistryObject<Block> GIFT_FROM_KAZI_MANOR = BLOCKS.register("gift_from_kazi_manor", () -> new DecorationBlock.HorizontalDirectional.Animated.Waterlogged(
            BlockBehaviour.Properties.of().sound(SoundType.SNOW).strength(1f, 5f).noOcclusion(),
            Block.box(2, 0, 2, 14, 10, 14)));

    RegistryObject<Block> KEY_UNDER_THE_LAKE = BLOCKS.register("key_under_the_lake", () -> new DecorationBlock.HorizontalDirectional.Animated.Waterlogged(
            BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(1f, 5f).noOcclusion(),
            Block.box(1, 0, 1, 15, 7, 15)));

    RegistryObject<Block> LOUD_BUTTON = BLOCKS.register("loud_button", LoudButtonBlock::new);

    RegistryObject<Block> LOW_CABINET_WITH_TABLECLOTH = BLOCKS.register("low_cabinet_with_tablecloth",
            () -> new DecorationBlock.HorizontalDirectional.Animated.Waterlogged(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED).sound(SoundType.WOOD).strength(2f, 10f).noOcclusion(),
                    Block.box(0, 0, 0, 16, 15, 16)));

    RegistryObject<Block> WOODEN_BARREL_BOOKSHELF = BLOCKS.register("wooden_barrel_bookshelf",
            () -> new DecorationBlock.HorizontalDirectional.Animated.Waterlogged(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN).sound(SoundType.WOOD).strength(2f, 10f).noOcclusion(),
                    Block.box(0, 0, 0, 14, 16, 16)));

    RegistryObject<Block> WOODWORKING_TABLE = BLOCKS.register("woodworking_table",
            () -> new OneByTwoBlock.Animated.Waterlogged(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN).sound(SoundType.WOOD).strength(2f, 10f).noOcclusion(),
                    Block.box(1, 0, 0, 16, 16, 16),
                    Block.box(0, 0, 0, 15, 16, 16)));

    RegistryObject<Block> LONG_STORAGE_TABLE = BLOCKS.register("long_storage_table",
            () -> new OneByThreeBlock.Animated.Waterlogged(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN).sound(SoundType.WOOD).strength(2f, 10f).noOcclusion(),
                    Block.box(1, 0, 0, 16, 15, 16),
                    Block.box(0, 0, 0, 16, 15, 16),
                    Block.box(0, 0, 0, 15, 15, 16)));

    RegistryObject<Block> EDGED_CHALKBOARD = BLOCKS.register("edged_chalkboard", () -> new TwoByThreeVerticalBlock.Animated.Waterlogged(
            BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(2f, 10f).noOcclusion(),
                    Block.box(0, 0, 13, 16, 16, 16)));

    RegistryObject<Block> CUPBOARD = BLOCKS.register("cupboard", () -> new TwoByThreeVerticalBlock.Waterlogged(BlockBehaviour.Properties.of()
            .ignitedByLava().instrument(NoteBlockInstrument.BASS).mapColor(MapColor.COLOR_BROWN).sound(SoundType.WOOD).strength(2f, 10f).noOcclusion(),
            Block.box(0, 0, 12, 16, 16, 16)));

    RegistryObject<Block> FIREPLACE_DECORATION = BLOCKS.register("fireplace_decoration", () -> new TwoByThreeVerticalBlock.Waterlogged(BlockBehaviour.Properties.of()
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
            )
    ));

    RegistryObject<Block> LARGE_DINING_TABLE = BLOCKS.register("large_dining_table", () -> new ThreeByThreeBlock.Waterlogged(
            BlockBehaviour.Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(2f, 10f).noOcclusion(),
            Block.box(0, 0, 0, 16, 15, 16)));

    // 使用简单geckolib动画的通用方块实体
    RegistryObject<BlockEntityType<AnimatedBlockEntity>> ANIMATED_BE = BLOCK_ENTITIES.register(
            "animated", () -> BlockEntityType.Builder
                    .of(AnimatedBlockEntity::new,
                            LOUD_BUTTON.get(),
                            GIFT_FROM_KAZI_MANOR.get(),
                            KEY_UNDER_THE_LAKE.get(),
                            LOW_CABINET_WITH_TABLECLOTH.get(),
                            WOODEN_BARREL_BOOKSHELF.get(),
                            WOODWORKING_TABLE.get(),
                            LONG_STORAGE_TABLE.get(),
                            EDGED_CHALKBOARD.get()
                    ).build(null)
    );

    // 食物部分
    RegistryObject<Block> BUCKET_OF_FRIED_CHICKEN = BLOCKS.register("bucket_of_fried_chicken", () -> new DecorationBlock.HorizontalDirectional.Waterlogged(
            BlockBehaviour.Properties.of().sound(SoundType.SNOW).strength(1f, 4f).noOcclusion(),
            Block.box(2, 0, 2, 14, 14, 14)) {
        @Override
        public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
            if (!level.isClientSide() && !player.isSecondaryUseActive() && player.getMainHandItem().isEmpty()) {
                ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ModItems.FRIED_CHICKEN_LEG.get(), 4));
                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                return InteractionResult.SUCCESS;
            }
            return super.use(state, level, pos, player, hand, hitResult);
        }
    });

    RegistryObject<Block> SAUSAGE_MACE_WEAPON_BLOCK = BLOCKS.register("sausage_mace_weapon", () ->
            new TwoByOneSmeltableBlock.Animated(
                    BlockBehaviour.Properties.of().sound(SoundType.SHROOMLIGHT).strength(1f, 4f).noOcclusion(),
                    Shapes.or(
                            Block.box(1, 1, 1, 15, 15, 15),
                            Block.box(6, 6, -16, 10, 10, 0)
                    ),
                    Block.box(1, 1, 0, 15, 15, 15),
                    100, 2, 21,
                    () -> ModItems.RAW_SAUSAGE_MACE_WEAPON.get().getDefaultInstance(),
                    () -> ModItems.SAUSAGE_MACE_WEAPON.get().getDefaultInstance()
            ));

    RegistryObject<Block> CHUNKY_SALMON = BLOCKS.register("chunky_salmon", () ->
            new ChunkySalmonBlock(
                    BlockBehaviour.Properties.of().sound(SoundType.SHROOMLIGHT).strength(1f, 4f).noOcclusion(),
                    Block.box(0, 0, 0, 16, 10, 16),
                    100, 0, 0,
                    () -> ModItems.CHUNKY_SALMON.get().getDefaultInstance(),
                    () -> ModItems.CHUNKY_SMOKED_SALMON.get().getDefaultInstance()
            ));

    RegistryObject<Block> SALMON_HEAD = BLOCKS.register("salmon_head", () ->
            new TwoByOneSmeltableBlock(
                    BlockBehaviour.Properties.of().sound(SoundType.SHROOMLIGHT).strength(1f, 4f).noOcclusion(),
                    Block.box(0, 0, 0, 16, 20, 16),
                    Block.box(0, 0, 0, 16, 20, 16),
                    100, 0, 0,
                    Items.SALMON::getDefaultInstance,
                    () -> ModItems.SMOKED_SALMON_HEAD.get().getDefaultInstance()
            ));

    RegistryObject<Block> MANGA_MEAT = BLOCKS.register("manga_meat", () ->
            new MangaMeatBlock(
                    BlockBehaviour.Properties.of().sound(SoundType.SHROOMLIGHT).strength(1f, 4f).noOcclusion(),
                    Block.box(1, 0, 0, 15, 15, 16),
                    200, 4, 16,
                    () -> new ItemStack(ModItems.RAW_MANGA_MEAT.get()),
                    () -> new ItemStack(ModItems.MANGA_MEAT.get())
            ));

    RegistryObject<Block> MONSTER_STEAK = BLOCKS.register("monster_steak", () ->
            new MonsterSteakBlock(
                    BlockBehaviour.Properties.of().sound(SoundType.SHROOMLIGHT).strength(1f, 4f).noOcclusion(),
                    Block.box(0, 0, 0, 16, 12, 16),
                    100, 0, 0,
                    () -> new ItemStack(ModItems.MONSTER_STEAK.get()),
                    () -> ItemStack.EMPTY
            ));

    RegistryObject<Block> LARGE_STEAK = BLOCKS.register("large_steak", () -> new DecorationBlock.HorizontalDirectional.Variant(
            BlockBehaviour.Properties.of().sound(SoundType.SHROOMLIGHT).strength(1f, 4f).noOcclusion(),
            Block.box(0, 0, 0, 16, 12, 16), 6));

    RegistryObject<Block> TRAY_BLOCK = BLOCKS.register("tray", () -> new DecorationBlock.HorizontalDirectional.Variant.Waterlogged(
            BlockBehaviour.Properties.of().strength(0.5f, 10f).noOcclusion().noLootTable(),
            Block.box(0, 0, 0, 16, 2, 16), 2
    ));

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
    RegistryObject<BlockEntityType<SmeltableBlockEntity.Animated>> ANIMATED_SMELTABLE_BE = BLOCK_ENTITIES.register(
            "geo_smeltable_block", () -> BlockEntityType.Builder
                    .of(SmeltableBlockEntity.Animated::new,
                            SAUSAGE_MACE_WEAPON_BLOCK.get(),
                            MANGA_MEAT.get()
                    ).build(null)
    );

    RegistryObject<Block> KAZI_LUCKY_CAT = BLOCKS.register("kazi_lucky_cat", () -> new KaziLuckyCatBlock(
            Block.box(0, 0, 1, 16, 16, 15),
            Block.box(0, 0, 1, 16, 16, 15)
    ));

    RegistryObject<BlockEntityType<KaziLuckyCatBlockEntity>> KAZI_LUCKY_CAT_BE = BLOCK_ENTITIES.register("kazi_lucky_cat",
            () -> BlockEntityType.Builder.of(KaziLuckyCatBlockEntity::new, KAZI_LUCKY_CAT.get()).build(null));

    @SuppressWarnings("all")
    private static RotatedPillarBlock log(MapColor topColor, MapColor sideColor) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(
                        (state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : sideColor)
                .instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    }

    @SuppressWarnings("all")
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
