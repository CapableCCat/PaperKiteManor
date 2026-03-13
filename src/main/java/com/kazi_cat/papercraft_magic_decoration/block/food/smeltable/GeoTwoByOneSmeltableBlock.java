package com.kazi_cat.papercraft_magic_decoration.block.food.smeltable;

import com.kazi_cat.papercraft_magic_decoration.api.IBlockGeoModelProvider;
import com.kazi_cat.papercraft_magic_decoration.blockentity.food.smeltable.GeoSmeltableBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.client.model.PathGeoModel;
import com.kazi_cat.papercraft_magic_decoration.client.model.PathGeoModelFactory;
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
    public GeoTwoByOneSmeltableBlock(Properties properties, VoxelShape frontShape, VoxelShape behindShape, int cookingTime, int maxFlipCount,
                                     int flipCooldown, ResourceLocation modelUid, Supplier<ItemStack> rawSupplier, Supplier<ItemStack> resultSupplier) {
        super(properties, frontShape, behindShape, cookingTime, maxFlipCount, flipCooldown, rawSupplier, resultSupplier);
        // 注册 GeckoLib 模型
        PathGeoModelFactory.put(GeoSmeltableBlockEntity.class, modelUid, "cooked",
                PathGeoModel.create(GeoSmeltableBlockEntity.class)
                        .mapper(PathGeoModelFactory.blockMapper())
                        .texturePrefix("block/").build().get()
        );
        PathGeoModelFactory.put(GeoSmeltableBlockEntity.class, modelUid, "raw",
                PathGeoModel.create(GeoSmeltableBlockEntity.class)
                        .mapper(PathGeoModelFactory.prefixedBlockMapper("raw_"))
                        .texturePrefix("block/").build().get()
        );
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
        if (animatable instanceof GeoSmeltableBlockEntity smeltable) {
            if (smeltable.getBlockState().getValue(COOKED)) {
                return PathGeoModelFactory.get(GeoSmeltableBlockEntity.class, getKey(), "cooked");
            } else {
                return PathGeoModelFactory.get(GeoSmeltableBlockEntity.class, getKey(), "raw");
            }
        }
        return null;
    }

    protected ResourceLocation getKey() {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(this));
    }
}
