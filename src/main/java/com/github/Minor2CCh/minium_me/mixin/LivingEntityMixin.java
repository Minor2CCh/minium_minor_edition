package com.github.Minor2CCh.minium_me.mixin;

import com.github.Minor2CCh.minium_me.entity.EntityFunctions;
import com.github.Minor2CCh.minium_me.item.MiniumItem;
import com.github.Minor2CCh.minium_me.statuseffect.MiniumStatusEffects;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.tag.DamageTypeTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;


@Mixin(LivingEntity.class)
public class LivingEntityMixin {
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
