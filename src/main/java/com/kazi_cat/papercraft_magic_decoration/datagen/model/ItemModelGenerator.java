package com.kazi_cat.papercraft_magic_decoration.datagen.model;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

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
        basicItem(ModItems.BREADED_RAW_CHICKEN.get());
        basicItem(ModItems.FRIED_CHICKEN_LEG.get());

        drinkItem(ModItems.BLAZE_WHISKEY.get());
        drinkItem(ModItems.FERRY_WHISKEY.get());
        drinkItem(ModItems.FLY_WHISKEY.get());
        drinkItem(ModItems.LAND_NO1.get());
        drinkItem(ModItems.LUCKY_CACTUS.get());
        drinkItem(ModItems.POISON_RUM.get());
        drinkItem(ModItems.BLOODY_MARY.get());
        drinkItem(ModItems.DIPLOMATICO_COFFEE.get());
        drinkItem(ModItems.DEVIL_MARGARITA.get());
        drinkItem(ModItems.DIONYSUS.get());
        drinkItem(ModItems.KALEIDOSCOPE_WHISKEY_SOUR.get());
        drinkItem(ModItems.LONG_ISLAND_POPSICLE_TEA.get());
        drinkItem(ModItems.GUANG_S.get());

        basicItem(ModItems.NOCTURNAL_CAT_COFFEE.get());
        basicItem(ModItems.GOLD_MEDAL_COFFEE.get());
        basicItem(ModItems.WHITE_RABBIT_MOCHA.get());
        basicItem(ModItems.BOTTLE_OF_BLAZE_WHISKEY.get());
        basicItem(ModItems.BOTTLE_OF_FERRY_WHISKEY.get());
        basicItem(ModItems.BOTTLE_OF_FLY_WHISKEY.get());
        basicItem(ModItems.BOTTLE_OF_LAND_NO1.get());
        basicItem(ModItems.BOTTLE_OF_LUCKY_CACTUS.get());
        basicItem(ModItems.BOTTLE_OF_POISON_RUM.get());
        basicItem(ModItems.WHITE_PAPER.get());
        basicItem(ModItems.BLUE_PAPER.get());
        basicItem(ModItems.BLACK_PAPER.get());
        basicItem(ModItems.RED_PAPER.get());
        basicItem(ModItems.YELLOW_PAPER.get());
        basicItem(ModItems.DEWY_MEMBRANE.get());
        basicItem(ModItems.COTTON_SERGE.get());
        basicItem(ModItems.AMETHYST_SCISSORS.get());

        withExistingParent("sausage_mace_weapon", modLoc("displaysettings/sausage_mace_weapon"))
                .texture("layer0", "papercraft_magic_decoration:block/sausage_mace_weapon");
        withExistingParent("raw_sausage_mace_weapon", modLoc("displaysettings/sausage_mace_weapon"))
                .texture("layer0", "papercraft_magic_decoration:block/raw_sausage_mace_weapon");
        withExistingParent("manga_meat", modLoc("displaysettings/manga_meat"))
                .texture("layer0", "papercraft_magic_decoration:block/manga_meat_grill");
        withExistingParent("raw_manga_meat", modLoc("displaysettings/manga_meat"))
                .texture("layer0", "papercraft_magic_decoration:block/raw_manga_meat_grill");
        withExistingParent("bucket_of_fried_chicken", modLoc("block/bucket_of_fried_chicken"));
        withExistingParent("pack_of_guang_s", modLoc("block/drink/guang_s/count4_boxed"));
        withExistingParent("white_paper_block", modLoc("block/white_paper_block"));
        withExistingParent("blue_paper_block", modLoc("block/blue_paper_block"));
        withExistingParent("black_paper_block", modLoc("block/black_paper_block"));
        withExistingParent("red_paper_block", modLoc("block/red_paper_block"));
        withExistingParent("yellow_paper_block", modLoc("block/yellow_paper_block"));
        withExistingParent("dewy_membrane_block", modLoc("block/dewy_membrane_block"));
        withExistingParent("cotton_serge_block", modLoc("block/cotton_serge_block"));
    }

    public void handheldItem(ResourceLocation item) {
        getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", new ResourceLocation(item.getNamespace(), "item/" + item.getPath()));
    }

    public void drinkItem(Item item) {
        ResourceLocation itemKey = ForgeRegistries.ITEMS.getKey(item);
        if (itemKey == null) {
            throw new IllegalArgumentException("Item not registered: " + Item.getId(item));
        }
        String name = itemKey.getPath();
        ItemModelBuilder blockModel = new ItemModelBuilder(modLoc(name), this.existingFileHelper)
                .parent(new ModelFile.UncheckedModelFile(modLoc("block/drink/%s/handheld".formatted(name))));
        ItemModelBuilder flatModel = new ItemModelBuilder(modLoc(name), this.existingFileHelper)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", modLoc("item/%s".formatted(name)));
        getBuilder(name)
                .guiLight(BlockModel.GuiLight.FRONT)
                .customLoader(SeparateTransformsModelBuilder::begin).base(blockModel)
                .perspective(ItemDisplayContext.GROUND, flatModel)
                .perspective(ItemDisplayContext.GUI, flatModel)
                .perspective(ItemDisplayContext.FIXED, flatModel);
    }
}
