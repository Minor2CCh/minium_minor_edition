package com.github.Minor2CCh.minium_me.mixin;

import com.github.Minor2CCh.minium_me.block.entity.MiniumHopperBlockEntity;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HopperBlockEntity.class)
public class HopperBlockEntityMixin {
    @WrapOperation(method = "insertAndExtract", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/HopperBlockEntity;setTransferCooldown(I)V"))
    private static void injectTransferCooldown(HopperBlockEntity instance, int transferCooldown, Operation<Void> original) {
        if(instance instanceof MiniumHopperBlockEntity){
            original.call(instance, transferCooldown/2);
            return;
        }
        original.call(instance, transferCooldown);
    }
}
