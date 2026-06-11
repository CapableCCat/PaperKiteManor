package com.kazi_cat.papercraft_magic_decoration.init.registry;

import com.google.common.collect.Maps;
import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.init.ModFoods;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.compress.utils.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DrinkRegistry {
    public static final Map<ResourceLocation, DrinkData> DRINK_DATA_MAP = Maps.newLinkedHashMap();

    public static ResourceLocation BLAZE_WHISKEY;
    public static ResourceLocation FERRY_WHISKEY;
    public static ResourceLocation FLY_WHISKEY;
    public static ResourceLocation LAND_NO1;
    public static ResourceLocation LUCKY_CACTUS;
    public static ResourceLocation POISON_RUM;
    public static ResourceLocation BLOODY_MARY;
    public static ResourceLocation DIPLOMATICO_COFFEE;
    public static ResourceLocation DEVIL_MARGARITA;
    public static ResourceLocation DIONYSUS;
    public static ResourceLocation KALEIDOSCOPE_WHISKEY_SOUR;
    public static ResourceLocation LONG_ISLAND_POPSICLE_TEA;
    public static ResourceLocation NOCTURNAL_CAT_COFFEE;
    public static ResourceLocation GOLD_MEDAL_COFFEE;
    public static ResourceLocation WHITE_RABBIT_MOCHA;
    public static ResourceLocation GUANG_S;

    public static void init() {
        DrinkRegistry registry = new DrinkRegistry();

        BLAZE_WHISKEY = registry.registerDrinkData("blaze_whiskey", DrinkData.create(3).addShapes(
                Block.box(4, 0, 4, 12, 9, 12),
                Shapes.or(
                        Block.box(1, 0, 1, 15, 1, 15),
                        Block.box(2, 1, 2, 9, 10, 9),
                        Block.box(8.5, 1, 8.5, 13.5, 10, 13.5)
                ),
                Shapes.or(
                        Block.box(1, 0, 1, 15, 1, 15),
                        Block.box(5, 1, 1.5, 11.5, 10, 8),
                        Block.box(1.5, 1, 8, 8, 10, 14),
                        Block.box(9, 1, 9, 14, 10, 14)
                )).setFood(ModFoods.BLAZE_WHISKEY));

        FERRY_WHISKEY = registry.registerDrinkData("ferry_whiskey", DrinkData.create(1).addShapes(
                Block.box(4, 0, 4, 12, 10, 12)
        ).setFood(ModFoods.FERRY_WHISKEY));

        FLY_WHISKEY = registry.registerDrinkData("fly_whiskey", DrinkData.create(1).addShapes(
                Block.box(4, 0, 4, 12, 10, 12)
        ).setFood(ModFoods.FLY_WHISKEY));

        LAND_NO1 = registry.registerDrinkData("land_no1", DrinkData.create(4).addShapes(
                Block.box(6, 0, 6, 10, 16, 10),
                Block.box(2, 0, 6, 14, 16, 10),
                Shapes.or(
                        Block.box(2, 0, 10, 14, 16, 14),
                        Block.box(6, 0, 2, 10, 16, 14)
                ),
                Block.box(2, 0, 2, 14, 16, 14)
        ).setFood(ModFoods.LAND_NO1));

        LUCKY_CACTUS = registry.registerDrinkData("lucky_cactus", DrinkData.create(3).addShapes(
                Block.box(4, 0, 4, 12, 10, 12),
                Shapes.or(
                        Block.box(1, 0, 1, 15, 1, 15),
                        Block.box(2, 1, 2, 9, 10, 9),
                        Block.box(8.5, 1, 8.5, 13.5, 10, 13.5)
                ),
                Shapes.or(
                        Block.box(1, 0, 1, 15, 1, 15),
                        Block.box(2, 1, 2, 14, 10, 14)
                )).setFood(ModFoods.LUCKY_CACTUS));

        POISON_RUM = registry.registerDrinkData("poison_rum", DrinkData.create(1).addShapes(
                Block.box(4, 0, 4, 12, 10, 12)
        ).setFood(ModFoods.POISON_RUM));

        BLOODY_MARY = registry.registerDrinkData("bloody_mary", DrinkData.create(1).addShapes(
                Block.box(4, 0, 4, 12, 13, 12)
        ).setFood(ModFoods.BLOODY_MARY));

        DIPLOMATICO_COFFEE = registry.registerDrinkData("diplomatico_coffee", DrinkData.create(1).addShapes(
                Block.box(4, 0, 4, 12, 10, 12)
        ).setFood(ModFoods.DIPLOMATICO_COFFEE));

        DEVIL_MARGARITA = registry.registerDrinkData("devil_margarita", DrinkData.create(1).addShapes(
                Block.box(4, 0, 4, 12, 10, 12)
        ).setFood(ModFoods.DEVIL_MARGARITA));

        DIONYSUS = registry.registerDrinkData("dionysus", DrinkData.create(1).addShapes(
                Block.box(4, 0, 4, 12, 10, 12)
        ).setFood(ModFoods.DIONYSUS));

        KALEIDOSCOPE_WHISKEY_SOUR = registry.registerDrinkData("kaleidoscope_whiskey_sour", DrinkData.create(1).addShapes(
                Block.box(4, 0, 4, 12, 8.5, 12)
        ).setFood(ModFoods.KALEIDOSCOPE_WHISKEY_SOUR));

        LONG_ISLAND_POPSICLE_TEA = registry.registerDrinkData("long_island_popsicle_tea", DrinkData.create(1).addShapes(
                Block.box(4, 0, 4, 12, 8.5, 12)
        ).setFood(ModFoods.LONG_ISLAND_POPSICLE_TEA));

        NOCTURNAL_CAT_COFFEE = registry.registerDrinkData("nocturnal_cat_coffee", DrinkData.create(1).addShapes(
                Block.box(4, 0, 4, 12, 6, 12)
        ).setProperties(BlockBehaviour.Properties.of()
                .noOcclusion()
                .instabreak()
                .sound(SoundType.WOOD)
                .pushReaction(PushReaction.DESTROY)
        ).setItemModelType(ItemModelType.BASIC).setFood(ModFoods.NOCTURNAL_CAT_COFFEE));

        GOLD_MEDAL_COFFEE = registry.registerDrinkData("gold_medal_coffee", DrinkData.create(1).addShapes(
                Block.box(4, 0, 4, 12, 6, 12)
        ).setProperties(BlockBehaviour.Properties.of()
                .noOcclusion()
                .instabreak()
                .sound(SoundType.WOOD)
                .pushReaction(PushReaction.DESTROY)
        ).setItemModelType(ItemModelType.BASIC).setFood(ModFoods.GOLD_MEDAL_COFFEE));

        WHITE_RABBIT_MOCHA = registry.registerDrinkData("white_rabbit_mocha", DrinkData.create(1).addShapes(
                Block.box(4, 0, 4, 12, 6, 12)
        ).setProperties(BlockBehaviour.Properties.of()
                .noOcclusion()
                .instabreak()
                .sound(SoundType.WOOD)
                .pushReaction(PushReaction.DESTROY)
        ).setItemModelType(ItemModelType.BASIC).setFood(ModFoods.WHITE_RABBIT_MOCHA));

        GUANG_S = registry.registerDrinkData("guang_s", DrinkData.create(4).addShapes(
                Block.box(5, 0, 5, 11, 11, 11),
                Block.box(2, 0, 5, 14, 11, 11),
                Shapes.or(
                        Block.box(2, 0, 9, 14, 11, 15),
                        Block.box(5, 0, 2, 11, 11, 15)
                ),
                Block.box(1, 0, 1, 15, 11, 15)
        ).setProperties(BlockBehaviour.Properties.of()
                .noOcclusion()
                .instabreak()
                .sound(SoundType.LANTERN)
                .pushReaction(PushReaction.DESTROY)
        ).setBlockType(BlockType.BOXED).setFood(ModFoods.GUANG_S));
    }

    public ResourceLocation registerDrinkData(ResourceLocation id, DrinkData data) {
        DRINK_DATA_MAP.put(id, data);
        return id;
    }

    public ResourceLocation registerDrinkData(String name, DrinkData data) {
        return registerDrinkData(PaperKiteManor.modLoc(name), data);
    }

    public static Item getItem(ResourceLocation name) {
        return ForgeRegistries.ITEMS.getValue(name);
    }

    public static Block getBlock(ResourceLocation name) {
        return ForgeRegistries.BLOCKS.getValue(name);
    }

    public static final class DrinkData {
        private final int maxCount;
        private final List<VoxelShape> shapes;
        private BlockType blockType = BlockType.SIMPLE;
        private ItemModelType itemModelType = ItemModelType.HANDHELD3D;
        private FoodProperties food = ModFoods.DRINK_DEFAULT;
        private BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().noOcclusion().instabreak().pushReaction(PushReaction.DESTROY).sound(SoundType.GLASS);

        private DrinkData(int maxCount) {
            this.maxCount = maxCount;
            this.shapes = Lists.newArrayList();
        }

        public static DrinkData create(int maxCount) { return new DrinkData(maxCount); }

        public DrinkData setShapes(VoxelShape... shapes) {
            this.shapes.clear();
            this.shapes.addAll(Arrays.asList(shapes));
            return this;
        }

        public DrinkData addShapes(VoxelShape... shapes) {
            this.shapes.addAll(Arrays.asList(shapes));
            return this;
        }

        public DrinkData setBlockType(BlockType blockType) {
            this.blockType = blockType;
            return this;
        }

        public DrinkData setItemModelType(ItemModelType itemModelType) {
            this.itemModelType = itemModelType;
            return this;
        }

        public DrinkData setProperties(BlockBehaviour.Properties properties) {
            this.properties = properties;
            return this;
        }

        public DrinkData setFood(FoodProperties food) {
            this.food = food;
            return this;
        }

        public int getMaxCount() {
            return maxCount;
        }

        public List<VoxelShape> getShapes() {
            return shapes;
        }

        public BlockType getBlockType() { return blockType; }

        public ItemModelType getItemModelType() { return itemModelType; }

        public FoodProperties getFood() { return food; }

        public BlockBehaviour.Properties getProperties() { return properties; }
    }

    public enum BlockType {
        SIMPLE,
        BOXED
    }

    public enum ItemModelType {
        BASIC,
        HANDHELD3D
    }
}
