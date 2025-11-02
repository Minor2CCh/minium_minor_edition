package com.github.Minor2CCh.minium_me.block;

import com.github.Minor2CCh.minium_me.item.HasCustomTooltip;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class PassableGlassBlock extends TransparentBlock implements HasCustomTooltip {
    public PassableGlassBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        // Entity の情報は ShapeContext を EntityShapeContext にキャストして取得
        if (context instanceof EntityShapeContext esc) {
            Entity entity = esc.getEntity();
            if (entity != null) {
                if (entity instanceof PlayerEntity) {
                    return VoxelShapes.empty();
                }
            }
        }
        // デフォルトは普通の当たり判定
        return VoxelShapes.fullCube();
    }
}
