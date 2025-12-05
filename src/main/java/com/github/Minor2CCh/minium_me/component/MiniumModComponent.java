package com.github.Minor2CCh.minium_me.component;

import com.github.Minor2CCh.minium_me.Minium_me;
import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.dynamic.Codecs;

public class MiniumModComponent {
    public static final ComponentType<EnergyComponent> REMAIN_ENERGY = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Minium_me.of("energy_profile"),
            ComponentType.<EnergyComponent>builder().codec(EnergyComponent.CODEC)
                    .packetCodec(EnergyComponent.PACKET_CODEC).build()
    );
    public static final ComponentType<Integer> TEMPORALLY_REMAIN = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Minium_me.of("temporally_remain"),
            ComponentType.<Integer>builder().codec(Codecs.NONNEGATIVE_INT).packetCodec(PacketCodecs.VAR_INT).build()
    );
    public static final ComponentType<ArmorReinforcedComponent> ARMOR_REINFORCED = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Minium_me.of("armor_reinforced"),
            ComponentType.<ArmorReinforcedComponent>builder().codec(ArmorReinforcedComponent.CODEC).packetCodec(ArmorReinforcedComponent.PACKET_CODEC).build()
    );
    public static final ComponentType<GunReloadComponent> GUN_RELOAD = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Minium_me.of("gun_reload"),
            ComponentType.<GunReloadComponent>builder().codec(GunReloadComponent.CODEC).packetCodec(GunReloadComponent.PACKET_CODEC).build()
    );
    public static final ComponentType<Long> DOUBLE_CLICK_HANDLER = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Minium_me.of("double_click_handler"),
            ComponentType.<Long>builder().codec(Codec.LONG).packetCodec(PacketCodecs.VAR_LONG).build()
    );
    public static final ComponentType<ToolReinforcedComponent> TOOL_REINFORCED = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Minium_me.of("tool_reinforced"),
            ComponentType.<ToolReinforcedComponent>builder().codec(ToolReinforcedComponent.CODEC).packetCodec(ToolReinforcedComponent.PACKET_CODEC).build()
    );
    public static void initialize() {
    }
}
