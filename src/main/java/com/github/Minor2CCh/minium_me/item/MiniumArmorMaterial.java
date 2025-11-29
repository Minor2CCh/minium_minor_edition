package com.github.Minor2CCh.minium_me.item;

import com.github.Minor2CCh.minium_me.Minium_me;
import com.github.Minor2CCh.minium_me.config.MiniumConfigLoader;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class MiniumArmorMaterial{






    public static RegistryEntry<ArmorMaterial> registerMaterial(String id, Map<ArmorItem.Type, Integer> defensePoints, int enchantability, RegistryEntry<SoundEvent> equipSound, Supplier<Ingredient> repairIngredientSupplier, float toughness, float knockbackResistance, boolean dyeable) {
        List<ArmorMaterial.Layer> layers = List.of(
                new ArmorMaterial.Layer(Minium_me.of(id), "", dyeable)
        );

        ArmorMaterial material = new ArmorMaterial(defensePoints, enchantability, equipSound, repairIngredientSupplier, layers, toughness, knockbackResistance);
        material = Registry.register(Registries.ARMOR_MATERIAL, Minium_me.of(id), material);
        return RegistryEntry.of(material);
    }
    public static final RegistryEntry<ArmorMaterial> MINIUM = registerMaterial("minium",
            Map.of(
                    ArmorItem.Type.HELMET, MiniumConfigLoader.getConfig().getArmorMaterialMinium().getHelmetValue(),
                    ArmorItem.Type.CHESTPLATE, MiniumConfigLoader.getConfig().getArmorMaterialMinium().getChestplateValue(),
                    ArmorItem.Type.LEGGINGS, MiniumConfigLoader.getConfig().getArmorMaterialMinium().getLeggingsValue(),
                    ArmorItem.Type.BOOTS, MiniumConfigLoader.getConfig().getArmorMaterialMinium().getBootsValue()
            ),
            MiniumConfigLoader.getConfig().getArmorMaterialMinium().getEnchantability(),
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            () -> Ingredient.fromTag(MiniumItemTag.MINIUM_INGOT),
            MiniumConfigLoader.getConfig().getArmorMaterialMinium().getToughness(),
            MiniumConfigLoader.getConfig().getArmorMaterialMinium().getKnockbackResistance(),
            false);

    public static final RegistryEntry<ArmorMaterial> C_MINIUM = registerMaterial("concentrated_minium",
            Map.of(
                    ArmorItem.Type.HELMET, MiniumConfigLoader.getConfig().getArmorMaterialConcentratedMinium().getHelmetValue(),
                    ArmorItem.Type.CHESTPLATE, MiniumConfigLoader.getConfig().getArmorMaterialConcentratedMinium().getChestplateValue(),
                    ArmorItem.Type.LEGGINGS, MiniumConfigLoader.getConfig().getArmorMaterialConcentratedMinium().getLeggingsValue(),
                    ArmorItem.Type.BOOTS, MiniumConfigLoader.getConfig().getArmorMaterialConcentratedMinium().getBootsValue()
            ),
            MiniumConfigLoader.getConfig().getArmorMaterialConcentratedMinium().getEnchantability(),
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,
            () -> Ingredient.fromTag(MiniumItemTag.C_MINIUM_INGOT),
            MiniumConfigLoader.getConfig().getArmorMaterialConcentratedMinium().getToughness(),
            MiniumConfigLoader.getConfig().getArmorMaterialConcentratedMinium().getKnockbackResistance(),
            false);

    public static final RegistryEntry<ArmorMaterial> IRIS_QUARTZ = registerMaterial("iris_quartz",
            Map.of(
                    ArmorItem.Type.HELMET, MiniumConfigLoader.getConfig().getArmorMaterialIrisQuartz().getHelmetValue(),
                    ArmorItem.Type.CHESTPLATE, MiniumConfigLoader.getConfig().getArmorMaterialIrisQuartz().getChestplateValue(),
                    ArmorItem.Type.LEGGINGS, MiniumConfigLoader.getConfig().getArmorMaterialIrisQuartz().getLeggingsValue(),
                    ArmorItem.Type.BOOTS, MiniumConfigLoader.getConfig().getArmorMaterialIrisQuartz().getBootsValue()
            ),
            MiniumConfigLoader.getConfig().getArmorMaterialIrisQuartz().getEnchantability(),
            SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND,
            () -> Ingredient.fromTag(MiniumItemTag.IRIS_QUARTZ_INGOT),
            MiniumConfigLoader.getConfig().getArmorMaterialIrisQuartz().getToughness(),
            MiniumConfigLoader.getConfig().getArmorMaterialIrisQuartz().getKnockbackResistance(),
            false);
    public static final int MINIUM_DURABILITY_MULTIPLIER = MiniumConfigLoader.getConfig().getArmorMaterialMinium().getBaseDurability();
    public static final int C_MINIUM_DURABILITY_MULTIPLIER = MiniumConfigLoader.getConfig().getArmorMaterialConcentratedMinium().getBaseDurability();
    public static final int IRIS_QUARTZ_DURABILITY_MULTIPLIER = MiniumConfigLoader.getConfig().getArmorMaterialIrisQuartz().getBaseDurability();
}
