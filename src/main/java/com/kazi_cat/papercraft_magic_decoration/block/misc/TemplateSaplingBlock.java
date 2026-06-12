package com.kazi_cat.papercraft_magic_decoration.block.misc;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.utils.StructureUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import javax.annotation.Nullable;
import java.util.Optional;

public class TemplateSaplingBlock extends SaplingBlock {
    private final Entry[] treeTemplates;

    public TemplateSaplingBlock(Properties properties, Entry... treeTemplates) {
        super(new AbstractTreeGrower() {
            @Nullable
            @Override
            protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
                return null;
            }
        }, properties);
        this.treeTemplates = treeTemplates;
    }

    @Override
    public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        if (state.getValue(STAGE) == 0) {
            level.setBlock(pos, state.cycle(STAGE), 4);
        } else {
            Entry data = treeTemplates[random.nextInt(treeTemplates.length)];
            StructureTemplateManager manager = level.getStructureManager();

            Optional<StructureTemplate> templateOptional = manager.get(data.id());
            if (templateOptional.isEmpty()) {
                PaperKiteManor.LOGGER.error("无法加载结构: {}", data.id());
                return;
            }
            StructureTemplate template = templateOptional.get();

            // 检查空间是否足够
            for (int y = 0; y < Math.min(data.crownHeight, template.getSize().getY()); y++) {
                for (int x = -data.trunkRadius; x <= data.trunkRadius; x++) {
                    for (int z = -data.trunkRadius; z <= data.trunkRadius; z++) {
                        if (x == 0 && y == 0 && z == 0) {
                            continue;
                        }
                        BlockState existingState = level.getBlockState(pos.offset(x, y, z));
                        if (!existingState.canBeReplaced()) {
                            return;
                        }
                    }
                }
            }

            int xRadius = Mth.ceil(template.getSize().getX() / 2D);
            int zRadius = Mth.ceil(template.getSize().getX() / 2D);
            for (int y = data.crownHeight; y < template.getSize().getY(); y++) {
                for (int x = -xRadius; x <= xRadius; x++) {
                    for (int z = -zRadius; z <= zRadius; z++) {
                        BlockState existingState = level.getBlockState(pos.offset(x, y, z));
                        if (!existingState.canBeReplaced()) {
                            return;
                        }
                    }
                }
            }

            level.removeBlock(pos, false);
            StructureUtils.placeStructure(level, data.id(), pos, true);
        }
    }

    public record Entry(ResourceLocation id, int crownHeight, int trunkRadius) {
        public static Entry of(ResourceLocation id, int crownHeight, int trunkRadius) {
            return new Entry(id, crownHeight, trunkRadius);
        }
    }
}
