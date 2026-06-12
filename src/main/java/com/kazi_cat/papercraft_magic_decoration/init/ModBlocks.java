package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.block.chocolate.ChocolateInMoldBlock;
import com.kazi_cat.papercraft_magic_decoration.block.chocolate.OversizedBoxOfChocolatesBlock;
import com.kazi_cat.papercraft_magic_decoration.block.crop.CoffeePastinacaSativaCropBlock;
import com.kazi_cat.papercraft_magic_decoration.block.decoration.*;
import com.kazi_cat.papercraft_magic_decoration.block.drink.BottleDrinkBlock;
import com.kazi_cat.papercraft_magic_decoration.block.misc.DirtHoleBlock;
import com.kazi_cat.papercraft_magic_decoration.block.misc.TemplateSaplingBlock;
import com.kazi_cat.papercraft_magic_decoration.block.misc.UmbrellaCashewBlock;
import com.kazi_cat.papercraft_magic_decoration.block.smeltable.*;
import com.kazi_cat.papercraft_magic_decoration.block.utility.*;
import com.kazi_cat.papercraft_magic_decoration.blockentity.*;
import com.kazi_cat.papercraft_magic_decoration.init.registry.DrinkRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.common.IPlantable;
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

    // 大瓶酒方块
    RegistryObject<Block> BOTTLE_OF_BLAZE_WHISKEY = BLOCKS.register("bottle_of_blaze_whiskey", () -> new BottleDrinkBlock(
            () -> Collections.singletonList(new ItemStack(DrinkRegistry.getItem(DrinkRegistry.BLAZE_WHISKEY), 4)),
            Block.box(2, 0, 2, 14, 22, 14)));

    RegistryObject<Block> BOTTLE_OF_FERRY_WHISKEY = BLOCKS.register("bottle_of_ferry_whiskey", () -> new BottleDrinkBlock(
            () -> Collections.singletonList(new ItemStack(DrinkRegistry.getItem(DrinkRegistry.FERRY_WHISKEY), 4)),
            Block.box(2, 0, 2, 14, 20, 14)));

    RegistryObject<Block> BOTTLE_OF_FLY_WHISKEY = BLOCKS.register("bottle_of_fly_whiskey", () -> new BottleDrinkBlock(
            () -> Collections.singletonList(new ItemStack(DrinkRegistry.getItem(DrinkRegistry.FLY_WHISKEY), 4)),
            Block.box(2, 0, 2, 14, 20, 14)));

    RegistryObject<Block> BOTTLE_OF_LAND_NO1 = BLOCKS.register("bottle_of_land_no1", () -> new BottleDrinkBlock(
            () -> Collections.singletonList(new ItemStack(DrinkRegistry.getItem(DrinkRegistry.LAND_NO1), 4)),
            Block.box(2, 0, 2, 14, 20, 14)));

    RegistryObject<Block> BOTTLE_OF_LUCKY_CACTUS = BLOCKS.register("bottle_of_lucky_cactus", () -> new BottleDrinkBlock(
            () -> Collections.singletonList(new ItemStack(DrinkRegistry.getItem(DrinkRegistry.LUCKY_CACTUS), 4)),
            Block.box(2, 0, 2, 14, 20, 14)));

    RegistryObject<Block> BOTTLE_OF_POISON_RUM = BLOCKS.register("bottle_of_poison_rum", () -> new BottleDrinkBlock(
            () -> Collections.singletonList(new ItemStack(DrinkRegistry.getItem(DrinkRegistry.POISON_RUM), 4)),
            Block.box(2, 0, 2, 14, 20, 14)));

    // 功能方块
    RegistryObject<Block> COPPER_BARTENDER = BLOCKS.register("copper_bartender", CopperBartenderBlock::new);

    RegistryObject<BlockEntityType<CopperBartenderBlockEntity>> COPPER_BARTENDER_BE = BLOCK_ENTITIES.register("copper_bartender_block", () -> BlockEntityType.Builder.of(CopperBartenderBlockEntity::new, COPPER_BARTENDER.get()).build(null));

    RegistryObject<Block> COPPER_STILL = BLOCKS.register("copper_still", CopperStillBlock::new);

    RegistryObject<BlockEntityType<CopperStillBlockEntity>> COPPER_STILL_BE = BLOCK_ENTITIES.register("copper_still", () -> BlockEntityType.Builder.of(CopperStillBlockEntity::new, COPPER_STILL.get()).build(null));

    RegistryObject<Block> PAPER_CUTTING_TABLE = BLOCKS.register("paper_cutting_table", PaperCuttingTableBlock::new);

    RegistryObject<BlockEntityType<PaperCuttingTableBlockEntity>> PAPER_CUTTING_TABLE_BE = BLOCK_ENTITIES.register("paper_cutting_table", () -> BlockEntityType.Builder.of(PaperCuttingTableBlockEntity::new, PAPER_CUTTING_TABLE.get()).build(null));

    RegistryObject<Block> SPORES_COLLECTION_PLATE = BLOCKS.register("spores_collection_plate", SporesCollectionPlateBlock::new);

    RegistryObject<Block> MOCHA_POT = BLOCKS.register("mocha_pot", MochaPotBlock::new);

    RegistryObject<BlockEntityType<MochaPotBlockEntity>> MOCHA_POT_BE = BLOCK_ENTITIES.register("mocha_pot", () -> BlockEntityType.Builder.of(MochaPotBlockEntity::new, MOCHA_POT.get()).build(null));

    // 巧克力
    RegistryObject<Block> TRUFFLE_CHOCOLATE = BLOCKS.register("truffle_chocolate", () -> new PickableDecorationBlock(
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .strength(0.5f, 2f)
                    .sound(SoundType.BONE_BLOCK),
            () -> List.of(ModItems.TRUFFLE_CHOCOLATE.get().getDefaultInstance()),
            Block.box(1.5, 0 ,1.5, 14.5, 8, 14.5)));

    RegistryObject<Block> MILK_CHOCOLATE = BLOCKS.register("milk_chocolate", () -> new PickableDecorationBlock(
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .strength(0.5f, 2f)
                    .sound(SoundType.BONE_BLOCK),
            () -> List.of(ModItems.MILK_CHOCOLATE.get().getDefaultInstance()),
            Block.box(1.5, 0 ,1.5, 14.5, 8, 14.5)));

    RegistryObject<Block> PRALINE_CHOCOLATE = BLOCKS.register("praline_chocolate", () -> new PickableDecorationBlock(
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .strength(0.5f, 2f)
                    .sound(SoundType.BONE_BLOCK),
            () -> List.of(ModItems.PRALINE_CHOCOLATE.get().getDefaultInstance()),
            Block.box(1.5, 0 ,1.5, 14.5, 8, 14.5)));

    RegistryObject<Block> TRUFFLE_CHOCOLATE_IN_MOLD = BLOCKS.register("truffle_chocolate_in_mold", () -> new ChocolateInMoldBlock(
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .sound(SoundType.LANTERN)
                    .strength(1f, 4f),
            ModItems.TRUFFLE_CHOCOLATE));

    RegistryObject<Block> MILK_CHOCOLATE_IN_MOLD = BLOCKS.register("milk_chocolate_in_mold", () -> new ChocolateInMoldBlock(
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .sound(SoundType.LANTERN)
                    .strength(1f, 4f),
            ModItems.MILK_CHOCOLATE));

    RegistryObject<Block> PRALINE_CHOCOLATE_IN_MOLD = BLOCKS.register("praline_chocolate_in_mold", () -> new ChocolateInMoldBlock(
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .sound(SoundType.LANTERN)
                    .strength(1f, 4f),
            ModItems.PRALINE_CHOCOLATE));

    RegistryObject<Block> OVERSIZED_BOX_OF_CHOCOLATES = BLOCKS.register("oversized_box_of_chocolates", () -> new OversizedBoxOfChocolatesBlock(
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .sound(SoundType.BAMBOO_WOOD)
                    .strength(1f, 4f)));

    RegistryObject<BlockEntityType<ChocolateInMoldBlockEntity>> CHOCOLATE_IN_MOLD_BE = BLOCK_ENTITIES.register(
            "melted_cocoa_in_mold_be", () -> BlockEntityType.Builder
                    .of(ChocolateInMoldBlockEntity::new,
                            TRUFFLE_CHOCOLATE_IN_MOLD.get(),
                            MILK_CHOCOLATE_IN_MOLD.get(),
                            PRALINE_CHOCOLATE_IN_MOLD.get()
                    ).build(null));

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

    // 咖啡欧防风
    RegistryObject<Block> COFFEE_PASTINACA_SATIVA = BLOCKS.register("coffee_pastinaca_sativa", CoffeePastinacaSativaCropBlock::new);

    RegistryObject<Block> COFFEE_PASTINACA_SATIVA_FRUITING_STEM = BLOCKS.register("coffee_pastinaca_sativa_fruiting_stem", () -> new SimpleDecorationBlock(
            BlockBehaviour.Properties.of()
                    .instabreak()
                    .replaceable()
                    .noCollission()
                    .ignitedByLava()
                    .sound(SoundType.LILY_PAD)
                    .pushReaction(PushReaction.DESTROY),
            Block.box(0, 0, 0, 16, 16, 16)));

    RegistryObject<Block> COFFEE_PASTINACA_SATIVA_CORE = BLOCKS.register("coffee_pastinaca_sativa_core", () -> new Block(
            BlockBehaviour.Properties.of()
                    .forceSolidOn()
                    .ignitedByLava()
                    .strength(2f, 10f)));

    RegistryObject<Block> COFFEE_PASTINACA_SATIVA_RIM = BLOCKS.register("coffee_pastinaca_sativa_rim", ()-> new TwoByOneDecorationBlock(
            BlockBehaviour.Properties.of()
                    .ignitedByLava()
                    .strength(2f, 10f),
            Block.box(0, 0, 0, 8, 16, 16),
            Block.box(0, 0, 0, 8, 16, 8)));

    RegistryObject<Block> COFFEE_PASTINACA_SATIVA_FLOWERS = BLOCKS.register("coffee_pastinaca_sativa_flowers", () -> new WaterloggedOnlyBlock(
            BlockBehaviour.Properties.of()
                    .instabreak()
                    .replaceable()
                    .noCollission()
                    .ignitedByLava()
                    .sound(SoundType.LILY_PAD)
                    .pushReaction(PushReaction.DESTROY), null));

    // 棕榈树
    RegistryObject<Block> MINI_PALM_TREE = BLOCKS.register("mini_palm_tree", () -> new TemplateSaplingBlock(
            BlockBehaviour.Properties.of()
                    .instabreak()
                    .randomTicks()
                    .noOcclusion()
                    .noCollission()
                    .ignitedByLava()
                    .sound(SoundType.GRASS)
                    .mapColor(MapColor.COLOR_GREEN),
            TemplateSaplingBlock.Entry.of(PaperKiteManor.modLoc("palm_tree_a"), 12, 0),
            TemplateSaplingBlock.Entry.of(PaperKiteManor.modLoc("palm_tree_b"), 16, 0),
            TemplateSaplingBlock.Entry.of(PaperKiteManor.modLoc("palm_tree_c"), 18, 0),
            TemplateSaplingBlock.Entry.of(PaperKiteManor.modLoc("palm_tree_d"), 20, 0),
            TemplateSaplingBlock.Entry.of(PaperKiteManor.modLoc("palm_tree_e"), 22, 0),
            TemplateSaplingBlock.Entry.of(PaperKiteManor.modLoc("palm_tree_f"), 23, 0)) {
        protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
            return super.mayPlaceOn(state, level, pos) || state.is(BlockTags.SAND);
        }
    });

    RegistryObject<Block> PALM_TREE_CROWN = BLOCKS.register("palm_tree_crown", () -> new SimpleDecorationBlock(
            BlockBehaviour.Properties.of()
                    .instabreak()
                    .noOcclusion()
                    .noCollission()
                    .ignitedByLava()
                    .mapColor(MapColor.PLANT)
                    .sound(SoundType.AZALEA_LEAVES)
                    .instrument(NoteBlockInstrument.BASS),
            Block.box(-16, -16, -16, 16, 32, 16)));

    RegistryObject<Block> PALM_TREE_TOP = BLOCKS.register("palm_tree_top", () -> new Block(
            BlockBehaviour.Properties.of()
                    .instabreak()
                    .noOcclusion()
                    .ignitedByLava()
                    .sound(SoundType.WOOD)
                    .mapColor(MapColor.PLANT)
                    .instrument(NoteBlockInstrument.BASS)));

    RegistryObject<Block> PALM_TREE_TRUNK_TOP = BLOCKS.register("palm_tree_trunk_top", () -> new Block(
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .ignitedByLava()
                    .sound(SoundType.WOOD)
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2f, 10f)));

    RegistryObject<Block> PALM_TREE_TRUNK = BLOCKS.register("palm_tree_trunk", () -> new WaterloggedOnlyBlock(
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .ignitedByLava()
                    .sound(SoundType.WOOD)
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2f, 10f),
            Block.box(3, 0, 3, 13, 16, 13)));

    RegistryObject<Block> ROUGH_PALM_TREE_TRUNK = BLOCKS.register("rough_palm_tree_trunk", () -> new WaterloggedOnlyBlock(
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .ignitedByLava()
                    .sound(SoundType.WOOD)
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2f, 10f),
            Block.box(2, 0, 2, 14, 16, 14)));

    // 阴幕树
    RegistryObject<Block> UMBRELLA_CASHEW = BLOCKS.register("umbrella_cashew", UmbrellaCashewBlock::new);

    RegistryObject<Block> MINI_CANOPY_TREE = BLOCKS.register("mini_canopy_tree", () -> new TemplateSaplingBlock(
            BlockBehaviour.Properties.of()
                    .randomTicks()
                    .noOcclusion()
                    .noCollission()
                    .ignitedByLava()
                    .sound(SoundType.GRASS)
                    .mapColor(MapColor.COLOR_GREEN)
                    .strength(0.5f, 10f),
            TemplateSaplingBlock.Entry.of(PaperKiteManor.modLoc("canopy_tree_a"), 36, 1),
            TemplateSaplingBlock.Entry.of(PaperKiteManor.modLoc("canopy_tree_b"), 37, 1),
            TemplateSaplingBlock.Entry.of(PaperKiteManor.modLoc("canopy_tree_c"), 34, 1),
            TemplateSaplingBlock.Entry.of(PaperKiteManor.modLoc("canopy_tree_d"), 34, 1),
            TemplateSaplingBlock.Entry.of(PaperKiteManor.modLoc("canopy_tree_e"), 30, 1),
            TemplateSaplingBlock.Entry.of(PaperKiteManor.modLoc("canopy_tree_f"), 29, 1)));

    RegistryObject<Block> CANOPY_TREE_LIMB = BLOCKS.register("canopy_tree_limb", () -> new RotatedPillarBlock(
            BlockBehaviour.Properties.of()
                    .ignitedByLava()
                    .sound(SoundType.WOOD)
                    .strength(2.0F)
                    .mapColor(MapColor.COLOR_BROWN)
                    .instrument(NoteBlockInstrument.BASS)));

    RegistryObject<Block> CANOPY_TREE_FOLIAGE = BLOCKS.register("canopy_tree_foliage", () -> new Block(
            BlockBehaviour.Properties.of()
                    .ignitedByLava()
                    .mapColor(MapColor.PLANT)
                    .sound(SoundType.AZALEA_LEAVES)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(0.5f, 0.2f)) {
        @Override
        public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
            return true;
        }
    });

    RegistryObject<Block> CANOPY_TREE_FERN = BLOCKS.register("canopy_tree_fern", () -> new MossBlock(
            BlockBehaviour.Properties.of()
                    .sound(SoundType.MOSS)
                    .strength(0.1F)
                    .mapColor(MapColor.COLOR_GREEN)
                    .pushReaction(PushReaction.DESTROY)){
        @Override
        public boolean canSustainPlant(BlockState state, BlockGetter level, BlockPos pos, Direction facing, IPlantable plantable) { return true; }
    });

    RegistryObject<Block> CANOPY_TREE_TRUNK = BLOCKS.register("canopy_tree_trunk", () -> new SimpleDecorationBlock(
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .ignitedByLava()
                    .sound(SoundType.WOOD)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2f, 10f),
            Block.box(0, 0, 4, 12, 16, 16)));

    RegistryObject<Block> CANOPY_TREE_MUSHROOM = BLOCKS.register("canopy_tree_mushroom", () -> new CustomShapeBlock(
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .ignitedByLava()
                    .sound(SoundType.SHROOMLIGHT)
                    .lightLevel(s -> 5)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(0.5f, 0.2f),
            Shapes.or(Block.box(-4, 8, -4, 20, 16, 20), Block.box(-10, 0, -10, 26, 8, 26))));

    RegistryObject<Block> CANOPY_TREE_DROOPING_ROOT = BLOCKS.register("canopy_tree_drooping_root", () -> new Block(
            BlockBehaviour.Properties.of()
                    .ignitedByLava()
                    .sound(SoundType.WOOD)
                    .mapColor(MapColor.WOOD)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(0.5f, 10f)));


    // 装饰方块
    RegistryObject<Block> TRAY_BLOCK = BLOCKS.register("tray", () -> new VariantDecorationBlock(
            BlockBehaviour.Properties.of()
                    .strength(0.5f, 10f)
                    .noOcclusion()
                    .noLootTable(),
            Block.box(0, 0, 0, 16, 2, 16), 2));

    RegistryObject<Block> DIRT_HOLE = BLOCKS.register("dirt_hole", DirtHoleBlock::new);

    RegistryObject<BlockEntityType<DirtHoleBlockEntity>> DIRT_HOLE_BE = BLOCK_ENTITIES.register("dirt_hole", () -> BlockEntityType.Builder.of(DirtHoleBlockEntity::new, DIRT_HOLE.get()).build(null));

    RegistryObject<Block> KAZI_LUCKY_CAT = BLOCKS.register("kazi_lucky_cat", KaziLuckyCatBlock::new);

    RegistryObject<BlockEntityType<KaziLuckyCatBlockEntity>> KAZI_LUCKY_CAT_BE = BLOCK_ENTITIES.register("kazi_lucky_cat", () -> BlockEntityType.Builder.of(KaziLuckyCatBlockEntity::new, KAZI_LUCKY_CAT.get()).build(null));

    private static boolean never(BlockState state, BlockGetter getter, BlockPos pos) {
        return false;
    }
}
