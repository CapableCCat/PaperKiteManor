package com.kazi_cat.papercraft_magic_decoration.block.dispenser;

import com.kazi_cat.papercraft_magic_decoration.block.SporesCollectionPlateBlock;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import com.kazi_cat.papercraft_magic_decoration.utils.ItemUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SporesCollectionPlateBlockDispenseBehavior extends OptionalDispenseItemBehavior {
    private final DispenseItemBehavior original;

    public SporesCollectionPlateBlockDispenseBehavior(DispenseItemBehavior original) {
        this.original = original;
    }

    @Override
    protected ItemStack execute(BlockSource source, ItemStack stack) {
        this.setSuccess(false);
        if (stack.is(Items.GLASS_BOTTLE)) {
            ServerLevel level = source.getLevel();
            Direction facing = source.getBlockState().getValue(DispenserBlock.FACING);
            BlockPos pos = source.getPos().relative(facing, 2);
            BlockState plateState = level.getBlockState(pos);
            if (plateState.getBlock() instanceof SporesCollectionPlateBlock && plateState.getValue(SporesCollectionPlateBlock.FILLED)) {
                stack.shrink(1);
                ItemUtils.spawnItemEntity(level, pos.getCenter(), ModItems.VITALITY_SPORES.get().getDefaultInstance(), Vec3.ZERO);
                level.setBlockAndUpdate(pos, plateState.setValue(SporesCollectionPlateBlock.FILLED, false));
                this.setSuccess(true);
                return stack;
            }
        }
        return original.dispense(source, stack);
    }
}
