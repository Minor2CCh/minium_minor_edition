package com.github.Minor2CCh.minium_me.event;

import com.github.Minor2CCh.minium_me.component.ArmorReinforcedComponent;
import com.github.Minor2CCh.minium_me.component.MiniumModComponent;
import com.github.Minor2CCh.minium_me.util.EntityFunctions;
import com.github.Minor2CCh.minium_me.registry.MiniumTrackDatas;
import com.github.Minor2CCh.minium_me.statuseffect.MiniumStatusEffects;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;

import java.util.Objects;


public class ArmorReinforceEvent {
    private static final int INVINCIBLE_TIME = 60;
    private static final int AP_INVINCIBLE_TIME = 40;
    public static void initialize() {
        // ダメージ無効化
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (source == null){
                return true;
            }
            if (EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.ALL_PROTECTION) == 4){
                long prevHitTime = entity.getDataTracker().get(MiniumTrackDatas.getPrevDamageTimeTracker());
                if(entity.getWorld().getTime() - prevHitTime >= 0 && entity.getWorld().getTime() - prevHitTime < AP_INVINCIBLE_TIME){
                    return false;
                }else{
                    entity.getDataTracker().set(MiniumTrackDatas.getPrevDamageTimeTracker(), entity.getWorld().getTime());
                }
            }
            if (source.isIn(DamageTypeTags.IS_FIRE)){
                if (EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.FIRE_IMMUNE) >= 3){
                    return false;
                }
            }
            if (source.isIn(DamageTypeTags.IS_FREEZING)){
                if (EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.FROZEN_IMMUNE) >= 2){
                    return false;
                }
            }
            if(source.isIn(DamageTypeTags.BURN_FROM_STEPPING)){
                if (EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.FROZEN_IMMUNE) >= 2){
                    return false;
                }

            }
            if (source.isIn(DamageTypeTags.WITCH_RESISTANT_TO)){
                if (EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.MAGIC_IMMUNE) >= 3){
                    if (EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.MAGIC_IMMUNE) >= 4 && amount > 0){
                        entity.heal(Math.min(entity.getMaxHealth() / 2, amount));
                    }
                    return false;
                }
            }
            return true;
        });
        // ダメージ後
        ServerLivingEntityEvents.AFTER_DAMAGE.register((LivingEntity entity, DamageSource source, float amount, float damageTaken, boolean blocked) -> {

            if (source.getAttacker() instanceof LivingEntity attackerEntity){
                if (EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.FROZEN_IMMUNE) >= 3){
                    if(attackerEntity.canFreeze()){
                        int addFreezeTime = EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.FROZEN_IMMUNE) == 3 ? 120 : 240;
                        attackerEntity.setFrozenTicks(Math.min(attackerEntity.getFrozenTicks()+addFreezeTime, attackerEntity.getMinFreezeDamageTicks() * 4));
                        if (EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.FROZEN_IMMUNE) == 4){
                            DamageSource damageSource = attackerEntity.getDamageSources().create(DamageTypes.FREEZE, entity);
                            attackerEntity.damage(damageSource, 5.0F);
                        }
                    }
                }
                if (EntityFunctions.numReinforcedComponent(entity, ArmorReinforcedComponent.WATER_ADAPTION) >= 4){
                    int maxAir = attackerEntity.getMaxAir();
                    int air = attackerEntity.getAir();
                    if (maxAir > 0){
                        attackerEntity.setAir(air > maxAir / 2 ? air / 2 : air - maxAir / 5);
                    }
                    if (!attackerEntity.isTouchingWaterOrRain()){
                        attackerEntity.addStatusEffect(new StatusEffectInstance(MiniumStatusEffects.PICKLING_SALT, 200, 0));
                    }
                }

            }
        });
        // トーテム効果
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, source, amount) -> {
            if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                return true;
            }
            for(EquipmentSlot slot : EntityFunctions.EQUIPMENT_SLOT_LIST){
                ItemStack stack = entity.getEquippedStack(slot);
                ArmorReinforcedComponent component = stack.get(MiniumModComponent.ARMOR_REINFORCED);
                if (Objects.equals(component, ArmorReinforcedComponent.UNDYING)){
                    AttributeModifiersComponent attr = component.attributeFunction.apply(slot);
                    removeAttributes(entity, attr);
                    stack.remove(MiniumModComponent.ARMOR_REINFORCED);

                    if (!entity.isAlive()){
                        entity.setHealth(1.0F);
                    }
                    if (entity.timeUntilRegen < INVINCIBLE_TIME){
                        entity.timeUntilRegen = INVINCIBLE_TIME;
                    }
                    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, INVINCIBLE_TIME, 3));
                    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, INVINCIBLE_TIME, 4));
                    entity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, INVINCIBLE_TIME * 4, 0));
                    entity.getWorld().sendEntityStatus(entity, EntityStatuses.USE_TOTEM_OF_UNDYING);
                    return false;
                }
            }
            return true;
        });
        // コンポーネント反映
        ServerEntityEvents.EQUIPMENT_CHANGE.register((livingEntity, slot, oldStack, newStack) -> {
            if(slot.isArmorSlot()){
                // --- 1. oldStack の補強効果を REMOVE ---
                ArmorReinforcedComponent oldComp = oldStack.get(MiniumModComponent.ARMOR_REINFORCED);
                if (oldComp != null) {
                    AttributeModifiersComponent attr = oldComp.attributeFunction.apply(slot);
                    removeAttributes(livingEntity, attr);
                }

                // --- 2. newStack の補強効果を ADD ---
                ArmorReinforcedComponent newComp = newStack.get(MiniumModComponent.ARMOR_REINFORCED);
                if (newComp != null) {
                    AttributeModifiersComponent attr = newComp.attributeFunction.apply(slot);
                    addAttributes(livingEntity, attr);
                }
            }
        });
    }
    private static void addAttributes(LivingEntity entity, AttributeModifiersComponent component) {
        for (var entry : component.modifiers()) {
            EntityAttributeInstance instance = entity.getAttributeInstance(entry.attribute());
            if (instance != null) {
                if(!instance.hasModifier(entry.modifier().id())){// クラッシュ対策
                    instance.addTemporaryModifier(entry.modifier());
                }
            }
        }
    }
    private static void removeAttributes(LivingEntity entity, AttributeModifiersComponent component) {
        for (var entry : component.modifiers()) {
            EntityAttributeInstance instance = entity.getAttributeInstance(entry.attribute());
            if (instance != null) {
                instance.removeModifier(entry.modifier().id());
            }
        }
    }
}
