package com.kazi_cat.papercraft_magic_decoration.item.tool;

import com.kazi_cat.papercraft_magic_decoration.block.decoration.DirtHoleBlock;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class GardenTrowelItem extends ShovelItem {
    public GardenTrowelItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
        super(tier, attackDamage, attackSpeed, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();

        if (FLATTENABLES.containsKey(level.getBlockState(pos).getBlock())) {
            digHole(level, pos);
            if (player != null && !player.isCreative()) {
                context.getItemInHand().hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(context.getHand()));
            }
            return InteractionResult.SUCCESS;
        }

        return super.useOn(context);
    }

    protected void digHole(Level level, BlockPos pos) {
        FluidState fluidState = level.getFluidState(pos);
        level.setBlockAndUpdate(pos, ModBlocks.DIRT_HOLE.get().defaultBlockState()
                .setValue(DirtHoleBlock.WATERLOGGED, fluidState.getType() == Fluids.WATER));
    }
}
