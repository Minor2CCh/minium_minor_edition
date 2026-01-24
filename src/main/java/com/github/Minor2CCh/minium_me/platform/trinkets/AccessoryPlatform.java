package com.github.Minor2CCh.minium_me.platform.trinkets;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface AccessoryPlatform {
    boolean enableTrinkets();
    @Nullable
    ItemStack getTrinketsStackNecklace(LivingEntity entity, Item item);
    @Nullable
    ItemStack getTrinketsStack(LivingEntity entity, Item item, String group1, String group2);
    @Nullable
    ItemStack getIrisQuartzElytraStack(LivingEntity entity);
    @Nullable
    ItemStack getTrinketsStackBelt(LivingEntity entity, Item item);
}
