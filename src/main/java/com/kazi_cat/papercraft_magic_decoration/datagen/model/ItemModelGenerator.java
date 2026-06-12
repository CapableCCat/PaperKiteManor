package com.kazi_cat.papercraft_magic_decoration.datagen.model;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import com.kazi_cat.papercraft_magic_decoration.init.registry.DrinkRegistry;
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

        basicItem(ModItems.BOTTLE_OF_BLAZE_WHISKEY.get());
        basicItem(ModItems.BOTTLE_OF_FERRY_WHISKEY.get());
        basicItem(ModItems.BOTTLE_OF_FLY_WHISKEY.get());
        basicItem(ModItems.BOTTLE_OF_LAND_NO1.get());
        basicItem(ModItems.BOTTLE_OF_LUCKY_CACTUS.get());
        basicItem(ModItems.BOTTLE_OF_POISON_RUM.get());
        basicItem(ModItems.TRUFFLE_CHOCOLATE.get());
        basicItem(ModItems.MILK_CHOCOLATE.get());
        basicItem(ModItems.PRALINE_CHOCOLATE.get());
        basicItem(ModItems.DARK_COCOA_IN_MOLD.get());
        basicItem(ModItems.MILK_COCOA_IN_MOLD.get());
        basicItem(ModItems.PRALINE_COCOA_IN_MOLD.get());
        basicItem(ModItems.MELTED_DARK_COCOA_IN_MOLD.get());
        basicItem(ModItems.MELTED_MILK_COCOA_IN_MOLD.get());
        basicItem(ModItems.MELTED_PRALINE_COCOA_IN_MOLD.get());
        basicItem(ModItems.OVERSIZED_BOX_OF_CHOCOLATES.get());
        basicItem(ModItems.WHITE_PAPER.get());
        basicItem(ModItems.BLUE_PAPER.get());
        basicItem(ModItems.BLACK_PAPER.get());
        basicItem(ModItems.RED_PAPER.get());
        basicItem(ModItems.YELLOW_PAPER.get());
        basicItem(ModItems.DEWY_MEMBRANE.get());
        basicItem(ModItems.COTTON_SERGE.get());
        basicItem(ModItems.AMETHYST_SCISSORS.get());
        basicItem(ModItems.WHISKEY_RAW.get());
        basicItem(ModItems.VITALITY_SPORES.get());
        basicItem(ModItems.COFFEE_FRUIT.get());
        basicItem(ModItems.GOLDEN_COFFEE_FRUIT.get());
        basicItem(ModItems.COFFEE_PASTINACA_SATIVA_TUBER.get());
        basicItem(ModItems.COPPER_STILL.get());
        basicItem(ModItems.GLOW_CASHEWS.get());
        basicItem(ModItems.KNITTED_LEOPARD_RUG.get());
        basicItem(ModItems.GIFT_FROM_KAZI_MANOR.get());
        basicItem(ModItems.KEY_UNDER_THE_LAKE.get());

        handheldItem(ModItems.GARDEN_TROWEL.get());

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
        withExistingParent("paper_cutting_table", modLoc("displaysettings/paper_cutting_table"));
        withExistingParent("copper_bartender", modLoc("displaysettings/copper_bartender"));
        withExistingParent("kazi_lucky_cat", modLoc("displaysettings/kazi_lucky_cat"));
        withExistingParent("spores_collection_plate", modLoc("block/spores_collection_plate"));
        withExistingParent("mocha_pot", modLoc("block/mocha_pot"));
        withExistingParent("mini_palm_tree", modLoc("block/mini_palm_tree"));
        withExistingParent("palm_tree_crown", modLoc("block/palm_tree_crown"));
        withExistingParent("palm_tree_top", modLoc("block/palm_tree_top"));
        withExistingParent("palm_tree_trunk", modLoc("block/palm_tree_trunk"));
        withExistingParent("palm_tree_trunk_top", modLoc("block/palm_tree_trunk_top"));
        withExistingParent("rough_palm_tree_trunk", modLoc("block/rough_palm_tree_trunk"));
        withExistingParent("canopy_tree_drooping_root", modLoc("block/canopy_tree_drooping_root"));
        withExistingParent("canopy_tree_fern", modLoc("block/canopy_tree_fern"));
        withExistingParent("canopy_tree_foliage", modLoc("block/canopy_tree_foliage"));
        withExistingParent("canopy_tree_mushroom", modLoc("block/canopy_tree_mushroom"));
        withExistingParent("canopy_tree_trunk", modLoc("block/canopy_tree_trunk"));
        withExistingParent("canopy_tree_limb", modLoc("block/canopy_tree_limb"));
        withExistingParent("mini_canopy_tree", modLoc("block/mini_canopy_tree"));
        withExistingParent("wine_aroma_red_wallpaper_wall", modLoc("block/wine_aroma_red_wallpaper_wall"));
        withExistingParent("wine_aroma_blue_wallpaper_wall", modLoc("block/wine_aroma_blue_wallpaper_wall"));
        withExistingParent("underground_wallpaper_wall", modLoc("block/underground_wallpaper_wall"));
        withExistingParent("rustic_blue_wallpaper_wall", modLoc("block/rustic_blue_wallpaper_wall"));
        withExistingParent("black_and_white_checker_board_tile", modLoc("block/black_and_white_checker_board_tile"));
        withExistingParent("blue_and_white_checker_board_tile", modLoc("block/blue_and_white_checker_board_tile"));
        withExistingParent("underground_panelling", modLoc("block/underground_panelling"));
        withExistingParent("rustic_panelling", modLoc("block/rustic_panelling"));
        withExistingParent("underground_door_frames", modLoc("block/underground_door_frames"));
        withExistingParent("emerald_blue_earth_tile", modLoc("block/emerald_blue_earth_tile"));
        withExistingParent("roses_in_water_bottle", modLoc("block/roses_in_water_bottle"));
        withExistingParent("star_embellished_ceiling", modLoc("block/star_embellished_ceiling"));
        withExistingParent("loud_button", modLoc("displaysettings/loud_button"));
        withExistingParent("low_cabinet_with_tablecloth", modLoc("displaysettings/low_cabinet_with_tablecloth"));
        withExistingParent("wooden_barrel_bookshelf", modLoc("displaysettings/wooden_barrel_bookshelf"));
        withExistingParent("woodworking_table", modLoc("displaysettings/woodworking_table"));
        withExistingParent("long_storage_table", modLoc("displaysettings/long_storage_table"));
        withExistingParent("edged_chalkboard", modLoc("displaysettings/edged_chalkboard"));
        withExistingParent("cupboard", modLoc("block/cupboard"));
        withExistingParent("fireplace_decoration", modLoc("block/fireplace_decoration"));

        DrinkRegistry.DRINK_DATA_MAP.forEach((key, data) -> {
            Item item = ForgeRegistries.ITEMS.getValue(key);
            if (item != null) {
                switch (data.getItemModelType()) {
                    case BASIC -> basicItem(item);
                    case HANDHELD3D -> drinkItem(item);
                }
            }
        });
    }

    public void handheldItem(Item item) {
        ResourceLocation itemKey = ForgeRegistries.ITEMS.getKey(item);
        if (itemKey == null) {
            throw new IllegalArgumentException("Item not registered: " + Item.getId(item));
        }
        getBuilder(itemKey.toString())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", new ResourceLocation(itemKey.getNamespace(), "item/" + itemKey.getPath()));
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
