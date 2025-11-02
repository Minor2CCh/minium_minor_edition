package com.github.Minor2CCh.minium_me.particle;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MiniumParticles {
    public static final SimpleParticleType ENERGY_HIT_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType ENERGY_HIT_PARTICLE2 = FabricParticleTypes.simple();

    public static void initialize() {
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(Minium_me.MOD_ID, "energy_hit"), ENERGY_HIT_PARTICLE);
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(Minium_me.MOD_ID, "energy2_hit"), ENERGY_HIT_PARTICLE2);
    }
}
