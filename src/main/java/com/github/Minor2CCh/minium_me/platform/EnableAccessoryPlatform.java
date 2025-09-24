package com.github.Minor2CCh.minium_me.platform;

import com.github.Minor2CCh.minium_me.item.MiniumItem;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketInventory;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Map;
import java.util.Optional;

public class EnableAccessoryPlatform implements AccessoryPlatform{
    @Override
    public boolean enableTrinkets() {
        return true;
    }


    @Override
    public ItemStack getTrinketsStackNecklace(LivingEntity entity, Item item) {
        return getTrinketsStack(entity, item, "chest", "necklace");
    }

    @Override
    public ItemStack getTrinketsStack(LivingEntity entity, Item item, String group1, String group2) {
        Optional<TrinketComponent> componentOpt = TrinketsApi.getTrinketComponent(entity);

        if (componentOpt.isPresent()) {
            TrinketComponent component = componentOpt.get();

            Map<String, Map<String, TrinketInventory>> inventory = component.getInventory();

            TrinketInventory trinketInv = inventory
                    .getOrDefault(group1, Map.of())
                    .get(group2);

            if (trinketInv != null) {
                for (int i = 0; i < trinketInv.size(); i++) {
                    ItemStack stack = trinketInv.getStack(i);
                    if (stack.getItem() == item) {
                        return stack;
                    }
                }
            }
        }
        return null;
    }
    @SuppressWarnings("all")
    @Override
    public ItemStack getIrisQuartzElytraStack(LivingEntity entity) {
        if(entity.getEquippedStack(EquipmentSlot.CHEST).isOf(MiniumItem.IRIS_QUARTZ_ELYTRA) || entity.getEquippedStack(EquipmentSlot.CHEST).isOf(MiniumItem.IRIS_QUARTZ_ELYTRA_CHESTPLATE))
            return entity.getEquippedStack(EquipmentSlot.CHEST);
        if(FabricLoader.getInstance().isModLoaded("elytraslot")){

            ItemStack stack1 = getTrinketsStack(entity, MiniumItem.IRIS_QUARTZ_ELYTRA, "chest", "cape");
            if(stack1 != null){
                return stack1;
            }
            ItemStack stack2 = getTrinketsStack(entity, MiniumItem.IRIS_QUARTZ_ELYTRA, "chest", "back");
            if(stack2 != null){
                return stack2;
            }
        }
        return null;
    }
}
