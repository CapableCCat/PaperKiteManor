package com.kazi_cat.papercraft_magic_decoration.init.registry;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.block.dispenser.SporesCollectionPlateBlockDispenseBehavior;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = PaperKiteManor.MOD_ID)
public class CommonRegistry {
    @SubscribeEvent
    public static void onSetupEvent(FMLCommonSetupEvent event) {
        event.enqueueWork(CommonRegistry::addDispenserBehavior);
    }

    private static void addDispenserBehavior() {
        DispenserBlock.registerBehavior(Items.GLASS_BOTTLE, new SporesCollectionPlateBlockDispenseBehavior(DispenserBlock.DISPENSER_REGISTRY.get(Items.GLASS_BOTTLE)));
    }
}
