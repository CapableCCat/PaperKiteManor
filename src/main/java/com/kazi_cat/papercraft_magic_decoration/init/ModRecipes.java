package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.crafting.recipe.DistillerRecipe;
import com.kazi_cat.papercraft_magic_decoration.crafting.recipe.MixologyRecipe;
import com.kazi_cat.papercraft_magic_decoration.crafting.recipe.PaperCuttingRecipe;
import com.kazi_cat.papercraft_magic_decoration.crafting.recipe.PaperMakingRecipe;
import com.kazi_cat.papercraft_magic_decoration.crafting.serializer.DistillerRecipeSerializer;
import com.kazi_cat.papercraft_magic_decoration.crafting.serializer.MixologyRecipeSerializer;
import com.kazi_cat.papercraft_magic_decoration.crafting.serializer.PaperCuttingRecipeSerializer;
import com.kazi_cat.papercraft_magic_decoration.crafting.serializer.PaperMakingRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = PaperKiteManor.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, PaperKiteManor.MOD_ID);

    public static RegistryObject<RecipeSerializer<?>> PAPERMAKING_SERIALIZER = RECIPE_SERIALIZERS.register("papermaking", PaperMakingRecipeSerializer::new);
    public static RegistryObject<RecipeSerializer<?>> PAPERCUTTING_SERIALIZER = RECIPE_SERIALIZERS.register("papercutting", PaperCuttingRecipeSerializer::new);
    public static RegistryObject<RecipeSerializer<?>> MIXOLOGY_SERIALIZER = RECIPE_SERIALIZERS.register("mixology", MixologyRecipeSerializer::new);
    public static RegistryObject<RecipeSerializer<?>> DISTILLER_SERIALIZER = RECIPE_SERIALIZERS.register("distiller", DistillerRecipeSerializer::new);

    public static RecipeType<PaperMakingRecipe> PAPERMAKING_RECIPE;
    public static RecipeType<PaperCuttingRecipe> PAPERCUTTING_RECIPE;
    public static RecipeType<MixologyRecipe> MIXOLOGY_RECIPE;
    public static RecipeType<DistillerRecipe> DISTILLER_RECIPE;

    @SubscribeEvent
    public static void register(RegisterEvent evt) {
        if (evt.getRegistryKey().equals(Registries.RECIPE_SERIALIZER)) {
            MIXOLOGY_RECIPE = RecipeType.simple(PaperKiteManor.resourceLocation("mixology"));
            PAPERMAKING_RECIPE = RecipeType.simple(PaperKiteManor.resourceLocation("papermaking"));
            PAPERCUTTING_RECIPE = RecipeType.simple(PaperKiteManor.resourceLocation("papercutting"));
            DISTILLER_RECIPE = RecipeType.simple(PaperKiteManor.resourceLocation("distiller"));
        }
    }
}
