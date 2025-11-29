package com.github.Minor2CCh.minium_me.block.entity;

import com.github.Minor2CCh.minium_me.registry.MiniumBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.util.math.BlockPos;

public class MiniumHopperBlockEntity extends HopperBlockEntity {
    public MiniumHopperBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }
    @Override
    public BlockEntityType<?> getType() {
        return MiniumBlockEntityTypes.MINIUM_HOPPER_BLOCK_ENTITY;
    }
    @Override
    public boolean supports(BlockState state) {
        return this.getType().supports(state);
    }
}
