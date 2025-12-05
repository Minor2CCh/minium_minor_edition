package com.github.Minor2CCh.minium_me.event;

import com.github.Minor2CCh.minium_me.Minium_me;
import com.github.Minor2CCh.minium_me.component.ArmorReinforcedComponent;
import com.github.Minor2CCh.minium_me.config.MiniumConfigLoader;
import com.github.Minor2CCh.minium_me.util.EntityFunctions;
import com.github.Minor2CCh.minium_me.item.MiniumItem;
import com.github.Minor2CCh.minium_me.item.MiniumItemTag;
import com.github.Minor2CCh.minium_me.registry.MiniumTrackDatas;
import com.github.Minor2CCh.minium_me.statuseffect.MiniumStatusEffects;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;

import java.util.Objects;

public class LivingEntityTickEvent {
    private static final Identifier HEALTH_BOOST_ATTRIBUTE_KEY = Minium_me.of("health_boost");
    public static void initialize(){
        ServerTickEvents.START_WORLD_TICK.register((world) -> {
            for(Entity entity : world.iterateEntities()) {
                if (!entity.isRemoved()) {
                    if (entity instanceof LivingEntity livingEntity) {
                        EntityAttributeInstance instance = livingEntity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
                        if(instance != null){
                            final int boostLimit = MiniumConfigLoader.getConfig().getHealthBoostLimit();
                            int healthValue = Math.min(boostLimit, livingEntity.getDataTracker().get(MiniumTrackDatas.getMaxHealthBoostTracker()));
                            if(instance.hasModifier(HEALTH_BOOST_ATTRIBUTE_KEY)){
                                EntityAttributeModifier modifier = instance.getModifier(HEALTH_BOOST_ATTRIBUTE_KEY);
                                if(modifier != null && modifier.value() != healthValue){
                                    instance.overwritePersistentModifier(buildHealthAttributeModifier((healthValue)));
                                }else if(healthValue == 0){
                                    instance.removeModifier(HEALTH_BOOST_ATTRIBUTE_KEY);
                                }
                            }else if(healthValue > 0){
                                instance.addPersistentModifier(buildHealthAttributeModifier(healthValue));
                            }
                        }
                    }
                }
            }

        });
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
    private static EntityAttributeModifier buildHealthAttributeModifier(int health){
        return new EntityAttributeModifier(HEALTH_BOOST_ATTRIBUTE_KEY, health, EntityAttributeModifier.Operation.ADD_VALUE);
    }
    public static void endTickCommonEvents(LivingEntity livingEntity){
        if(livingEntity.isSpectator()){
            return;
        }
        boolean hasPendant = EntityFunctions.hasItem(livingEntity, MiniumItem.IRIS_QUARTZ_PENDANT);
        int ParamDifficulty = MathHelper.clamp(livingEntity.getWorld().getDifficulty().getId(), 0, 3);
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
        if(EntityFunctions.numReinforcedComponent(livingEntity, ArmorReinforcedComponent.WATER_ADAPTION) >= 3){
            if(livingEntity.isTouchingWaterOrRain()){
                int conduitPowerTime;
                if(EntityFunctions.numReinforcedComponent(livingEntity, ArmorReinforcedComponent.WATER_ADAPTION) == 3){
                    conduitPowerTime = 20;
                }else{
                    if(livingEntity.hasStatusEffect(StatusEffects.CONDUIT_POWER)){
                        int tempTime = Objects.requireNonNull(livingEntity.getStatusEffect(StatusEffects.CONDUIT_POWER)).getDuration();
                        if(tempTime < 19){
                            conduitPowerTime = 20;
                        }else{
                            conduitPowerTime = tempTime + 2;
                        }
                    }else{
                        conduitPowerTime = 20;
                    }

                }

                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, conduitPowerTime, 0, true, false, true));
            }
        }
        if(EntityFunctions.numReinforcedComponent(livingEntity, ArmorReinforcedComponent.FIRE_IMMUNE) >= 3){
            if(!livingEntity.isFireImmune() && livingEntity.getFireTicks() <= 0 && livingEntity.getFrozenTicks() > 0 && !livingEntity.isTouchingWaterOrRain() && !livingEntity.getWorld().isClient()){
                livingEntity.setOnFireForTicks(80);
            }
        }
        if(EntityFunctions.numReinforcedComponent(livingEntity, ArmorReinforcedComponent.FIRE_IMMUNE) == 4){
            if(livingEntity.getFireTicks() > 0 && livingEntity.getWorld().getTime() % 40 == 0 && !livingEntity.getWorld().isClient()){
                if(livingEntity.isAlive()){
                    livingEntity.heal(1);
                }
            }
            if(livingEntity.getWorld().getDimension().ultrawarm() && livingEntity.getWorld().getTime() % 400 == 0 && !livingEntity.getWorld().isClient()){
                if(livingEntity.isAlive()){
                    livingEntity.heal(1);
                }
            }
            if(livingEntity instanceof PlayerEntity player){
                if(player.isFallFlying() && player.isInLava()){
                    player.stopFallFlying();
                }
            }

        }
        if(EntityFunctions.numReinforcedComponent(livingEntity, ArmorReinforcedComponent.WATER_ADAPTION) == 4){
            if(livingEntity.hasStatusEffect(StatusEffects.CONDUIT_POWER)){
                if(!livingEntity.getWorld().isClient()){
                    if(livingEntity.getWorld().getTime() % 40 == 0){
                        if(livingEntity.isAlive()){
                            livingEntity.heal(1);
                        }
                    }
                    if(livingEntity.getWorld().getTime() % 400 == 0){
                        if(livingEntity instanceof PlayerEntity player){
                            player.getHungerManager().add(1, 1);
                        }
                    }

                }
                livingEntity.setStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 300, 0, false, false, false), livingEntity);
                livingEntity.setStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 20, 0, false, false, false), livingEntity);
            }
        }
        if(EntityFunctions.numReinforcedComponent(livingEntity, ArmorReinforcedComponent.FROZEN_IMMUNE) >= 2){
            livingEntity.setFrozenTicks(0);
        }

    }
}
