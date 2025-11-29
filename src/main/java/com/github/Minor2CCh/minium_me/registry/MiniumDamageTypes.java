package com.github.Minor2CCh.minium_me.registry;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class MiniumDamageTypes {
    public static final RegistryKey<DamageType> ENERGY_DEFAULT = register("energy_default");
    public static final RegistryKey<DamageType> ENERGY_FIRE = register("energy_fire");
    public static final RegistryKey<DamageType> ENERGY_NOT_PROJECTILE = register("energy_not_projectile");
    public static final RegistryKey<DamageType> ENERGY_AMETHYST = register("energy_amethyst");
    public static final RegistryKey<DamageType> ENERGY_IRIS_QUARTZ = register("energy_iris_quartz");
    public static final RegistryKey<DamageType> ENERGY_TITANIUM = register("energy_titanium");
    public static final RegistryKey<DamageType> MINIUM_GRINDING = register("minium_grinding");
    public static RegistryKey<DamageType> register(String id) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Minium_me.of(id));
    }
    public static void initialize() {
    }
}
