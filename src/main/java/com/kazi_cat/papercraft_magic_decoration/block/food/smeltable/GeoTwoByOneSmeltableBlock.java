package com.kazi_cat.papercraft_magic_decoration.block.food.smeltable;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.api.IBlockGeoModelProvider;
import com.kazi_cat.papercraft_magic_decoration.blockentity.food.smeltable.GeoSmeltableBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.client.model.PathGeoModel;
import com.kazi_cat.papercraft_magic_decoration.client.model.GeoModelFactory;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.model.GeoModel;

import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class GeoTwoByOneSmeltableBlock extends TwoByOneSmeltableBlock implements IBlockGeoModelProvider {
    protected boolean modelRegistered = false;

    public GeoTwoByOneSmeltableBlock(Properties properties, VoxelShape frontShape, VoxelShape behindShape, int cookingTime, int maxFlipCount,
                                     int flipCooldown, Supplier<ItemStack> rawSupplier, Supplier<ItemStack> resultSupplier) {
        super(properties, frontShape, behindShape, cookingTime, maxFlipCount, flipCooldown, rawSupplier, resultSupplier);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        if (blockState.getValue(POSITION) == BEHIND) return null;
        return new GeoSmeltableBlockEntity(blockPos, blockState);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (state.getValue(POSITION) == BEHIND) return null;
        if (state.getValue(COOKED)) return null;
        return createTickerHelper(blockEntityType, ModBlocks.GEO_SMELTABLE_BE.get(),
                (levelIn, blockPos, blockState, smeltable) -> smeltable.tick(levelIn));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public @Nullable GeoModel<? extends GeoBlockEntity> getModel(GeoBlockEntity animatable) {
        if (!modelRegistered) {
            registerModels(getKey());
            modelRegistered = true;
        }

        if (animatable instanceof GeoSmeltableBlockEntity smeltable) {
            if (smeltable.getBlockState().getValue(COOKED)) {
                return GeoModelFactory.get(GeoSmeltableBlockEntity.class, getKey(), "cooked");
            } else {
                return GeoModelFactory.get(GeoSmeltableBlockEntity.class, getKey(), "raw");
            }
        }
        return null;
    }

    // 注册 GeckoLib 模型
    @Override
    public void registerModels(ResourceLocation modelUID) {
        GeoModelFactory.put(GeoSmeltableBlockEntity.class, modelUID, "cooked",
                PathGeoModel.create(GeoSmeltableBlockEntity.class)
                        .mapper(GeoModelFactory.blockMapper())
                        .texturePrefix("block/").build().get()
        );
        GeoModelFactory.put(GeoSmeltableBlockEntity.class, modelUID, "raw",
                PathGeoModel.create(GeoSmeltableBlockEntity.class)
                        .mapper(GeoModelFactory.prefixedBlockMapper("raw_"))
                        .texturePrefix("block/").build().get()
        );
    }

    protected ResourceLocation getKey() {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(this));
    }
}
