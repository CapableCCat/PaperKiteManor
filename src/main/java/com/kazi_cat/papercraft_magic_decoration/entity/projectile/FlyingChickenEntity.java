package com.kazi_cat.papercraft_magic_decoration.entity.projectile;

import com.kazi_cat.papercraft_magic_decoration.init.ModEntities;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PlayMessages;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class FlyingChickenEntity extends TeamedThrownProjectile {
    public static final EntityType<FlyingChickenEntity> TYPE = EntityType.Builder
            .<FlyingChickenEntity>of(FlyingChickenEntity::new, MobCategory.MISC)
            .setCustomClientFactory(FlyingChickenEntity::new)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .sized(0.5f, 0.5f)
            .build("flying_chicken_projectile");

    public FlyingChickenEntity(EntityType<? extends FlyingChickenEntity> type, Level world) {
        super(type, world);
    }

    public FlyingChickenEntity(EntityType<? extends FlyingChickenEntity> type, double x, double y, double z, Level world) {
        super(type, x, y, z, world);
    }

    public FlyingChickenEntity(EntityType<? extends FlyingChickenEntity> type, LivingEntity entity, Level world) {
        super(type, entity, world);
    }

    public FlyingChickenEntity(PlayMessages.SpawnEntity packet, Level world) { super(TYPE, world); }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return ModItems.FRIED_CHICKEN_LEG.get().getDefaultInstance();
    }

    @Override
    protected ItemStack getPickupItem() {
        return ModItems.FRIED_CHICKEN_LEG.get().getDefaultInstance();
    }

    @Override
    protected void onHitEnemy(EntityHitResult hitResult) {
        Entity target = hitResult.getEntity();
        this.playSound(SoundEvents.SNOW_HIT, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        if (target instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
        }
    }

    public static FlyingChickenEntity shootTowards(LivingEntity source, LivingEntity target) {
        FlyingChickenEntity projectile = new FlyingChickenEntity(ModEntities.FLYING_CHICKEN.get(), source, source.level());
        shootTowards(projectile, source, target);
        return projectile;
    }
}
