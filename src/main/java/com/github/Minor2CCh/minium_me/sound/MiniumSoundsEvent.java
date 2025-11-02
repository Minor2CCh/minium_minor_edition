package com.github.Minor2CCh.minium_me.sound;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class MiniumSoundsEvent {
    public static SoundEvent SHOOT_ENERGY_BULLET_EVENT = register("item.minium_me.energy_gun.shoot");
    public static SoundEvent ENERGY_GUN_EMPTY_EVENT = register("item.minium_me.energy_gun.empty");
    public static SoundEvent RELOAD_ENERGY_GUN_EVENT = register("item.minium_me.energy_gun.reload");
    public static SoundEvent HIT_ENERGY_BULLET_EVENT = register("item.minium_me.energy_gun.hit");

    private static SoundEvent register(String id){
        Identifier soundId = Minium_me.of(id);
        return Registry.register(Registries.SOUND_EVENT, soundId, SoundEvent.of(soundId));
    }
    public static void initialize() {
    }


}
