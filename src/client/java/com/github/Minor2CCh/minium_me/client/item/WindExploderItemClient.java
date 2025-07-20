package com.github.Minor2CCh.minium_me.client.item;

import com.github.Minor2CCh.minium_me.item.MiniumItem;
import com.github.Minor2CCh.minium_me.item.WindExploderItem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class WindExploderItemClient extends WindExploderItem {
    public WindExploderItemClient(Settings settings) {
        super(settings);
    }
    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (Screen.hasShiftDown()) {
            if(itemStack.isOf(MiniumItem.WIND_EXPLODER)){
                tooltip.add(Text.translatable("item.minium_me.wind_exploder.desc").formatted(Formatting.WHITE));
            }else
            if(itemStack.isOf(MiniumItem.ADVANCED_WIND_EXPLODER)){
                tooltip.add(Text.translatable("item.minium_me.advanced_wind_exploder.desc").formatted(Formatting.WHITE));
            }
        } else {
            tooltip.add(Text.translatable("item.minium_me.hide_tooltip.desc"));
        }

    }
}
