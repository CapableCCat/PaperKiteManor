package com.kazi_cat.papercraft_magic_decoration.entity;

import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.ModEntities;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PlayMessages;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class ThrownVodkaEntity extends AbstractArrow implements ItemSupplier {
    public static final EntityType<ThrownVodkaEntity> TYPE = EntityType.Builder
            .<ThrownVodkaEntity>of(ThrownVodkaEntity::new, MobCategory.MISC)
            .setShouldReceiveVelocityUpdates(true)
            .sized(0.5f, 0.5f)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .build("thrown_vodka");

    public ThrownVodkaEntity(EntityType<? extends ThrownVodkaEntity> type, Level world) {
        super(type, world);
    }

    public ThrownVodkaEntity(EntityType<? extends ThrownVodkaEntity> type, double x, double y, double z, Level world) {
        super(type, x, y, z, world);
    }

    public ThrownVodkaEntity(EntityType<? extends ThrownVodkaEntity> type, LivingEntity entity, Level world) {
        super(type, entity, world);
    }

    public ThrownVodkaEntity(PlayMessages.SpawnEntity packet, Level world) { super(TYPE, world); }

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
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        Entity target = hitResult.getEntity();
        MobEffectInstance effectInstance;
        if ((target instanceof TamableAnimal tamable && tamable.isTame())
            || (getOwner() instanceof TamableAnimal source && source.getOwner() == target)) {
            effectInstance = new MobEffectInstance(MobEffects.REGENERATION, 100 ,3);
        } else {
            effectInstance = new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2);
            super.onHitEntity(hitResult);
            target.level().levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, target.blockPosition(), Block.getId(ModBlocks.VODKA.get().defaultBlockState()));
            this.playSound(SoundEvents.SNOW_HIT, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        }

        if (target instanceof LivingEntity living) {
            living.addEffect(effectInstance);
        }
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
        entity.setArrowCount(entity.getArrowCount() - 1);
    }

    public static ThrownVodkaEntity shootTowards(LivingEntity entity, LivingEntity target) {
        ThrownVodkaEntity projectile = new ThrownVodkaEntity(ModEntities.THROWN_VODKA.get(), entity, entity.level());
        double dx = target.getX() - entity.getX();
        double dy = target.getY() + target.getEyeHeight() - 1.1;
        double dz = target.getZ() - entity.getZ();
        projectile.shoot(dx, dy - projectile.getY() + Math.hypot(dx, dz) * 0.2F, dz, 1f * 2, 12.0F);
        projectile.setSilent(true);
        projectile.setBaseDamage(5);
        projectile.setKnockback(1);
        entity.level().addFreshEntity(projectile);
        entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.SPLASH_POTION_THROW, SoundSource.PLAYERS, 1, 1f / (RandomSource.create().nextFloat() * 0.5f + 1));
        return projectile;
    }
}
