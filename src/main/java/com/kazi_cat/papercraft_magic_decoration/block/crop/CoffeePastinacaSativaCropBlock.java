package com.kazi_cat.papercraft_magic_decoration.block.crop;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import com.kazi_cat.papercraft_magic_decoration.utils.StructureUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;

@SuppressWarnings("deprecation")
public class CoffeePastinacaSativaCropBlock extends CropBlock {
    public static final ResourceLocation COFFEE_PASTINACA_SATIVA = PaperKiteManor.modLoc("coffee_pastinaca_sativa");
    public static final int MAX_AGE = 2;

    public CoffeePastinacaSativaCropBlock() {
        super(BlockBehaviour.Properties.of()
                .instabreak()
                .randomTicks()
                .sound(SoundType.CROP)
                .mapColor(MapColor.PLANT));

        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hitResult) {
        ItemStack itemInHand = player.getItemInHand(hand);
        if (itemInHand.is(Items.BONE_MEAL) && getAge(state) == MAX_AGE && level instanceof ServerLevel serverLevel) {
            if (tryPlaceStructure(serverLevel, pos)) {
                if (!player.isCreative()) {
                    itemInHand.split(1);
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, level, pos, player, hand, hitResult);
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected int getBonemealAgeIncrease(Level level) {
        return 1;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.COFFEE_FRUIT.get();
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(ModBlocks.DIRT_HOLE.get());
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isAreaLoaded(pos, 2)) {
            return;
        }
        if (random.nextFloat() < 0.5) {
            if (getAge(state) < MAX_AGE) {
                super.randomTick(state, level, pos, random);
            } else {
                if (random.nextFloat() < 0.25) {
                    if (getAge(state) >= MAX_AGE) {
                        tryPlaceStructure(level, pos);
                    }
                }
            }
        }
    }

    protected boolean tryPlaceStructure(ServerLevel level, BlockPos pos) {
        for (int x = -1; x < 2; x++) {
            for (int z = -1; z < 2; z++) {
                for (int y = -1; y < 1; y++) {
                    if (x == 0 && y == 0 && z == 0) continue;
                    if (!level.getBlockState(pos.subtract(new Vec3i(x, y, z))).canBeReplaced()) {
                        return false;
                    }
                }
            }
        }
        level.removeBlock(pos, false);
        StructureUtils.placeStructure(level, COFFEE_PASTINACA_SATIVA, pos, true);
        return true;
    }
}
