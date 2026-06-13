package com.kazi_cat.papercraft_magic_decoration.entity;

import com.kazi_cat.papercraft_magic_decoration.init.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
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

public class AirFlowCarpEntity extends TamableAnimal implements GeoEntity {
    public static final EntityType<AirFlowCarpEntity> TYPE = EntityType.Builder
            .<AirFlowCarpEntity>of(AirFlowCarpEntity::new, MobCategory.CREATURE)
            .setCustomClientFactory(AirFlowCarpEntity::new)
            .setShouldReceiveVelocityUpdates(true)
            .setTrackingRange(64)
            .setUpdateInterval(3)
            .sized(2f, 2.8f)
            .build("air_flow_crap");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public AirFlowCarpEntity(PlayMessages.SpawnEntity packet, Level world) { this(TYPE, world); }

    public AirFlowCarpEntity(EntityType<AirFlowCarpEntity> type, Level world) {
        super(type, world);
        this.moveControl = new FlyingMoveControl(this, 10, true);
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
                        level.sendParticles(
                                ParticleTypes.HEART,
                                this.getX(), this.getY() + this.getBbHeight(), this.getZ(),
                                5,
                                0.2, 0.2, 0.2,
                                0.01
                        );
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
        } else if (hand == InteractionHand.MAIN_HAND && itemstack.isEmpty() && this.getPassengers().isEmpty()) {
            if (this.isTame()) {
                player.startRiding(this);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void travel(Vec3 dir) {
        Entity entity = this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
        if (this.isVehicle() && entity != null) {
            this.setYRot(entity.getYRot());
            this.yRotO = this.getYRot();
            this.setXRot(entity.getXRot() * 0.5F);
            this.setRot(this.getYRot(), this.getXRot());
            this.yBodyRot = entity.getYRot();
            this.yHeadRot = entity.getYRot();
            this.setMaxUpStep(1.0F);
            if (entity instanceof LivingEntity passenger) {
                this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                float forward = passenger.zza;
                float strafe = passenger.xxa;
                float vertical = forward > 0.01 ? (float) (passenger.getLookAngle().y() * 0.3F) : 0;
                super.travel(new Vec3(strafe, vertical, forward));
            }
            double d1 = this.getX() - this.xo;
            double d0 = this.getZ() - this.zo;
            float f1 = (float) Math.sqrt(d1 * d1 + d0 * d0) * 4;
            if (f1 > 1.0F)
                f1 = 1.0F;
            this.walkAnimation.setSpeed(this.walkAnimation.speed() + (f1 - this.walkAnimation.speed()) * 0.4F);
            this.walkAnimation.position(this.walkAnimation.position() + this.walkAnimation.speed());
            this.calculateEntityAnimation(true);
            return;
        }
        this.setMaxUpStep(0.5F);
        super.travel(dir);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.MELON_SLICE);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageAble) {
        AirFlowCarpEntity entity = ModEntities.AIR_FLOW_CARP.get().create(serverWorld);
        if (entity != null) {
            entity.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.BREEDING, null, null);
        }
        return entity;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new FollowOwnerGoal(this, 1, (float) 10, (float) 2, false));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2, Ingredient.of(Items.MELON_SLICE), false));
        this.goalSelector.addGoal(3, new FollowMobGoal(this, 1, (float) 10, (float) 5));
        this.goalSelector.addGoal(4, new BreathAirGoal(this));
        this.goalSelector.addGoal(5, new PanicGoal(this, 1.5));
        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 0.8, 20) {
            @Override
            protected Vec3 getPosition() {
                RandomSource random = AirFlowCarpEntity.this.getRandom();
                double dir_x = AirFlowCarpEntity.this.getX() + ((random.nextFloat() * 2 - 1) * 16);
                double dir_y = AirFlowCarpEntity.this.getY() + ((random.nextFloat() * 2 - 1) * 16);
                double dir_z = AirFlowCarpEntity.this.getZ() + ((random.nextFloat() * 2 - 1) * 16);
                return new Vec3(dir_x, dir_y, dir_z);
            }
        });
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return 1.8F;
    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        return new FlyingPathNavigation(this, world);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean causeFallDamage(float l, float d, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {}

    @Override
    public void setNoGravity(boolean ignored) {
        super.setNoGravity(true);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.updateSwingTime();
        this.setNoGravity(true);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvents.GENERIC_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.GENERIC_DEATH;
    }

    private PlayState movementPredicate(AnimationState<AirFlowCarpEntity> event) {
        if (!this.onGround()) {
            return event.setAndContinue(RawAnimation.begin().thenLoop("animation.air_flow_carp.flow"));
        }
        return event.setAndContinue(RawAnimation.begin().thenLoop("animation.air_flow_carp.idle"));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "movement", 4, this::movementPredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
