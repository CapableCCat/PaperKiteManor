package com.kazi_cat.papercraft_magic_decoration.init.registry;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.block.dispenser.SporesCollectionPlateBlockDispenseBehavior;
import com.kazi_cat.papercraft_magic_decoration.init.ModEntities;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = PaperKiteManor.MOD_ID)
public class CommonRegistry {
    @SubscribeEvent
    public static void onSetupEvent(FMLCommonSetupEvent event) {
        event.enqueueWork(CommonRegistry::addDispenserBehavior);
        event.enqueueWork(CommonRegistry::registerBrewingRecipes);
        event.enqueueWork(CommonRegistry::registerSpawnPlacements);
    }

    private static void addDispenserBehavior() {
        DispenserBlock.registerBehavior(Items.GLASS_BOTTLE, new SporesCollectionPlateBlockDispenseBehavior(DispenserBlock.DISPENSER_REGISTRY.get(Items.GLASS_BOTTLE)));
    }

    private static void registerBrewingRecipes() {
        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(Items.JUNGLE_SAPLING),
                Ingredient.of(ModItems.VITALITY_SPORES.get()),
                ModItems.MINI_PALM_TREE.get().getDefaultInstance()
        );

        BrewingRecipeRegistry.addRecipe(
                Ingredient.of(Items.DARK_OAK_SAPLING),
                Ingredient.of(ModItems.VITALITY_SPORES.get()),
                ModItems.MINI_CANOPY_TREE.get().getDefaultInstance()
        );
    }

    @SuppressWarnings("deprecation")
    private static void registerSpawnPlacements() {
        SpawnPlacements.register(ModEntities.AOAO.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) -> (world.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && world.getRawBrightness(pos, 0) > 8));

        SpawnPlacements.register(ModEntities.WHITE_RABBIT_MAID.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) -> (world.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && world.getRawBrightness(pos, 0) > 8));

        SpawnPlacements.register(ModEntities.BLACK_CAT_LOBBY_BOY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) -> (world.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && world.getRawBrightness(pos, 0) > 8));

        SpawnPlacements.register(ModEntities.AIR_FLOW_CARP.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (entityType, world, reason, pos, random) -> (world.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && world.getRawBrightness(pos, 0) > 8));
    }
}
