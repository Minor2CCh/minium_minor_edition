package com.github.Minor2CCh.minium_me.client.render;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BannerPatternsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;

import java.util.Objects;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class MiniumShieldRenderer extends BuiltinModelItemRenderer {
    public static final MiniumShieldRenderer INSTANCE = new MiniumShieldRenderer(MinecraftClient.getInstance().getBlockEntityRenderDispatcher(), MinecraftClient.getInstance().getEntityModelLoader());

    protected final Supplier<ShieldEntityModel> shieldModel;

    public static final SpriteIdentifier SHIELD_BASE = new SpriteIdentifier(TexturedRenderLayers.SHIELD_PATTERNS_ATLAS_TEXTURE, Minium_me.of("entity/minium_shield_base"));
    public static final SpriteIdentifier SHIELD_BASE_NO_PATTERN = new SpriteIdentifier(TexturedRenderLayers.SHIELD_PATTERNS_ATLAS_TEXTURE, Minium_me.of("entity/minium_shield_base_nopattern"));
    public MiniumShieldRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelLoader entityModelLoader) {
        super(blockEntityRenderDispatcher, entityModelLoader);
        shieldModel = () -> new ShieldEntityModel(entityModelLoader.getModelPart(EntityModelLayers.SHIELD));
    }
    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light,
                       int overlay) {
        BannerPatternsComponent bannerPatternsComponent = stack.getOrDefault(DataComponentTypes.BANNER_PATTERNS, BannerPatternsComponent.DEFAULT);
        DyeColor dyeColor2 = stack.get(DataComponentTypes.BASE_COLOR);
        boolean bl = !bannerPatternsComponent.layers().isEmpty() || dyeColor2 != null;
        matrices.push();
        matrices.scale(1.0f, -1.0f, -1.0f);
        SpriteIdentifier spriteIdentifier = bl ? SHIELD_BASE : SHIELD_BASE_NO_PATTERN;
        VertexConsumer vertexConsumer = spriteIdentifier.getSprite().getTextureSpecificVertexConsumer(ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.shieldModel.get().getLayer(spriteIdentifier.getAtlasId()), true, stack.hasGlint()));
        this.shieldModel.get().getHandle().render(matrices, vertexConsumer, light, overlay);
        if (bl) {
            BannerBlockEntityRenderer.renderCanvas(matrices, vertexConsumers, light, overlay, this.shieldModel.get().getPlate(), spriteIdentifier, false, Objects.requireNonNullElse(dyeColor2, DyeColor.WHITE), bannerPatternsComponent, stack.hasGlint());
        } else {
            this.shieldModel.get().getPlate().render(matrices, vertexConsumer, light, overlay);
        }
        matrices.pop();
    }
}
