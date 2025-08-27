package com.github.Minor2CCh.minium_me.item;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import vectorwing.farmersdelight.common.item.KnifeItem;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.function.Supplier;
//Sinytra Connector環境での動作確認済み
public class FDItems {
    public static final Supplier<Item> MINIUM_KNIFE = register(() -> new KnifeItem(MiniumToolMaterial.MINIUM_TOOL, ModItems.knifeItem(MiniumToolMaterial.MINIUM_TOOL)), "minium_knife");
    public static final Supplier<Item> C_MINIUM_KNIFE = register(() -> new KnifeItem(MiniumToolMaterial.C_MINIUM_TOOL, ModItems.knifeItem(MiniumToolMaterial.C_MINIUM_TOOL)), "concentrated_minium_knife");
    public static final Supplier<Item> IRIS_QUARTZ_KNIFE = register(() -> new KnifeItem(MiniumToolMaterial.IRIS_QUARTZ_TOOL, ModItems.knifeItem(MiniumToolMaterial.IRIS_QUARTZ_TOOL).rarity(Rarity.EPIC).fireproof()), "iris_quartz_knife");
    public static void initialize() {
    }
    private static Supplier<Item> register(Supplier<Item> item, String id) {
        Item itemInfo = item.get();
        Identifier itemID = Identifier.of(Minium_me.MOD_ID, id);
        Registry.register(Registries.ITEM, itemID, itemInfo);
        return () -> itemInfo;
    }
    public static void addCreativeTabKnife(RegistryKey<ItemGroup> groupKey){
        ItemGroupEvents.modifyEntriesEvent(groupKey).register(itemGroup -> {
            itemGroup.add(MINIUM_KNIFE.get());
            itemGroup.add(C_MINIUM_KNIFE.get());
            itemGroup.add(IRIS_QUARTZ_KNIFE.get());
        });

    }
}
