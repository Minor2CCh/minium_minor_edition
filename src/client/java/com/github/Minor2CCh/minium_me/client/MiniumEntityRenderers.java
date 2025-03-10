package com.github.Minor2CCh.minium_me.client;

import com.github.Minor2CCh.minium_me.Minium_me;
import com.github.Minor2CCh.minium_me.entity.MiniumEntityType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;



@Environment(EnvType.CLIENT)
public class MiniumEntityRenderers{

    public static EntityModelLayer ENERGY_BULLET = new EntityModelLayer(Identifier.of(Minium_me.MOD_ID, "energy_bullet"), "main");

    public static void initialize() {
        EntityRendererRegistry.register(MiniumEntityType.ENERGY_BULLET, EnergyBulletRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ENERGY_BULLET, EnergyBulletEntityModel::getTexturedModelData);
    }
}
