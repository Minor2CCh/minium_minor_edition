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
            //int poisonDuration = Objects.requireNonNull(entity.getStatusEffect(StatusEffects.POISON)).getDuration();

            if (!entity.hasStatusEffect(MiniumStatusEffects.POISONOUS_REGENERATION)){
                entity.addStatusEffect(new StatusEffectInstance(MiniumStatusEffects.POISONOUS_REGENERATION, 50, poisonAmplifier, true, true, true));
            }


        }
        return super.applyUpdateEffect(entity, amplifier);
    }
}
