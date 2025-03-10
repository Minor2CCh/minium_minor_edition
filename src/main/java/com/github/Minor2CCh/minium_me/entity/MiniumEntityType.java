package com.github.Minor2CCh.minium_me.entity;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.minecraft.entity.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MiniumEntityType{
//<T extends Entity> extends EntityType<T>
    public static final EntityType<EnergyBulletEntity> ENERGY_BULLET = register(
            "energy_bullet",
            EntityType.Builder.<EnergyBulletEntity>create(EnergyBulletEntity::new, SpawnGroup.MISC)
                    .dimensions(0.75F, 0.75F)
                    .eyeHeight(0.13F)
                    .maxTrackingRange(4)
                    .trackingTickInterval(20)
                    .makeFireImmune()
    );

    /*
    public MiniumEntityType(EntityType.EntityFactory<T> factory, SpawnGroup spawnGroup, boolean saveable, boolean summonable, boolean fireImmune, boolean spawnableFarFromPlayer, ImmutableSet<Block> canSpawnInside, EntityDimensions dimensions, float spawnBoxScale, int maxTrackDistance, int trackTickInterval, FeatureSet requiredFeatures) {
        super(factory, spawnGroup, saveable, summonable, fireImmune, spawnableFarFromPlayer, canSpawnInside, dimensions, spawnBoxScale, maxTrackDistance, trackTickInterval, requiredFeatures);
    }*/

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, Identifier.of(Minium_me.MOD_ID, id), type.build(id));
    }
    public static void initialize() {

    }
}
