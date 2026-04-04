package com.kazi_cat.papercraft_magic_decoration.client.render.item;

import com.kazi_cat.papercraft_magic_decoration.client.model.ItemGeoModelManager;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class BaseGeoItemRenderer<T extends Item & GeoAnimatable> extends GeoItemRenderer<T> {
    public BaseGeoItemRenderer(T item) {
        super(item);
    }

    @Override
    public GeoModel<T> getGeoModel() {
        T animatable = this.animatable;
        if (animatable != null) {
            GeoModel<T> geoModel = ItemGeoModelManager.getModel(animatable);
            if (geoModel != null) {
                return geoModel;
            }
        }
        return super.getGeoModel();
    }
}
