package com.github.Minor2CCh.minium_me.damage_type;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class MiniumDamageType {
    public static final RegistryKey<DamageType> ENERGY_DEFAULT = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Minium_me.of("energy_default"));
    public static final RegistryKey<DamageType> ENERGY_FIRE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Minium_me.of("energy_fire"));
    public static final RegistryKey<DamageType> ENERGY_NOT_PROJECTILE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Minium_me.of("energy_not_projectile"));
    public static final RegistryKey<DamageType> ENERGY_AMETHYST = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Minium_me.of("energy_amethyst"));
    public static final RegistryKey<DamageType> ENERGY_IRIS_QUARTZ = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Minium_me.of("energy_iris_quartz"));
    public static final RegistryKey<DamageType> MINIUM_GRINDING = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Minium_me.of("minium_grinding"));
    public static RegistryKey<DamageType> register(String id) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Minium_me.of(id));
    }

    public static void initialize() {
    }
}
