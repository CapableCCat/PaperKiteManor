package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.entity.*;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = PaperKiteManor.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PaperKiteManor.MOD_ID);

    public static final RegistryObject<EntityType<AoaoEntity>> AOAO = ENTITY_TYPES.register("aoao", () -> AoaoEntity.TYPE);
    public static final RegistryObject<EntityType<FlyingChickenEntity>> FLYING_CHICKEN = ENTITY_TYPES.register("flying_chicken_projectile", () -> FlyingChickenEntity.TYPE);
    public static final RegistryObject<EntityType<WhiteRabbitMaidEntity>> WHITE_RABBIT_MAID = ENTITY_TYPES.register("white_rabbit_maid", () -> WhiteRabbitMaidEntity.TYPE);
    public static final RegistryObject<EntityType<ThrownVodkaEntity>> THROWN_VODKA = ENTITY_TYPES.register("thrown_vodka", () -> ThrownVodkaEntity.TYPE);
    public static final RegistryObject<EntityType<BlackCatLobbyBoyEntity>> BLACK_CAT_LOBBY_BOY = ENTITY_TYPES.register("black_cat_lobby_boy", () -> BlackCatLobbyBoyEntity.TYPE);
    public static final RegistryObject<EntityType<ThrownFireWhiskeyEntity>> THROWN_FIRE_WHISKEY = ENTITY_TYPES.register("thrown_fire_whiskey", () -> ThrownFireWhiskeyEntity.TYPE);

    @SubscribeEvent
    static void addEntityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(AoaoEntity.TYPE, AoaoEntity.createAttributes().build());
        event.put(WhiteRabbitMaidEntity.TYPE, WhiteRabbitMaidEntity.createAttributes().build());
        event.put(BlackCatLobbyBoyEntity.TYPE, BlackCatLobbyBoyEntity.createAttributes().build());
    }
}
