package com.github.Minor2CCh.minium_me.item;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class AddLootTable {
    //private static final Identifier END_CITY_ID = Identifier.ofVanilla("chests/end_city_treasure");
    public static RegistryKey<LootTable> END_CITY_ID = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.ofVanilla("chests/end_city_treasure"));
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
                        .with(ItemEntry.builder(MiniumItem.IRIS_QUARTZ_UPGRADE_SMITHING_TEMPLATE).weight(1))//抽選アイテム
                        .with(ItemEntry.builder(Items.AIR).weight(5))//抽選アイテム
                        //.bonusRolls(ConstantLootNumberProvider.create(0.5F))//幸運ステータスによる追加ボーナスらしい

                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)));//スタック個数

                // Add the loot pool to the loot table
                tableBuilder.pool(pool);
            }
        });
    }

    public static void initialize() {
        modifyLootTable();
    }
}
