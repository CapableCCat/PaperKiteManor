package com.kazi_cat.papercraft_magic_decoration.entity;

import com.kazi_cat.papercraft_magic_decoration.init.ModEntities;
import com.kazi_cat.papercraft_magic_decoration.inventory.container.BunnySuitcaseContainer;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.EntityArmorInvWrapper;
import net.minecraftforge.items.wrapper.EntityHandsInvWrapper;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

public class WhiteRabbitMaidEntity extends TamableAnimal implements RangedAttackMob, GeoEntity, MenuProvider {
    public static final EntityType<WhiteRabbitMaidEntity> TYPE = EntityType.Builder
            .<WhiteRabbitMaidEntity>of(WhiteRabbitMaidEntity::new, MobCategory.CREATURE)
            .setCustomClientFactory(WhiteRabbitMaidEntity::new)
            .setShouldReceiveVelocityUpdates(true)
            .fireImmune()
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .sized(0.6f, 1.2f)
            .build("white_rabbit_maid");
    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(WhiteRabbitMaidEntity.class, EntityDataSerializers.BOOLEAN);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private final ItemStackHandler inventory = new ItemStackHandler(21) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            setPersistenceRequired();
        }
    };
    private final CombinedInvWrapper wrapper = new CombinedInvWrapper(inventory, new EntityHandsInvWrapper(this), new EntityArmorInvWrapper(this));
    private boolean swinging;
    private long lastSwing;

    public WhiteRabbitMaidEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(ModEntities.WHITE_RABBIT_MAID.get(), world);
    }

    public WhiteRabbitMaidEntity(EntityType<WhiteRabbitMaidEntity> type, Level world) {
        super(type, world);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (isFood(itemstack)) {
            if (this.isTame()) {
                if (this.isOwnedBy(player) && this.getHealth() < this.getMaxHealth()) {
                    this.usePlayerItem(player, hand, itemstack);
                    this.heal(8);
                    if (player.level() instanceof ServerLevel level) {
                        level.sendParticles(ParticleTypes.HEART,
                                this.getX(), this.getY() + this.getBbHeight(), this.getZ(),
                                5, 0.2, 0.2, 0.2, 0.01);
                    }
                    return InteractionResult.SUCCESS;
                }
            } else {
                this.usePlayerItem(player, hand, itemstack);
                if (this.random.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
                    this.tame(player);
                    this.level().broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                }
                this.setPersistenceRequired();
                return InteractionResult.SUCCESS;
            }
        } else {
            if (!player.level().isClientSide() && player instanceof ServerPlayer serverPlayer) {
                NetworkHooks.openScreen(serverPlayer, this, buf -> {
                    buf.writeVarInt(this.getId());
                });
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float f) {
        ThrownVodkaEntity.shootTowards(this, target);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.COOKIE);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.updateSwingTime();
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob pOtherParent) {
        WhiteRabbitMaidEntity entity = ModEntities.WHITE_RABBIT_MAID.get().create(level);
        if (entity != null) {
            entity.finalizeSpawn(level, level.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.BREEDING, null, null);
        }
        return entity;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT, false);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FollowOwnerGoal(this, 1.2, (float) 10, (float) 2, false));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(3, new PanicGoal(this, 1.2));
        this.goalSelector.addGoal(4, new FloatGoal(this));
        this.goalSelector.addGoal(5, new MoveBackToVillageGoal(this, 0.6, false));
        this.goalSelector.addGoal(6, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
        this.goalSelector.addGoal(7, new BreathAirGoal(this));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(9, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(1, new AnimatedRangedAttackGoal(this, this::setShoot, 1.25, 40, 16f));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getDirectEntity() instanceof Player && this.isTame())
            return false;
        if (source.is(DamageTypes.FALL))
            return false;
        return super.hurt(source, amount);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction side) {
        if (this.isAlive() && capability == ForgeCapabilities.ITEM_HANDLER)
            return LazyOptional.of(() -> wrapper).cast();
        return super.getCapability(capability, side);
    }

    @Override
    protected void dropEquipment() {
        super.dropEquipment();
        for (int i = 0; i < inventory.getSlots(); ++i) {
            ItemStack itemstack = inventory.getStackInSlot(i);
            if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) {
                this.spawnAtLocation(itemstack);
            }
        }
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.RABBIT_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvents.RABBIT_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.RABBIT_DEATH;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.put("inventory", inventory.serializeNBT());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.contains("inventory")) {
            this.inventory.deserializeNBT(compoundTag.getCompound("inventory"));
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory playerInv, Player player) {
        return new BunnySuitcaseContainer(id, playerInv, this);
    }

    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    private void setShoot(boolean value) { this.entityData.set(SHOOT, value); }

    private PlayState movementPredicate(AnimationState<WhiteRabbitMaidEntity> event) {
        if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("animation.white_rabbit_maid.walking"));
        }
        if (this.isInWaterOrBubble()) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("animation.white_rabbit_maid.swim"));
        }
        return event.setAndContinue(RawAnimation.begin().thenLoop("animation.white_rabbit_maid.idle"));
    }

    private PlayState attackingPredicate(AnimationState<WhiteRabbitMaidEntity> event) {
        if (getAttackAnim(event.getPartialTick()) > 0f && !this.swinging) {
            this.swinging = true;
            this.lastSwing = level().getGameTime();
        }
        if (this.swinging && this.lastSwing + 7L <= level().getGameTime()) {
            this.swinging = false;
        }
        if ((this.swinging || this.entityData.get(SHOOT)) && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().forceAnimationReset();
            return event.setAndContinue(RawAnimation.begin().thenPlay("animation.white_rabbit_maid.attack"));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "movement", 4, this::movementPredicate));
        data.add(new AnimationController<>(this, "attacking", 4, this::attackingPredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
