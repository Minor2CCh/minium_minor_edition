package com.github.Minor2CCh.minium_me.component;

import com.github.Minor2CCh.minium_me.Minium_me;
import com.github.Minor2CCh.minium_me.damage_type.MiniumDamageType;
import com.github.Minor2CCh.minium_me.item.MiniumItemTag;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.dynamic.Codecs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
@SuppressWarnings("unused")
public class MiniumModComponent {
    //Vanilla ores
    private static final HashMap<String, String> ENERGY_KEY = new HashMap<>();
    private static final HashMap<String, Integer> ENERGY_COLOR = new HashMap<>();
    private static final HashMap<String, RegistryKey<DamageType>> ENERGY_DAMAGE_TYPE = new HashMap<>();
    private static final HashMap<String, Float> ENERGY_DAMAGE = new HashMap<>();
    private static final HashMap<String, TagKey<Item>> ENERGY_MATERIAL = new HashMap<>();
    private static final HashMap<String, TagKey<Item>> ENERGY_MATERIAL_SB = new HashMap<>();
    private static final HashMap<String, Integer> ENERGY_REMAIN_SB = new HashMap<>();
    public static final List<String> ENERGY_LIST = new ArrayList<>();
    public static final HashMap<String, Integer> ENERGY_INDEX = new HashMap<>();
    public static final HashMap<Integer, String> ENERGY_INDEX_REVERSE = new HashMap<>();
    public static int indexCount = 0;
    public static final String ENERGY_EMPTY = register("energy_empty", 0xFFFFFF, DamageTypes.OUT_OF_WORLD, 0.0F, null, null, 0);
    public static final String ENERGY_COAL = register("energy_coal", 0x555555, MiniumDamageType.ENERGY_FIRE, 0.5F, MiniumItemTag.ENERGY_COAL, MiniumItemTag.ENERGY_COAL_STORAGE_BLOCKS, 10);
    public static final String ENERGY_IRON = register("energy_iron", 0xBFC9C8, MiniumDamageType.ENERGY_DEFAULT, 1.0F, MiniumItemTag.ENERGY_IRON, MiniumItemTag.ENERGY_IRON_STORAGE_BLOCKS, 10);
    public static final String ENERGY_COPPER = register("energy_copper", 0xB4684D, MiniumDamageType.ENERGY_DEFAULT, 1.0F, MiniumItemTag.ENERGY_COPPER, MiniumItemTag.ENERGY_COPPER_STORAGE_BLOCKS, 10);
    public static final String ENERGY_GOLD = register("energy_gold", 0xECD93F, MiniumDamageType.ENERGY_DEFAULT, 1.0F, MiniumItemTag.ENERGY_GOLD, MiniumItemTag.ENERGY_GOLD_STORAGE_BLOCKS, 10);
    public static final String ENERGY_LAPIS = register("energy_lapis", 0x1C4D9C, MiniumDamageType.ENERGY_DEFAULT, 0.8F, MiniumItemTag.ENERGY_LAPIS, MiniumItemTag.ENERGY_LAPIS_STORAGE_BLOCKS, 10);
    public static final String ENERGY_REDSTONE = register("energy_redstone", 0x971607, MiniumDamageType.ENERGY_DEFAULT, 0.8F, MiniumItemTag.ENERGY_REDSTONE, MiniumItemTag.ENERGY_REDSTONE_STORAGE_BLOCKS, 10);
    public static final String ENERGY_DIAMOND = register("energy_diamond", 0x6EFCF2, MiniumDamageType.ENERGY_DEFAULT, 1.75F, MiniumItemTag.ENERGY_DIAMOND, MiniumItemTag.ENERGY_DIAMOND_STORAGE_BLOCKS, 10);
    public static final String ENERGY_EMERALD = register("energy_emerald", 0x0EC754, MiniumDamageType.ENERGY_NOT_PROJECTILE, 1.5F, MiniumItemTag.ENERGY_EMERALD, MiniumItemTag.ENERGY_EMERALD_STORAGE_BLOCKS, 10);
    public static final String ENERGY_QUARTZ = register("energy_quartz", 0xF6EADF, MiniumDamageType.ENERGY_NOT_PROJECTILE, 1.0F, MiniumItemTag.ENERGY_QUARTZ, null, 5);
    public static final String ENERGY_GLOWSTONE = register("energy_glowstone", 0xFBDA74, MiniumDamageType.ENERGY_DEFAULT, 1.0F, MiniumItemTag.ENERGY_GLOWSTONE, null, 5);
    public static final String ENERGY_NETHERITE = register("energy_netherite", 0x443A3B, MiniumDamageType.ENERGY_NOT_PROJECTILE, 2.5F, MiniumItemTag.ENERGY_NETHERITE, MiniumItemTag.ENERGY_NETHERITE_STORAGE_BLOCKS, 10);
    public static final String ENERGY_AMETHYST = register("energy_amethyst", 0x9A5CC5, MiniumDamageType.ENERGY_AMETHYST, 0.6F, MiniumItemTag.ENERGY_AMETHYST, null, 5);
    //MiniumMOD ores
    public static final String ENERGY_MINIUM = register("energy_minium", 0x2BD8B3, MiniumDamageType.ENERGY_NOT_PROJECTILE, 1.5F, MiniumItemTag.ENERGY_MINIUM, MiniumItemTag.ENERGY_MINIUM_STORAGE_BLOCKS, 10);
    public static final String ENERGY_C_MINIUM = register("energy_c_minium", 0x009866, MiniumDamageType.ENERGY_NOT_PROJECTILE, 2.5F, MiniumItemTag.ENERGY_C_MINIUM, MiniumItemTag.ENERGY_C_MINIUM_STORAGE_BLOCKS, 10);
    public static final String ENERGY_OSMIUM = register("energy_osmium", 0xDDEFFD, MiniumDamageType.ENERGY_DEFAULT, 1.5F, MiniumItemTag.ENERGY_OSMIUM, MiniumItemTag.ENERGY_OSMIUM_STORAGE_BLOCKS, 10);
    public static final String ENERGY_IRIS_QUARTZ = register("energy_iris_quartz", 0xFFFFFF, MiniumDamageType.ENERGY_IRIS_QUARTZ, 4.0F, MiniumItemTag.ENERGY_IRIS_QUARTZ, MiniumItemTag.ENERGY_IRIS_QUARTZ_STORAGE_BLOCKS, 10);
    //OtherMOD ores
    public static final String ENERGY_REFINED_IRON = register("energy_refined_iron", 0xD8DEFF, MiniumDamageType.ENERGY_DEFAULT, 1.5F, MiniumItemTag.ENERGY_REFINED_IRON, MiniumItemTag.ENERGY_REFINED_IRON_STORAGE_BLOCKS, 10);
    public static final String ENERGY_SOURCE_GEM = register("energy_source_gem", 0xCC66FF, MiniumDamageType.ENERGY_DEFAULT, 1.2F, MiniumItemTag.ENERGY_SOURCE_GEM, MiniumItemTag.ENERGY_SOURCE_GEM_STORAGE_BLOCKS, 5);
    public static final String ENERGY_ALUMINIUM = register("energy_aluminium", 0xE7EAEA, MiniumDamageType.ENERGY_DEFAULT, 1.0F, MiniumItemTag.ENERGY_ALUMINIUM, MiniumItemTag.ENERGY_ALUMINIUM_STORAGE_BLOCKS, 10);//アルミニウム
    public static final String ENERGY_LEAD = register("energy_lead", 0x8CA7A3, MiniumDamageType.ENERGY_DEFAULT, 1.2F, MiniumItemTag.ENERGY_LEAD, MiniumItemTag.ENERGY_LEAD_STORAGE_BLOCKS, 10);//鉛
    public static final String ENERGY_NICKEL = register("energy_nickel", 0xB0A075, MiniumDamageType.ENERGY_DEFAULT, 1.2F, MiniumItemTag.ENERGY_NICKEL, MiniumItemTag.ENERGY_NICKEL_STORAGE_BLOCKS, 10);//ニッケル
    public static final String ENERGY_SILVER = register("energy_silver", 0x9FADB4, MiniumDamageType.ENERGY_DEFAULT, 1.2F, MiniumItemTag.ENERGY_SILVER, MiniumItemTag.ENERGY_SILVER_STORAGE_BLOCKS, 10);//銀
    public static final String ENERGY_TIN = register("energy_tin", 0xFBEFE3, MiniumDamageType.ENERGY_DEFAULT, 1.2F, MiniumItemTag.ENERGY_TIN, MiniumItemTag.ENERGY_TIN_STORAGE_BLOCKS, 10);//錫
    public static final String ENERGY_URANIUM = register("energy_uranium", 0xB1FAB3, MiniumDamageType.ENERGY_DEFAULT, 1.0F, MiniumItemTag.ENERGY_URANIUM, MiniumItemTag.ENERGY_URANIUM_STORAGE_BLOCKS, 10);//ウラン
    public static final String ENERGY_ZINC = register("energy_zinc", 0xF0F1EF, MiniumDamageType.ENERGY_DEFAULT, 0.4F, MiniumItemTag.ENERGY_ZINC, MiniumItemTag.ENERGY_ZINC_STORAGE_BLOCKS, 10);//亜鉛
    public static final String ENERGY_BRONZE = register("energy_bronze", 0xC68754, MiniumDamageType.ENERGY_NOT_PROJECTILE, 1.75F, MiniumItemTag.ENERGY_BRONZE, MiniumItemTag.ENERGY_BRONZE_STORAGE_BLOCKS, 10);//青銅
    public static final String ENERGY_STEEL = register("energy_steel", 0xA2A2A2, MiniumDamageType.ENERGY_FIRE, 1.0F, MiniumItemTag.ENERGY_STEEL, MiniumItemTag.ENERGY_STEEL_STORAGE_BLOCKS, 10);//鋼鉄
    public static final String ENERGY_CERTUS_QUARTZ = register("energy_certus_quartz", 0xACE9FF, MiniumDamageType.ENERGY_NOT_PROJECTILE, 1.0F, MiniumItemTag.ENERGY_CERTUS_QUARTZ, MiniumItemTag.ENERGY_CERTUS_QUARTZ_STORAGE_BLOCKS, 5);//ケルタスクォーツ
    public static final String ENERGY_FLUIX = register("energy_fluix", 0xFF80D7, MiniumDamageType.ENERGY_NOT_PROJECTILE, 1.5F, MiniumItemTag.ENERGY_FLUIX, null, 5);//フルーシュ
    public static final String ENERGY_FLUORITE = register("energy_fluorite", 0xEEFDF6, MiniumDamageType.ENERGY_NOT_PROJECTILE, 1.0F, MiniumItemTag.ENERGY_FLUORITE, MiniumItemTag.ENERGY_FLUORITE_STORAGE_BLOCKS, 10);//蛍石
    public static final String ENERGY_REFINED_GLOWSTONE = register("energy_refined_glowstone", 0xFFF09D, MiniumDamageType.ENERGY_NOT_PROJECTILE, 1.5F, MiniumItemTag.ENERGY_REFINED_GLOWSTONE, MiniumItemTag.ENERGY_REFINED_GLOWSTONE_STORAGE_BLOCKS, 10);//精製グロウストーン
    public static final String ENERGY_REFINED_OBSIDIAN = register("energy_refined_obsidian", 0x8469AC, MiniumDamageType.ENERGY_DEFAULT, 3.0F, MiniumItemTag.ENERGY_REFINED_OBSIDIAN, MiniumItemTag.ENERGY_REFINED_OBSIDIAN_STORAGE_BLOCKS, 10);//精製黒曜石
    public static final String ENERGY_SALT = register("energy_salt", 0xDBDCD8, MiniumDamageType.ENERGY_DEFAULT, 0.6F, MiniumItemTag.ENERGY_SALT, null, 5);//塩
    public static final String ENERGY_RUBY = register("energy_ruby", 0xB23744, MiniumDamageType.ENERGY_NOT_PROJECTILE, 1.0F, MiniumItemTag.ENERGY_RUBY, MiniumItemTag.ENERGY_RUBY_STORAGE_BLOCKS, 10);//ルビー
    public static final String ENERGY_SAPPHIRE = register("energy_sapphire", 0x6E9DEF, MiniumDamageType.ENERGY_NOT_PROJECTILE, 1.0F, MiniumItemTag.ENERGY_SAPPHIRE, MiniumItemTag.ENERGY_SAPPHIRE_STORAGE_BLOCKS, 10);//サファイア
    public static final String ENERGY_ELECTRUM = register("energy_electrum", 0xCFB56F, MiniumDamageType.ENERGY_DEFAULT, 1.0F, MiniumItemTag.ENERGY_ELECTRUM, MiniumItemTag.ENERGY_ELECTRUM_STORAGE_BLOCKS, 10);//エレクトラム


