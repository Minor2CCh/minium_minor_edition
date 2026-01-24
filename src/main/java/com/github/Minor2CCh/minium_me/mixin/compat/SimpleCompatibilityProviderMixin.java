package com.github.Minor2CCh.minium_me.mixin.compat;

import com.illusivesoulworks.elytraslot.common.SimpleCompatibilityProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(SimpleCompatibilityProvider.class)
public class SimpleCompatibilityProviderMixin {
    @Final
    @Shadow
    private static Map<String, Identifier> ID_TO_TEXTURE;
    @Inject(method = "matches", at = @At("RETURN"))
    private void addCustomElytra(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        ID_TO_TEXTURE.put("minium_me:iris_quartz_elytra",
                Identifier.of("minium_me:textures/entity/iris_quartz_elytra.png"));

    }
}
