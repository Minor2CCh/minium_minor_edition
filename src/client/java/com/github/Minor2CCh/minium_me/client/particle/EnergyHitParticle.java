package com.github.Minor2CCh.minium_me.client.particle;

import com.github.Minor2CCh.minium_me.entity.EnergyBulletEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;

@Environment(EnvType.CLIENT)
public class EnergyHitParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;
    protected EnergyHitParticle(ClientWorld world, double x, double y, double z, double d, SpriteProvider spriteProvider) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.maxAge = 10;//6 + this.random.nextInt(4);
        //float f = 1.0F;//this.random.nextFloat() * 0.6F + 0.4F;
        int r = (EnergyBulletEntity.particleColor & 0xFF0000) >> 16;int g = (EnergyBulletEntity.particleColor & 0xFF00) >> 8;int b = (EnergyBulletEntity.particleColor & 0xFF);
        this.red = (float)(r / 255.0);
        this.green = (float)(g / 255.0);
        this.blue = (float)(b / 255.0);
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
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new EnergyHitParticle(clientWorld, d, e, f, g, this.spriteProvider);
        }
    }
}
