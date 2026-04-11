package com.kazi_cat.papercraft_magic_decoration.compat.jei;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.compat.jei.category.DistillerRecipeCategory;
import com.kazi_cat.papercraft_magic_decoration.compat.jei.category.MixologyRecipeCategory;
import com.kazi_cat.papercraft_magic_decoration.compat.jei.category.PaperCuttingRecipeCategory;
import com.kazi_cat.papercraft_magic_decoration.compat.jei.category.PaperMakingRecipeCategory;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class ModJeiPlugin implements IModPlugin {
    private static final ResourceLocation UID = PaperKiteManor.resourceLocation("jei");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new PaperMakingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MixologyRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new PaperCuttingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new DistillerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(PaperMakingRecipeCategory.TYPE, PaperMakingRecipeCategory.getRecipes());
        registration.addRecipes(MixologyRecipeCategory.TYPE, MixologyRecipeCategory.getRecipes());
        registration.addRecipes(PaperCuttingRecipeCategory.TYPE, PaperCuttingRecipeCategory.getRecipes());
        registration.addRecipes(DistillerRecipeCategory.TYPE, DistillerRecipeCategory.getRecipes());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ModItems.AMETHYST_SCISSORS.get(), PaperMakingRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModItems.COPPER_BARTENDER.get(), MixologyRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModItems.PAPER_CUTTING_TABLE.get(), PaperCuttingRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModItems.COPPER_STILL.get(), DistillerRecipeCategory.TYPE);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }
}
