package com.github.Minor2CCh.minium_me.event;

import com.github.Minor2CCh.minium_me.item.MiniumItem;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.Objects;

public class MaceAdvancementEvent {
    public static void initialize(){
        ServerLivingEntityEvents.AFTER_DAMAGE.register((LivingEntity entity, DamageSource source, float amount, float damageTaken, boolean blocked) -> {
            if(source.getAttacker() instanceof ServerPlayerEntity serverPlayerEntity){
                doneAdvancement(serverPlayerEntity, source, amount);
            }
        });
        ServerLivingEntityEvents.ALLOW_DEATH.register((LivingEntity entity, DamageSource source, float damageAmount) -> {
            if(source.getAttacker() instanceof ServerPlayerEntity serverPlayerEntity){
                doneAdvancement(serverPlayerEntity, source, damageAmount);
            }
            return true;
        });

    }
    public static void doneAdvancement(ServerPlayerEntity player, DamageSource source, float amount){
        if(amount >= 100 && player.getMainHandStack().isOf(MiniumItem.IRIS_QUARTZ_MACE)){
            Identifier advancementId = Identifier.ofVanilla("adventure/overoverkill");
            AdvancementEntry advancement = Objects.requireNonNull(player.getServer()).getAdvancementLoader().get(advancementId);
            if (advancement != null) {
                AdvancementProgress progress = player.getAdvancementTracker().getProgress(advancement);
                if (!progress.isDone()) {
                    for (String criterion : progress.getUnobtainedCriteria()) {
                        player.getAdvancementTracker().grantCriterion(advancement, criterion);
                    }
                }
            }
        }
    }
}
