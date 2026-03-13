package com.kazi_cat.papercraft_magic_decoration.api;

import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.model.GeoModel;

public interface IBlockGeoModelProvider {
    @Nullable GeoModel<? extends GeoBlockEntity> getModel(GeoBlockEntity animatable);
}
