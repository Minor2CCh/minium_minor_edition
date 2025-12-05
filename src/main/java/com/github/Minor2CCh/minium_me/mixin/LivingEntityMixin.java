package com.github.Minor2CCh.minium_me.mixin;

import com.github.Minor2CCh.minium_me.component.ArmorReinforcedComponent;
import com.github.Minor2CCh.minium_me.config.MiniumConfigLoader;
import com.github.Minor2CCh.minium_me.util.EntityFunctions;
import com.github.Minor2CCh.minium_me.item.MiniumItem;
import com.github.Minor2CCh.minium_me.registry.MiniumTrackDatas;
import com.github.Minor2CCh.minium_me.statuseffect.MiniumStatusEffects;
import com.google.common.base.Suppliers;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;


@Mixin(LivingEntity.class)
public class LivingEntityMixin{
    @ModifyReturnValue(method = "hurtByWater()Z", at = @At("RETURN"))
    private boolean pickingSaltHurt(boolean original){
        LivingEntity entity = ((LivingEntity) (Object) this);
        if(entity.hasStatusEffect(MiniumStatusEffects.PICKLING_SALT)){
            return true;
        }
        return original;
    }
    @ModifyReturnValue(method = "canHaveStatusEffect", at = @At("RETURN"))
    private boolean immuneDarknessAndBlindness(boolean original, StatusEffectInstance effect){
        LivingEntity entity = ((LivingEntity) (Object) this);
        if(effect.equals(StatusEffects.BLINDNESS) || effect.equals(StatusEffects.DARKNESS)){
            if(EntityFunctions.equipIrisQuartzArmors(entity, 4)){
                return false;
            }
        }
        return original;
    }
    @ModifyReturnValue(method = "canFreeze", at = @At("RETURN"))
    private boolean immuneFreeze(boolean original){
        LivingEntity entity = ((LivingEntity) (Object) this);
        if(EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.FROZEN_IMMUNE) >= 2){
            return false;
        }
        return original;
    }
    @WrapOperation(
            method = "travel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;updateVelocity(FLnet/minecraft/util/math/Vec3d;)V"))
    private static void movableInLava(LivingEntity entity, float speed, Vec3d movementInput, Operation<Void> original) {
        if(speed == 0.02F && entity.isInLava()){
            if(EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.FIRE_IMMUNE) == 4){
                entity.updateVelocity(0.1F+(entity.isSprinting() ? 0.1F : 0.0F), movementInput);
                entity.setVelocity(entity.getVelocity().x, entity.getVelocity().y*(entity.getVelocity().y > 0 ? 1.6 : (entity.isSneaking() ? 1.6 : 1)), entity.getVelocity().z);
                return;
            }
        }else if(entity.isTouchingWater()){
            if(EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.WATER_ADAPTION) > 0){
                float f = entity.isSwimming() ? 0.3F : 0.2F;
                entity.updateVelocity((speed * (1 + f*EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.WATER_ADAPTION))), movementInput);
                return;
            }

        }
        original.call(entity, speed,  movementInput);
    }
    @Unique
    private final HashMap<UUID, DamageSource> checkSource = new HashMap<>();
    @ModifyVariable(
            method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
            at = @At("HEAD"),
            argsOnly = true)
    private DamageSource pendantProtectionCheck(DamageSource source){
        LivingEntity entity = ((LivingEntity) (Object) this);
        if(!entity.getWorld().isClient()){
            checkSource.remove(entity.getUuid());
            checkSource.put(entity.getUuid(), source);
        }
        return source;
    }
    // 直値だとコンフィグのロード順の関係上クラッシュする
    @Unique
    private static final Supplier<Float> DAMAGE_ROUND = Suppliers.memoize(() -> MiniumConfigLoader.getConfig().getRoundIrisQuartzMaxDamageAmount());
    @Unique
    private static final Supplier<Float> DAMAGE_MUL = Suppliers.memoize(() -> MiniumConfigLoader.getConfig().getDamageMulIrisQuartz());
    @ModifyVariable(
            method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
            at = @At("HEAD"),
            argsOnly = true)
    private float pendantProtection(float amount){
        LivingEntity entity = ((LivingEntity) (Object) this);
        if(entity.getWorld().isClient()){
            return amount;
        }

        DamageSource getSource = checkSource.getOrDefault(entity.getUuid(), null);
        if(getSource == null)
            return amount;

        boolean hasPendant = EntityFunctions.hasItem(entity, MiniumItem.IRIS_QUARTZ_PENDANT);
        if (EntityFunctions.equipIrisQuartzArmors(entity, 4)) {
            if(hasPendant){
                if(!getSource.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)){
                    if(amount > DAMAGE_ROUND.get()){
                        return DAMAGE_ROUND.get() * DAMAGE_MUL.get();
                    }
                    return amount * DAMAGE_MUL.get();
                }
            }
        }
        return amount;
    }
    @ModifyVariable(
            method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z",
            at = @At("HEAD"),
            argsOnly = true)
    private float reinforcedProtection(float amount){
        LivingEntity entity = ((LivingEntity) (Object) this);
        if(entity.getWorld().isClient()){
            return amount;
        }
        DamageSource getSource = checkSource.getOrDefault(entity.getUuid(), null);
        if(getSource == null)
            return amount;
        if(getSource.isIn(DamageTypeTags.IS_FIRE)){
            if(EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.FIRE_IMMUNE) == 1){
                amount = amount * 3 / 4;
            } else if(EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.FIRE_IMMUNE) == 2){
                amount /= 2;
            }
        }
        if(getSource.isIn(DamageTypeTags.IS_FREEZING)){
            if(EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.FROZEN_IMMUNE) == 1){
                amount /= 2;
            }
        }
        if(getSource.isIn(DamageTypeTags.WITCH_RESISTANT_TO)){
            if(EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.MAGIC_IMMUNE) == 1){
                amount = amount * 3 / 4;
            } else if(EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.MAGIC_IMMUNE) == 2){
                amount /= 2;
            }
        }
        if(EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.ALL_PROTECTION) == 1){
            amount = amount * 9 / 10;
        }else if(EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.ALL_PROTECTION) == 2){
            amount = amount * 8 / 10;
        }else if(EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.ALL_PROTECTION) == 3){
            amount = amount * 7 / 10;
        }else if(EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.ALL_PROTECTION) == 4){
            amount = amount / 2;
        }
        if(EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.WIND_AMULET) > 2){
            if(entity.hasStatusEffect(StatusEffects.WIND_CHARGED)){
                amount = amount * 3 / 10;
                entity.removeStatusEffect(StatusEffects.WIND_CHARGED);
                entity.getWorld().createExplosion(
                        entity,
                        null,
                        new AdvancedExplosionBehavior(
                                true, false, Optional.of(0.0F), Registries.BLOCK.getEntryList(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity())),
                        entity.getX(),
                        entity.getY()+entity.getEyeHeight(entity.getPose()) / 2,
                        entity.getZ(),
                        0.0F,
                        false,
                        World.ExplosionSourceType.TRIGGER,
                        ParticleTypes.GUST_EMITTER_SMALL,
                        ParticleTypes.GUST_EMITTER_LARGE,
                        SoundEvents.ENTITY_WIND_CHARGE_WIND_BURST
                );
                if(entity.getFireTicks() >= 0){
                    entity.setFireTicks(-20);
                    entity.getWorld().playSound(null, entity.getPos().getX(), entity.getPos().getY(), entity.getPos().getZ(), SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.PLAYERS, 1.0F, (1.0F + entity.getWorld().getRandom().nextFloat() * 0.2F) * 0.7F);

                }
            }

        }
        return amount;
    }
    static {
        MiniumTrackDatas.initialize();  // ← ココが超重要
    }
    @Unique
    private static final String PREV_DAMAGE_TYPE_NBT = "MiniumPrevDamageTime";
    @Unique
    private static final String HEALTH_BOOST_VALUE_NBT = "MiniumHealthBoostValue";
    @Inject(method = "initDataTracker", at = @At("RETURN"))
    private void initTrackedData(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(MiniumTrackDatas.getPrevDamageTimeTracker(), 0L);
        builder.add(MiniumTrackDatas.getMaxHealthBoostTracker(), 0);
    }
    @Inject(method = "writeCustomDataToNbt", at = @At("HEAD"))
    private void writeCustomNbt(NbtCompound nbt, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object)this;
        long time = entity.getDataTracker().get(MiniumTrackDatas.getPrevDamageTimeTracker());
        int healthBoost = entity.getDataTracker().get(MiniumTrackDatas.getMaxHealthBoostTracker());
        nbt.putLong(PREV_DAMAGE_TYPE_NBT, time);
        nbt.putInt(HEALTH_BOOST_VALUE_NBT, healthBoost);
    }
    @Inject(method = "readCustomDataFromNbt", at = @At("HEAD"))
    private void readCustomNbt(NbtCompound nbt, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object)this;
        if (nbt.contains(PREV_DAMAGE_TYPE_NBT)) {
            long time = nbt.getLong(PREV_DAMAGE_TYPE_NBT);
            entity.getDataTracker().set(MiniumTrackDatas.getPrevDamageTimeTracker(), time);
        }
        if (nbt.contains(HEALTH_BOOST_VALUE_NBT)) {
            int healthBoost = nbt.getInt(HEALTH_BOOST_VALUE_NBT);
            entity.getDataTracker().set(MiniumTrackDatas.getMaxHealthBoostTracker(), healthBoost);
        }
    }

}
