package com.kazi_cat.papercraft_magic_decoration.entity;

import com.kazi_cat.papercraft_magic_decoration.init.ModEntities;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class ThrownVodkaEntity extends AbstractArrow implements ItemSupplier {
    public ThrownVodkaEntity(PlayMessages.SpawnEntity packet, Level world) {
        super(ModEntities.THROWN_VODKA.get(), world);
    }

    public ThrownVodkaEntity(EntityType<? extends ThrownVodkaEntity> type, Level world) {
        super(type, world);
    }

    public ThrownVodkaEntity(EntityType<? extends ThrownVodkaEntity> type, double x, double y, double z, Level world) {
        super(type, x, y, z, world);
    }

    public ThrownVodkaEntity(EntityType<? extends ThrownVodkaEntity> type, LivingEntity entity, Level world) {
        super(type, entity, world);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() {
        return ModItems.VODKA.get().getDefaultInstance();
    }

    @Override
    protected ItemStack getPickupItem() {
        return ModItems.VODKA.get().getDefaultInstance();
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
        entity.setArrowCount(entity.getArrowCount() - 1);
    }

    @Override
    public void onHitEntity(EntityHitResult entityHitResult) {
        if (entityHitResult.getEntity() instanceof LivingEntity target && !target.level().isClientSide()) {
            if ((target instanceof TamableAnimal tamable && tamable.isTame()) || (this.getOwner() instanceof TamableAnimal source && source.getOwner() == target)) {
                target.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100 ,3));
            } else {
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
                super.onHitEntity(entityHitResult);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.inGround)
            this.discard();
    }

    public static ThrownVodkaEntity shoot(Level world, LivingEntity entity, RandomSource source) {
        return shoot(world, entity, source, 1f, 5, 1);
    }

    public static ThrownVodkaEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
        ThrownVodkaEntity projectile = new ThrownVodkaEntity(ModEntities.THROWN_VODKA.get(), entity, world);
        projectile.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
        projectile.setSilent(true);
        projectile.setBaseDamage(damage);
        projectile.setKnockback(knockback);
        world.addFreshEntity(projectile);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.SPLASH_POTION_THROW, SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
        return projectile;
    }

    public static ThrownVodkaEntity shoot(LivingEntity entity, LivingEntity target) {
        ThrownVodkaEntity projectile = new ThrownVodkaEntity(ModEntities.THROWN_VODKA.get(), entity, entity.level());
        double dx = target.getX() - entity.getX();
        double dy = target.getY() + target.getEyeHeight() - 1.1;
        double dz = target.getZ() - entity.getZ();
        projectile.shoot(dx, dy - projectile.getY() + Math.hypot(dx, dz) * 0.2F, dz, 1f * 2, 12.0F);
        projectile.setSilent(true);
        projectile.setBaseDamage(5);
        projectile.setKnockback(1);
        entity.level().addFreshEntity(projectile);
        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.SPLASH_POTION_THROW, SoundSource.PLAYERS, 1,
                1f / (RandomSource.create().nextFloat() * 0.5f + 1));
        return projectile;
    }

    public static final EntityType<ThrownVodkaEntity> TYPE = EntityType.Builder.<ThrownVodkaEntity>of(ThrownVodkaEntity::new, MobCategory.MISC)
            .setCustomClientFactory(ThrownVodkaEntity::new)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .sized(0.5f, 0.5f)
            .build("thrown_vodka");
}
