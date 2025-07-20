package com.github.Minor2CCh.minium_me.mixin;

import com.github.Minor2CCh.minium_me.item.MiniumItemTag;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {
    @ModifyReturnValue(method = "canWalkOnPowderSnow(Lnet/minecraft/entity/Entity;)Z", at = @At("RETURN"))
    private static boolean extraWalkOnPowderSnow(boolean original, Entity entity){
        if(!original){
            if(entity instanceof LivingEntity && ((LivingEntity) entity).getEquippedStack(EquipmentSlot.FEET).isIn(MiniumItemTag.WALKABLE_POWDER_SNOW)){
                return true;
            }
        }
        return original;
    }
}
