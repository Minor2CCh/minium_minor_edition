package com.github.Minor2CCh.minium_me.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class ConveyorBlock extends HorizontalFacingBlock {
    public static final MapCodec<ConveyorBlock> CODEC = createCodec(ConveyorBlock::new);
    public static final double VELOCITY = 0.16;
    @Override
    public MapCodec<ConveyorBlock> getCodec() {
        return CODEC;
    }
    public ConveyorBlock(AbstractBlock.Settings settings) {
        super(settings);
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        Vec3d prevPos = entity.getPos();
        switch(state.get(FACING)){
            case Direction.NORTH:
                //entity.setPos(entity.getX(), entity.getY(), entity.getZ()+VELOCITY);
                entity.addVelocity(0, 0, VELOCITY);
                break;
            case Direction.WEST:
                //entity.setPos(entity.getX()+VELOCITY, entity.getY(), entity.getZ());
                entity.addVelocity(VELOCITY, 0, 0);
                break;
            case Direction.EAST:
                //entity.setPos(entity.getX()-VELOCITY, entity.getY(), entity.getZ());
                entity.addVelocity(-VELOCITY, 0, 0);
                break;
            case Direction.SOUTH:
                //entity.setPos(entity.getX(), entity.getY(), entity.getZ()-VELOCITY);
                entity.addVelocity(0, 0, -VELOCITY);
                break;
            }
            if(entity.isInsideWall()){
                entity.setPos(prevPos.x, prevPos.y, prevPos.z);
            }
        super.onSteppedOn(world, pos, state, entity);
    }
}
