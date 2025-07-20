package com.github.Minor2CCh.minium_me.item;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class AddLootTable {
    //private static final Identifier END_CITY_ID = Identifier.ofVanilla("chests/end_city_treasure");
    public static RegistryKey<LootTable> END_CITY_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/end_city_treasure"));
    public static RegistryKey<LootTable> MINESHAFT_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/abandoned_mineshaft"));
    public static RegistryKey<LootTable> BURIED_TREASURE_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/buried_treasure"));
    public static RegistryKey<LootTable> DESERT_PYRAMID_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/desert_pyramid"));
    public static RegistryKey<LootTable> JUNGLE_TEMPLATE_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/jungle_temple"));
    public static RegistryKey<LootTable> PILLAGER_OUTPOST_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/pillager_outpost"));
    public static RegistryKey<LootTable> SHIPWRECK_TREASURE_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/shipwreck_treasure"));
    public static RegistryKey<LootTable> SIMPLE_DUNGEON_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/simple_dungeon"));
    public static RegistryKey<LootTable> STRONGHOLD_CORRIDOR_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/stronghold_corridor"));
    public static RegistryKey<LootTable> STRONGHOLD_CROSSING_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/stronghold_crossing"));
    public static RegistryKey<LootTable> UNDERWATER_RUIN_BIG_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/underwater_ruin_big"));
    public static RegistryKey<LootTable> UNDERWATER_RUIN_SMALL_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/underwater_ruin_small"));
    public static RegistryKey<LootTable> TRIAL_SPAWNER_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("spawners/trial_chamber/consumables"));
    public static RegistryKey<LootTable> OMINOUS_TRIAL_SPAWNER_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("spawners/ominous/trial_chamber/consumables"));
    public static RegistryKey<LootTable> INJECT_MINESHAFT_LOOT_TABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(Minium_me.MOD_ID,"chests/inject/inject_enchanted_book"));
    public static RegistryKey<LootTable> INJECT_MINIUM_LITTLE_LOOT_TABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(Minium_me.MOD_ID,"chests/inject/inject_custom_little"));
    public static RegistryKey<LootTable> INJECT_MINIUM_MEDIUM_LOOT_TABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(Minium_me.MOD_ID,"chests/inject/inject_custom_medium"));
    public static RegistryKey<LootTable> INJECT_MINIUM_LARGE_LOOT_TABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(Minium_me.MOD_ID,"chests/inject/inject_custom_large"));
    public static RegistryKey<LootTable> INJECT_POTION_LOOT_TABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(Minium_me.MOD_ID,"chests/inject/inject_potion"));
    public static void modifyLootTable(){
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            /*
            // If the loot table is for the cobblestone block and it is not overridden by a user:
            if (Blocks.COBBLESTONE.getLootTableKey() == key && source.isBuiltin()) {
                // Create a new loot pool that will hold the diamonds.
                LootPool.Builder pool = LootPool.builder()
                        // Add diamonds...
                        .with(ItemEntry.builder(Items.DIAMOND))
                        // ...only if the block would survive a potential explosion.
                        .conditionally(SurvivesExplosionLootCondition.builder());

                // Add the loot pool to the loot table
                tableBuilder.pool(pool);
            }

             */
            if (END_CITY_ID == key && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))//抽選回数
                        .with(ItemEntry.builder(MiniumItem.IRIS_QUARTZ_UPGRADE_SMITHING_TEMPLATE).weight(1)
                                .conditionally(RandomChanceLootCondition.builder(1 / 6.0F)))//抽選アイテム

                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)));//スタック個数

                // Add the loot pool to the loot table
                tableBuilder.pool(pool);
            }
            if (MINESHAFT_ID == key && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))//抽選回数
                        .conditionally(RandomChanceLootCondition.builder(2 / 3.0F))
                        .with(LootTableEntry.builder(INJECT_MINESHAFT_LOOT_TABLE).build());
                tableBuilder.pool(pool);
            }
            if (source.isBuiltin() && (SIMPLE_DUNGEON_ID == key || PILLAGER_OUTPOST_ID == key || UNDERWATER_RUIN_SMALL_ID == key)) {
                LootPool.Builder pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))//抽選回数
                        .conditionally(RandomChanceLootCondition.builder(1 / 2.0F))
                        .with(LootTableEntry.builder(INJECT_MINIUM_LITTLE_LOOT_TABLE).build());
                tableBuilder.pool(pool);
            }
            if (source.isBuiltin() && (DESERT_PYRAMID_ID == key || JUNGLE_TEMPLATE_ID == key || STRONGHOLD_CORRIDOR_ID == key || STRONGHOLD_CROSSING_ID == key || UNDERWATER_RUIN_BIG_ID == key)) {
                LootPool.Builder pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))//抽選回数
                        .conditionally(RandomChanceLootCondition.builder(4 / 5.0F))
                        .with(LootTableEntry.builder(INJECT_MINIUM_MEDIUM_LOOT_TABLE).build());
                tableBuilder.pool(pool);
            }
            if (source.isBuiltin() && (BURIED_TREASURE_ID == key || SHIPWRECK_TREASURE_ID == key)) {
                LootPool.Builder pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))//抽選回数
                        .conditionally(RandomChanceLootCondition.builder(3 / 4.0F))
                        .with(LootTableEntry.builder(INJECT_MINIUM_LARGE_LOOT_TABLE).build());
                tableBuilder.pool(pool);
            }
            if (source.isBuiltin() && (TRIAL_SPAWNER_ID == key)) {
                LootPool.Builder pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))//抽選回数
                        .conditionally(RandomChanceLootCondition.builder(3 / 100.0F))
                        .with(LootTableEntry.builder(INJECT_POTION_LOOT_TABLE).build());
                tableBuilder.pool(pool);
            }
            if (source.isBuiltin() && (OMINOUS_TRIAL_SPAWNER_ID == key)) {
                LootPool.Builder pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))//抽選回数
                        .conditionally(RandomChanceLootCondition.builder(6 / 100.0F))
                        .with(LootTableEntry.builder(INJECT_POTION_LOOT_TABLE).build());
                tableBuilder.pool(pool);
            }
        });
    }

    public static void initialize() {
        modifyLootTable();
    }
}
