package com.github.Minor2CCh.minium_me.mixin;

import com.github.Minor2CCh.minium_me.component.ArmorReinforcedComponent;
import com.github.Minor2CCh.minium_me.component.MiniumModComponent;
import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Inject(method = "isFireImmune", at = @At("HEAD"), cancellable = true)
    private void enableFireImmune(CallbackInfoReturnable<Boolean> cir){
        ItemEntity entity = (ItemEntity)(Object)(this);
        ArmorReinforcedComponent component = entity.getStack().get(MiniumModComponent.ARMOR_REINFORCED);
        if(component != null){
            if(Objects.equals(component, ArmorReinforcedComponent.FIRE_IMMUNE)){
                cir.setReturnValue(true);
                cir.cancel();
            }
        }

    }
}
