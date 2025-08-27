package com.github.Minor2CCh.minium_me.entity;

import com.github.Minor2CCh.minium_me.item.MiniumItemTag;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EntityFunctions {
    public static boolean hasItem(LivingEntity entity, Item item){
        if(entity instanceof PlayerEntity playerEntity){
            if(playerEntity.getOffHandStack().getItem() == item){
                return true;
            }
            for (int i = 0; i < playerEntity.getInventory().size(); i++) {
                ItemStack stack = playerEntity.getInventory().getStack(i);
                if (!stack.isEmpty() && stack.getItem() == item) {
                    return true;
                }
            }
        }else if(entity instanceof LivingEntity livingEntity){
            return livingEntity.getOffHandStack().getItem() == item || livingEntity.getMainHandStack().getItem() == item;

        }
        return false;
    }
    public static boolean useItem(LivingEntity entity, Item item){
        if(entity instanceof PlayerEntity playerEntity){
            if(playerEntity.getOffHandStack().getItem() == item){
                playerEntity.getOffHandStack().decrement(1);
                return true;
            }
            for (int i = 0; i < playerEntity.getInventory().size(); i++) {
                ItemStack stack = playerEntity.getInventory().getStack(i);
                if (!stack.isEmpty() && stack.getItem() == item) {
                    stack.decrement(1);
                    return true;
                }
            }
        }else if(entity instanceof LivingEntity livingEntity){
            if(livingEntity.getOffHandStack().getItem() == item){
                livingEntity.getOffHandStack().decrement(1);
                return true;
            }else if(livingEntity.getMainHandStack().getItem() == item){
                livingEntity.getMainHandStack().decrement(1);
                return true;
            }

        }
        return false;
    }
    public static boolean equipMiniumArmors(LivingEntity entity, int requireNum){
        int count = 0;
        List<ItemStack> equipList = new ArrayList<>();
        equipList.add(entity.getEquippedStack(EquipmentSlot.HEAD));
        equipList.add(entity.getEquippedStack(EquipmentSlot.CHEST));
        equipList.add(entity.getEquippedStack(EquipmentSlot.LEGS));
        equipList.add(entity.getEquippedStack(EquipmentSlot.FEET));
        for(ItemStack stack : equipList){
            if(stack.isIn(MiniumItemTag.MINIUM_ARMORS)){
                count++;
            }
        }
        return count >= requireNum;
    }
    public static boolean equipConcentratedMiniumArmors(LivingEntity entity, int requireNum){
        int count = 0;
        List<ItemStack> equipList = new ArrayList<>();
        equipList.add(entity.getEquippedStack(EquipmentSlot.HEAD));
        equipList.add(entity.getEquippedStack(EquipmentSlot.CHEST));
        equipList.add(entity.getEquippedStack(EquipmentSlot.LEGS));
        equipList.add(entity.getEquippedStack(EquipmentSlot.FEET));
        for(ItemStack stack : equipList){
            if(stack.isIn(MiniumItemTag.CONCENTRATED_MINIUM_ARMORS)){
                count++;
            }
        }
        return count >= requireNum;
    }
    public static boolean equipIrisQuartzArmors(LivingEntity entity, int requireNum){
        int count = 0;
        List<ItemStack> equipList = new ArrayList<>();
        equipList.add(entity.getEquippedStack(EquipmentSlot.HEAD));
        equipList.add(entity.getEquippedStack(EquipmentSlot.CHEST));
        equipList.add(entity.getEquippedStack(EquipmentSlot.LEGS));
        equipList.add(entity.getEquippedStack(EquipmentSlot.FEET));
        for(ItemStack stack : equipList){
            if(stack.isIn(MiniumItemTag.IRIS_QUARTZ_ARMORS)){
                count++;
            }
        }
        return count >= requireNum;
    }

}
