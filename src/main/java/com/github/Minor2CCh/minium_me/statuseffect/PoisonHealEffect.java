package com.github.Minor2CCh.minium_me.statuseffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import java.util.Objects;

public class PoisonHealEffect extends StatusEffect{
    protected PoisonHealEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xCDA360);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(entity.hasStatusEffect(StatusEffects.POISON)) {
            int poisonAmplifier = Objects.requireNonNull(entity.getStatusEffect(StatusEffects.POISON)).getAmplifier();
            int poisonDuration = Objects.requireNonNull(entity.getStatusEffect(StatusEffects.POISON)).getDuration();
            entity.removeStatusEffect(StatusEffects.POISON);
            if (!entity.hasStatusEffect(StatusEffects.POISON)){
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, poisonDuration, poisonAmplifier, true, true, true));
            }
            /*
            int i = 25 >> poisonAmplifier;
            if(i == 0 || poisonDuration % i == 0){
                if(entity.isAlive()){
                    entity.heal(1.0F);
                }
            }
            int j = 50 >> poisonAmplifier;
            if(j == 0 || poisonDuration % j == 0){
                entity.heal(1.0F);
            }*/


        }
        return super.applyUpdateEffect(entity, amplifier);
    }
}
