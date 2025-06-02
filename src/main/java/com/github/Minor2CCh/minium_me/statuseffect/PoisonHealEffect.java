package com.github.Minor2CCh.minium_me.statuseffect;

import net.fabricmc.loader.api.FabricLoader;
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
            //Sinytra環境だとmixinで毒回避ができないので旧式、ただし処理順の仕様上確実なダメージ回避はできない
            if(FabricLoader.getInstance().isModLoaded("neoforge")){
                if((25 >> poisonAmplifier) <= 1){//Lv.5以上は毒回避ができないので4に丸める
                    entity.removeStatusEffect(StatusEffects.POISON);
                    entity.setStatusEffect(new StatusEffectInstance(StatusEffects.POISON, poisonDuration, (poisonAmplifier = 3)), null);
                }
                if(poisonDuration <= 0){
                    entity.removeStatusEffect(StatusEffects.POISON);
                }else if((poisonDuration) % (25 >> poisonAmplifier) == 0){
                    entity.removeStatusEffect(StatusEffects.POISON);
                    entity.setStatusEffect(new StatusEffectInstance(StatusEffects.POISON, poisonDuration - 1, poisonAmplifier), null);
                }
            }

            if (!entity.hasStatusEffect(MiniumStatusEffects.POISONOUS_REGENERATION)){
                entity.addStatusEffect(new StatusEffectInstance(MiniumStatusEffects.POISONOUS_REGENERATION, 50, poisonAmplifier, true, true, true));
            }


        }
        return super.applyUpdateEffect(entity, amplifier);
    }
}
