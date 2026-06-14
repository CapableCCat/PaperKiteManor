package com.kazi_cat.papercraft_magic_decoration.compat.ponder.scenes;

import com.kazi_cat.papercraft_magic_decoration.block.utility.CopperStillBlock;
import com.kazi_cat.papercraft_magic_decoration.blockentity.CopperStillBlockEntity;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.scene.*;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class CopperStillScenes {
    public static void introduction(SceneBuilder scene, SceneBuildingUtil util) {
        Level level = Minecraft.getInstance().level;
        if (level == null) {
            return;
        }

        VectorUtil vector = util.vector();
        SelectionUtil select = util.select();
        PositionUtil grid = util.grid();

        scene.title("copper_still", "");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();

        Selection wineSel = select.fromTo(3, 1, 2, 3, 2, 2);
        Selection stillSel = select.fromTo(1, 1, 2, 2, 2, 2);
        BlockPos part1 = grid.at(2, 1, 2);
        BlockPos part2 = grid.at(2, 2, 2);
        BlockPos part3 = grid.at(1, 1, 2);
        BlockPos part4 = grid.at(1, 2, 2);

        scene.world().showSection(wineSel, Direction.UP);
        scene.idle(20);
        scene.world().showSection(stillSel, Direction.DOWN);
        scene.idle(20);

        scene.overlay().showText(40).text("").pointAt(vector.blockSurface(part3, Direction.WEST)).placeNearTarget();
        scene.idle(5);
        scene.overlay().showControls(vector.blockSurface(part3, Direction.UP).relative(Direction.UP, 0.2), Pointing.DOWN, 25).rightClick().withItem(ModItems.WHISKEY_RAW.get().getDefaultInstance());
        scene.idle(5);
        scene.world().modifyBlocks(stillSel, s -> s.setValue(CopperStillBlock.STATUS, 1), false);
        scene.idle(50);

        scene.addKeyframe();

        scene.idle(15);
        scene.overlay().showText(40).text("").pointAt(vector.blockSurface(part2, Direction.WEST)).placeNearTarget();
        scene.idle(5);
        scene.overlay().showControls(vector.blockSurface(part2, Direction.EAST).relative(Direction.DOWN, 0.2), Pointing.RIGHT, 25).rightClick().withItem(Items.APPLE.getDefaultInstance());
        scene.idle(5);
        scene.world().modifyBlockEntity(part1, CopperStillBlockEntity.class, be -> {
            for (int i = 0; i < 4; i++) {
                be.addIngredient(level, null, Items.APPLE.getDefaultInstance());
            }
        });
        scene.idle(50);

        scene.addKeyframe();

        scene.idle(15);
        scene.overlay().showText(40).text("").pointAt(vector.blockSurface(part1, Direction.WEST)).placeNearTarget();
        scene.idle(5);
        scene.overlay().showControls(vector.blockSurface(part1, Direction.EAST).relative(Direction.DOWN, 0.2), Pointing.RIGHT, 25).rightClick().withItem(Items.COAL.getDefaultInstance());
        scene.idle(5);
        scene.world().modifyBlocks(stillSel, s -> s.setValue(CopperStillBlock.STATUS, 2), false);
        scene.idle(50);

        scene.addKeyframe();

        scene.idle(15);
        scene.overlay().showText(60).text("").placeNearTarget();
        scene.idle(5);
        scene.overlay().showControls(vector.topOf(part2).relative(Direction.UP, 0.5), Pointing.DOWN, 40).withItem(Items.CLOCK.getDefaultInstance());
        scene.idle(70);
        scene.world().modifyBlocks(stillSel, s -> s.setValue(CopperStillBlock.STATUS, 3), false);
        scene.idle(15);

        scene.addKeyframe();

        scene.idle(15);

        scene.overlay().showText(60).text("").pointAt(vector.blockSurface(part3, Direction.WEST)).placeNearTarget();
        scene.idle(10);
        scene.overlay().showControls(vector.blockSurface(part3, Direction.EAST).relative(Direction.DOWN, 0.2), Pointing.RIGHT, 20).rightClick();
        scene.idle(5);
        scene.world().modifyBlocks(stillSel, s -> s.setValue(CopperStillBlock.STATUS, 0), false);
        scene.idle(25);
        scene.overlay().showControls(vector.blockSurface(part3, Direction.EAST).relative(Direction.DOWN, 0.2), Pointing.RIGHT, 20).withItem(ModItems.BOTTLE_OF_LAND_NO1.get().getDefaultInstance());
        scene.idle(40);
        scene.markAsFinished();
    }
}
