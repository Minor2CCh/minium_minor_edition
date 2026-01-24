package com.github.Minor2CCh.minium_me.event;

import com.github.Minor2CCh.minium_me.registry.MiniumTrackData;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;

public final class CopyNBTEvents {
    private CopyNBTEvents() {}
    public static void initialize(){
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            int boostHealth = oldPlayer.getDataTracker().get(MiniumTrackData.getMaxHealthBoostTracker());
            newPlayer.getDataTracker().set(MiniumTrackData.getMaxHealthBoostTracker(), boostHealth);
            //System.out.println("copied:"+alive);
        });

    }
}
