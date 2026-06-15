package com.kazi_cat.papercraft_magic_decoration.effect;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.common.ForgeMod;

public class SilkyFeelEffect extends BaseEffect {
    public SilkyFeelEffect(int color) {
        super(color);
        this.addAttributeModifier(
                ForgeMod.STEP_HEIGHT_ADDITION.get(),
                "7641666d-31e7-4b43-95ab-d186054516d1",
                0.5,
                AttributeModifier.Operation.ADDITION
        );
    }
}
