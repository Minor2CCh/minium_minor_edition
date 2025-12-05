package com.github.Minor2CCh.minium_me.component;

import com.github.Minor2CCh.minium_me.Minium_me;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;

import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

public enum ArmorReinforcedComponent implements StringIdentifiable {
    EMPTY(0, "empty", noAttribute(), 0xFFFFFF, 0),
    FIRE_IMMUNE(1, "fire_immune", noAttribute(), 0xF09405, 5),
    FROZEN_IMMUNE(2, "frozen_immune", noAttribute(), 0x9CFFF4, 5),
    WATER_ADAPTION(3, "water_adaption", waterBreathAttribute(), 0x4B7CFF, 4),
    ALL_PROTECTION(4, "all_protection", noAttribute(), 0x7F7F7F, 5),
    UNDYING(5, "undying", noAttribute(), 0xFFDA26, 2),
    MAGIC_IMMUNE(6, "magic_immune", noAttribute(), 0xE730D0, 5),
    DIS_HOSTILE(7, "dis_hostile", noAttribute(), 0xB11E00, 5),
    WIND_AMULET(8, "wind_amulet", noAttribute(), 0xD3DBFF, 5);
    public static final Codec<ArmorReinforcedComponent> CODEC = StringIdentifiable.createBasicCodec(ArmorReinforcedComponent::values);
    private final int index;
    private final String name;
    public final Function<EquipmentSlot, AttributeModifiersComponent> attributeFunction;
    private final int tooltipColor;
    private final int tooltipLine;
    public static final IntFunction<ArmorReinforcedComponent> ID_TO_VALUE = ValueLists.createIdToValueFunction((ToIntFunction<ArmorReinforcedComponent>) value -> value.index, values(), ValueLists.OutOfBoundsHandling.ZERO);
    public static final PacketCodec<ByteBuf, ArmorReinforcedComponent> PACKET_CODEC = PacketCodecs.indexed(ID_TO_VALUE, value -> value.index);

    ArmorReinforcedComponent(final int index, final String name, final Function<EquipmentSlot, AttributeModifiersComponent> attributeFunction, final int tooltipColor, final int tooltipLine) {
        this.index = index;
        this.name = name;
        this.attributeFunction = attributeFunction;
        this.tooltipColor = tooltipColor;
        this.tooltipLine = tooltipLine;
    }
    private static Function<EquipmentSlot, AttributeModifiersComponent> noAttribute() {
        return (slot) -> AttributeModifiersComponent.builder().build();
    }
    private static Function<EquipmentSlot, AttributeModifiersComponent> waterBreathAttribute() {
        return (slot) -> {
            AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
            AttributeModifierSlot attributeModifierSlot = AttributeModifierSlot.forEquipmentSlot(slot);
            Identifier identifier = Minium_me.of("reinforced.armor." + slot.asString());
            builder.add(EntityAttributes.GENERIC_OXYGEN_BONUS, new EntityAttributeModifier(identifier, 5, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot);
            builder.add(EntityAttributes.GENERIC_WATER_MOVEMENT_EFFICIENCY, new EntityAttributeModifier(identifier, 0.25, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot);
            builder.add(EntityAttributes.PLAYER_SUBMERGED_MINING_SPEED, new EntityAttributeModifier(identifier, 0.2, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot);
            return builder.build();
        };
    }
    @Override
    public String asString() {
        return this.name;
    }
    @SuppressWarnings("unused")
    public int getIndex(){
        return this.index;
    }
    public int getColor(){
        return this.tooltipColor;
    }
    public int getTooltipLine(){
        return this.tooltipLine;
    }
}
