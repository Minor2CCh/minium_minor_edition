package com.github.Minor2CCh.minium_me.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import org.jetbrains.annotations.NotNull;

public record GunReloadComponent(long prevTime, ItemStack prevStack){
    public static final Codec<GunReloadComponent> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.LONG.fieldOf("prev_time").forGetter(GunReloadComponent::prevTime),
            ItemStack.CODEC.optionalFieldOf("prev_stack", ItemStack.EMPTY).forGetter(GunReloadComponent::prevStack)

    ).apply(builder, GunReloadComponent::new));

    public static final PacketCodec<RegistryByteBuf, GunReloadComponent> PACKET_CODEC =
            PacketCodec.tuple(
                    PacketCodecs.VAR_LONG, GunReloadComponent::prevTime,
                    ItemStack.PACKET_CODEC, GunReloadComponent::prevStack,
                    GunReloadComponent::new
            );
    public long getPrevTime(){
        return prevTime;
    }
    public ItemStack getPrevStack(){
        return prevStack;
    }
    public static long getPrevTime(ItemStack stack){
        GunReloadComponent component = stack.getOrDefault(MiniumModComponent.GUN_RELOAD, new GunReloadComponent(0, ItemStack.EMPTY));
        return component.getPrevTime();
    }
    @NotNull
    public static ItemStack getPrevStack(ItemStack stack){
        GunReloadComponent component = stack.getOrDefault(MiniumModComponent.GUN_RELOAD, new GunReloadComponent(0, ItemStack.EMPTY));
        return component.getPrevStack();
    }
}
