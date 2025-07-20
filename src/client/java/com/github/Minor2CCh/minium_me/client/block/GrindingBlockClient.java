package com.github.Minor2CCh.minium_me.client.block;

import com.github.Minor2CCh.minium_me.block.GrindingBlock;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class GrindingBlockClient extends GrindingBlock {
    public GrindingBlockClient(Settings settings) {
        super(settings);
    }
    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable(stack.getItem().getTranslationKey()+".desc", GRIND_DAMAGE).formatted(Formatting.WHITE));
        } else {
            tooltip.add(Text.translatable("item.minium_me.hide_tooltip.desc"));
        }
    }
}
