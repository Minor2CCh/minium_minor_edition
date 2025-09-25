package com.github.Minor2CCh.minium_me.client;

import com.github.Minor2CCh.minium_me.block.MiniumBlock;
import com.github.Minor2CCh.minium_me.client.event.LivingEntityEventTickClient;
import com.github.Minor2CCh.minium_me.client.keybinds.ClientIrisQuartzElytraBoostEvent;
import com.github.Minor2CCh.minium_me.event.IrisQuartzElytraBoostEvent;
import com.github.Minor2CCh.minium_me.item.HasCustomTooltip;
import com.github.Minor2CCh.minium_me.item.IrisQuartzElytraItem;
import com.github.Minor2CCh.minium_me.item.MiniumItem;
import com.github.Minor2CCh.minium_me.particle.MiniumParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class Minium_meClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(),
                MiniumBlock.MINIUM_GRATE,
                MiniumBlock.REDSTONE_ENERGY_BLOCK,
                MiniumBlock.GLOWSTONE_ENERGY_BLOCK,
                MiniumBlock.MINIUM_CHAIN);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                MiniumBlock.EASY_GRINDER,
                MiniumBlock.ADVANCED_GRINDER,
                MiniumBlock.MINIUM_ARTIFICIAL_FLOWER,
                MiniumBlock.POTTED_MINIUM_ARTIFICIAL_FLOWER,
                MiniumBlock.MINIUM_LANTERN);
        LivingEntityFeatureRendererRegistrationCallback.EVENT
                .register((entityType, entityRenderer, registrationHelper, context) -> registrationHelper
                        .register(new IrisQuartzElytraFeatureRenderer<>(entityRenderer,
                                context.getModelLoader())));
        ModelPredicateProviderRegistry.register(MiniumItem.IRIS_QUARTZ_ELYTRA.asItem(),
                Identifier.of("broken"),
                (itemStack, clientWorld, livingEntity, seed) -> IrisQuartzElytraItem.isUsable(itemStack) ? 0.0F : 1.0F);
        MiniumEntityRenderers.initialize();
        ParticleFactoryRegistry.getInstance().register(MiniumParticle.ENERGY_HIT_PARTICLE, EnergyHitParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(MiniumParticle.ENERGY_HIT_PARTICLE2, EnergyHitParticle.Factory::new);
        ItemTooltipCallback.EVENT.register((ItemStack stack, Item.TooltipContext context, TooltipType tooltipType, java.util.List<Text> lines) -> {
            boolean hasShiftDown = Screen.hasShiftDown();
            if (stack.getItem() instanceof HasCustomTooltip tooltipItem) {
                tooltipItem.customTooltip(stack, context, lines, tooltipType, hasShiftDown);
            }else if (stack.getItem() instanceof BlockItem blockItem) {
                if(blockItem.getBlock() instanceof HasCustomTooltip tooltipItem){
                    tooltipItem.customTooltip(stack, context, lines, tooltipType, hasShiftDown);
                }
            }
            if (stack.getItem().equals(MiniumItem.IRIS_QUARTZ_PENDANT)) {
                if (hasShiftDown) {
                    lines.add(Text.translatable(stack.getTranslationKey()+".desc.0").formatted(Formatting.WHITE));
                    lines.add(Text.translatable(stack.getTranslationKey()+".desc.1").formatted(Formatting.WHITE));
                    lines.add(Text.translatable(stack.getTranslationKey()+".desc.2").formatted(Formatting.WHITE));
                    lines.add(Text.translatable(stack.getTranslationKey()+".desc.3").formatted(Formatting.WHITE));
                } else {
                    lines.add(Text.translatable("item.minium_me.hide_tooltip.desc"));
                }
            }
            if (stack.getItem().equals(MiniumItem.IRIS_QUARTZ_ELYTRA) || stack.getItem().equals(MiniumItem.IRIS_QUARTZ_ELYTRA_CHESTPLATE)) {
                if (hasShiftDown) {
                    if(ClientIrisQuartzElytraBoostEvent.keyBoostIrisQuartzElytra.isUnbound()){

                        lines.add(Text.translatable(MiniumItem.IRIS_QUARTZ_ELYTRA.getTranslationKey()+".desc.unbound"));
                    }else{
                        lines.add(Text.translatable(MiniumItem.IRIS_QUARTZ_ELYTRA.getTranslationKey()+".desc", ClientIrisQuartzElytraBoostEvent.keyBoostIrisQuartzElytra.getBoundKeyLocalizedText().getString(), IrisQuartzElytraBoostEvent.BOOST_COOL_TIME / 20.0).formatted(Formatting.WHITE));
                    }
                } else {
                    lines.add(Text.translatable("item.minium_me.hide_tooltip.desc"));
                }
            }
        });
        ClientIrisQuartzElytraBoostEvent.initialize();
        LivingEntityEventTickClient.initialize();


    }
}
