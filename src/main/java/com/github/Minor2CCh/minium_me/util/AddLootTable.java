package com.github.Minor2CCh.minium_me.util;

import com.github.Minor2CCh.minium_me.Minium_me;
import com.github.Minor2CCh.minium_me.component.MiniumModComponent;
import com.github.Minor2CCh.minium_me.component.ToolReinforcedComponent;
import com.github.Minor2CCh.minium_me.item.MiniumItem;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.loot.function.SetComponentsLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.function.SetDamageLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public final class AddLootTable {
    private static final RegistryKey<LootTable> END_CITY_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/end_city_treasure"));
    private static final RegistryKey<LootTable> MINESHAFT_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/abandoned_mineshaft"));
    private static final RegistryKey<LootTable> BURIED_TREASURE_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/buried_treasure"));
    private static final RegistryKey<LootTable> DESERT_PYRAMID_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/desert_pyramid"));
    private static final RegistryKey<LootTable> JUNGLE_TEMPLATE_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/jungle_temple"));
    private static final RegistryKey<LootTable> PILLAGER_OUTPOST_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/pillager_outpost"));
    private static final RegistryKey<LootTable> SHIPWRECK_TREASURE_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/shipwreck_treasure"));
    private static final RegistryKey<LootTable> SIMPLE_DUNGEON_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/simple_dungeon"));
    private static final RegistryKey<LootTable> STRONGHOLD_CORRIDOR_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/stronghold_corridor"));
    private static final RegistryKey<LootTable> STRONGHOLD_CROSSING_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/stronghold_crossing"));
    private static final RegistryKey<LootTable> UNDERWATER_RUIN_BIG_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/underwater_ruin_big"));
    private static final RegistryKey<LootTable> UNDERWATER_RUIN_SMALL_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/underwater_ruin_small"));
    private static final RegistryKey<LootTable> TRIAL_SPAWNER_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("spawners/trial_chamber/consumables"));
    private static final RegistryKey<LootTable> ANCIENT_CITY_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/ancient_city"));
    private static final RegistryKey<LootTable> ANCIENT_CITY_ICE_BOX_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/ancient_city_ice_box"));
    private static final RegistryKey<LootTable> BASTION_BRIDGE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/bastion_bridge"));
    private static final RegistryKey<LootTable> BASTION_HOGLIN_STABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/bastion_hoglin_stable"));
    private static final RegistryKey<LootTable> BASTION_OTHER = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/bastion_other"));
    private static final RegistryKey<LootTable> BASTION_TREASURE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/bastion_treasure"));
    private static final RegistryKey<LootTable> NETHER_BRIDGE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/nether_bridge"));
    private static final RegistryKey<LootTable> WOODLAND_MANSION = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/woodland_mansion"));
    private static final RegistryKey<LootTable> VIlLAGE_ARMORER = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/village/village_armorer"));
    private static final RegistryKey<LootTable> TRIAL_CHAMBERS_REWARD = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/trial_chambers/reward"));
    private static final RegistryKey<LootTable> TRIAL_CHAMBERS_REWARD_OMINOUS = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/trial_chambers/reward_ominous"));
    private static final RegistryKey<LootTable> TRIAL_CHAMBERS_ENTRANCE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/trial_chambers/entrance"));
    private static final RegistryKey<LootTable> TRIAL_CHAMBERS_CORRIDOR = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/trial_chambers/corridor"));
    private static final RegistryKey<LootTable> TRIAL_CHAMBERS_INTERSECTION_BARREL = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/trial_chambers/intersection_barrel"));


    private static final RegistryKey<LootTable> OMINOUS_TRIAL_SPAWNER_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("spawners/ominous/trial_chamber/consumables"));
    private static final RegistryKey<LootTable> INJECT_MINESHAFT_LOOT_TABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Minium_me.of("chests/inject/inject_enchanted_book"));
    private static final RegistryKey<LootTable> INJECT_MINIUM_LITTLE_LOOT_TABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Minium_me.of("chests/inject/inject_custom_little"));
    private static final RegistryKey<LootTable> INJECT_MINIUM_MEDIUM_LOOT_TABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Minium_me.of("chests/inject/inject_custom_medium"));
    private static final RegistryKey<LootTable> INJECT_MINIUM_LARGE_LOOT_TABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Minium_me.of("chests/inject/inject_custom_large"));
    private static final RegistryKey<LootTable> INJECT_POTION_LOOT_TABLE = RegistryKey.of(RegistryKeys.LOOT_TABLE, Minium_me.of("chests/inject/inject_potion"));
    private static void modifyLootTable(){
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
            if (TRIAL_CHAMBERS_ENTRANCE == key && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))//抽選回数
                        .with(ItemEntry.builder(Items.WOODEN_AXE).weight(1)
                                .conditionally(RandomChanceLootCondition.builder(1 / 4.0F)))//抽選アイテム
                        .apply(SetComponentsLootFunction.builder(MiniumModComponent.TOOL_REINFORCED, ToolReinforcedComponent.HONEY_PAINTER))
                        .apply(SetDamageLootFunction.builder(
                                UniformLootNumberProvider.create(0.1f, 0.7f)
                        ))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)));//スタック個数
                tableBuilder.pool(pool);
            }
            if (TRIAL_CHAMBERS_CORRIDOR == key && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))//抽選回数
                        .with(ItemEntry.builder(Items.STONE_AXE).weight(6))//抽選アイテム
                        .with(ItemEntry.builder(Items.IRON_AXE).weight(1))//抽選アイテム
                        .conditionally(RandomChanceLootCondition.builder(1 / 8.0F))
                        .apply(SetComponentsLootFunction.builder(MiniumModComponent.TOOL_REINFORCED, ToolReinforcedComponent.HONEY_PAINTER))
                        .apply(SetDamageLootFunction.builder(
                                UniformLootNumberProvider.create(0.1f, 0.7f)
                        ))
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)));//スタック個数
                tableBuilder.pool(pool);
            }
            if (TRIAL_CHAMBERS_INTERSECTION_BARREL == key && source.isBuiltin()) {
                LootPool.Builder pool = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0f))//抽選回数
                        .with(ItemEntry.builder(Items.GOLDEN_AXE).weight(10))//抽選アイテム
                        .with(ItemEntry.builder(Items.DIAMOND_AXE).weight(1))//抽選アイテム
                        .conditionally(RandomChanceLootCondition.builder(1 / 4.0F))
                        .apply(SetComponentsLootFunction.builder(MiniumModComponent.TOOL_REINFORCED, ToolReinforcedComponent.HONEY_PAINTER))
                        .apply(SetDamageLootFunction.builder(
                                UniformLootNumberProvider.create(0.1f, 0.4f)
                        ))
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
    private AddLootTable(){}
    public static void initialize() {
        modifyLootTable();
    }
}
