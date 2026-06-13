package com.kazi_cat.papercraft_magic_decoration.entity.projectile;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public abstract class TeamedThrownProjectile extends AbstractArrow implements ItemSupplier {
    public TeamedThrownProjectile(EntityType<? extends TeamedThrownProjectile> type, Level world) {
        super(type, world);
    }

    public TeamedThrownProjectile(EntityType<? extends TeamedThrownProjectile> type, double x, double y, double z, Level world) {
        super(type, x, y, z, world);
    }

    public TeamedThrownProjectile(EntityType<? extends TeamedThrownProjectile> type, LivingEntity entity, Level world) {
        super(type, entity, world);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        Entity target = hitResult.getEntity();
        if ((target instanceof TamableAnimal tamable && tamable.isTame())
                || (getOwner() instanceof TamableAnimal source && source.getOwner() == target)) {
            onHitFriendly(hitResult);
        } else {
            super.onHitEntity(hitResult);
            onHitEnemy(hitResult);
        }
    }

    protected void onHitFriendly(EntityHitResult hitResult) {
        if (hitResult.getEntity() instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100 ,3));
        }
    }

    protected abstract void onHitEnemy(EntityHitResult hitResult);

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
        entity.setArrowCount(entity.getArrowCount() - 1);
    }

    protected static void shootTowards(TeamedThrownProjectile projectile, LivingEntity source, LivingEntity target) {
        double dx = target.getX() - source.getX();
        double dy = target.getY() + target.getEyeHeight() - 1.1;
        double dz = target.getZ() - source.getZ();
        projectile.shoot(dx, dy - projectile.getY() + Math.hypot(dx, dz) * 0.2F, dz, 1f * 2, 12.0F);
        projectile.setSilent(true);
        projectile.setBaseDamage(5);
        projectile.setKnockback(1);
        source.level().addFreshEntity(projectile);
        source.level().playSound(null, source.getX(), source.getY(), source.getZ(), SoundEvents.SPLASH_POTION_THROW, SoundSource.PLAYERS, 1, 1f / (RandomSource.create().nextFloat() * 0.5f + 1));
    }
}
