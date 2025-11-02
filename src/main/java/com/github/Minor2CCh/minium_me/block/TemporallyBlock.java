package com.github.Minor2CCh.minium_me.block;

import com.github.Minor2CCh.minium_me.block.entity.TemporallyBlockEntity;
import com.github.Minor2CCh.minium_me.item.TemporallyBlockPlacerItem;
import com.github.Minor2CCh.minium_me.registry.MiniumBlockEntityTypes;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class TemporallyBlock extends TransparentBlock implements Waterloggable, BlockEntityProvider {
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty FREEZE = BooleanProperty.of("freeze");
    public static final IntProperty EXTEND = IntProperty.of("extend", 0, 255);
    public TemporallyBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, Boolean.FALSE).with(FREEZE, Boolean.FALSE).with(EXTEND, 0));
    }
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TemporallyBlockEntity(pos, state);
    }
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return Objects.requireNonNull(this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER).with(FREEZE, Boolean.FALSE).with(EXTEND, 0));
    }
    @Override
    protected BlockState getStateForNeighborUpdate(
            BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos
    ) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
    @Override
    protected boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED).add(FREEZE).add(EXTEND);
    }
    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
                                                                  BlockEntityType<T> type) {
        if (type != MiniumBlockEntityTypes.TEMPORALLY_BLOCK_ENTITY)
            return null;
        return ((w, p, s, be) ->
                TemporallyBlockEntity.tick(w, p, s, (TemporallyBlockEntity) be));

    }
    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(!world.isClient()){
            ItemStack stack = player.getMainHandStack();
            if(stack.getItem() instanceof TemporallyBlockPlacerItem){
                TemporallyBlockPlacerItem.recoverTemporallyBlock(player, stack);
            }
        }

        return super.onBreak(world, pos, state, player);
    }
}
