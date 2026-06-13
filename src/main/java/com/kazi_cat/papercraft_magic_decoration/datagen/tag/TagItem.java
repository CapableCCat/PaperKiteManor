package com.kazi_cat.papercraft_magic_decoration.datagen.tag;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import com.kazi_cat.papercraft_magic_decoration.init.tag.TagMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TagItem extends ItemTagsProvider {
    public TagItem(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider,
                   CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, PaperKiteManor.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(TagMod.MAGIC_PAPER)
                .add(ModItems.WHITE_PAPER.get(), ModItems.BLUE_PAPER.get(),
                        ModItems.BLACK_PAPER.get(), ModItems.RED_PAPER.get(),
                        ModItems.YELLOW_PAPER.get(), ModItems.DEWY_MEMBRANE.get(),
                        ModItems.COTTON_SERGE.get());
    }
}
