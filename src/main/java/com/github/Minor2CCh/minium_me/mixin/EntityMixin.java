package com.github.Minor2CCh.minium_me.mixin;

import com.github.Minor2CCh.minium_me.component.ArmorReinforcedComponent;
import com.github.Minor2CCh.minium_me.util.EntityFunctions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ProjectileDeflection;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "getProjectileDeflection", at = @At("TAIL"), cancellable = true)
    private void extraWindAmuletDeflection(ProjectileEntity projectile, CallbackInfoReturnable<ProjectileDeflection> cir){
        if(cir.getReturnValue().equals(ProjectileDeflection.NONE)){
            if((Object)(this) instanceof LivingEntity entity){
                if(EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.WIND_AMULET) > 1){
                    cir.setReturnValue(EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.WIND_AMULET) > 2
                            ? (projectileEntity, hitEntity, random) -> {
                        hitEntity.getWorld().playSoundFromEntity(null, hitEntity, SoundEvents.ENTITY_BREEZE_DEFLECT, hitEntity.getSoundCategory(), 1.0F, 1.0F);
                        ProjectileDeflection.SIMPLE.deflect(projectileEntity, hitEntity, random);}
                            : ProjectileDeflection.NONE);
                    cir.cancel();
                }
            }
        }
    }
}
