package com.github.Minor2CCh.minium_me.mixin;

import com.github.Minor2CCh.minium_me.component.ArmorReinforcedComponent;
import com.github.Minor2CCh.minium_me.util.EntityFunctions;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndermanEntity.class)
public class EndermanEntityMixin {
    @Inject(method = "isPlayerStaring", at = @At("HEAD"), cancellable = true)
    private void disHostileEnderman(PlayerEntity player, CallbackInfoReturnable<Boolean> cir){
        if(EntityFunctions.numReinforcedComponent(player, ArmorReinforcedComponent.DIS_HOSTILE) > 2){
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
