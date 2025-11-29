package com.github.Minor2CCh.minium_me.client.event;

import com.github.Minor2CCh.minium_me.event.LivingEntityTickEvent;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class LivingEntityEventTickClient {
    public static void initialize(){
        ClientTickEvents.END_WORLD_TICK.register(clientWorld -> {
            for(Entity entity : clientWorld.getEntities()){
                if (!entity.isRemoved()) {
                    if(entity instanceof LivingEntity livingEntity){
                        LivingEntityTickEvent.endTickCommonEvents(livingEntity);
                    }
                }
            }
        });
    }
}
