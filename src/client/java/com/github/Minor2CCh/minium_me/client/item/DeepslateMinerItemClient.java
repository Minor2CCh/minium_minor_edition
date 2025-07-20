package com.github.Minor2CCh.minium_me.client.item;

import com.github.Minor2CCh.minium_me.item.DeepslateMinerItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

@Environment(EnvType.CLIENT)
public class DeepslateMinerItemClient extends DeepslateMinerItem {
    public DeepslateMinerItemClient(Settings settings) {
        super(settings);
    }
    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("item.minium_me.deepslate_miner.desc").formatted(Formatting.WHITE));
        } else {
            tooltip.add(Text.translatable("item.minium_me.hide_tooltip.desc"));
        }
    }
}
