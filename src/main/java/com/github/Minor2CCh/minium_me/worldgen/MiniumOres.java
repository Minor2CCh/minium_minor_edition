package com.github.Minor2CCh.minium_me.worldgen;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

public class MiniumOres {
    public static final RegistryKey<PlacedFeature> MINIUM_ORE_PLACED_KEY = register("ore_minium");
    public static final RegistryKey<PlacedFeature> NETHER_MINIUM_ORE_PLACED_KEY = register("ore_minium_nether");
    public static final RegistryKey<PlacedFeature> END_MINIUM_ORE_PLACED_KEY = register("ore_minium_end");
    public static final RegistryKey<PlacedFeature> IRIS_QUARTZ_ORE_PLACED_KEY = register("ore_iris_quartz");

    //from mekanism
    public static final RegistryKey<PlacedFeature> OSMIUM_ORE_PLACED_KEY1 = register("ore_osmium_wave1");
    public static final RegistryKey<PlacedFeature> OSMIUM_ORE_PLACED_KEY2 = register("ore_osmium_wave2");






    public static RegistryKey<PlacedFeature> register(String id) {
        Identifier oreID = Identifier.of(Minium_me.MOD_ID, id);

        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, oreID);
    }
    public static void initialize() {
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, MINIUM_ORE_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.UNDERGROUND_ORES, NETHER_MINIUM_ORE_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.foundInTheEnd(), GenerationStep.Feature.UNDERGROUND_ORES, END_MINIUM_ORE_PLACED_KEY);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, IRIS_QUARTZ_ORE_PLACED_KEY);
        //from mekanism
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, OSMIUM_ORE_PLACED_KEY1);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, OSMIUM_ORE_PLACED_KEY2);
    }
}
