package com.github.Minor2CCh.minium_me.component;

import com.github.Minor2CCh.minium_me.Minium_me;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MiniumModComponent {
    //Vanilla ores
    public static final String ENERGY_EMPTY = "energy_empty";
    public static final String ENERGY_COAL = "energy_coal";
    public static final String ENERGY_IRON = "energy_iron";
    public static final String ENERGY_COPPER = "energy_copper";
    public static final String ENERGY_GOLD = "energy_gold";
    public static final String ENERGY_LAPIS = "energy_lapis";
    public static final String ENERGY_REDSTONE = "energy_redstone";
    public static final String ENERGY_DIAMOND = "energy_diamond";
    public static final String ENERGY_EMERALD = "energy_emerald";
    public static final String ENERGY_QUARTZ = "energy_quartz";
    public static final String ENERGY_GLOWSTONE = "energy_glowstone";
    public static final String ENERGY_NETHERITE = "energy_netherite";
    public static final String ENERGY_AMETHYST = "energy_amethyst";
    //MiniumMOD ores
    public static final String ENERGY_MINIUM = "energy_minium";
    public static final String ENERGY_C_MINIUM = "energy_c_minium";
    public static final String ENERGY_OSMIUM = "energy_osmium";
    public static final String ENERGY_IRIS_QUARTZ = "energy_iris_quartz";
    //OtherMOD ores
    public static final String ENERGY_REFINED_IRON = "energy_refined_iron";
    public static final String ENERGY_SOURCE_GEM = "energy_source_gem";
    public static final String ENERGY_ALUMINIUM = "energy_aluminium";//アルミニウム
    public static final String ENERGY_LEAD = "energy_lead";//鉛
    public static final String ENERGY_NICKEL = "energy_nickel";//ニッケル
    public static final String ENERGY_SILVER = "energy_silver";//銀
    public static final String ENERGY_TIN = "energy_tin";//錫
    public static final String ENERGY_URANIUM = "energy_uranium";//ウラン
    public static final String ENERGY_ZINC = "energy_zinc";//亜鉛
    public static final String ENERGY_BRONZE = "energy_bronze";//青銅
    public static final String ENERGY_STEEL = "energy_steel";//鋼鉄
    public static final String ENERGY_CERTUS_QUARTZ = "energy_certus_quartz";//ケルタスクォーツ
    public static final String ENERGY_FLUIX = "energy_fluix";//フルーシュ
    public static final String ENERGY_FLUORITE = "energy_fluorite";//フルーシュ
    public static final String ENERGY_REFINED_GLOWSTONE = "energy_refined_glowstone";//精製グロウストーン
    public static final String ENERGY_REFINED_OBSIDIAN = "energy_refined_obsidian";//精製黒曜石
    public static final Codec<EnergyComponent> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            Codec.INT.fieldOf("energy_remain").forGetter(EnergyComponent::remain),
            Codec.STRING.optionalFieldOf("energy_type", ENERGY_EMPTY).forGetter(EnergyComponent::type)
    ).apply(builder, EnergyComponent::new));
    public static final ComponentType<EnergyComponent> REMAIN_ENERGY = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(Minium_me.MOD_ID, "energy_profile"),
            ComponentType.<EnergyComponent>builder().codec(CODEC).build()
    );

    /*
    private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator){
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(Minium_me.MOD_ID, name),
        builderOperator.apply(ComponentType.builder()).build());
    }*/
    public record EnergyComponent(int remain, String type) {
    }
    public static void initialize() {

    }
}
