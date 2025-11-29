package com.github.Minor2CCh.minium_me.entity;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.minecraft.entity.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class MiniumEntityType{
    public static final EntityType<EnergyBulletEntity> ENERGY_BULLET = register(
            "energy_bullet",
            EntityType.Builder.<EnergyBulletEntity>create(EnergyBulletEntity::new, SpawnGroup.MISC)
                    .dimensions(0.4F, 0.4F)
                    .eyeHeight(0.13F)
                    .maxTrackingRange(4)
                    .trackingTickInterval(20)
                    .makeFireImmune()
    );

    private static <T extends Entity> EntityType<T> register(@SuppressWarnings("all")String id, EntityType.Builder<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, Minium_me.of(id), type.build(id));
    }
    public static void initialize() {

    }
}
