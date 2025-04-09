package com.github.Minor2CCh.minium_me.item;

import com.github.Minor2CCh.minium_me.Minium_me;
import com.github.Minor2CCh.minium_me.component.MiniumModComponent;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
//import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class MiniumItem{
    public static final Item MINIUM_INGOT = register(new Item(new Item.Settings()), "minium_ingot");
    public static final Item C_MINIUM_INGOT = register(new Item(new Item.Settings()), "concentrated_minium_ingot");
    public static final Item R_MINIUM = register(new Item(new Item.Settings()), "raw_minium");
    //public static Item DUMMY = register(new Item(new Item.Settings()),  "sculk");
    public static final Item MINIUM_UPGRADE_SMITHING_TEMPLATE = register(MiniumSmithingTemplateItem.createMiniumUpgrade(), "minium_upgrade_smithing_template");
    public static final Item IRIS_QUARTZ_UPGRADE_SMITHING_TEMPLATE = register(MiniumSmithingTemplateItem.createIrisQuartzUpgrade(), "iris_quartz_upgrade_smithing_template");

    //tool
    public static final Item MINIUM_SWORD = register(new SwordItem(MiniumToolMaterial.MINIUM_TOOL, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(MiniumToolMaterial.MINIUM_TOOL, 3, -2.4f))), "minium_sword");
    public static final Item MINIUM_SHOVEL = register(new ShovelItem(MiniumToolMaterial.MINIUM_TOOL, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(MiniumToolMaterial.MINIUM_TOOL, 1.5f, -3.0f))), "minium_shovel");
    public static final Item MINIUM_PICKAXE = register(new PickaxeItem(MiniumToolMaterial.MINIUM_TOOL, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(MiniumToolMaterial.MINIUM_TOOL, 1.0f, -2.8f))), "minium_pickaxe");
    public static final Item MINIUM_AXE = register(new AxeItem(MiniumToolMaterial.MINIUM_TOOL, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(MiniumToolMaterial.MINIUM_TOOL, 6, -2.8f))), "minium_axe");
    public static final Item MINIUM_HOE = register(new HoeItem(MiniumToolMaterial.MINIUM_TOOL, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(MiniumToolMaterial.MINIUM_TOOL, -2.5f, 0f))), "minium_hoe");
    public static final Item MINIUM_MULTITOOL = register(new MiniumMultiTool(MiniumToolMaterial.MINIUM_MULTITOOL, new Item.Settings().attributeModifiers(MiniumMultiTool.createAttributeModifiers(MiniumToolMaterial.MINIUM_MULTITOOL, 1.5f, -2.0f))), "minium_multitool");
    public static final Item C_MINIUM_SWORD = register(new SwordItem(MiniumToolMaterial.C_MINIUM_TOOL, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(MiniumToolMaterial.C_MINIUM_TOOL, 3, -2.4f))), "concentrated_minium_sword");
    public static final Item C_MINIUM_SHOVEL = register(new ShovelItem(MiniumToolMaterial.C_MINIUM_TOOL, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(MiniumToolMaterial.C_MINIUM_TOOL, 1.5f, -3.0f))), "concentrated_minium_shovel");
    public static final Item C_MINIUM_PICKAXE = register(new PickaxeItem(MiniumToolMaterial.C_MINIUM_TOOL, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(MiniumToolMaterial.C_MINIUM_TOOL, 1.0f, -2.8f))), "concentrated_minium_pickaxe");
    public static final Item C_MINIUM_AXE = register(new AxeItem(MiniumToolMaterial.C_MINIUM_TOOL, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(MiniumToolMaterial.C_MINIUM_TOOL, 6, -2.8f))), "concentrated_minium_axe");
    public static final Item C_MINIUM_HOE = register(new HoeItem(MiniumToolMaterial.C_MINIUM_TOOL, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(MiniumToolMaterial.C_MINIUM_TOOL, -2.5f, 0f))), "concentrated_minium_hoe");
    public static final Item C_MINIUM_MULTITOOL = register(new MiniumMultiTool(MiniumToolMaterial.C_MINIUM_MULTITOOL, new Item.Settings().attributeModifiers(MiniumMultiTool.createAttributeModifiers(MiniumToolMaterial.C_MINIUM_MULTITOOL, 1.5f, -2.0f))), "concentrated_minium_multitool");
    public static final Item IRIS_QUARTZ_SWORD = register(new SwordItem(MiniumToolMaterial.IRIS_QUARTZ_TOOL, new Item.Settings().attributeModifiers(SwordItem.createAttributeModifiers(MiniumToolMaterial.IRIS_QUARTZ_TOOL, 3, -2.4f)).rarity(Rarity.EPIC).fireproof()), "iris_quartz_sword");
    public static final Item IRIS_QUARTZ_SHOVEL = register(new ShovelItem(MiniumToolMaterial.IRIS_QUARTZ_TOOL, new Item.Settings().attributeModifiers(ShovelItem.createAttributeModifiers(MiniumToolMaterial.IRIS_QUARTZ_TOOL, 1.5f, -3.0f)).rarity(Rarity.EPIC).fireproof()), "iris_quartz_shovel");
    public static final Item IRIS_QUARTZ_PICKAXE = register(new PickaxeItem(MiniumToolMaterial.IRIS_QUARTZ_TOOL, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(MiniumToolMaterial.IRIS_QUARTZ_TOOL, 1.0f, -2.8f)).rarity(Rarity.EPIC).fireproof()), "iris_quartz_pickaxe");
    public static final Item IRIS_QUARTZ_AXE = register(new AxeItem(MiniumToolMaterial.IRIS_QUARTZ_TOOL, new Item.Settings().attributeModifiers(AxeItem.createAttributeModifiers(MiniumToolMaterial.IRIS_QUARTZ_TOOL, 6, -2.8f)).rarity(Rarity.EPIC).fireproof()), "iris_quartz_axe");
    public static final Item IRIS_QUARTZ_HOE = register(new HoeItem(MiniumToolMaterial.IRIS_QUARTZ_TOOL, new Item.Settings().attributeModifiers(HoeItem.createAttributeModifiers(MiniumToolMaterial.IRIS_QUARTZ_TOOL, -4.5f, 0f)).rarity(Rarity.EPIC).fireproof()), "iris_quartz_hoe");
    public static final Item IRIS_QUARTZ_MULTITOOL = register(new MiniumMultiTool(MiniumToolMaterial.IRIS_QUARTZ_MULTITOOL, new Item.Settings().attributeModifiers(MiniumMultiTool.createAttributeModifiers(MiniumToolMaterial.IRIS_QUARTZ_MULTITOOL, 1.5f, -2.0f)).rarity(Rarity.EPIC).fireproof()), "iris_quartz_multitool");
    //armor
    public static final Item MINIUM_HELMET = register(new ArmorItem(MiniumArmorMaterial.MINIUM, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(MiniumArmorMaterial.MINIUM_DURABILITY_MULTIPLIER))), "minium_helmet");
    public static final Item MINIUM_CHESTPLATE = register(new ArmorItem(MiniumArmorMaterial.MINIUM, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(MiniumArmorMaterial.MINIUM_DURABILITY_MULTIPLIER))), "minium_chestplate");
    public static final Item MINIUM_LEGGINGS = register(new ArmorItem(MiniumArmorMaterial.MINIUM, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(MiniumArmorMaterial.MINIUM_DURABILITY_MULTIPLIER))), "minium_leggings");
    public static final Item MINIUM_BOOTS = register(new ArmorItem(MiniumArmorMaterial.MINIUM, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(MiniumArmorMaterial.MINIUM_DURABILITY_MULTIPLIER))), "minium_boots");
    public static final Item C_MINIUM_HELMET = register(new ArmorItem(MiniumArmorMaterial.C_MINIUM, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(MiniumArmorMaterial.C_MINIUM_DURABILITY_MULTIPLIER))), "concentrated_minium_helmet");
    public static final Item C_MINIUM_CHESTPLATE = register(new ArmorItem(MiniumArmorMaterial.C_MINIUM, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(MiniumArmorMaterial.C_MINIUM_DURABILITY_MULTIPLIER))), "concentrated_minium_chestplate");
    public static final Item C_MINIUM_LEGGINGS = register(new ArmorItem(MiniumArmorMaterial.C_MINIUM, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(MiniumArmorMaterial.C_MINIUM_DURABILITY_MULTIPLIER))), "concentrated_minium_leggings");
    public static final Item C_MINIUM_BOOTS = register(new ArmorItem(MiniumArmorMaterial.C_MINIUM, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(MiniumArmorMaterial.C_MINIUM_DURABILITY_MULTIPLIER))), "concentrated_minium_boots");
    public static final Item IRIS_QUARTZ_HELMET = register(new ArmorItem(MiniumArmorMaterial.IRIS_QUARTZ, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(MiniumArmorMaterial.IRIS_QUARTZ_DURABILITY_MULTIPLIER)).rarity(Rarity.EPIC).fireproof()), "iris_quartz_helmet");
    public static final Item IRIS_QUARTZ_CHESTPLATE = register(new ArmorItem(MiniumArmorMaterial.IRIS_QUARTZ, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(MiniumArmorMaterial.IRIS_QUARTZ_DURABILITY_MULTIPLIER)).rarity(Rarity.EPIC).fireproof()), "iris_quartz_chestplate");
    public static final Item IRIS_QUARTZ_LEGGINGS = register(new ArmorItem(MiniumArmorMaterial.IRIS_QUARTZ, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(MiniumArmorMaterial.IRIS_QUARTZ_DURABILITY_MULTIPLIER)).rarity(Rarity.EPIC).fireproof()), "iris_quartz_leggings");
    public static final Item IRIS_QUARTZ_BOOTS = register(new ArmorItem(MiniumArmorMaterial.IRIS_QUARTZ, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(MiniumArmorMaterial.IRIS_QUARTZ_DURABILITY_MULTIPLIER)).rarity(Rarity.EPIC).fireproof()), "iris_quartz_boots");
    public static final Item IRIS_QUARTZ_ELYTRA = register(new IrisQuartzElytraItem(new Item.Settings().maxDamage(2048).rarity(Rarity.EPIC).fireproof()),"iris_quartz_elytra");
    public static final Item IRIS_QUARTZ_ELYTRA_CHESTPLATE = register(new ElytraChestplateItem(MiniumArmorMaterial.IRIS_QUARTZ, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(2048).rarity(Rarity.EPIC).fireproof()),"iris_quartz_elytra_chestplate");
    public static final Item IRIS_QUARTZ = register(new Item(new Item.Settings().fireproof()), "iris_quartz");
    public static final Item IRIS_QUARTZ_INGOT = register(new Item(new Item.Settings().fireproof()), "iris_quartz_ingot");
    public static final Item ENERGY_GUN = register(new MiniumGunItem(new Item.Settings().maxCount(1).component(MiniumModComponent.REMAIN_ENERGY, new MiniumModComponent.EnergyComponent(0, MiniumModComponent.ENERGY_EMPTY))), "energy_gun");

    public static final Item DEEPSLATE_MINER = register(new DeepslateMinerItem(new Item.Settings().maxCount(16)), "deepslate_miner");
    public static final Item WIND_EXPLODER = register(new WindExploderItem(new Item.Settings().maxDamage(64)), "wind_exploder");
    public static final Item ADVANCED_WIND_EXPLODER = register(new WindExploderItem(new Item.Settings().maxDamage(256)), "advanced_wind_exploder");
    //from mekanism
    public static final Item OSMIUM_INGOT_FROM_MEKANISM = register(new Item(new Item.Settings()), "osmium_ingot");
    public static final Item OSMIUM_NUGGET_FROM_MEKANISM = register(new Item(new Item.Settings()), "osmium_nugget");
    public static final Item RAW_OSMIUM_FROM_MEKANISM = register(new Item(new Item.Settings()), "raw_osmium");



    public static Item register(Item item, String id) {
        Identifier itemID = Identifier.of(Minium_me.MOD_ID, id);
        return Registry.register(Registries.ITEM, itemID, item);
    }

    public static void initialize() {
    }
}