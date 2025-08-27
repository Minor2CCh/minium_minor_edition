package com.github.Minor2CCh.minium_me.mixin;

import com.github.Minor2CCh.minium_me.entity.EntityFunctions;
import com.github.Minor2CCh.minium_me.item.MiniumItem;
import com.github.Minor2CCh.minium_me.item.MiniumItemTag;
import com.github.Minor2CCh.minium_me.statuseffect.MiniumStatusEffects;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;


@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "tick()V", at = @At("RETURN"), cancellable = false)
    private void IrisQuartzBonusInject(CallbackInfo ci){
        LivingEntity entity = ((LivingEntity) (Object) this);
        boolean hasPendant = EntityFunctions.hasItem(entity, MiniumItem.IRIS_QUARTZ_PENDANT);
        int ParamDifficulty = switch (entity.getWorld().getDifficulty()) {
            case Difficulty.PEACEFUL -> 0;
            case Difficulty.EASY -> 1;
            case Difficulty.HARD -> 3;
            default -> 2;
        };
        if(hasPendant){
            entity.addStatusEffect(new StatusEffectInstance(MiniumStatusEffects.POISON_HEAL, 20, 0, false, false, false));
        }

        if (EntityFunctions.equipIrisQuartzArmors(entity, 4)) {
            int[] coolTick = new int[]{200, 200, 400, 800};//tick、1s=20tick
            int[] resistLevel = new int[]{1, 1, 0, 0};//peaceful,easy=level 2 normal,hard=level 1
            int[] absorptionLevel = new int[]{3, 2, 1, 0};
            int pendantBonus = hasPendant ? 1 : 0;
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20, resistLevel[ParamDifficulty] + pendantBonus, false, false, false));
            entity.addStatusEffect(new StatusEffectInstance(MiniumStatusEffects.POISON_HEAL, 20, 0, false, false, false));
            if(entity.getWorld().getTime() % coolTick[ParamDifficulty] == 0){
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, coolTick[ParamDifficulty]+1, absorptionLevel[ParamDifficulty] + pendantBonus, false, false, true));
            }
            if(entity.hasStatusEffect(StatusEffects.DARKNESS)){
                entity.removeStatusEffect(StatusEffects.DARKNESS);
            }
            if(entity.hasStatusEffect(StatusEffects.BLINDNESS)){
                entity.removeStatusEffect(StatusEffects.BLINDNESS);
            }
            if(entity.getWorld().getDifficulty() == Difficulty.HARD){
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.TRIAL_OMEN, 20, 0, false, false, true));
            }
            if(entity.getEquippedStack(EquipmentSlot.MAINHAND).isIn(MiniumItemTag.IRIS_QUARTZ_BONUS_WEAPON)){
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 20, 3 + pendantBonus * 2, false, false, false));
            }
        }else if (EntityFunctions.equipConcentratedMiniumArmors(entity, 4)) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20, 0, false, false, false));
        }else if (EntityFunctions.equipMiniumArmors(entity, 4)) {
            int[] resistLevel = new int[]{1, 1, 0, 0};//peaceful,easy=level 2 normal,hard=level 1
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20, resistLevel[ParamDifficulty], false, false, false));
        }
    }
    @ModifyReturnValue(method = "hurtByWater()Z", at = @At("RETURN"))
    private boolean pickingSaltHurt(boolean original){
        LivingEntity entity = ((LivingEntity) (Object) this);
        if(entity.hasStatusEffect(MiniumStatusEffects.PICKLING_SALT)){
            return true;
        }
        return original;
    }
    @Unique
    private final HashMap<UUID, DamageSource> checkSource = new HashMap<>();
    @Unique
    private static final float damageMul = 0.5F;
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
                if(!Objects.requireNonNull(getSource).isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)){
                    if(amount > 50.0F){
                        return 50.0F * damageMul;
                    }
                    return amount * damageMul;
                }
            }
        }
        return amount;
    }
}
