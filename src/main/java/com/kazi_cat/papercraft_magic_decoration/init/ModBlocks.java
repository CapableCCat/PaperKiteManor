package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.block.*;
import com.kazi_cat.papercraft_magic_decoration.blockentity.CopperBartenderBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.blockentity.DrinkBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.blockentity.PaperCuttingTableBlockEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings({"DataFlowIssue"})
public interface ModBlocks {
    DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PaperKiteManor.MOD_ID);
    DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, PaperKiteManor.MOD_ID);

    // 饮品方块
    RegistryObject<Block> BLAZE_WHISKEY = BLOCKS.register("blaze_whiskey", DrinkBlock.create().maxCount(1).shapes(
            Block.box(4, 0, 4, 12, 9, 12)
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
}
