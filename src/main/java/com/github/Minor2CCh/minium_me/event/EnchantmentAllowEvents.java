package com.github.Minor2CCh.minium_me.event;

import com.github.Minor2CCh.minium_me.item.MiniumItem;
import com.github.Minor2CCh.minium_me.item.MiniumItemTag;
import net.fabricmc.fabric.api.item.v1.EnchantmentEvents;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Items;

public class EnchantmentAllowEvents {
    public static void initialize(){
        EnchantmentEvents.ALLOW_ENCHANTING.register((enchantment, stack, context) -> {
            Enchantment enchant = enchantment.value();
            if(stack.isIn(MiniumItemTag.SPEARS)){
                if (enchant.isPrimaryItem(Items.WOODEN_SWORD.getDefaultStack()) && enchant.isAcceptableItem(MiniumItem.MINIUM_SPEAR.getDefaultStack())) {
                    return TriState.TRUE;
                }

            }else if(stack.isOf(MiniumItem.IRIS_QUARTZ_MACE)){
                if (enchant.isPrimaryItem(Items.WOODEN_SWORD.getDefaultStack()) && enchant.isAcceptableItem(MiniumItem.IRIS_QUARTZ_MACE.getDefaultStack())) {
                    return TriState.TRUE;
                }
            }
            return TriState.DEFAULT;
        });

    }   // 剣で付与される攻撃系のエンチャントがエンチャントテーブルから出るように
        // Mixinで許可するとConnector使用時に反映されなくなる

}
