package com.github.Minor2CCh.minium_me.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Objects;

public class RedStoneEnergyBlock extends AbstractEnergyBlock{
    public static final MapCodec<RedStoneEnergyBlock> CODEC = createCodec(RedStoneEnergyBlock::new);
    private static final Vec3d[] COLORS = Util.make(new Vec3d[16], colors -> {
        for (int i = 0; i <= 15; i++) {
            float f = (float)i / 15.0F;
            float g = f * 0.6F + (f > 0.0F ? 0.4F : 0.3F);
            float h = MathHelper.clamp(f * f * 0.7F - 0.5F, 0.0F, 1.0F);
            float j = MathHelper.clamp(f * f * 0.6F - 0.7F, 0.0F, 1.0F);
            colors[i] = new Vec3d(g, h, j);
        }
    });
    public RedStoneEnergyBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public MapCodec<RedStoneEnergyBlock> getCodec() {
        return CODEC;
    }
    @Override
    protected boolean emitsRedstonePower(BlockState state) {
        return true;
    }
    @Override
    protected boolean hasComparatorOutput(BlockState state) {
        return true;
    }
    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return 15;
    }
    @Override
    protected int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return 15;
    }
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        for (Direction direction : Direction.Type.HORIZONTAL) {
            this.addPoweredParticles(world, random, pos, COLORS[Random.create().nextBetween(0, 15)], direction);
        }
    }
    private void addPoweredParticles(World world, Random random, BlockPos pos, Vec3d color, Direction direction2) {
        float h = (float) 0.5;
            float j = h * random.nextFloat();
            Direction direction;
            int rand = Random.create().nextBetween(0, 5);
            direction = switch (rand) {
                case 0 -> Direction.DOWN;
                case 1 -> Direction.UP;
                case 2 -> Direction.NORTH;
                case 3 -> Direction.SOUTH;
                case 4 -> Direction.WEST;
                default -> Direction.EAST;
            };
            double d = 0.5 + (double)(0.4375F * (float) direction.getOffsetX()) + (double)(j * (float)direction2.getOffsetX());
            double e = 0.5 + (double)(0.4375F * (float) direction.getOffsetY()) + (double)(j * (float)direction2.getOffsetY());
            double k = 0.5 + (double)(0.4375F * (float) direction.getOffsetZ()) + (double)(j * (float)direction2.getOffsetZ());
            world.addParticle(new DustParticleEffect(color.toVector3f(), 1.0F), (double)pos.getX() + d, (double)pos.getY() + e, (double)pos.getZ() + k, 0.0, 0.0, 0.0);

    }
}
