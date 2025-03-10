package com.github.Minor2CCh.minium_me.item;

//import com.github.Minor2CCh.minium_me.Minium_me;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class MiniumArmorMaterial{






    public static RegistryEntry<ArmorMaterial> registerMaterial(String id, Map<ArmorItem.Type, Integer> defensePoints, int enchantability, RegistryEntry<SoundEvent> equipSound, Supplier<Ingredient> repairIngredientSupplier, float toughness, float knockbackResistance, boolean dyeable) {
        List<ArmorMaterial.Layer> layers = List.of(
                new ArmorMaterial.Layer(Identifier.of("minium_me", id), "", dyeable)
        );

        ArmorMaterial material = new ArmorMaterial(defensePoints, enchantability, equipSound, repairIngredientSupplier, layers, toughness, knockbackResistance);
        material = Registry.register(Registries.ARMOR_MATERIAL, Identifier.of("minium_me", id), material);
        return RegistryEntry.of(material);
    }
    public static final RegistryEntry<ArmorMaterial> MINIUM = registerMaterial("minium",
            Map.of(
                    ArmorItem.Type.HELMET, 2,
                    ArmorItem.Type.CHESTPLATE, 7,
                    ArmorItem.Type.LEGGINGS, 5,
                    ArmorItem.Type.BOOTS, 2
            ),
            15,
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.ofItems(MiniumItem.MINIUM_INGOT),
            1.0F,
            0.0F,
            false);

    public static final RegistryEntry<ArmorMaterial> C_MINIUM = registerMaterial("concentrated_minium",
            Map.of(
                    ArmorItem.Type.HELMET, 3,
                    ArmorItem.Type.CHESTPLATE, 8,
                    ArmorItem.Type.LEGGINGS, 6,
                    ArmorItem.Type.BOOTS, 3
            ),
            15,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,
            () -> Ingredient.ofItems(MiniumItem.C_MINIUM_INGOT),
            2.0F,
            0.05F,
            false);

    public static final RegistryEntry<ArmorMaterial> IRIS_QUARTZ = registerMaterial("iris_quartz",
            Map.of(
                    ArmorItem.Type.HELMET, 4,
                    ArmorItem.Type.CHESTPLATE, 9,
                    ArmorItem.Type.LEGGINGS, 7,
                    ArmorItem.Type.BOOTS, 4
            ),
            25,
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,
            () -> Ingredient.ofItems(MiniumItem.IRIS_QUARTZ_INGOT),
            5.0F,
            0.1F,
            false);
    public static final int MINIUM_DURABILITY_MULTIPLIER = 15;
    public static final int C_MINIUM_DURABILITY_MULTIPLIER = 45;
    public static final int IRIS_QUARTZ_DURABILITY_MULTIPLIER = 100;
}
