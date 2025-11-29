package com.github.Minor2CCh.minium_me.mixin;

import com.github.Minor2CCh.minium_me.component.ArmorReinforcedComponent;
import com.github.Minor2CCh.minium_me.util.EntityFunctions;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEntityTypeTags;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PhantomEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(MobEntity.class)
public class MobEntityMixin {
    @ModifyReturnValue(method = "getTarget", at = @At("TAIL"))
    private LivingEntity disHostilePhantom(LivingEntity original){
        if((Object)(this) instanceof PhantomEntity){
            if(original != null){
                if(EntityFunctions.numReinforcedComponent(original, ArmorReinforcedComponent.DIS_HOSTILE) > 0){
                    return null;
                }
            }
        }
        return original;

    }
    @Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
    private void disHostileEternity(LivingEntity target, CallbackInfo ci){
        if(target != null){
            MobEntity entity = (MobEntity)(Object)(this);
            if(!entity.getType().isIn(ConventionalEntityTypeTags.BOSSES)){
                if(EntityFunctions.numReinforcedComponent(target, ArmorReinforcedComponent.DIS_HOSTILE) == 4){
                    if(!Objects.equals(entity.getAttacker(), target)){
                        ci.cancel();
                    }
                }
            }
        }
    }
}
