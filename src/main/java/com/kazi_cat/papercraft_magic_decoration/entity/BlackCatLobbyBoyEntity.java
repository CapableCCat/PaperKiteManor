package com.kazi_cat.papercraft_magic_decoration.entity;

import com.kazi_cat.papercraft_magic_decoration.entity.ai.AnimatedRangedAttackGoal;
import com.kazi_cat.papercraft_magic_decoration.entity.projectile.ThrownFireWhiskeyEntity;
import com.kazi_cat.papercraft_magic_decoration.init.ModEntities;
import com.kazi_cat.papercraft_magic_decoration.init.ModItems;
import com.kazi_cat.papercraft_magic_decoration.init.registry.DrinkRegistry;
import com.kazi_cat.papercraft_magic_decoration.inventory.container.LobbyBoyBackpackContainer;
import com.kazi_cat.papercraft_magic_decoration.network.NetworkHandler;
import com.kazi_cat.papercraft_magic_decoration.network.message.OffersSyncS2CMessage;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class BlackCatLobbyBoyEntity extends TamableAnimal implements RangedAttackMob, GeoEntity, MenuProvider {
    public static final EntityType<BlackCatLobbyBoyEntity> TYPE = EntityType.Builder
            .<BlackCatLobbyBoyEntity>of(BlackCatLobbyBoyEntity::new, MobCategory.CREATURE)
            .setCustomClientFactory(BlackCatLobbyBoyEntity::new)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .fireImmune()
            .sized(0.6f, 1.2f)
            .build("black_cat_lobby_boy");
    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(BlackCatLobbyBoyEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Long> LAST_RESTOCK_DAY = SynchedEntityData.defineId(BlackCatLobbyBoyEntity.class, EntityDataSerializers.LONG);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final List<Offer> offers = NonNullList.withSize(6, Offer.EMPTY);
    private boolean swinging;
    private long lastSwing;

    public BlackCatLobbyBoyEntity(PlayMessages.SpawnEntity packet, Level world) { this(TYPE, world); }

    public BlackCatLobbyBoyEntity(EntityType<BlackCatLobbyBoyEntity> type, Level world) {
        super(type, world);
    }

    public void tryRestock(Level level) {
        if (level.getGameTime() % 23 != 0) {
            return;
        }
        long today = level.getGameTime() / 24000;
        if (today <= this.entityData.get(LAST_RESTOCK_DAY)) {
            return;
        }
        this.entityData.set(LAST_RESTOCK_DAY, today);
        this.offers.clear();
        ResourceLocation[] idList = DrinkRegistry.DRINK_DATA_MAP.keySet().toArray(new ResourceLocation[0]);
        ItemStack drink = DrinkRegistry.getItem(idList[level.random.nextInt(idList.length)]).getDefaultInstance();
        this.offers.set(0, Offer.of(4, drink, 1));
        ItemStack gift = ModItems.GIFT_FROM_KAZI_MANOR.get().getDefaultInstance();
        this.offers.set(1, Offer.of(8, gift, 16));
        ItemStack key = new ItemStack(ModItems.KEY_UNDER_THE_LAKE.get(), 8);
        this.offers.set(2, Offer.of(8, key, 16));
        setPersistenceRequired();
        if (level.isClientSide()) {
            return;
        }
        for (var player : level.players()) {
            if (player.containerMenu instanceof LobbyBoyBackpackContainer container && container.entity == this) {
                NetworkHandler.sendToClientPlayer(new OffersSyncS2CMessage(this.getId(), this.offers), player);
            }
        }
    }

    public List<Offer> getOffers() { return this.offers; }

    public void setOffers(List<Offer> offers) {
        for (int i = 0; i < Math.min(offers.size(), this.offers.size()); i++) {
            this.offers.set(i, offers.get(i));
        }
        setPersistenceRequired();
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
                NetworkHandler.sendToClientPlayer(new OffersSyncS2CMessage(this.getId(), this.offers), player);
                NetworkHooks.openScreen(serverPlayer, this, buf -> buf.writeVarInt(this.getId()));
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void performRangedAttack(LivingEntity target, float f) {
        ThrownFireWhiskeyEntity.shootTowards(this, target);
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
        this.goalSelector.addGoal(1, new AnimatedRangedAttackGoal(this, this::setShoot, 1.25, 40, 10f));
    }

    private void setShoot(boolean value) { this.entityData.set(SHOOT, value); }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.COOKED_BEEF);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.updateSwingTime();
    }

    @Override
    public void baseTick() {
        super.baseTick();
        this.refreshDimensions();
        tryRestock(level());
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageAble) {
        BlackCatLobbyBoyEntity entity = ModEntities.BLACK_CAT_LOBBY_BOY.get().create(serverWorld);
        if (entity != null) {
            entity.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.BREEDING, null, null);
        }
        return entity;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT, false);
        this.entityData.define(LAST_RESTOCK_DAY, -1L);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory playerInv, Player player) {
        return new LobbyBoyBackpackContainer(id, playerInv, this);
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.CAT_PURR;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvents.CAT_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.CAT_DEATH;
    }

    private PlayState movementPredicate(AnimationState<BlackCatLobbyBoyEntity> event) {
        if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F))) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("animation.black_cat_lobby_boy.walking"));
        }
        if (this.isInWaterOrBubble()) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("animation.black_cat_lobby_boy.swim"));
        }
        return event.setAndContinue(RawAnimation.begin().thenLoop("animation.black_cat_lobby_boy.idle"));
    }

    private PlayState attackingPredicate(AnimationState<BlackCatLobbyBoyEntity> event) {
        if (getAttackAnim(event.getPartialTick()) > 0f && !this.swinging) {
            this.swinging = true;
            this.lastSwing = level().getGameTime();
        }
        if (this.swinging && this.lastSwing + 7L <= level().getGameTime()) {
            this.swinging = false;
        }
        if ((this.swinging || this.entityData.get(SHOOT)) && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().forceAnimationReset();
            return event.setAndContinue(RawAnimation.begin().thenPlay("animation.black_cat_lobby_boy.attack"));
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

    public static class Offer implements INBTSerializable<CompoundTag> {
        public static final Offer EMPTY = new Offer(0, ItemStack.EMPTY, -1);

        public int price;
        public int tradeLimit;
        public ItemStack result;

        private Offer(int price, ItemStack result, int tradeLimit) {
            this.price = price;
            this.result = result;
            this.tradeLimit = tradeLimit;
        }

        private Offer() {}

        public static Offer of(int price, ItemStack result, int tradeLimit) {
            return new Offer(price, result, tradeLimit);
        }

        public static Offer of(CompoundTag tag) {
            Offer offer = new Offer();
            offer.deserializeNBT(tag);
            return offer;
        }

        public boolean isEmpty() {
            return result.isEmpty();
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag tag = new CompoundTag();
            tag.putInt("price", price);
            tag.putInt("trade_limit", tradeLimit);
            tag.put("result", result.serializeNBT());
            return tag;
        }

        @Override
        public void deserializeNBT(CompoundTag tag) {
            if (tag.contains("price")) {
                this.price = tag.getInt("price");
            }
            if (tag.contains("trade_limit")) {
                this.tradeLimit = tag.getInt("trade_limit");
            }
            if (tag.contains("result")) {
                this.result = ItemStack.of(tag.getCompound("result"));
            }
        }
    }
}
