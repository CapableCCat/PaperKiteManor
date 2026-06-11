package com.kazi_cat.papercraft_magic_decoration.init.registry;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.block.drink.BoxedDrinkBlock;
import com.kazi_cat.papercraft_magic_decoration.block.drink.DrinkBlock;
import com.kazi_cat.papercraft_magic_decoration.item.DrinkBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = PaperKiteManor.MOD_ID)
public class CommonRegistry {
    @SubscribeEvent
    public static void onSetupEvent(FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public static void onBlockRegistryEvent(RegisterEvent event) {
        if (event.getRegistryKey().equals(ForgeRegistries.Keys.BLOCKS)) {
            DrinkRegistry.DRINK_DATA_MAP.forEach(((resourceLocation, data) -> {
                    event.register(ForgeRegistries.Keys.BLOCKS, resourceLocation, () -> switch (data.getBlockType()) {
                        case SIMPLE -> new DrinkBlock(data.getProperties(), data.getMaxCount(), data.getShapes().toArray(new VoxelShape[0]));
                        case BOXED -> new BoxedDrinkBlock(data.getProperties(), data.getMaxCount(), data.getShapes().toArray(new VoxelShape[0]));
                    });
            }));
        }

        if (event.getRegistryKey().equals(ForgeRegistries.Keys.ITEMS)) {
            DrinkRegistry.DRINK_DATA_MAP.forEach((resourceLocation, data) -> {
                Block block = ForgeRegistries.BLOCKS.getValue(resourceLocation);
                if (block != null) {
                    event.register(ForgeRegistries.Keys.ITEMS, resourceLocation,
                            () -> new DrinkBlockItem(block, data.getFood()));
                }
            });
        }
    }
}
