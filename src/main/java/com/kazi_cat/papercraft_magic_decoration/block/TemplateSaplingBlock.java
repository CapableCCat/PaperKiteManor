package com.kazi_cat.papercraft_magic_decoration.block;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.utils.StructureUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
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
    private final ResourceLocation[] treeTemplates;

    public TemplateSaplingBlock(Properties properties, ResourceLocation[] treeTemplates) {
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
            ResourceLocation structureId = treeTemplates[random.nextInt(treeTemplates.length)];
            StructureTemplateManager manager = level.getStructureManager();

            Optional<StructureTemplate> templateOptional = manager.get(structureId);
            if (templateOptional.isEmpty()) {
                PaperKiteManor.LOGGER.error("无法加载结构: {}", structureId);
                return;
            }
            StructureTemplate template = templateOptional.get();

            BlockPos start = pos.offset(-template.getSize().getX() / 2, 0, -template.getSize().getZ() / 2);
            // 检查空间是否足够
            for (int x = 0; x < template.getSize().getX(); x++) {
                for (int y = 0; y < template.getSize().getY(); y++) {
                    for (int z = 0; z < template.getSize().getZ(); z++) {
                        BlockPos checkPos = start.offset(x, y, z);
                        BlockState existingState = level.getBlockState(checkPos);
                        if (!existingState.canBeReplaced() && !existingState.is(this)) {
                            return;
                        }
                    }
                }
            }

            level.removeBlock(pos, false);
            StructureUtils.placeStructure(level, structureId, pos, true);
        }
    }
}
