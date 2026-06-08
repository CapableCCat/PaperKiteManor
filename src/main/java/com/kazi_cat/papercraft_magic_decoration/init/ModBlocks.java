package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.block.smeltable.SausageMaceWeaponBlock;
import com.kazi_cat.papercraft_magic_decoration.blockentity.AnimatedSmeltableBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.blockentity.SmeltableBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings({"DataFlowIssue"})
public interface ModBlocks {
    DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PaperKiteManor.MOD_ID);
    DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, PaperKiteManor.MOD_ID);

    RegistryObject<Block> SAUSAGE_MACE_WEAPON_BLOCK = BLOCKS.register("sausage_mace_weapon", () -> new SausageMaceWeaponBlock(
            BlockBehaviour.Properties.of()
                    .sound(SoundType.SHROOMLIGHT)
                    .strength(1f, 4f)
                    .noOcclusion()
    ));

//    RegistryObject<BlockEntityType<SmeltableBlockEntity>> SMELTABLE_BE = BLOCK_ENTITIES.register(
//            "smeltable_block", () -> BlockEntityType.Builder
//                    .of(SmeltableBlockEntity::new,
//                    ).build(null)
//    );

    RegistryObject<BlockEntityType<AnimatedSmeltableBlockEntity>> ANIMATED_SMELTABLE_BE = BLOCK_ENTITIES.register(
            "animated_smeltable_block", () -> BlockEntityType.Builder
                    .of(AnimatedSmeltableBlockEntity::new,
                            SAUSAGE_MACE_WEAPON_BLOCK.get()
                    ).build(null)
    );
}
