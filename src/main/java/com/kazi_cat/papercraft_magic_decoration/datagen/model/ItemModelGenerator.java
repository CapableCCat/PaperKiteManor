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
        basicItem(ModItems.COFFEE_FRUIT.get());
        basicItem(ModItems.GOLDEN_COFFEE_FRUIT.get());
        basicItem(ModItems.COFFEE_PASTINACA_SATIVA_TUBER.get());
        basicItem(ModItems.VITALITY_SPORES.get());
        basicItem(ModItems.WHISKEY_RAW.get());
        basicItem(ModItems.COPPER_STILL.get());

        handheldItem(ModItems.GARDEN_TROWEL.get());

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
        withExistingParent("palm_tree_crown", modLoc("block/palm_tree_crown"));
        withExistingParent("palm_tree_top", modLoc("block/palm_tree_top"));
        withExistingParent("palm_tree_trunk", modLoc("block/palm_tree_trunk"));
        withExistingParent("palm_tree_trunk_top", modLoc("block/palm_tree_trunk_top"));
        withExistingParent("rough_palm_tree_trunk", modLoc("block/rough_palm_tree_trunk"));
        withExistingParent("underground_panelling", modLoc("block/underground_panelling"));
        withExistingParent("roses_in_water_bottle", modLoc("block/roses_in_water_bottle"));
        withExistingParent("spores_collection_plate", modLoc("block/spores_collection_plate"));
        withExistingParent("canopy_tree_drooping_root", modLoc("block/canopy_tree_drooping_root"));
        withExistingParent("canopy_tree_fern", modLoc("block/canopy_tree_fern"));
        withExistingParent("canopy_tree_foliage", modLoc("block/canopy_tree_foliage"));
        withExistingParent("canopy_tree_mushroom", modLoc("block/canopy_tree_mushroom"));
        withExistingParent("canopy_tree_trunk", modLoc("block/canopy_tree_trunk"));
        withExistingParent("canopy_tree_limb", modLoc("block/canopy_tree_limb"));
        withExistingParent("star_embellished_ceiling", modLoc("block/star_embellished_ceiling"));
        withExistingParent("wine_aroma_red_wallpaper_wall", modLoc("block/wine_aroma_red_wallpaper_wall"));
        withExistingParent("wine_aroma_blue_wallpaper_wall", modLoc("block/wine_aroma_blue_wallpaper_wall"));
        withExistingParent("underground_wallpaper_wall", modLoc("block/underground_wallpaper_wall"));
        withExistingParent("rustic_blue_wallpaper_wall", modLoc("block/rustic_blue_wallpaper_wall"));
        withExistingParent("black_and_white_checker_board_tile", modLoc("block/black_and_white_checker_board_tile"));
        withExistingParent("blue_and_white_checker_board_tile", modLoc("block/blue_and_white_checker_board_tile"));
        withExistingParent("loud_button", modLoc("displaysettings/loud_button"));
        withExistingParent("low_cabinet_with_tablecloth", modLoc("displaysettings/low_cabinet_with_tablecloth"));
        withExistingParent("wooden_barrel_bookshelf", modLoc("displaysettings/wooden_barrel_bookshelf"));
        withExistingParent("woodworking_table", modLoc("displaysettings/woodworking_table"));
        withExistingParent("long_storage_table", modLoc("displaysettings/long_storage_table"));
        withExistingParent("edged_chalkboard", modLoc("displaysettings/edged_chalkboard"));
        withExistingParent("cupboard", modLoc("block/cupboard"));
        withExistingParent("fireplace_decoration", modLoc("block/fireplace_decoration"));
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
