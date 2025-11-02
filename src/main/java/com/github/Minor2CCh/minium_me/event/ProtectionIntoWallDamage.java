package com.github.Minor2CCh.minium_me.event;

import com.github.Minor2CCh.minium_me.item.MiniumItem;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;

public class ProtectionIntoWallDamage {
    // 虹水晶のメイスでのスマッシュ攻撃は衝突ダメージも無効化する
    public static void initialize(){
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if(source.isOf(DamageTypes.FLY_INTO_WALL)){
                if(entity.getMainHandStack().getItem() == MiniumItem.IRIS_QUARTZ_MACE || entity.getOffHandStack().getItem() == MiniumItem.IRIS_QUARTZ_MACE){
                    if(entity instanceof PlayerEntity player){
                        return !player.shouldIgnoreFallDamageFromCurrentExplosion();
                    }
                }

            }
            return true;
        });

    }
}
