package com.github.Minor2CCh.minium_me.mixin;

import com.github.Minor2CCh.minium_me.component.ArmorReinforcedComponent;
import com.github.Minor2CCh.minium_me.util.EntityFunctions;
import com.github.Minor2CCh.minium_me.item.MiniumItemTag;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.BlockState;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {
    @ModifyReturnValue(method = "canWalkOnPowderSnow(Lnet/minecraft/entity/Entity;)Z", at = @At("RETURN"))
    private static boolean extraWalkOnPowderSnow(boolean original, Entity entity){
        if(!original){
            if(entity instanceof LivingEntity && (((LivingEntity) entity).getEquippedStack(EquipmentSlot.FEET).isIn(MiniumItemTag.WALKABLE_POWDER_SNOW)
                 || EntityFunctions.numReinforcedComponent((LivingEntity) entity,ArmorReinforcedComponent.FROZEN_IMMUNE) >= 3)){
                return true;
            }
        }
        return original;
    }
    @WrapOperation(
            method = "onEntityCollision",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;slowMovement(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Vec3d;)V"))
    private static void ImmuneSlowMovement(Entity entity, BlockState state, Vec3d multiplier, Operation<Void> original) {
        if(entity instanceof LivingEntity){
            if(EntityFunctions.numReinforcedComponent((LivingEntity) entity,ArmorReinforcedComponent.FROZEN_IMMUNE) >= 3){
                return;
            }
        }
        original.call(entity, state, multiplier);
    }
}
