package com.github.Minor2CCh.minium_me.item;

import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.Identifier;

public class MiniumShieldItem extends ShieldItem {
    public MiniumShieldItem(Settings settings) {
        super(settings);
    }
    public static AttributeModifiersComponent createAttributeModifiers(float genericArmor, float genericArmorToughness) {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.GENERIC_ARMOR,
                        new EntityAttributeModifier(
                                Identifier.ofVanilla("generic.armor"), genericArmor, EntityAttributeModifier.Operation.ADD_VALUE
                        ),
                        AttributeModifierSlot.OFFHAND
                )
                .add(
                        EntityAttributes.GENERIC_ARMOR_TOUGHNESS,
                        new EntityAttributeModifier(Identifier.ofVanilla("generic.armor_toughness"), genericArmorToughness, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.OFFHAND
                )
                .build();
    }
}
