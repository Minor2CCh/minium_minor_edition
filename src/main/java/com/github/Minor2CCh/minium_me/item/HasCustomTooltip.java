package com.github.Minor2CCh.minium_me.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
@SuppressWarnings("unused")
public interface HasCustomTooltip {
    default void customTooltip(ItemStack itemStack, Item.TooltipContext context, List<Text> tooltip, TooltipType type, boolean hasShiftDown){
        if (hasShiftDown) {
            tooltip.add(Text.translatable(itemStack.getTranslationKey()+".desc").formatted(Formatting.WHITE));
        } else {
            tooltip.add(Text.translatable("item.minium_me.hide_tooltip.desc"));
        }

    }
    static void staticCustomTooltip(ItemStack itemStack, Item.TooltipContext context, List<Text> tooltip, TooltipType type, boolean hasShiftDown){
        if (hasShiftDown) {
            tooltip.add(Text.translatable(itemStack.getTranslationKey()+".desc").formatted(Formatting.WHITE));
        } else {
            tooltip.add(Text.translatable("item.minium_me.hide_tooltip.desc"));
        }

    }
}
