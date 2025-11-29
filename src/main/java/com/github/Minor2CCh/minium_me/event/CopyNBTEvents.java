package com.github.Minor2CCh.minium_me.event;

import com.github.Minor2CCh.minium_me.registry.MiniumTrackDatas;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;

public class CopyNBTEvents {
    public static void initialize(){
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            int boostHealth = oldPlayer.getDataTracker().get(MiniumTrackDatas.getMaxHealthBoostTracker());
            newPlayer.getDataTracker().set(MiniumTrackDatas.getMaxHealthBoostTracker(), boostHealth);
            //System.out.println("copied:"+alive);
        });

    }
}
