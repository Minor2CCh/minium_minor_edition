package com.github.Minor2CCh.minium_me.mixin;

import com.github.Minor2CCh.minium_me.component.ArmorReinforcedComponent;
import com.github.Minor2CCh.minium_me.component.MiniumModComponent;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Inject(method = "takesDamageFrom", at = @At("HEAD"), cancellable = true)
    private void enableFireImmune(DamageSource source, CallbackInfoReturnable<Boolean> cir){
        ItemStack stack = (ItemStack)(Object)(this);
        ArmorReinforcedComponent component = stack.get(MiniumModComponent.ARMOR_REINFORCED);
        if(source.isIn(DamageTypeTags.IS_FIRE) && component != null){
            if(Objects.equals(component, ArmorReinforcedComponent.FIRE_IMMUNE)){
                cir.setReturnValue(false);
                cir.cancel();
            }
        }

    }
}
