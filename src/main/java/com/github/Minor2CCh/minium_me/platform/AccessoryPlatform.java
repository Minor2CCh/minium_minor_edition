package com.github.Minor2CCh.minium_me.platform;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface AccessoryPlatform {
    boolean enableTrinkets();
    ItemStack getTrinketsStackNecklace(LivingEntity entity, Item item);
    ItemStack getTrinketsStack(LivingEntity entity, Item item, String group1, String group2);
    ItemStack getIrisQuartzElytraStack(LivingEntity entity);
    ItemStack getTrinketsStackBelt(LivingEntity entity, Item item);
}
