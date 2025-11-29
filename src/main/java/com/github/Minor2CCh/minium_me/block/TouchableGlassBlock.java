package com.github.Minor2CCh.minium_me.block;

import com.github.Minor2CCh.minium_me.util.HasCustomTooltip;
import net.minecraft.block.BlockState;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TransparentBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class TouchableGlassBlock extends TransparentBlock implements HasCustomTooltip {
    public TouchableGlassBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext esc) {
            Entity entity = esc.getEntity();
            if (entity != null) {
                if (entity instanceof PlayerEntity) {
                    return VoxelShapes.fullCube();
                }
            }
        }
        return VoxelShapes.empty();
    }
}
