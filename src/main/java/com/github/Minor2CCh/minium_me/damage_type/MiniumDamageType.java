package com.github.Minor2CCh.minium_me.damage_type;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class MiniumDamageType {
    public static final RegistryKey<DamageType> ENERGY_DEFAULT = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(Minium_me.MOD_ID, "energy_default"));
    public static final RegistryKey<DamageType> ENERGY_FIRE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(Minium_me.MOD_ID, "energy_fire"));
    public static final RegistryKey<DamageType> ENERGY_NOT_PROJECTILE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(Minium_me.MOD_ID, "energy_not_projectile"));
    public static final RegistryKey<DamageType> ENERGY_AMETHYST = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(Minium_me.MOD_ID, "energy_amethyst"));
    public static final RegistryKey<DamageType> ENERGY_IRIS_QUARTZ = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(Minium_me.MOD_ID, "energy_iris_quartz"));
    public static final RegistryKey<DamageType> MINIUM_GRINDING = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(Minium_me.MOD_ID, "minium_grinding"));
    //public static DamageSource of(World world, RegistryKey<DamageType> key) {
    //    return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
    //}

    public static void initialize() {


    }
}
