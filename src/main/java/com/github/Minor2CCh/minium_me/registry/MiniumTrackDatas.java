package com.github.Minor2CCh.minium_me.registry;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;

public class MiniumTrackDatas {
    // 注意！
    // このクラスは必ず初期化前に読み込まないこと
    // ワールドに入る際にクラッシュします
    private static TrackedData<Long> PREV_DAMAGE_TIME;
    private static TrackedData<Integer> MAX_HEALTH_BOOST_VALUE;
    public static void initialize(){
        PREV_DAMAGE_TIME =
            DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.LONG);
        MAX_HEALTH_BOOST_VALUE =
                DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.INTEGER);

    }
    public static TrackedData<Long> getPrevDamageTimeTracker(){
        return PREV_DAMAGE_TIME;
    }
    public static TrackedData<Integer> getMaxHealthBoostTracker(){
        return MAX_HEALTH_BOOST_VALUE;
    }
}
