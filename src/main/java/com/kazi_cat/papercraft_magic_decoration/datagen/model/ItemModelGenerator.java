package com.kazi_cat.papercraft_magic_decoration.datagen.model;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PaperKiteManor.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.SMOKED_SALMON_HEAD.get());
        basicItem(ModItems.CHUNKY_SALMON.get());
        basicItem(ModItems.CHUNKY_SMOKED_SALMON.get());
        basicItem(ModItems.JUMBO_SALMON.get());
        basicItem(ModItems.MONSTER_STEAK.get());
        basicItem(ModItems.LARGE_STEAK.get());
        basicItem(ModItems.CUBED_SAUSAGE.get());
        withExistingParent("sausage_mace_weapon", modLoc("displaysettings/sausage_mace_weapon"))
                .texture("layer0", "papercraft_magic_decoration:block/sausage_mace_weapon");
        withExistingParent("raw_sausage_mace_weapon", modLoc("displaysettings/sausage_mace_weapon"))
                .texture("layer0", "papercraft_magic_decoration:block/raw_sausage_mace_weapon");
        withExistingParent("manga_meat", modLoc("displaysettings/manga_meat"))
                .texture("layer0", "papercraft_magic_decoration:block/manga_meat_grill");
        withExistingParent("raw_manga_meat", modLoc("displaysettings/manga_meat"))
                .texture("layer0", "papercraft_magic_decoration:block/raw_manga_meat_grill");
    }

    public void handheldItem(ResourceLocation item) {
        getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", new ResourceLocation(item.getNamespace(), "item/" + item.getPath()));
    }
}
