package com.github.Minor2CCh.minium_me.event;

import com.github.Minor2CCh.minium_me.entity.EntityFunctions;
import com.github.Minor2CCh.minium_me.item.MiniumItem;
import com.github.Minor2CCh.minium_me.item.MiniumItemTag;
import com.github.Minor2CCh.minium_me.statuseffect.MiniumStatusEffects;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.world.Difficulty;

public class LivingEntityTickEvent {
    public static void initialize(){
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            for(Entity entity : world.iterateEntities()){
                if (!entity.isRemoved()) {
                    if(entity instanceof LivingEntity livingEntity){
                        endTickCommonEvents(livingEntity);
                    }
                }

            }
        });
    }
    public static void endTickCommonEvents(LivingEntity livingEntity){
        boolean hasPendant = EntityFunctions.hasItem(livingEntity, MiniumItem.IRIS_QUARTZ_PENDANT);
        int ParamDifficulty = switch (livingEntity.getWorld().getDifficulty()) {
            case Difficulty.PEACEFUL -> 0;
            case Difficulty.EASY -> 1;
            case Difficulty.HARD -> 3;
            default -> 2;
        };
        if(hasPendant){
            livingEntity.addStatusEffect(new StatusEffectInstance(MiniumStatusEffects.POISON_HEAL, 20, 0, false, false, false));
        }

        if (EntityFunctions.equipIrisQuartzArmors(livingEntity, 4)) {
            int[] coolTick = new int[]{200, 200, 400, 800};//tick、1s=20tick
            int[] resistLevel = new int[]{1, 1, 0, 0};//peaceful,easy=level 2 normal,hard=level 1
            int[] absorptionLevel = new int[]{3, 2, 1, 0};
            int pendantBonus = hasPendant ? 1 : 0;
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20, resistLevel[ParamDifficulty] + pendantBonus, false, false, false));
            livingEntity.addStatusEffect(new StatusEffectInstance(MiniumStatusEffects.POISON_HEAL, 20, 0, false, false, false));
            if(livingEntity.getWorld().getTime() % coolTick[ParamDifficulty] == 0){
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, coolTick[ParamDifficulty]+1, absorptionLevel[ParamDifficulty] + pendantBonus, false, false, true));
            }
            if(livingEntity.hasStatusEffect(StatusEffects.DARKNESS)){
                livingEntity.removeStatusEffect(StatusEffects.DARKNESS);
            }
            if(livingEntity.hasStatusEffect(StatusEffects.BLINDNESS)){
                livingEntity.removeStatusEffect(StatusEffects.BLINDNESS);
            }
            if(livingEntity.getWorld().getDifficulty() == Difficulty.HARD){
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.TRIAL_OMEN, 20, 0, false, false, true));
            }
            if(livingEntity.getEquippedStack(EquipmentSlot.MAINHAND).isIn(MiniumItemTag.IRIS_QUARTZ_BONUS_WEAPON)){
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 20, 3 + pendantBonus * 2, false, false, false));
            }
        }else if (EntityFunctions.equipConcentratedMiniumArmors(livingEntity, 4)) {
            int[] resistLevel = new int[]{1, 1, 0, 0};//peaceful,easy=level 2 normal,hard=level 1
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20, resistLevel[ParamDifficulty], false, false, false));
        }else if (EntityFunctions.equipMiniumArmors(livingEntity, 4)) {
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20, 0, false, false, false));
        }

    }
}
