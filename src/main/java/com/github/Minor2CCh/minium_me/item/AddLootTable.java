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
    public static final RegistryKey<LootTable> END_CITY_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/end_city_treasure"));
    public static final RegistryKey<LootTable> MINESHAFT_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/abandoned_mineshaft"));
    public static final RegistryKey<LootTable> BURIED_TREASURE_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/buried_treasure"));
    public static final RegistryKey<LootTable> DESERT_PYRAMID_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/desert_pyramid"));
    public static final RegistryKey<LootTable> JUNGLE_TEMPLATE_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/jungle_temple"));
    public static final RegistryKey<LootTable> PILLAGER_OUTPOST_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/pillager_outpost"));
    public static final RegistryKey<LootTable> SHIPWRECK_TREASURE_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/shipwreck_treasure"));
    public static final RegistryKey<LootTable> SIMPLE_DUNGEON_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/simple_dungeon"));
    public static final RegistryKey<LootTable> STRONGHOLD_CORRIDOR_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/stronghold_corridor"));
    public static final RegistryKey<LootTable> STRONGHOLD_CROSSING_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/stronghold_crossing"));
    public static final RegistryKey<LootTable> UNDERWATER_RUIN_BIG_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/underwater_ruin_big"));
    public static final RegistryKey<LootTable> UNDERWATER_RUIN_SMALL_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/underwater_ruin_small"));
    public static final RegistryKey<LootTable> TRIAL_SPAWNER_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("spawners/trial_chamber/consumables"));
    public static final RegistryKey<LootTable> ANCIENT_CITY_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/ancient_city"));
    public static final RegistryKey<LootTable> ANCIENT_CITY_ICE_BOX_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/ancient_city_ice_box"));
    public static final RegistryKey<LootTable> BASTION_BRIDGE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/bastion_bridge"));
    public static final RegistryKey<LootTable> BASTION_HOGLIN_STABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/bastion_hoglin_stable"));
    public static final RegistryKey<LootTable> BASTION_OTHER = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/bastion_other"));
    public static final RegistryKey<LootTable> BASTION_TREASURE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/bastion_treasure"));
    public static final RegistryKey<LootTable> NETHER_BRIDGE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/nether_bridge"));
    public static final RegistryKey<LootTable> WOODLAND_MANSION = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/woodland_mansion"));
    public static final RegistryKey<LootTable> VIlLAGE_ARMORER = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/village/village_armorer"));
    public static final RegistryKey<LootTable> TRIAL_CHAMBERS_REWARD = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/trial_chambers/reward"));
    public static final RegistryKey<LootTable> TRIAL_CHAMBERS_REWARD_OMINOUS = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/trial_chambers/reward_ominous"));


    public static final RegistryKey<LootTable> OMINOUS_TRIAL_SPAWNER_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("spawners/ominous/trial_chamber/consumables"));
    public static final RegistryKey<LootTable> INJECT_MINESHAFT_LOOT_TABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Minium_me.of("chests/inject/inject_enchanted_book"));
    public static final RegistryKey<LootTable> INJECT_MINIUM_LITTLE_LOOT_TABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Minium_me.of("chests/inject/inject_custom_little"));
    public static final RegistryKey<LootTable> INJECT_MINIUM_MEDIUM_LOOT_TABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Minium_me.of("chests/inject/inject_custom_medium"));
    public static final RegistryKey<LootTable> INJECT_MINIUM_LARGE_LOOT_TABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Minium_me.of("chests/inject/inject_custom_large"));
    public static final RegistryKey<LootTable> INJECT_POTION_LOOT_TABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Minium_me.of("chests/inject/inject_potion"));
    public static void modifyLootTable(){
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            /*
            // If the loot table is for the cobblestone block, and it is not overridden by a user:
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
            if (source.isBuiltin() && (
                    ANCIENT_CITY_ID == key || ANCIENT_CITY_ICE_BOX_ID == key || BASTION_BRIDGE == key || BASTION_HOGLIN_STABLE == key
                     || BASTION_OTHER == key || NETHER_BRIDGE == key || VIlLAGE_ARMORER == key || TRIAL_CHAMBERS_REWARD_OMINOUS == key
            )) {
                LootPool.Builder pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))//抽選回数
                        .with(ItemEntry.builder(MiniumItem.MINIUM_UPGRADE_SMITHING_TEMPLATE).weight(1)
                                .conditionally(RandomChanceLootCondition.builder(1 / 50.0F)))//2%

                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)));
                tableBuilder.pool(pool);
            }
            if (source.isBuiltin() && (TRIAL_CHAMBERS_REWARD == key)) {
                LootPool.Builder pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))//抽選回数
                        .with(ItemEntry.builder(MiniumItem.MINIUM_UPGRADE_SMITHING_TEMPLATE).weight(1)
                                .conditionally(RandomChanceLootCondition.builder(1 / 100.0F)))//1%
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)));
                tableBuilder.pool(pool);
            }
            if (source.isBuiltin() && (BASTION_TREASURE == key || WOODLAND_MANSION == key)) {
                LootPool.Builder pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))//抽選回数
                        .with(ItemEntry.builder(MiniumItem.MINIUM_UPGRADE_SMITHING_TEMPLATE).weight(1)
                                .conditionally(RandomChanceLootCondition.builder(15 / 100.0F)))//15%
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)));
                tableBuilder.pool(pool);
            }
        });
    }

    public static void initialize() {
        modifyLootTable();
    }
}
