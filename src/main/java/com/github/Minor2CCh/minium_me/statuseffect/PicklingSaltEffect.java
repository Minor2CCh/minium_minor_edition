package com.github.Minor2CCh.minium_me.statuseffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class PicklingSaltEffect extends StatusEffect {
    protected PicklingSaltEffect() {
        super(StatusEffectCategory.HARMFUL, 0xC2C2A9);
    }
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        return super.applyUpdateEffect(entity, amplifier);
    }
}
