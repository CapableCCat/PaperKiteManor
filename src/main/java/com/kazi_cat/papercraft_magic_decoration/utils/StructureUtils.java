package com.kazi_cat.papercraft_magic_decoration.utils;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;

public class StructureUtils {
    /**
     * 在指定位置放置一个结构
     *
     * @param level         服务端世界对象
     * @param structureId   结构的资源路径
     * @param pos           结构的中心
     * @param rotation      旋转角度
     * @param mirror        是否镜像
     * @param ignoreAir     是否忽略结构中的空气
     * @param centered      是否以传入的位置为放置结构的中心位置
     * @return              如果放置成功返回true，否则返回false
     */
    public static boolean placeStructure(ServerLevel level, ResourceLocation structureId, BlockPos pos,
                                         Rotation rotation, Mirror mirror, boolean ignoreAir, boolean centered) {
        StructureTemplateManager manager = level.getStructureManager();

        Optional<StructureTemplate> templateOptional = manager.get(structureId);
        if (templateOptional.isEmpty()) {
            PaperKiteManor.LOGGER.error("无法加载结构: {}", structureId);
            return false;
        }
        StructureTemplate template = templateOptional.get();

        StructurePlaceSettings settings = new StructurePlaceSettings()
                .setRotation(rotation)
                .setMirror(mirror)
                .setRandom(level.getRandom())
                .addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);

        if (ignoreAir) {
            settings.addProcessor(BlockIgnoreProcessor.AIR);
        }

        if (centered) {
            pos = pos.offset(-template.getSize().getX() / 2, 0, -template.getSize().getZ() / 2);
        }

        template.placeInWorld(level, pos, pos, settings, level.getRandom(), 3);

        return true;
    }

    public static boolean placeStructure(ServerLevel level, ResourceLocation structureId, BlockPos pos, boolean centered) {
        return placeStructure(level, structureId, pos, Rotation.NONE, Mirror.NONE, true, centered);
    }
}

