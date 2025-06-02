package com.github.Minor2CCh.minium_me.mixin;

import com.github.Minor2CCh.minium_me.statuseffect.MiniumStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "net.minecraft.entity.LivingEntity.damage", at = @At("HEAD"), cancellable = true)
    private void PoisonEffectModify(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir){
        LivingEntity entity = (LivingEntity) (Object)(this);
        if(entity.hasStatusEffect(StatusEffects.POISON) //毒治癒状態かつ毒状態の時に1ダメージの魔法ダメージを無効化
                && entity.hasStatusEffect(MiniumStatusEffects.POISON_HEAL)
                && source.equals(entity.getDamageSources().magic())
                && amount == 1.0F){
            cir.setReturnValue(false);
        }
    }
}
