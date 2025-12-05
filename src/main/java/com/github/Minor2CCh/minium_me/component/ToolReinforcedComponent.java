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

public enum ToolReinforcedComponent implements StringIdentifiable {
    EMPTY(0, "empty", noAttribute(), 0xFFFFFF, 0),
    SHARPNESS(1, "sharpness",  sharpnessAttribute(), 0xFFF32D, 1),
    AUTO_SMELT(2, "auto_smelt",  noAttribute(), 0xAE3C00, 1),
    SPEED_MINING(3, "speed_mining",  speedMiningAttribute(), 0x9EFEEB, 1),
    WIND_SLINGER(4, "wind_slinger",  noAttribute(), 0xD3DBFF, 5),
    LIFE_STEAL(5, "life_steal",  noAttribute(), 0x82F6AD, 1),
    GENERATE_LIGHT(6, "generate_light",  noAttribute(), 0xFFF09D, 1),
    HONEY_PAINTER(7, "honey_painter",  noAttribute(), 0xD56B2B, 2);

    public static final Codec<ToolReinforcedComponent> CODEC = StringIdentifiable.createBasicCodec(ToolReinforcedComponent::values);
    private final int index;
    private final String name;
    public final Function<EquipmentSlot, AttributeModifiersComponent> attributeFunction;
    private final int tooltipColor;
    private final int tooltipLine;
    public static final IntFunction<ToolReinforcedComponent> ID_TO_VALUE = ValueLists.createIdToValueFunction((ToIntFunction<ToolReinforcedComponent>) value -> value.index, values(), ValueLists.OutOfBoundsHandling.ZERO);
    public static final PacketCodec<ByteBuf, ToolReinforcedComponent> PACKET_CODEC = PacketCodecs.indexed(ID_TO_VALUE, value -> value.index);
    ToolReinforcedComponent(final int index, final String name, final Function<EquipmentSlot, AttributeModifiersComponent> attributeFunction, final int tooltipColor, final int tooltipLine) {
        this.index = index;
        this.name = name;
        this.attributeFunction = attributeFunction;
        this.tooltipColor = tooltipColor;
        this.tooltipLine = tooltipLine;
    }
    private static Function<EquipmentSlot, AttributeModifiersComponent> noAttribute() {
        return (slot) -> AttributeModifiersComponent.builder().build();
    }
    private static Function<EquipmentSlot, AttributeModifiersComponent> sharpnessAttribute() {
        return (slot) -> {
            AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
            AttributeModifierSlot attributeModifierSlot = AttributeModifierSlot.forEquipmentSlot(slot);
            Identifier identifier = Minium_me.of("reinforced.tool.0." + slot.asString());
            builder.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(identifier, 3, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot);
            Identifier identifier2 = Minium_me.of("reinforced.tool.1." + slot.asString());
            builder.add(
                    EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(identifier2, 0.1, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE), attributeModifierSlot
            );
            return builder.build();
        };
    }
    private static Function<EquipmentSlot, AttributeModifiersComponent> speedMiningAttribute() {
        return (slot) -> {
            AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
            AttributeModifierSlot attributeModifierSlot = AttributeModifierSlot.forEquipmentSlot(slot);
            Identifier identifier = Minium_me.of("reinforced.tool." + slot.asString());
            builder.add(EntityAttributes.PLAYER_MINING_EFFICIENCY, new EntityAttributeModifier(identifier, 0.5, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE), attributeModifierSlot);
            builder.add(
                    EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(identifier, 0.3, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE), attributeModifierSlot
            );
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
