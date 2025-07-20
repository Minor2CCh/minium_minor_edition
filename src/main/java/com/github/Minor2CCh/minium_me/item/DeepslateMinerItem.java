package com.github.Minor2CCh.minium_me.item;

import com.github.Minor2CCh.minium_me.block.MiniumBlockTag;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;


import static net.minecraft.block.Block.dropStacks;

public class DeepslateMinerItem extends Item{
    public DeepslateMinerItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        PlayerEntity playerEntity = context.getPlayer();
        ItemStack itemStack = context.getStack();
        if (rangeStoneBreak(context, world, blockPos, playerEntity)) {
            itemStack.decrementUnlessCreative(1, playerEntity);
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }
    //一括破壊
    private boolean rangeStoneBreak(ItemUsageContext context, World world, BlockPos blockPos, PlayerEntity playerEntity){
        BlockState blockState = world.getBlockState(blockPos);
        ItemStack itemStack = context.getStack();
        if (blockState.isIn(MiniumBlockTag.DEEPSLATE_MINER_CAN_BREAK)) {//深層岩系ブロック
                if (!world.isClient()) {
                    stoneBreakDrop(world, blockPos, playerEntity, itemStack);
                    for(int i = 0;i < 5;i++){
                        for(int j = 0;j < 5;j++){
                            for(int k = 0;k < 5;k++) {
                                Direction direction = context.getSide();
                                BlockPos tempBlockPos = blockPos.add(i + (direction == Direction.EAST ? -4 : (direction == Direction.WEST ? 0 : -2)), j - 2, k + (direction == Direction.SOUTH ? -4 : (direction == Direction.NORTH ? 0 : -2)));
                                if (world.getBlockState(tempBlockPos).isIn(MiniumBlockTag.DEEPSLATE_MINER_CAN_BREAK)){
                                    stoneBreakDrop(world, tempBlockPos, playerEntity, itemStack);
                                }
                            }
                        }
                    }

                }
                return true;


        }
        return false;
    }
    private void stoneBreakDrop(World world, BlockPos blockPos, PlayerEntity playerEntity, ItemStack itemStack){
        BlockState blockState = world.getBlockState(blockPos);
        dropStacks(blockState, world, blockPos, null, playerEntity, itemStack);
        world.breakBlock(blockPos, false, playerEntity);

    }
}
