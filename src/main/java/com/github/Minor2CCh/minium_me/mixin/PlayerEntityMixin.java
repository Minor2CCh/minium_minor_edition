package com.github.Minor2CCh.minium_me.mixin;

import com.github.Minor2CCh.minium_me.item.MiniumItem;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "disableShield", at = @At("HEAD"))
    private void setMiniumShieldCooldownTime(CallbackInfo ci){
        PlayerEntity player = (PlayerEntity)(Object)this;
        player.getItemCooldownManager().set(MiniumItem.MINIUM_SHIELD, 100);

    }

}
