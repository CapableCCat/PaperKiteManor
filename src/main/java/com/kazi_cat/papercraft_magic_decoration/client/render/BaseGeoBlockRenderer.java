package com.kazi_cat.papercraft_magic_decoration.client.render;

import com.kazi_cat.papercraft_magic_decoration.client.model.BlockGeoModelManager;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class BaseGeoBlockRenderer<T extends BlockEntity & GeoBlockEntity> extends GeoBlockRenderer<T> {
    public BaseGeoBlockRenderer(BlockEntityType<T> blockEntityType) {
        super(blockEntityType);
    }

    @Override
    public GeoModel<T> getGeoModel() {
        T animatable = this.animatable;
        if (animatable != null && animatable.getLevel() != null) {
            GeoModel<T> geoModel = BlockGeoModelManager.getModel(animatable.getBlockState());
            if (geoModel != null) {
                return geoModel;
            }
        }
        return super.getGeoModel();
    }
}