    public static final Codec<EnergyComponent> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.INT.fieldOf("energy_remain").forGetter(EnergyComponent::remain),
            Codec.STRING.optionalFieldOf("energy_type", ENERGY_EMPTY).forGetter(EnergyComponent::type)
    ).apply(builder, EnergyComponent::new));
    public static final ComponentType<EnergyComponent> REMAIN_ENERGY = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Minium_me.of("energy_profile"),
            ComponentType.<EnergyComponent>builder().codec(CODEC).build()
    );
    public static final ComponentType<Integer> TEMPORALLY_REMAIN = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Minium_me.of("temporally_remain"),
            ComponentType.<Integer>builder().codec(Codecs.NONNEGATIVE_INT).build()
    );

    public static int getEnergyColor(String type) {
        return ENERGY_COLOR.getOrDefault(type, 0x7F7F7F);
    }
    public static RegistryKey<DamageType> getEnergyDamageType(String type) {
        return ENERGY_DAMAGE_TYPE.getOrDefault(type, DamageTypes.OUT_OF_WORLD);
    }
    public static float getEnergyMulDamage(String type) {
        return ENERGY_DAMAGE.getOrDefault(type, 0.0F);
    }
    public static String getEnergyKey(String type) {
        return ENERGY_KEY.getOrDefault(type, "item.minium_me.energy.type.error");
    }
    public static int getEnergyIndex(String type) {
        return ENERGY_INDEX.getOrDefault(type, -1);
    }
    public static String getEnergyTypeFromIndex(int typeIndex) {
        return ENERGY_INDEX_REVERSE.getOrDefault(typeIndex, "energy_error");
    }
    public static TagKey<Item> getEnergyMaterial(String type) {
        return ENERGY_MATERIAL.getOrDefault(type, null);
    }
    public static TagKey<Item> getEnergyMaterialSB(String type) {
        return ENERGY_MATERIAL_SB.getOrDefault(type, null);
    }
    public static int getEnergyRemainSB(String type) {
        return ENERGY_REMAIN_SB.getOrDefault(type, 0);
    }
    public static String register(String type, int color, RegistryKey<DamageType> damageType, float damageMul, TagKey<Item> material, TagKey<Item> materialSB, int remainSB) {
        ENERGY_KEY.put(type, "item.minium_me.energy.type."+type.substring(7));
        ENERGY_COLOR.put(type, color);
        ENERGY_DAMAGE_TYPE.put(type, damageType);
        ENERGY_DAMAGE.put(type, damageMul);
        if(material != null){
            ENERGY_MATERIAL.put(type, material);
        }
        if(materialSB != null){
            ENERGY_MATERIAL_SB.put(type, materialSB);
        }
        ENERGY_REMAIN_SB.put(type, remainSB);
        if(!Objects.equals(type, "energy_empty")){
            ENERGY_LIST.add(type);
        }
        ENERGY_INDEX.put(type, indexCount);
        ENERGY_INDEX_REVERSE.put(indexCount, type);
        indexCount++;
        return type;
    }
    public record EnergyComponent(int remain, String type) {
    }
    public static void initialize() {
    }
}
