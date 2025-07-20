package com.github.Minor2CCh.minium_me.mixin;

import com.github.Minor2CCh.minium_me.item.MiniumItem;
import com.github.Minor2CCh.minium_me.item.MiniumItemTag;
import com.github.Minor2CCh.minium_me.statuseffect.MiniumStatusEffects;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "tick()V", at = @At("RETURN"), cancellable = false)
    private void IrisQuartzBonusInject(CallbackInfo ci){
        LivingEntity entity = ((LivingEntity) (Object) this);
        ItemStack itemStackHead = entity.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack itemStackChestplate = entity.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack itemStackLeggings = entity.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack itemStackBoots = entity.getEquippedStack(EquipmentSlot.FEET);
        int ParamDifficulty = switch (entity.getWorld().getDifficulty()) {
            case Difficulty.PEACEFUL -> 0;
            case Difficulty.EASY -> 1;
            case Difficulty.HARD -> 3;
            default -> 2;
        };

        if (itemStackHead.isOf(MiniumItem.IRIS_QUARTZ_HELMET) &&
                (itemStackChestplate.isOf(MiniumItem.IRIS_QUARTZ_CHESTPLATE) || itemStackChestplate.isOf(MiniumItem.IRIS_QUARTZ_ELYTRA_CHESTPLATE)) &&
                itemStackLeggings.isOf(MiniumItem.IRIS_QUARTZ_LEGGINGS) &&
                itemStackBoots.isOf(MiniumItem.IRIS_QUARTZ_BOOTS)) {

            int[] coolTick = new int[]{200, 200, 400, 800};//tick、1s=20tick
            int[] resistLevel = new int[]{1, 1, 0, 0};//peaceful,easy=level 2 normal,hard=level 1
            int[] absorptionLevel = new int[]{3, 2, 1, 0};
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20, resistLevel[ParamDifficulty], false, false, false));
            entity.addStatusEffect(new StatusEffectInstance(MiniumStatusEffects.POISON_HEAL, 20, resistLevel[ParamDifficulty], false, false, false));
            if(entity.getWorld().getTime() % coolTick[ParamDifficulty] == 0){
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, coolTick[ParamDifficulty]+1, absorptionLevel[ParamDifficulty], false, false, true));
            }
            if(entity.hasStatusEffect(StatusEffects.DARKNESS)){
                entity.removeStatusEffect(StatusEffects.DARKNESS);
            }
            if(entity.hasStatusEffect(StatusEffects.BLINDNESS)){
                entity.removeStatusEffect(StatusEffects.BLINDNESS);
            }
            if(entity.getWorld().getDifficulty() == Difficulty.HARD){
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.TRIAL_OMEN, 20, 0, false, false, true));
            }
            if(entity.getEquippedStack(EquipmentSlot.MAINHAND).isIn(MiniumItemTag.IRIS_QUARTZ_BONUS_WEAPON)){
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 20, 3, false, false, false));
            }
        }else if ((itemStackHead.isOf(MiniumItem.MINIUM_HELMET) &&
                itemStackChestplate.isOf(MiniumItem.MINIUM_CHESTPLATE) &&
                itemStackLeggings.isOf(MiniumItem.MINIUM_LEGGINGS) &&
                itemStackBoots.isOf(MiniumItem.MINIUM_BOOTS))) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20, 0, false, false, false));
        }else if ((itemStackHead.isOf(MiniumItem.C_MINIUM_HELMET) &&
                itemStackChestplate.isOf(MiniumItem.C_MINIUM_CHESTPLATE) &&
                itemStackLeggings.isOf(MiniumItem.C_MINIUM_LEGGINGS) &&
                itemStackBoots.isOf(MiniumItem.C_MINIUM_BOOTS))) {
            int[] resistLevel = new int[]{1, 1, 0, 0};//peaceful,easy=level 2 normal,hard=level 1
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20, resistLevel[ParamDifficulty], false, false, false));
        }
    }
    @ModifyReturnValue(method = "hurtByWater()Z", at = @At("RETURN"))
    private boolean pickingSaltHurt(boolean original){
        LivingEntity entity = ((LivingEntity) (Object) this);
        if(entity.hasStatusEffect(MiniumStatusEffects.PICKLING_SALT)){
            return true;
        }
        return original;
    }
}
