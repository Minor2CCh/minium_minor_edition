package com.github.Minor2CCh.minium_me.event;

import com.github.Minor2CCh.minium_me.item.MiniumShieldItem;
import com.github.Minor2CCh.minium_me.mixin.LivingEntityAccessor;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

public class ShieldDamageEvent {
    public static void initialize(){
        ServerLivingEntityEvents.AFTER_DAMAGE.register((LivingEntity entity, DamageSource source, float amount, float damageTaken, boolean blocked) -> {
            if(entity instanceof PlayerEntity player){
                ItemStack stack = ((LivingEntityAccessor)(player)).getActiveItemStack();
                if (blocked && stack.getItem() instanceof MiniumShieldItem) {
                    if (!player.getWorld().isClient) {
                        player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
                    }

                    if (amount >= 3.0F) {
                        int i = 1 + MathHelper.floor(amount);
                        Hand hand = player.getActiveHand();
                        stack.damage(i, player, PlayerEntity.getSlotForHand(hand));
                        if (stack.isEmpty()) {
                            if (hand == Hand.MAIN_HAND) {
                                player.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
                            } else {
                                player.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
                            }

                            stack.decrement(stack.getCount());
                            player.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + player.getWorld().random.nextFloat() * 0.4F);
                        }
                    }
                }

            }
        });

    }
}
