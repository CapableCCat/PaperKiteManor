package com.kazi_cat.papercraft_magic_decoration.client.render.block;

import com.kazi_cat.papercraft_magic_decoration.blockentity.food.smeltable.GeoSmeltableBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.client.model.PathGeoModel;
import com.kazi_cat.papercraft_magic_decoration.client.model.PathGeoModelFactory;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class GeoSmeltableBlockRenderer extends GeoBlockRenderer<GeoSmeltableBlockEntity> {
    public static final GeoModel<GeoSmeltableBlockEntity> DEFAULT_MODEL = PathGeoModel.create(GeoSmeltableBlockEntity.class)
            .mapper(PathGeoModelFactory.blockMapper()).build().get();

    public GeoSmeltableBlockRenderer() {
        super(ModBlocks.GEO_SMELTABLE_BE.get());
    }

    @Override
    public GeoModel<GeoSmeltableBlockEntity> getGeoModel() {
        GeoSmeltableBlockEntity blockEntity = this.animatable;
        GeoModel<GeoSmeltableBlockEntity> result = null;
        if (blockEntity != null && blockEntity.getLevel() != null) {
            result = blockEntity.getModel();
        }
        return result != null ? result : DEFAULT_MODEL;
    }
}
