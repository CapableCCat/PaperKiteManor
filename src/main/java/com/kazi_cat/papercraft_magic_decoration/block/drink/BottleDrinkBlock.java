package com.kazi_cat.papercraft_magic_decoration.block.drink;

import com.kazi_cat.papercraft_magic_decoration.block.decoration.PickableDecorationBlock;
import com.kazi_cat.papercraft_magic_decoration.utils.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class BottleDrinkBlock extends PickableDecorationBlock {
    public BottleDrinkBlock(Properties properties, Supplier<List<ItemStack>> pickupLoot, VoxelShape shape) {
        super(properties, pickupLoot, shape);
    }

    public BottleDrinkBlock(Supplier<List<ItemStack>> pickupLoot, VoxelShape shape) {
        super(Properties.of()
                .noOcclusion()
                .instabreak()
                .pushReaction(PushReaction.DESTROY)
                .sound(SoundType.GLASS), pickupLoot, shape);
    }

    @Override
    public void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!level.isClientSide) {
            BlockPos pos = hit.getBlockPos();
            if (projectile.mayInteract(level, pos)) {
                level.removeBlock(pos, false);
                int id = Block.getId(this.defaultBlockState());
                level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, id);
                Vec3 dropPos = pos.getCenter();
                for (var stack : pickupLoot.get()) {
                    ItemUtils.spawnItemEntity(level, dropPos, stack);
                }
            }
        }
    }
}
