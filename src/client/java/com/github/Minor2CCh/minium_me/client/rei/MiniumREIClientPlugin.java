package com.github.Minor2CCh.minium_me.client.rei;

import com.github.Minor2CCh.minium_me.block.AdvancedGrindingBlock;
import com.github.Minor2CCh.minium_me.block.GrindingBlock;
import com.github.Minor2CCh.minium_me.item.*;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.entry.EntryRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

public class MiniumREIClientPlugin implements REIClientPlugin {
    @Override
    public void registerEntries(EntryRegistry registry){
        registry.removeEntryIf(entry -> {
            Object value = entry.getValue();
            if (value instanceof ItemStack stack) {
                if(stack.getItem().getClass().equals(DeepslateMinerItem.class)
                    || stack.getItem().getClass().equals(MiniumGunItem.class)
                    || stack.getItem().getClass().equals(WindExploderItem.class)
                    || stack.getItem().getTranslationKey().equals(MiniumItem.IRIS_QUARTZ_MULTITOOL.getTranslationKey()) && stack.getItem().getClass().equals(MiniumMultiToolItem.class)){
                return true;
                }else if(!FabricLoader.getInstance().isModLoaded("neoforge") && stack.getItem() instanceof BlockItem blockItem){
                    return blockItem.getBlock().getClass().equals(GrindingBlock.class)
                            || blockItem.getBlock().getClass().equals(AdvancedGrindingBlock.class);
                }
            }
            return false;
        });
    }
}
