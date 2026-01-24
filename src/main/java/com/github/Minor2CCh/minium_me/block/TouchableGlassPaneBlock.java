package com.github.Minor2CCh.minium_me.block;

import com.github.Minor2CCh.minium_me.util.HasCustomTooltip;
import net.minecraft.block.BlockState;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.PaneBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.List;

public class TouchableGlassPaneBlock extends PaneBlock implements HasCustomTooltip {
    public TouchableGlassPaneBlock(Settings settings) {
        super(settings);
    }
    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext esc) {
            Entity entity = esc.getEntity();
            if (entity != null) {
                if (entity instanceof PlayerEntity) {
                    return super.getCollisionShape(state, world, pos, context);
                }
            }
        }
        return VoxelShapes.empty();
    }
    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return true;
    }
    @Override
    public void customTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options, boolean hasShiftDown) {
        if (hasShiftDown) {
            tooltip.add(Text.translatable(MiniumBlock.MINIUM_TOUCHABLE_GLASS .getTranslationKey()+".desc").formatted(Formatting.WHITE));
        } else {
            tooltip.add(Text.translatable("item.minium_me.hide_tooltip.desc"));
        }
    }
}
