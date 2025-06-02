package com.github.Minor2CCh.minium_me.mixin;

import com.github.Minor2CCh.minium_me.item.MiniumItem;
import com.github.Minor2CCh.minium_me.item.MiniumItemTag;
import com.github.Minor2CCh.minium_me.statuseffect.MiniumStatusEffects;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "Lnet/minecraft/entity/player/PlayerEntity;tick()V", at = @At("RETURN"), cancellable = false)
    private void IrisQuartzBonusInject(CallbackInfo ci){
        PlayerEntity player = ((PlayerEntity) (Object) this);
        ItemStack itemStackHead = player.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack itemStackChestplate = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack itemStackLeggings = player.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack itemStackBoots = player.getEquippedStack(EquipmentSlot.FEET);
        int ParamDifficulty = switch (player.getWorld().getDifficulty()) {
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
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20, resistLevel[ParamDifficulty], false, false, false));
            player.addStatusEffect(new StatusEffectInstance(MiniumStatusEffects.POISON_HEAL, 20, resistLevel[ParamDifficulty], false, false, false));
            if(player.getWorld().getTime() % coolTick[ParamDifficulty] == 0){
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, coolTick[ParamDifficulty], absorptionLevel[ParamDifficulty], false, false, true));
            }
            if(player.hasStatusEffect(StatusEffects.DARKNESS)){
                player.removeStatusEffect(StatusEffects.DARKNESS);
            }
            if(player.hasStatusEffect(StatusEffects.BLINDNESS)){
                player.removeStatusEffect(StatusEffects.BLINDNESS);
            }
            if(player.getWorld().getDifficulty() == Difficulty.HARD){
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.TRIAL_OMEN, 20, 0, false, false, true));
            }
            if(player.getEquippedStack(EquipmentSlot.MAINHAND).isIn(MiniumItemTag.IRIS_QUARTZ_BONUS_WEAPON)){
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 20, 3, false, false, false));
            }
        }else if ((itemStackHead.isOf(MiniumItem.MINIUM_HELMET) &&
                itemStackChestplate.isOf(MiniumItem.MINIUM_CHESTPLATE) &&
                        itemStackLeggings.isOf(MiniumItem.MINIUM_LEGGINGS) &&
                        itemStackBoots.isOf(MiniumItem.MINIUM_BOOTS))) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20, 0, false, false, false));
        } else if ((itemStackHead.isOf(MiniumItem.C_MINIUM_HELMET) &&
            itemStackChestplate.isOf(MiniumItem.C_MINIUM_CHESTPLATE) &&
            itemStackLeggings.isOf(MiniumItem.C_MINIUM_LEGGINGS) &&
            itemStackBoots.isOf(MiniumItem.C_MINIUM_BOOTS))) {
        int[] resistLevel = new int[]{1, 1, 0, 0};//peaceful,easy=level 2 normal,hard=level 1
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20, resistLevel[ParamDifficulty], false, false, false));
    }
    }

}
