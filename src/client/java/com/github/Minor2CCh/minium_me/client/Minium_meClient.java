package com.github.Minor2CCh.minium_me.client;

import com.github.Minor2CCh.minium_me.block.MiniumBlock;
import com.github.Minor2CCh.minium_me.particle.MiniumParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.RenderLayer;

public class Minium_meClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(),
                MiniumBlock.MINIUM_GRATE
        );
        LivingEntityFeatureRendererRegistrationCallback.EVENT
                .register((entityType, entityRenderer, registrationHelper, context) -> {
                    registrationHelper
                            .register(new IrisQuartzElytraFeatureRenderer<>(entityRenderer,
                                    context.getModelLoader()));
                });
        MiniumEntityRenderers.initialize();
        ParticleFactoryRegistry.getInstance().register(MiniumParticle.ENERGY_HIT_PARTICLE, EnergyHitParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(MiniumParticle.ENERGY_HIT_PARTICLE2, EnergyHitParticle.Factory::new);
    }
}
