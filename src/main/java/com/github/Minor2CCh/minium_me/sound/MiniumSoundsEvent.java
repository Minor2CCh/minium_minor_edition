package com.github.Minor2CCh.minium_me.sound;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class MiniumSoundsEvent {
    public static final Identifier SHOOT_ENERGY_BULLET = Identifier.of(Minium_me.MOD_ID, "item.minium_me.energy_gun.shoot");
    public static SoundEvent SHOOT_ENERGY_BULLET_EVENT = SoundEvent.of(SHOOT_ENERGY_BULLET);
    public static final Identifier ENERGY_GUN_EMPTY = Identifier.of(Minium_me.MOD_ID, "item.minium_me.energy_gun.empty");
    public static SoundEvent ENERGY_GUN_EMPTY_EVENT = SoundEvent.of(ENERGY_GUN_EMPTY);
    public static final Identifier RELOAD_ENERGY_GUN = Identifier.of(Minium_me.MOD_ID, "item.minium_me.energy_gun.reload");
    public static SoundEvent RELOAD_ENERGY_GUN_EVENT = SoundEvent.of(RELOAD_ENERGY_GUN);
    public static final Identifier HIT_ENERGY_BULLET = Identifier.of(Minium_me.MOD_ID, "item.minium_me.energy_gun.hit");
    public static SoundEvent HIT_ENERGY_BULLET_EVENT = SoundEvent.of(HIT_ENERGY_BULLET);


    public static void initialize() {
        Registry.register(Registries.SOUND_EVENT, SHOOT_ENERGY_BULLET, SHOOT_ENERGY_BULLET_EVENT);
        Registry.register(Registries.SOUND_EVENT, ENERGY_GUN_EMPTY, ENERGY_GUN_EMPTY_EVENT);
        Registry.register(Registries.SOUND_EVENT, RELOAD_ENERGY_GUN, RELOAD_ENERGY_GUN_EVENT);
        Registry.register(Registries.SOUND_EVENT, HIT_ENERGY_BULLET, HIT_ENERGY_BULLET_EVENT);
    }


}
