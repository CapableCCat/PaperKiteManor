package com.kazi_cat.papercraft_magic_decoration.datagen;

import com.kazi_cat.papercraft_magic_decoration.datagen.recipe.ModRecipeGenerator;
import com.kazi_cat.papercraft_magic_decoration.datagen.tag.TagBlock;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var registries = event.getLookupProvider();
        var vanillaPack = generator.getVanillaPack(true);
        var helper = event.getExistingFileHelper();
        var pack = generator.getPackOutput();

        var block = vanillaPack.addProvider(packOutput -> new TagBlock(packOutput, registries, helper));

        generator.addProvider(event.includeServer(), new ModRecipeGenerator(pack));
    }
}
