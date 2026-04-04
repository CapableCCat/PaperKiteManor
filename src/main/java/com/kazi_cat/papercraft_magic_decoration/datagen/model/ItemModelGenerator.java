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

import java.util.Objects;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PaperKiteManor.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        handheld3DDrinkItem("blaze_whiskey");
        handheld3DDrinkItem("ferry_whiskey");
        handheld3DDrinkItem("fly_whiskey");
        handheld3DDrinkItem("land_no1");
        handheld3DDrinkItem("lucky_cactus");
        handheld3DDrinkItem("poison_rum");
        handheld3DDrinkItem("bloody_mary");
        handheld3DDrinkItem("diplomatico_coffee");
        handheld3DDrinkItem("devil_margarita");
        handheld3DDrinkItem("guang_s");

        basicItem(ModItems.NOCTURNAL_CAT_COFFEE.get());
        basicItem(ModItems.GOLD_MEDAL_COFFEE.get());
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

        withExistingParent("pack_of_guang_s", modLoc("block/drink/guang_s/count4_boxed"));
        withExistingParent("white_paper_block", modLoc("block/white_paper_block"));
        withExistingParent("blue_paper_block", modLoc("block/blue_paper_block"));
        withExistingParent("black_paper_block", modLoc("block/black_paper_block"));
        withExistingParent("red_paper_block", modLoc("block/red_paper_block"));
        withExistingParent("yellow_paper_block", modLoc("block/yellow_paper_block"));
        withExistingParent("dewy_membrane_block", modLoc("block/dewy_membrane_block"));
        withExistingParent("cotton_serge_block", modLoc("block/cotton_serge_block"));
        withExistingParent("paper_cutting_table", modLoc("displaysettings/paper_cutting_table"));
        withExistingParent("copper_bartender", modLoc("displaysettings/copper_bartender"));
    }

    public ItemModelBuilder handheldItem(Item item) {
        return handheldItem(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)));
    }

    public ItemModelBuilder handheldItem(ResourceLocation item) {
        return getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", new ResourceLocation(item.getNamespace(), "item/" + item.getPath()));
    }

    public void handheld3DItem(String path, ItemModelBuilder blockModel, ItemModelBuilder flatModel) {
        getBuilder(path)
                .guiLight(BlockModel.GuiLight.FRONT)
                .customLoader(SeparateTransformsModelBuilder::begin).base(blockModel)
                .perspective(ItemDisplayContext.GROUND, flatModel)
                .perspective(ItemDisplayContext.GUI, flatModel)
                .perspective(ItemDisplayContext.FIXED, flatModel);
    }

    public void handheld3DDrinkItem(String name) {
        ItemModelBuilder blockModel = new ItemModelBuilder(modLoc(name), this.existingFileHelper)
                .parent(new ModelFile.UncheckedModelFile(modLoc("block/drink/%s/handheld".formatted(name))));
        ItemModelBuilder flatModel = new ItemModelBuilder(modLoc(name), this.existingFileHelper)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", modLoc("item/%s".formatted(name)));
        handheld3DItem(name, blockModel, flatModel);
    }
}
