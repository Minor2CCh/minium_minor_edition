package com.github.Minor2CCh.minium_me.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.WindChargeEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import net.minecraft.world.explosion.ExplosionBehavior;

import java.util.Optional;
import java.util.function.Function;

public class WindChargeBlock extends Block {
    public static final MapCodec<WindChargeBlock> CODEC = createCodec(WindChargeBlock::new);
    private static final ExplosionBehavior EXPLOSION_BEHAVIOR = new AdvancedExplosionBehavior(
            true, false, Optional.of(1.22F), Registries.BLOCK.getEntryList(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity())
    );
    public WindChargeBlock(Settings settings) {
        super(settings);
    }
    @Override
    public MapCodec<WindChargeBlock> getCodec() {
        return CODEC;
    }
    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!((double)fallDistance < 4.0) && entity instanceof LivingEntity livingEntity) {
            LivingEntity.FallSounds fallSounds = livingEntity.getFallSounds();
            SoundEvent soundEvent = (double)fallDistance < 4.0 ? fallSounds.small() : fallSounds.big();
            entity.playSound(soundEvent, 1.0F, 1.0F);
            if(!world.isClient()){
                WindChargeEntity windChargeEntity = new WindChargeEntity(world, pos.getX(), pos.getY(), pos.getZ(), new Vec3d(0, -0.125, 0));
                world.createExplosion(
                        windChargeEntity,
                        null,
                        EXPLOSION_BEHAVIOR,
                        pos.getX()+0.5,
                        pos.getY()+1.0,
                        pos.getZ()+0.5,
                        1.4F,
                        false,
                        World.ExplosionSourceType.TRIGGER,
                        ParticleTypes.GUST_EMITTER_SMALL,
                        ParticleTypes.GUST_EMITTER_LARGE,
                        SoundEvents.ENTITY_WIND_CHARGE_WIND_BURST
                );
            }
        }
    }

    @Override
    protected void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        BlockPos pos = hit.getBlockPos();
        if (!world.isClient && projectile.canModifyAt(world, pos) && projectile.canBreakBlocks(world)) {
                WindChargeEntity windChargeEntity = new WindChargeEntity(world, pos.getX(), pos.getY(), pos.getZ(), new Vec3d(0, -0.125, 0));
                world.createExplosion(
                        windChargeEntity,
                        null,
                        EXPLOSION_BEHAVIOR,
                        pos.getX()+0.5,
                        pos.getY()+1.0,
                        pos.getZ()+0.5,
                        1.4F,
                        false,
                        World.ExplosionSourceType.TRIGGER,
                        ParticleTypes.GUST_EMITTER_SMALL,
                        ParticleTypes.GUST_EMITTER_LARGE,
                        SoundEvents.ENTITY_WIND_CHARGE_WIND_BURST);
        }
    }

}
