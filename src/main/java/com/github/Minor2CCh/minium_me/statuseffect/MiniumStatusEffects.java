package com.github.Minor2CCh.minium_me.statuseffect;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public class MiniumStatusEffects {
    public static final RegistryEntry<StatusEffect> POISON_HEAL = Registry.registerReference(Registries.STATUS_EFFECT, Minium_me.of("poison_heal"), new PoisonHealEffect());
    public static final RegistryEntry<StatusEffect> POISONOUS_REGENERATION = Registry.registerReference(Registries.STATUS_EFFECT, Minium_me.of("poisonous_regeneration"), new PoisonousRegenerationEffect());
    public static final RegistryEntry<StatusEffect> PICKLING_SALT = Registry.registerReference(Registries.STATUS_EFFECT, Minium_me.of("pickling_salt"), new PicklingSaltEffect());

    public static void initialize() {
    }
}
