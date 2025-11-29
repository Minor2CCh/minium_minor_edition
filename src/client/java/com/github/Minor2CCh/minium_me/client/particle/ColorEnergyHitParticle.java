package com.github.Minor2CCh.minium_me.client.particle;

import com.github.Minor2CCh.minium_me.particle.EnergyGunParticleType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;

public class ColorEnergyHitParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;
    protected ColorEnergyHitParticle(ClientWorld world, double x, double y, double z, double d, SpriteProvider spriteProvider, float red, float green, float blue) {
        super(world, x, y, z, 0.0, 0.0, 0.0);

        this.maxAge = 10;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.scale = 2.0F * (1.0F - (float)d * 0.5F);
        this.spriteProvider = spriteProvider;
        this.setSpriteForAge(spriteProvider);
    }
    @Override
    public int getBrightness(float tint) {
        return 15728880;
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.setSpriteForAge(this.spriteProvider);
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_LIT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<EnergyGunParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(EnergyGunParticleType particleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new ColorEnergyHitParticle(clientWorld, d, e, f, g, this.spriteProvider, particleType.getRed(), particleType.getGreen(), particleType.getBlue());
        }
    }
}
