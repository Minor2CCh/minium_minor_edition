package com.github.Minor2CCh.minium_me.platform;

import com.github.Minor2CCh.minium_me.item.MiniumItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DisableAccessoryPlatform implements AccessoryPlatform{
    @Override
    public boolean enableTrinkets() {
        return false;
    }

    @Override
    public ItemStack getTrinketsStackNecklace(LivingEntity entity, Item item) {
        return null;
    }

    @Override
    public ItemStack getTrinketsStack(LivingEntity entity, Item item, String group1, String group2) {
        return null;
    }

    @Override
    public ItemStack getIrisQuartzElytraStack(LivingEntity entity) {
        if(entity.getEquippedStack(EquipmentSlot.CHEST).isOf(MiniumItem.IRIS_QUARTZ_ELYTRA) || entity.getEquippedStack(EquipmentSlot.CHEST).isOf(MiniumItem.IRIS_QUARTZ_ELYTRA_CHESTPLATE))
            return entity.getEquippedStack(EquipmentSlot.CHEST);
        return null;
    }
}
