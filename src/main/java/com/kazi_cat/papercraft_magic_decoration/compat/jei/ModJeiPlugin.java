package com.kazi_cat.papercraft_magic_decoration.compat.jei;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.compat.jei.category.*;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

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
        registration.addRecipeCategories(new SmeltableBlockCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(PaperMakingRecipeCategory.TYPE, PaperMakingRecipeCategory.getRecipes());
        registration.addRecipes(MixologyRecipeCategory.TYPE, MixologyRecipeCategory.getRecipes());
        registration.addRecipes(PaperCuttingRecipeCategory.TYPE, PaperCuttingRecipeCategory.getRecipes());
        registration.addRecipes(DistillationRecipeCategory.TYPE, DistillationRecipeCategory.getRecipes());
        registration.addRecipes(SmeltableBlockCategory.TYPE, SmeltableBlockCategory.getRecipes());
        registerItemStackInfos(registration);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ModItems.AMETHYST_SCISSORS.get(), PaperMakingRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModItems.COPPER_BARTENDER.get(), MixologyRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModItems.PAPER_CUTTING_TABLE.get(), PaperCuttingRecipeCategory.TYPE);
        registration.addRecipeCatalyst(ModItems.COPPER_STILL.get(), DistillationRecipeCategory.TYPE);
        registration.addRecipeCatalyst(Items.CAMPFIRE, SmeltableBlockCategory.TYPE);
    }

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    private void registerItemStackInfos(IRecipeRegistration registration) {
        registerSimpleItemInfo(registration, ModItems.VITALITY_SPORES.get());
        registerSimpleItemInfo(registration, ModItems.GIFT_FROM_KAZI_MANOR.get());
        registerSimpleItemInfo(registration, ModItems.CUBED_SAUSAGE.get());
        registerSimpleItemInfo(registration, ModItems.LARGE_STEAK.get());
        registerSimpleItemInfo(registration, ModItems.GLOW_CASHEWS.get());
        registerSimpleItemInfo(registration, ModItems.COFFEE_FRUIT.get());
        registerSimpleItemInfo(registration, ModItems.GOLDEN_COFFEE_FRUIT.get());
        registerSimpleItemInfo(registration, ModItems.PAPER_FOLD_TIGER.get());
        registerSimpleItemInfo(registration, ModItems.COPPER_BARTENDER.get());
    }

    private void registerSimpleItemInfo(IRecipeRegistration registration, Item item) {
        ResourceLocation itemKey = ForgeRegistries.ITEMS.getKey(item);
        if (itemKey == null) {
            return;
        }
        registration.addItemStackInfo(item.getDefaultInstance(), Component
                .translatable("jei.papercraft_magic_decoration.item_info.%s".formatted(itemKey.getPath()))
                .withStyle(Style.EMPTY.withColor(0x555555)));
    }
}
