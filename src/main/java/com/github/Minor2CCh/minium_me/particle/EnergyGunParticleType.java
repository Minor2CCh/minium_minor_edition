package com.github.Minor2CCh.minium_me.particle;

import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.ColorHelper;

public class EnergyGunParticleType implements ParticleEffect {
    public static MapCodec<EnergyGunParticleType> createCodec(ParticleType<EnergyGunParticleType> type) {
        return Codecs.ARGB.xmap(color -> new EnergyGunParticleType(type, color), effect -> effect.color).fieldOf("color");
    }

    public static PacketCodec<? super ByteBuf, EnergyGunParticleType> createPacketCodec(ParticleType<EnergyGunParticleType> type) {
        return PacketCodecs.INTEGER.xmap(color -> new EnergyGunParticleType(type, color), particleEffect -> particleEffect.color);
    }
    private final ParticleType<EnergyGunParticleType> type;
    private final int color;
    protected EnergyGunParticleType(ParticleType<EnergyGunParticleType> type, int color) {
        this.type = type;
        this.color = color;
    }

    @Override
    public ParticleType<EnergyGunParticleType> getType() {
        return this.type;
    }
    public float getRed() {
        return ColorHelper.Argb.getRed(this.color) / 255.0F;
    }

    public float getGreen() {
        return ColorHelper.Argb.getGreen(this.color) / 255.0F;
    }

    public float getBlue() {
        return ColorHelper.Argb.getBlue(this.color) / 255.0F;
    }
    @SuppressWarnings("unused")
    public float getAlpha() {
        return ColorHelper.Argb.getAlpha(this.color) / 255.0F;
    }

    public static EnergyGunParticleType create(ParticleType<EnergyGunParticleType> type, int color) {
        return new EnergyGunParticleType(type, color);
    }

    public static EnergyGunParticleType create(ParticleType<EnergyGunParticleType> type, float r, float g, float b) {
        return create(type, ColorHelper.Argb.fromFloats(1.0F, r, g, b));
    }
}
