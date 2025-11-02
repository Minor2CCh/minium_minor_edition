package com.github.Minor2CCh.minium_me.block.entity;

import com.github.Minor2CCh.minium_me.block.TemporallyBlock;
import com.github.Minor2CCh.minium_me.registry.MiniumBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TemporallyBlockEntity extends BlockEntity {
    public long placedTime = 0;
    /*
    public TemporallyBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }*/
    public TemporallyBlockEntity(BlockPos pos, BlockState state) {
        super(MiniumBlockEntityTypes.TEMPORALLY_BLOCK_ENTITY, pos, state);
    }
    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.placedTime = nbt.getLong("placedTime");
    }

    // 保存処理（ワールドセーブ時など）
    @Override
    public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putLong("placedTime", this.placedTime);
    }
    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup); // クライアントに初期データ送信
    }
    public static void tick(World world, BlockPos pos, BlockState state, TemporallyBlockEntity be) {
        be.tick(world, pos, state);
    }
    protected void tick(World world, BlockPos pos, BlockState state){
        if(!world.isClient()){
            if(!state.get(TemporallyBlock.FREEZE)){
                int extendMul = state.get(TemporallyBlock.EXTEND) + 1;
                if(this.placedTime > 200L * extendMul){
                    world.breakBlock(pos, false);
                }
                this.placedTime++;
                markDirty();
            }

        }
    }
}
