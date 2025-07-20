package com.github.Minor2CCh.minium_me.client.item;

import com.github.Minor2CCh.minium_me.component.MiniumModComponent;
import com.github.Minor2CCh.minium_me.item.MiniumGunItem;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class MiniumGunItemClient extends MiniumGunItem {
    public MiniumGunItemClient(Settings settings) {
        super(settings);
    }
    @Override
    public void appendTooltip(ItemStack itemStack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        MiniumModComponent.EnergyComponent EComp = itemStack.get(MiniumModComponent.REMAIN_ENERGY);
        int remain = EComp != null ? EComp.remain() : 0;
        String energyType = EComp != null ? EComp.type() : MiniumModComponent.ENERGY_EMPTY;
        if(itemStack.contains(MiniumModComponent.REMAIN_ENERGY)){
            tooltip.add(Text.translatable("item.minium_me.energy.remain", remain).formatted(Formatting.GOLD));
            int color = MiniumModComponent.getEnergyColor(energyType);
            String energyName = MiniumModComponent.getEnergyKey(energyType);
            tooltip.add(Text.translatable("item.minium_me.energy.type", Text.translatable(energyName)).withColor(color));


            if (Screen.hasShiftDown()) {
                tooltip.add(Text.translatable(energyName +".desc").formatted(Formatting.WHITE));
                var matchingItems = Registries.ITEM.iterateEntries(MiniumModComponent.getEnergyMaterial(energyType));
                var matchingItemsSB = Registries.ITEM.iterateEntries(MiniumModComponent.getEnergyMaterialSB(energyType));
                if(!matchingItems.iterator().hasNext() && !matchingItemsSB.iterator().hasNext() && MiniumModComponent.ENERGY_LIST.contains(energyType)){
                    tooltip.add(Text.translatable("item.minium_me.energy.unusable"));

                }
            } else {
                tooltip.add(Text.translatable("item.minium_me.hide_tooltip.desc"));
            }

        }
    }
}
