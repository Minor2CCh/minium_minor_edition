package com.github.Minor2CCh.minium_me.client.mixin;

import com.github.Minor2CCh.minium_me.util.EntityFunctions;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.client.render.BackgroundRenderer$StatusEffectFogModifier")
public interface StatusEffectFogModifierMixin {
    @ModifyReturnValue(method = "shouldApply", at = @At("RETURN"))
    private static boolean immuneDarknessAndBlindness(boolean original, LivingEntity entity, float tickDelta){
        if(EntityFunctions.equipIrisQuartzArmors(entity, 4)){
            return false;
        }
        return original;
    }

}
