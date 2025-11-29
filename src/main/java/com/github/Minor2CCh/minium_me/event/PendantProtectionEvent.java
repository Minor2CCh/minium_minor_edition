package com.github.Minor2CCh.minium_me.event;

import com.github.Minor2CCh.minium_me.util.EntityFunctions;
import com.github.Minor2CCh.minium_me.item.MiniumItem;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;

import java.util.Objects;

public class PendantProtectionEvent {
    public static final int IRIS_QUARTZ_PENDANT_TOTEM_COOLDOWN = 1200;
    public static final int INVINCIBLE_TIME = 200;
    public static void initialize(){
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if(EntityFunctions.hasItem(entity, MiniumItem.IRIS_QUARTZ_PENDANT)){
                if(Objects.requireNonNull(source).isOf(DamageTypes.WITHER)){
                    if(EntityFunctions.equipIrisQuartzArmors(entity, 4)){
                        return false;
                    }
                }
                return !Objects.requireNonNull(source).isOf(DamageTypes.FLY_INTO_WALL) && !Objects.requireNonNull(source).isIn(DamageTypeTags.IS_FALL);
            }
            return true;
        });
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, source, amount) -> {
            if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                return true;
            }

            if(entity instanceof PlayerEntity playerEntity){
                if(playerEntity.getItemCooldownManager().isCoolingDown(MiniumItem.IRIS_QUARTZ_PENDANT)){
                    return true;
                }
            }
            if(EntityFunctions.useItem(entity, MiniumItem.IRIS_QUARTZ_PENDANT)){
                if(entity instanceof PlayerEntity playerEntity){
                    playerEntity.getItemCooldownManager().set(MiniumItem.IRIS_QUARTZ_PENDANT, IRIS_QUARTZ_PENDANT_TOTEM_COOLDOWN);
                }
                if(!entity.isAlive()){
                    entity.setHealth(1.0F);
                }
                if(entity.timeUntilRegen < INVINCIBLE_TIME){
                    entity.timeUntilRegen = INVINCIBLE_TIME;
                }
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, INVINCIBLE_TIME, 3));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, INVINCIBLE_TIME, 4));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, INVINCIBLE_TIME * 4, 0));
                entity.getWorld().sendEntityStatus(entity, EntityStatuses.USE_TOTEM_OF_UNDYING);
                return false;
            }

            return true;
        });

        ServerLivingEntityEvents.AFTER_DAMAGE.register((LivingEntity entity, DamageSource source, float amount, float damageTaken, boolean blocked) -> {
            if(entity instanceof ServerPlayerEntity serverPlayerEntity){
                if(EntityFunctions.hasItem(serverPlayerEntity, MiniumItem.IRIS_QUARTZ_PENDANT)){
                    if(source.isOf(DamageTypes.OUT_OF_WORLD)){
                        if(serverPlayerEntity.getPos().getY() < serverPlayerEntity.getWorld().getDimension().minY() - 64){
                            if(!serverPlayerEntity.getWorld().getDimension().hasCeiling()){//天井がない場合はY座標の限界+64に移動
                                serverPlayerEntity.teleport(serverPlayerEntity.getServerWorld(), entity.getPos().getX(), (serverPlayerEntity.getWorld().getDimension().height() + serverPlayerEntity.getWorld().getDimension().minY()) + 64 , entity.getPos().getZ(), entity.getYaw(), entity.getPitch());
                                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 1200, 0));
                            }else{
                                // 天井がある場合は初期スポーン地点に移動
                                ServerWorld currentWorld = serverPlayerEntity.getServerWorld();
                                TeleportTarget respawnTarget = serverPlayerEntity.getRespawnTarget(false, TeleportTarget.NO_OP);
                                TeleportTarget target = respawnTarget == null ?
                                        new TeleportTarget(currentWorld,
                                                new Vec3d(currentWorld.getSpawnPos().getX() + 0.5, currentWorld.getSpawnPos().getY(), currentWorld.getSpawnPos().getZ() + 0.5),
                                                Vec3d.ZERO, 0f, 0f, TeleportTarget.NO_OP)
                                        : respawnTarget;

                                serverPlayerEntity.teleportTo(target);
                                serverPlayerEntity.onTeleportationDone();
                                serverPlayerEntity.onLanding();           // 着地状態のリセット
                                serverPlayerEntity.fallDistance = 0.0F;   // 落下距離リセット

                            }
                        }
                    }
                }
            }
        });
    }
}
