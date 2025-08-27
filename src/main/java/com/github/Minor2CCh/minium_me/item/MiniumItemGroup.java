package com.github.Minor2CCh.minium_me.item;

import com.github.Minor2CCh.minium_me.Minium_me;
import com.github.Minor2CCh.minium_me.block.MiniumBlock;
import com.github.Minor2CCh.minium_me.component.MiniumModComponent;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.UnbreakableComponent;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MiniumItemGroup {
    public static final RegistryKey<ItemGroup> MINIUM_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(Minium_me.MOD_ID, "minium_item_group"));
    public static final ItemGroup MINIUM_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(MiniumItem.MINIUM_INGOT))
            .displayName(Text.translatable(String.format("itemGroup.%s.%s", Minium_me.MOD_ID, "minium_item")))
            .build();

    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, MINIUM_ITEM_GROUP_KEY, MINIUM_ITEM_GROUP);
        ItemGroupEvents.modifyEntriesEvent(MINIUM_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(MiniumItem.MINIUM_SWORD);
            itemGroup.add(MiniumItem.MINIUM_SHOVEL);
            itemGroup.add(MiniumItem.MINIUM_PICKAXE);
            itemGroup.add(MiniumItem.MINIUM_AXE);
            itemGroup.add(MiniumItem.MINIUM_HOE);
            itemGroup.add(MiniumItem.MINIUM_MULTITOOL);
            itemGroup.add(MiniumItem.C_MINIUM_SWORD);
            itemGroup.add(MiniumItem.C_MINIUM_SHOVEL);
            itemGroup.add(MiniumItem.C_MINIUM_PICKAXE);
            itemGroup.add(MiniumItem.C_MINIUM_AXE);
            itemGroup.add(MiniumItem.C_MINIUM_HOE);
            itemGroup.add(MiniumItem.C_MINIUM_MULTITOOL);
            itemGroup.add(MiniumItem.MINIUM_HELMET);
            itemGroup.add(MiniumItem.MINIUM_CHESTPLATE);
            itemGroup.add(MiniumItem.MINIUM_LEGGINGS);
            itemGroup.add(MiniumItem.MINIUM_BOOTS);
            itemGroup.add(MiniumItem.C_MINIUM_HELMET);
            itemGroup.add(MiniumItem.C_MINIUM_CHESTPLATE);
            itemGroup.add(MiniumItem.C_MINIUM_LEGGINGS);
            itemGroup.add(MiniumItem.C_MINIUM_BOOTS);
            itemGroup.add(MiniumItem.IRIS_QUARTZ_SWORD);
            itemGroup.add(MiniumItem.IRIS_QUARTZ_SHOVEL);
            itemGroup.add(MiniumItem.IRIS_QUARTZ_PICKAXE);
            itemGroup.add(MiniumItem.IRIS_QUARTZ_AXE);
            itemGroup.add(MiniumItem.IRIS_QUARTZ_HOE);
            itemGroup.add(MiniumItem.IRIS_QUARTZ_MULTITOOL);
            itemGroup.add(MiniumItem.IRIS_QUARTZ_HELMET);
            itemGroup.add(MiniumItem.IRIS_QUARTZ_CHESTPLATE);
            itemGroup.add(MiniumItem.IRIS_QUARTZ_LEGGINGS);
            itemGroup.add(MiniumItem.IRIS_QUARTZ_BOOTS);
            itemGroup.add(MiniumItem.IRIS_QUARTZ_ELYTRA);
            itemGroup.add(MiniumItem.IRIS_QUARTZ_ELYTRA_CHESTPLATE);
            itemGroup.add(MiniumItem.MINIUM_INGOT);
            itemGroup.add(MiniumItem.C_MINIUM_INGOT);
            itemGroup.add(MiniumItem.R_MINIUM);
            itemGroup.add(MiniumItem.MINIUM_UPGRADE_SMITHING_TEMPLATE);
            itemGroup.add(MiniumItem.IRIS_QUARTZ_UPGRADE_SMITHING_TEMPLATE);
            itemGroup.add(MiniumBlock.MINIUM_ORE);
            itemGroup.add(MiniumBlock.DEEPSLATE_MINIUM_ORE);
            itemGroup.add(MiniumBlock.NETHER_MINIUM_ORE);
            itemGroup.add(MiniumBlock.END_MINIUM_ORE);
            itemGroup.add(MiniumBlock.MINIUM_BLOCK);
            itemGroup.add(MiniumBlock.CONCENTRATED_MINIUM_BLOCK);
            itemGroup.add(MiniumBlock.RAW_MINIUM_BLOCK);
            itemGroup.add(MiniumBlock.MINIUM_GRATE);
            itemGroup.add(MiniumBlock.MINIUM_BULB);
            itemGroup.add(MiniumItem.IRIS_QUARTZ);
            itemGroup.add(MiniumItem.IRIS_QUARTZ_INGOT);
            itemGroup.add(MiniumBlock.IRIS_QUARTZ_ORE);
            itemGroup.add(MiniumBlock.DEEPSLATE_IRIS_QUARTZ_ORE);
            itemGroup.add(MiniumBlock.IRIS_QUARTZ_BLOCK);
            itemGroup.add(MiniumItem.ENERGY_GUN);
            for (String s : MiniumModComponent.ENERGY_LIST) {
                ItemStack gunStack = MiniumItem.ENERGY_GUN.getDefaultStack();
                gunStack.set(MiniumModComponent.REMAIN_ENERGY, new MiniumModComponent.EnergyComponent(2147483647, s));
                itemGroup.add(gunStack);
            }
            itemGroup.add(MiniumBlock.WIND_CHARGE_BLOCK);
            itemGroup.add(MiniumItem.DEEPSLATE_MINER);
            itemGroup.add(MiniumItem.WIND_EXPLODER);
            ItemStack windExploderStack = MiniumItem.WIND_EXPLODER.getDefaultStack();
            windExploderStack.set(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(true));
            itemGroup.add(windExploderStack);
            itemGroup.add(MiniumItem.ADVANCED_WIND_EXPLODER);
            ItemStack advancedWindExploderStack = MiniumItem.ADVANCED_WIND_EXPLODER.getDefaultStack();
            advancedWindExploderStack.set(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(true));
            advancedWindExploderStack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
            itemGroup.add(advancedWindExploderStack);
            itemGroup.add(MiniumItem.MINIUM_SPEAR);
            itemGroup.add(MiniumItem.C_MINIUM_SPEAR);
            itemGroup.add(MiniumItem.IRIS_QUARTZ_SPEAR);
            itemGroup.add(MiniumBlock.EASY_GRINDER);
            itemGroup.add(MiniumBlock.ADVANCED_GRINDER);
            itemGroup.add(MiniumBlock.EASY_CONVEYOR);
            itemGroup.add(MiniumBlock.MINIUM_ARTIFICIAL_FLOWER);
            itemGroup.add(MiniumItem.IRIS_QUARTZ_PENDANT);


            //from mekanism
            itemGroup.add(MiniumItem.OSMIUM_INGOT_FROM_MEKANISM);
            itemGroup.add(MiniumItem.RAW_OSMIUM_FROM_MEKANISM);
            itemGroup.add(MiniumBlock.OSMIUM_ORE_FROM_MEKANISM);
            itemGroup.add(MiniumBlock.DEEPSLATE_OSMIUM_ORE_FROM_MEKANISM);
            itemGroup.add(MiniumItem.OSMIUM_NUGGET_FROM_MEKANISM);
            itemGroup.add(MiniumBlock.OSMIUM_BLOCK_FROM_MEKANISM);
            itemGroup.add(MiniumBlock.RAW_OSMIUM_BLOCK_FROM_MEKANISM);



        });
        if(FabricLoader.getInstance().isModLoaded("farmersdelight")){
            FDItems.addCreativeTabKnife(MINIUM_ITEM_GROUP_KEY);
        }
    }
}
