package com.github.Minor2CCh.minium_me.client.mixin.compat;

import com.github.Minor2CCh.minium_me.item.MiniumMultiToolItem;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.ftb.mods.ftbultimine.client.PlatformUtil;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlatformUtil.class)
public class PlatformUtilMixin {
    @ModifyReturnValue(method = "canAxeStrip", at = @At("RETURN"))
    private static boolean extraAxeStrip(boolean original, ItemStack stack){
        return original || stack.getItem() instanceof MiniumMultiToolItem;
    }
    @ModifyReturnValue(method = "canFlattenPath", at = @At("RETURN"))
    private static boolean extraShovelStrip(boolean original, ItemStack stack){
        return original || stack.getItem() instanceof MiniumMultiToolItem;
    }
}
