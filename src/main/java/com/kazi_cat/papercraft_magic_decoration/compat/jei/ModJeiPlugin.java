package com.kazi_cat.papercraft_magic_decoration.compat.jei;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.compat.jei.category.DistillationRecipeCategory;
import com.kazi_cat.papercraft_magic_decoration.compat.jei.category.MixologyRecipeCategory;
import com.kazi_cat.papercraft_magic_decoration.compat.jei.category.PaperCuttingRecipeCategory;
import com.kazi_cat.papercraft_magic_decoration.compat.jei.category.PaperMakingRecipeCategory;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@JeiPlugin
public class ModJeiPlugin implements IModPlugin {
    private static final ResourceLocation UID = PaperKiteManor.modLoc("jei");

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new PaperMakingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new MixologyRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new PaperCuttingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new DistillationRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(PaperMakingRecipeCategory.TYPE, PaperMakingRecipeCategory.getRecipes());
        registration.addRecipes(MixologyRecipeCategory.TYPE, MixologyRecipeCategory.getRecipes());
        registration.addRecipes(PaperCuttingRecipeCategory.TYPE, PaperCuttingRecipeCategory.getRecipes());
        registration.addRecipes(DistillationRecipeCategory.TYPE, DistillationRecipeCategory.getRecipes());
        registerItemStackInfos(registration);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ModItems.AMETHYST_SCISSORS.get(), PaperMakingRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModItems.COPPER_BARTENDER.get(), MixologyRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModItems.PAPER_CUTTING_TABLE.get(), PaperCuttingRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModItems.COPPER_STILL.get(), DistillationRecipeCategory.TYPE);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    private void registerItemStackInfos(IRecipeRegistration registration) {
        registration.addItemStackInfo(ModItems.VITALITY_SPORES.get().getDefaultInstance(),
                Component.literal("制作一个孢子收集盆,将其放置在孢子花的正下方一格处,就能收集孢子花逸散出的孢子。\n当盆内收集了足够多的孢子后，用玻璃瓶右键收集盆即可获得瓶装源生孢子。").withStyle(ChatFormatting.WHITE));
        registration.addItemStackInfo(ModItems.GIFT_FROM_KAZI_MANOR.get().getDefaultInstance(),
                Component.literal("用紫水晶与黑猫门童交易获取。").withStyle(ChatFormatting.WHITE));
        registration.addItemStackInfo(ModItems.CUBED_SAUSAGE.get().getDefaultInstance(),
                Component.literal("用斧头右键切割烤熟的狼牙棒火腿肠以获取。").withStyle(ChatFormatting.WHITE));
        registration.addItemStackInfo(ModItems.LARGE_STEAK.get().getDefaultInstance(),
                Component.literal("用斧头右键切割烤熟的大肉排以获取。").withStyle(ChatFormatting.WHITE));
        registration.addItemStackInfo(ModItems.GLOW_CASHEWS.get().getDefaultInstance(),
                Component.literal("种植阴幕树，从其上生长的伞腰果植株处收获发光腰果。").withStyle(ChatFormatting.WHITE));
    }
}
