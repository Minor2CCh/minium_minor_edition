package com.github.Minor2CCh.minium_me.mixin.compat;

import com.github.Minor2CCh.minium_me.Minium_me;
import com.illusivesoulworks.elytraslot.common.SimpleCompatibilityProvider;
import com.illusivesoulworks.elytraslot.platform.Services;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(SimpleCompatibilityProvider.class)
public class SimpleCompatibilityProviderMixin {
    @Inject(method = "matches", at = @At("RETURN"))
    private void addCustomElytra(ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        Predicate<String> isLoaded = Services.PLATFORM::isModLoaded;

        if (isLoaded.test(Minium_me.MOD_ID)) {
            SimpleCompatibilityProviderAccessor.getIdToTexture().put("minium_me:iris_quartz_elytra",
                    Identifier.of("minium_me:textures/entity/iris_quartz_elytra.png"));
        }
    }
}
