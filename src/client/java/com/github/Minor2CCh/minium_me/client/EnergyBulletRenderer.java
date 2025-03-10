package com.github.Minor2CCh.minium_me.client;

import com.github.Minor2CCh.minium_me.Minium_me;
import com.github.Minor2CCh.minium_me.entity.EnergyBulletEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
public class EnergyBulletRenderer extends EntityRenderer<EnergyBulletEntity> {
    private static final Identifier TEXTURE = Identifier.of(Minium_me.MOD_ID, "textures/entity/energy_bullet/spark.png");
    private static final RenderLayer LAYER = RenderLayer.getEntityTranslucent(TEXTURE);
    private final EnergyBulletEntityModel<EnergyBulletEntity> model;
    public EnergyBulletRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new EnergyBulletEntityModel<>(context.getPart(MiniumEntityRenderers.ENERGY_BULLET));
    }
    protected int getBlockLight(EnergyBulletEntity EnergyBulletEntity, BlockPos blockPos) {
        return 15;
    }

    public void render(EnergyBulletEntity energyBulletEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        float h = MathHelper.lerpAngleDegrees(g, energyBulletEntity.prevYaw, energyBulletEntity.getYaw());
        float j = MathHelper.lerp(g, energyBulletEntity.prevPitch, energyBulletEntity.getPitch());
        float k = (float)energyBulletEntity.age + g;
        matrixStack.translate(0.0F, 0.15F, 0.0F);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.sin(k * 0.1F) * 180.0F));
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(MathHelper.cos(k * 0.1F) * 180.0F));
        matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.sin(k * 0.15F) * 360.0F));
        matrixStack.scale(-0.5F, -0.5F, 0.5F);
        this.model.setAngles(energyBulletEntity, 0.0F, 0.0F, 0.0F, h, j);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(TEXTURE));
        if(energyBulletEntity.colorID == -1){
            energyBulletEntity.renderColor = EnergyBulletEntity.color;
            energyBulletEntity.colorID = 0;
        }
        //double rand = Math.random();
        //color = (int) (16777215*rand);
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, energyBulletEntity.renderColor);
        matrixStack.scale(1.5F, 1.5F, 1.5F);
        VertexConsumer vertexConsumer2 = vertexConsumerProvider.getBuffer(LAYER);
        this.model.render(matrixStack, vertexConsumer2, i, OverlayTexture.DEFAULT_UV, (energyBulletEntity.renderColor | 0x26000000));
        matrixStack.pop();
        super.render(energyBulletEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(EnergyBulletEntity entity) {
        return TEXTURE;
    }
}
