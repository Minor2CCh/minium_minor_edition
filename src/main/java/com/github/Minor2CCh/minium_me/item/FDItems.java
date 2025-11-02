package com.github.Minor2CCh.minium_me.item;

import com.github.Minor2CCh.minium_me.Minium_me;
import com.github.Minor2CCh.minium_me.entity.EntityFunctions;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import vectorwing.farmersdelight.common.item.KnifeItem;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.Objects;
import java.util.function.Supplier;
//Sinytra Connector環境での動作確認済み
public class FDItems {
    public static final Supplier<Item> MINIUM_KNIFE = register(() -> new KnifeItem(MiniumToolMaterial.MINIUM_TOOL, ModItems.knifeItem(MiniumToolMaterial.MINIUM_TOOL)), "minium_knife");
    public static final Supplier<Item> C_MINIUM_KNIFE = register(() -> new KnifeItem(MiniumToolMaterial.C_MINIUM_TOOL, ModItems.knifeItem(MiniumToolMaterial.C_MINIUM_TOOL)), "concentrated_minium_knife");
    public static final Supplier<Item> IRIS_QUARTZ_KNIFE = register(() -> new KnifeItem(MiniumToolMaterial.IRIS_QUARTZ_TOOL, ModItems.knifeItem(MiniumToolMaterial.IRIS_QUARTZ_TOOL).rarity(Rarity.EPIC).fireproof()), "iris_quartz_knife");
    public static void initialize() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            for(ServerPlayerEntity player : world.getPlayers()){
                if(EntityFunctions.hasItem(player, MINIUM_KNIFE.get()) || EntityFunctions.hasItem(player, C_MINIUM_KNIFE.get()) || EntityFunctions.hasItem(player, IRIS_QUARTZ_KNIFE.get())){
                    Identifier advancementId = Identifier.of("farmersdelight","main/craft_knife");
                    AdvancementEntry advancement = Objects.requireNonNull(player.getServer()).getAdvancementLoader().get(advancementId);
                    if (advancement != null) {
                        AdvancementProgress progress = player.getAdvancementTracker().getProgress(advancement);
                        if (!progress.isDone()) {
                            for (String criterion : progress.getUnobtainedCriteria()) {
                                player.getAdvancementTracker().grantCriterion(advancement, criterion);
                            }
                        }
                    }
                }

            }
        });
    }
    private static Supplier<Item> register(Supplier<Item> item, String id) {
        Item itemInfo = item.get();
        Identifier itemID = Minium_me.of(id);
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
