package com.github.Minor2CCh.minium_me.particle;

import com.github.Minor2CCh.minium_me.Minium_me;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.Function;

public class MiniumParticles {
    public static final ParticleType<EnergyGunParticleType> ENERGY_HIT_PARTICLE = register(
            "energy_hit", false, EnergyGunParticleType::createCodec, EnergyGunParticleType::createPacketCodec
    );
    public static final SimpleParticleType ENERGY_HIT_PARTICLE2 = registerSimpleParticle("energy2_hit", false);

    public static void initialize() {
    }
    @SuppressWarnings("all")
    private static SimpleParticleType registerSimpleParticle(String name, boolean alwaysShow){
        return Registry.register(Registries.PARTICLE_TYPE, Minium_me.of(name), FabricParticleTypes.simple(alwaysShow));
    }
    @SuppressWarnings("all")
    private static <T extends ParticleEffect> ParticleType<T> register(String name, boolean alwaysShow, Function<ParticleType<T>, MapCodec<T>> codecGetter,
            Function<ParticleType<T>, PacketCodec<? super RegistryByteBuf, T>> packetCodecGetter
    ) {
        return Registry.register(Registries.PARTICLE_TYPE, Minium_me.of(name), new ParticleType<T>(alwaysShow) {
            @Override
            public MapCodec<T> getCodec() {
                return codecGetter.apply(this);
            }

            @Override
            public PacketCodec<? super RegistryByteBuf, T> getPacketCodec() {
                return packetCodecGetter.apply(this);
            }
        });
    }
}
