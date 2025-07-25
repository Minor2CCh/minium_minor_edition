package com.github.Minor2CCh.minium_me.statuseffect;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class MiniumStatusEffects {
    public static final RegistryEntry<StatusEffect> POISON_HEAL = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(Minium_me.MOD_ID, "poison_heal"), new PoisonHealEffect());
    public static final RegistryEntry<StatusEffect> POISONOUS_REGENERATION = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(Minium_me.MOD_ID, "poisonous_regeneration"), new PoisonousRegenerationEffect());
    public static final RegistryEntry<StatusEffect> PICKLING_SALT = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(Minium_me.MOD_ID, "pickling_salt"), new PicklingSaltEffect());

    public static void initialize() {
    }
}
