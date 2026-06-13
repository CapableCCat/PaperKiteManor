package com.kazi_cat.papercraft_magic_decoration.entity.projectile;

import com.kazi_cat.papercraft_magic_decoration.init.ModBlocks;
import com.kazi_cat.papercraft_magic_decoration.init.ModEntities;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PlayMessages;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class ThrownFireWhiskeyEntity extends TeamedThrownProjectile {
    public static final EntityType<ThrownFireWhiskeyEntity> TYPE = EntityType.Builder
            .<ThrownFireWhiskeyEntity>of(ThrownFireWhiskeyEntity::new, MobCategory.MISC)
            .setCustomClientFactory(ThrownFireWhiskeyEntity::new)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(1)
            .sized(0.5f, 0.5f)
            .build("thrown_fire_whiskey");

    public ThrownFireWhiskeyEntity(EntityType<? extends ThrownFireWhiskeyEntity> type, Level world) {
        super(type, world);
    }

    public ThrownFireWhiskeyEntity(EntityType<? extends ThrownFireWhiskeyEntity> type, double x, double y, double z, Level world) {
        super(type, x, y, z, world);
    }

    public ThrownFireWhiskeyEntity(EntityType<? extends ThrownFireWhiskeyEntity> type, LivingEntity entity, Level world) {
        super(type, entity, world);
    }

    public ThrownFireWhiskeyEntity(PlayMessages.SpawnEntity packet, Level world) { super(TYPE, world); }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getItem() { return ModItems.FIRE_WHISKEY.get().getDefaultInstance(); }

    @Override
    protected ItemStack getPickupItem() { return ModItems.FIRE_WHISKEY.get().getDefaultInstance(); }

    @Override
    protected void onHitEnemy(EntityHitResult hitResult) {
        Entity target = hitResult.getEntity();
        target.level().levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, target.blockPosition(), Block.getId(ModBlocks.FIRE_WHISKEY.get().defaultBlockState()));
        this.playSound(SoundEvents.SNOW_HIT, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
        target.setSecondsOnFire(10);
    }

    public static ThrownFireWhiskeyEntity shootTowards(LivingEntity source, LivingEntity target) {
        ThrownFireWhiskeyEntity projectile = new ThrownFireWhiskeyEntity(ModEntities.THROWN_FIRE_WHISKEY.get(), source, source.level());
        shootTowards(projectile, source, target);
        return projectile;
    }
}
