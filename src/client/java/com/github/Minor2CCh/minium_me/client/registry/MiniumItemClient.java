package com.github.Minor2CCh.minium_me.client.registry;

import com.github.Minor2CCh.minium_me.client.item.*;
import com.github.Minor2CCh.minium_me.component.MiniumModComponent;
import com.github.Minor2CCh.minium_me.item.MiniumItem;
import com.github.Minor2CCh.minium_me.item.MiniumMultiToolItem;
import com.github.Minor2CCh.minium_me.item.MiniumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.util.Rarity;

public class MiniumItemClient {
    public static void initialize(){
        MiniumItem.IRIS_QUARTZ_MULTITOOL = MiniumItem.register(new IrisQuartzMultiToolItemItemClient(MiniumToolMaterial.IRIS_QUARTZ_MULTITOOL, new Item.Settings().attributeModifiers(MiniumMultiToolItem.createAttributeModifiers(MiniumToolMaterial.IRIS_QUARTZ_MULTITOOL, 1.5f, -2.0f)).rarity(Rarity.EPIC).fireproof()), MiniumItem.getItemId(MiniumItem.IRIS_QUARTZ_MULTITOOL));
        MiniumItem.DEEPSLATE_MINER = MiniumItem.register(new DeepslateMinerItemClient(new Item.Settings().maxCount(16)), MiniumItem.getItemId(MiniumItem.DEEPSLATE_MINER));
        MiniumItem.ENERGY_GUN = MiniumItem.register(new MiniumGunItemClient(new Item.Settings().maxCount(1).component(MiniumModComponent.REMAIN_ENERGY, new MiniumModComponent.EnergyComponent(0, MiniumModComponent.ENERGY_EMPTY))), MiniumItem.getItemId(MiniumItem.ENERGY_GUN));
        MiniumItem.WIND_EXPLODER = MiniumItem.register(new WindExploderItemClient(new Item.Settings().maxDamage(64)), MiniumItem.getItemId(MiniumItem.WIND_EXPLODER));
        MiniumItem.ADVANCED_WIND_EXPLODER = MiniumItem.register(new WindExploderItemClient(new Item.Settings().maxDamage(256)), MiniumItem.getItemId(MiniumItem.ADVANCED_WIND_EXPLODER));
    }
}
