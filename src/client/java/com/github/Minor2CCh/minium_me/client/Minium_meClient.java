package com.github.Minor2CCh.minium_me.client;

import com.github.Minor2CCh.minium_me.block.MiniumBlock;
import com.github.Minor2CCh.minium_me.client.registry.MiniumBlockClient;
import com.github.Minor2CCh.minium_me.client.registry.MiniumItemClient;
import com.github.Minor2CCh.minium_me.item.IrisQuartzElytraItem;
import com.github.Minor2CCh.minium_me.item.MiniumItem;
import com.github.Minor2CCh.minium_me.particle.MiniumParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class Minium_meClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(), MiniumBlock.MINIUM_GRATE);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(), MiniumBlock.REDSTONE_ENERGY_BLOCK);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(), MiniumBlock.GLOWSTONE_ENERGY_BLOCK);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), MiniumBlock.EASY_GRINDER);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), MiniumBlock.ADVANCED_GRINDER);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), MiniumBlock.MINIUM_ARTIFICIAL_FLOWER);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), MiniumBlock.POTTED_MINIUM_ARTIFICIAL_FLOWER);
        LivingEntityFeatureRendererRegistrationCallback.EVENT
                .register((entityType, entityRenderer, registrationHelper, context) -> {
                    registrationHelper
                            .register(new IrisQuartzElytraFeatureRenderer<>(entityRenderer,
                                    context.getModelLoader()));
                });
        ModelPredicateProviderRegistry.register(MiniumItem.IRIS_QUARTZ_ELYTRA.asItem(),
                Identifier.of("broken"),
                (itemStack, clientWorld, livingEntity, seed) -> {
                    return IrisQuartzElytraItem.isUsable(itemStack) ? 0.0F : 1.0F;
                });
        MiniumEntityRenderers.initialize();
        ParticleFactoryRegistry.getInstance().register(MiniumParticle.ENERGY_HIT_PARTICLE, EnergyHitParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(MiniumParticle.ENERGY_HIT_PARTICLE2, EnergyHitParticle.Factory::new);
        MiniumItemClient.initialize();
        MiniumBlockClient.initialize();
    }
}
