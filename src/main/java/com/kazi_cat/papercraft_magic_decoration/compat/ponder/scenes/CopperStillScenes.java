package com.kazi_cat.papercraft_magic_decoration.compat.ponder.scenes;

import net.createmod.ponder.api.scene.*;
import net.minecraft.core.Direction;

public class CopperStillScenes {
    public static void introduction(SceneBuilder scene, SceneBuildingUtil util) {
        VectorUtil vector = util.vector();
        SelectionUtil select = util.select();
        PositionUtil grid = util.grid();

        scene.title("copper_still", "");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();

        Selection stillSel = select.fromTo(2, 1, 2, 3, 2, 2);

        scene.idle(20);
        scene.world().showSection(stillSel, Direction.DOWN);
        scene.idle(20);
    }
}
