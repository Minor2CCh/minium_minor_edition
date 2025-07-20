package com.github.Minor2CCh.minium_me.event;

import com.github.Minor2CCh.minium_me.statuseffect.MiniumStatusEffects;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.Objects;

public class PicklingSaltReduceEvent {
    public static void initialize(){
        ServerLivingEntityEvents.AFTER_DAMAGE.register((LivingEntity entity, DamageSource source, float amount, float damageTaken, boolean blocked) -> {
            if(entity.hasStatusEffect(MiniumStatusEffects.PICKLING_SALT)){
                if(source.isOf(DamageTypes.DROWN)){
                    StatusEffectInstance effect = entity.getStatusEffect(MiniumStatusEffects.PICKLING_SALT);
                    int newDuration = Objects.requireNonNull(effect).getDuration() - 20;
                    if(newDuration > 0){
                        StatusEffectInstance newEffect = new StatusEffectInstance(
                                effect.getEffectType(),
                                newDuration,
                                effect.getAmplifier(),
                                effect.isAmbient(),
                                effect.shouldShowParticles(),
                                effect.shouldShowIcon()
                        );
                        entity.removeStatusEffect(effect.getEffectType());
                        entity.addStatusEffect(newEffect);
                    }else{
                        entity.removeStatusEffect(effect.getEffectType());
                    }
                }
            }
        });
    }
}
