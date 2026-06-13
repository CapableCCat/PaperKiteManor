package com.kazi_cat.papercraft_magic_decoration.init;

import com.kazi_cat.papercraft_magic_decoration.PaperKiteManor;
import com.kazi_cat.papercraft_magic_decoration.entity.ThrownVodkaEntity;
import com.kazi_cat.papercraft_magic_decoration.entity.WhiteRabbitMaidEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = PaperKiteManor.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PaperKiteManor.MOD_ID);

    public static final RegistryObject<EntityType<WhiteRabbitMaidEntity>> WHITE_RABBIT_MAID = ENTITY_TYPES.register("white_rabbit_maid", () -> WhiteRabbitMaidEntity.TYPE);

    public static final RegistryObject<EntityType<ThrownVodkaEntity>> THROWN_VODKA = ENTITY_TYPES.register("thrown_vodka", () -> ThrownVodkaEntity.TYPE);

    @SubscribeEvent
    static void addEntityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(WhiteRabbitMaidEntity.TYPE, Mob.createMobAttributes()
                .add(Attributes.ARMOR, 0)
                .add(Attributes.MAX_HEALTH, 30)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.ATTACK_DAMAGE, 0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5)
                .build());
    }
}
