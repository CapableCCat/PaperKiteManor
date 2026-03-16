package com.kazi_cat.papercraft_magic_decoration.block.decoration;

import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

public class CoffeePastinacaSativaFruitingStemBlock extends HorizontalDirectionalBlock {
    public CoffeePastinacaSativaFruitingStemBlock() {
        super(BlockBehaviour.Properties.of()
                .ignitedByLava()
                .instabreak()
                .sound(SoundType.LILY_PAD)
                .noCollission()
                .pushReaction(PushReaction.DESTROY));
    }
}
