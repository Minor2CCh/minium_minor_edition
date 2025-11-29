package com.github.Minor2CCh.minium_me.registry;

import com.github.Minor2CCh.minium_me.Minium_me;
import com.github.Minor2CCh.minium_me.block.MiniumBlock;
import com.github.Minor2CCh.minium_me.block.entity.MiniumHopperBlockEntity;
import com.github.Minor2CCh.minium_me.block.entity.TemporallyBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class MiniumBlockEntityTypes {
    public static final BlockEntityType<TemporallyBlockEntity> TEMPORALLY_BLOCK_ENTITY =
            register("temporally_block", BlockEntityType.Builder.create(TemporallyBlockEntity::new,
            MiniumBlock.TEMPORALLY_BLOCK));
    public static final BlockEntityType<MiniumHopperBlockEntity> MINIUM_HOPPER_BLOCK_ENTITY =
            register("minium_hopper", BlockEntityType.Builder.create(MiniumHopperBlockEntity::new,
                    MiniumBlock.MINIUM_HOPPER));
    public static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType.Builder<T> builder){
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Minium_me.of(name), builder.build());
    }
    public static void initialize(){

    }
}
