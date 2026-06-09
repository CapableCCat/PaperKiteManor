package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.block.decoration.PickableDecorationBlock;
import com.kazi_cat.papercraft_magic_decoration.block.decoration.VariantDecorationBlock;
import com.kazi_cat.papercraft_magic_decoration.block.smeltable.*;
import com.kazi_cat.papercraft_magic_decoration.blockentity.AnimatedSmeltableBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.blockentity.SmeltableBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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
                    .noOcclusion()
    ));

    RegistryObject<Block> MANGA_MEAT = BLOCKS.register("manga_meat", () -> new MangaMeatBlock(
            BlockBehaviour.Properties.of()
                    .sound(SoundType.SHROOMLIGHT)
                    .strength(1f, 4f)
                    .noOcclusion()
    ));

    RegistryObject<Block> SALMON_HEAD = BLOCKS.register("salmon_head", () -> new TwoByOneSmeltableBlock(
            BlockBehaviour.Properties.of()
                    .sound(SoundType.SHROOMLIGHT)
                    .strength(1f, 4f)
                    .noOcclusion(),
            Block.box(0, 0, 0, 16, 20, 16),
            Block.box(0, 0, 0, 16, 20, 16)
    ));

    RegistryObject<Block> CHUNKY_SALMON = BLOCKS.register("chunky_salmon", () -> new ChunkySalmonBlock(
            BlockBehaviour.Properties.of()
                    .sound(SoundType.SHROOMLIGHT)
                    .strength(1f, 4f)
                    .noOcclusion()
    ));

    RegistryObject<Block> MONSTER_STEAK = BLOCKS.register("monster_steak", () -> new MonsterSteakBlock(
            BlockBehaviour.Properties.of()
                    .sound(SoundType.SHROOMLIGHT)
                    .strength(1f, 4f)
                    .noOcclusion()
    ));

    RegistryObject<BlockEntityType<SmeltableBlockEntity>> SMELTABLE_BE = BLOCK_ENTITIES.register(
            "smeltable_block", () -> BlockEntityType.Builder
                    .of(SmeltableBlockEntity::new,
                            SALMON_HEAD.get(),
                            CHUNKY_SALMON.get(),
                            MONSTER_STEAK.get()
                    ).build(null)
    );

    RegistryObject<BlockEntityType<AnimatedSmeltableBlockEntity>> ANIMATED_SMELTABLE_BE = BLOCK_ENTITIES.register(
            "animated_smeltable_block", () -> BlockEntityType.Builder
                    .of(AnimatedSmeltableBlockEntity::new,
                            SAUSAGE_MACE_WEAPON_BLOCK.get(),
                            MANGA_MEAT.get()
                    ).build(null)
    );

    RegistryObject<Block> BUCKET_OF_FRIED_CHICKEN = BLOCKS.register("bucket_of_fried_chicken", () -> new PickableDecorationBlock(
            BlockBehaviour.Properties.of()
                    .sound(SoundType.SNOW)
                    .strength(1f, 4f)
                    .noOcclusion(),
            () -> List.of(new ItemStack(ModItems.FRIED_CHICKEN_LEG.get(), 4)),
            Block.box(2, 0, 2, 14, 14, 14)
    ));

    // 装饰方块
    RegistryObject<Block> TRAY_BLOCK = BLOCKS.register("tray", () -> new VariantDecorationBlock(
            BlockBehaviour.Properties.of()
                    .strength(0.5f, 10f)
                    .noOcclusion()
                    .noLootTable(),
            Block.box(0, 0, 0, 16, 2, 16), 2
    ));
}
