package com.github.Minor2CCh.minium_me.client.mixin;

import com.github.Minor2CCh.minium_me.component.ArmorReinforcedComponent;
import com.github.Minor2CCh.minium_me.util.EntityFunctions;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.enums.CameraSubmersionType;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class BackGroundRendererMixin {
    @Inject(method = "applyFog", at = @At("RETURN"))
    private static void overrideFogRange(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
        CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();
        Entity entity = camera.getFocusedEntity();
        float fogStart = -8.0F;
        float fogEnd = viewDistance * 0.5F;
        if (cameraSubmersionType == CameraSubmersionType.LAVA) {
            if (entity instanceof LivingEntity && EntityFunctions.numReinforcedComponent((LivingEntity)entity, ArmorReinforcedComponent.FIRE_IMMUNE) == 4) {
                RenderSystem.setShaderFogStart(fogStart);
                RenderSystem.setShaderFogEnd(fogEnd);
            }
        } else if (cameraSubmersionType == CameraSubmersionType.POWDER_SNOW) {
            if (entity instanceof LivingEntity && EntityFunctions.numReinforcedComponent((LivingEntity)entity, ArmorReinforcedComponent.FROZEN_IMMUNE) >= 3) {
                RenderSystem.setShaderFogStart(fogStart);
                RenderSystem.setShaderFogEnd(fogEnd);
            }
        }
    }
}
