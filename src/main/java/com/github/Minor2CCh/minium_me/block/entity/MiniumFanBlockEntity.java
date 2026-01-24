package com.github.Minor2CCh.minium_me.block.entity;

import com.github.Minor2CCh.minium_me.block.MiniumFanBlock;
import com.github.Minor2CCh.minium_me.registry.MiniumBlockEntityTypes;
import com.github.Minor2CCh.minium_me.util.EntityFunctions;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.List;

public class MiniumFanBlockEntity extends BlockEntity {
    protected static final double SPEED = 0.12;
    protected static final double REACH = 5.0;
    public MiniumFanBlockEntity(BlockPos pos, BlockState state) {
        super(MiniumBlockEntityTypes.MINIUM_FAN_BLOCK_ENTITY, pos, state);
    }
    public static void tick(World world, BlockPos pos, BlockState state, MiniumFanBlockEntity be) {
        be.tick(world, pos, state);
    }
    protected void tick(World world, BlockPos pos, BlockState state){
        Direction direction = state.get(MiniumFanBlock.FACING);
            if(state.get(MiniumFanBlock.POWERED)){
                BlockPos centerPos = BlockPos.ofFloored(pos.toCenterPos());
                Box box = createFanBox(centerPos, direction);


                Vec3d velocity = new Vec3d(
                        direction.getOffsetX(),
                        direction.getOffsetY(),
                        direction.getOffsetZ()
                ).normalize().multiply(SPEED);
                if(world instanceof ServerWorld serverWorld) {
                    List<Entity> entities = serverWorld.getOtherEntities(null, box);
                    for (Entity entity : entities) {
                        if(!(entity instanceof PlayerEntity) || !(((PlayerEntity)entity).isCreative() || entity.isSpectator())){
                            double velMul = 1 - MathHelper.clamp(Math.sqrt(entity.getBoundingBox().squaredMagnitude(centerPos(pos, direction))) / REACH, 0, 1);
                            Vec3d resultVelocity = velocity.multiply(velMul);
                            entity.addVelocity(resultVelocity);

                            if(direction == Direction.UP){
                                entity.fallDistance = 0.0F;
                            }
                        }
                    }/*
                    if(world.getTime() % 3 == 0){
                        Vec3d centerPos2 = centerPos(pos, direction);
                        Vec3d velocity2 = new Vec3d(
                                direction.getOffsetX(),
                                direction.getOffsetY(),
                                direction.getOffsetZ()
                        ).normalize().multiply(REACH);
                        serverWorld.spawnParticles(
                                ParticleTypes.SMALL_GUST,
                                centerPos2.getX() + velocity2.getX() * world.getRandom().nextFloat(), centerPos2.getY() + velocity2.getY() * world.getRandom().nextFloat(), centerPos2.getZ() + velocity2.getZ() * world.getRandom().nextFloat(),
                                1,
                                velocity2.getX() == 0 ? 0.25 : 0, velocity2.getY() == 0 ? 0.25 : 0, velocity2.getZ() == 0 ? 0.25 : 0,
                                0.0
                        );
                    }*/
                }else{
                    PlayerEntity clientPlayer = EntityFunctions.getClientPlayer.get();
                    if(clientPlayer != null){
                        if(box.intersects(clientPlayer.getBoundingBox())){
                            if(!clientPlayer.isCreative() && !clientPlayer.isSpectator()){
                                double velMul = 1 - MathHelper.clamp(Math.sqrt(clientPlayer.getBoundingBox().squaredMagnitude(centerPos(pos, direction))) / REACH, 0, 1);
                                Vec3d resultVelocity = velocity.multiply(velMul);
                                clientPlayer.addVelocity(resultVelocity);
                                if(direction == Direction.UP){
                                    clientPlayer.fallDistance = 0.0F;
                                }
                            }

                        }
                    }
                    if(world.getTime() % 3 == 0){
                        Vec3d centerPos2 = centerPos(pos, direction);
                        Vec3d velocity2 = new Vec3d(
                                direction.getOffsetX(),
                                direction.getOffsetY(),
                                direction.getOffsetZ()
                        ).normalize().multiply(REACH);
                        world.addParticle(ParticleTypes.SMALL_GUST,
                                centerPos2.getX() + velocity2.getX() * getCosRandom(world) + (velocity2.getX() == 0 ? (world.getRandom().nextFloat() - 0.5) : 0),
                                centerPos2.getY() + velocity2.getY() * getCosRandom(world) + (velocity2.getY() == 0 ? (world.getRandom().nextFloat() - 0.5) : 0),
                                centerPos2.getZ() + velocity2.getZ() * getCosRandom(world) + (velocity2.getZ() == 0 ? (world.getRandom().nextFloat() - 0.5) : 0),
                                0,0,0);
                    }
                }
        }
    }
    protected static float getCosRandom(World world){
        float f = (float) (world.getRandom().nextFloat() * Math.PI / 2);
        return 1 - MathHelper.cos(f);
    }
    protected Box createFanBox(BlockPos pos, Direction direction){
        return switch (direction) {
            case Direction.NORTH -> new Box(
                    pos.getX(), pos.getY(), pos.getZ() - REACH,
                    pos.getX() + 1, pos.getY() + 1, pos.getZ()
            );
            case Direction.SOUTH -> new Box(
                    pos.getX(), pos.getY(), pos.getZ() + 1,
                    pos.getX() + 1, pos.getY() + 1, pos.getZ() + (1 + REACH)
            );
            case Direction.WEST -> new Box(
                    pos.getX() - REACH, pos.getY(), pos.getZ(),
                    pos.getX(),     pos.getY() + 1, pos.getZ() + 1
            );
            case Direction.EAST -> new Box(
                    pos.getX() + 1, pos.getY(), pos.getZ(),
                    pos.getX() + (1 + REACH), pos.getY() + 1, pos.getZ() + 1
            );
            case Direction.UP -> new Box(
                    pos.getX(), pos.getY() + 1, pos.getZ(),
                    pos.getX() + 1, pos.getY() + (1 + REACH), pos.getZ() + 1
            );
            case Direction.DOWN -> new Box(
                    pos.getX(), pos.getY() - REACH, pos.getZ(),
                    pos.getX() + 1, pos.getY(), pos.getZ() + 1
            );
        };

    }
    protected Vec3d centerPos(BlockPos pos, Direction direction){
        return switch (direction) {
            case Direction.NORTH -> new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ());
            case Direction.SOUTH -> new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 1);
            case Direction.WEST -> new Vec3d(pos.getX(), pos.getY() + 0.5, pos.getZ() + 0.5);
            case Direction.EAST -> new Vec3d(pos.getX() + 1, pos.getY() + 0.5, pos.getZ() + 0.5);
            case Direction.UP -> new Vec3d(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
            case Direction.DOWN -> new Vec3d(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        };

    }
}
