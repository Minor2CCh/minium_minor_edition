package com.github.Minor2CCh.minium_me.mixin;

import com.github.Minor2CCh.minium_me.statuseffect.MiniumStatusEffects;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.entity.effect.PoisonStatusEffect")
public class PoisonStatusEffectMixin {
    @Inject(method = "applyUpdateEffect", at = @At("HEAD"), cancellable = true)
    private void avoidPoisonDamage(LivingEntity entity, int amplifier, CallbackInfoReturnable<Boolean> cir){

        if(entity.hasStatusEffect(MiniumStatusEffects.POISON_HEAL)){
            cir.setReturnValue(true);
        }
    }
}
