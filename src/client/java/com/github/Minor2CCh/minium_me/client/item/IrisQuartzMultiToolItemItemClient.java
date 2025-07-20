package com.github.Minor2CCh.minium_me.client.item;

import com.github.Minor2CCh.minium_me.item.MiniumMultiToolItem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class IrisQuartzMultiToolItemItemClient extends MiniumMultiToolItem {
    public IrisQuartzMultiToolItemItemClient(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(itemStack, context, tooltip, type);
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("item.minium_me.iris_quartz_multitool.desc").formatted(Formatting.WHITE));
        } else {
            tooltip.add(Text.translatable("item.minium_me.hide_tooltip.desc"));
        }

    }
}
