package com.github.Minor2CCh.minium_me.client.compat.rei.display;

import com.github.Minor2CCh.minium_me.client.compat.REICategoryIdentifiers;
import com.github.Minor2CCh.minium_me.component.EnergyComponent;
import com.github.Minor2CCh.minium_me.component.MiniumModComponent;
import com.github.Minor2CCh.minium_me.item.MiniumItem;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;

import java.util.ArrayList;
import java.util.List;

public class EnergyGunDisplay implements Display {
    private final ItemStack energyStack;
    private final TagKey<Item> ingredientKey;
    @SuppressWarnings("unused")
    public EnergyGunDisplay(){
        this.energyStack = new ItemStack(MiniumItem.ENERGY_GUN);
        this.ingredientKey = null;
    }
    public EnergyGunDisplay(EnergyComponent.EnergyType energyType, boolean isStorageBlock){
        this.energyStack = new ItemStack(MiniumItem.ENERGY_GUN);
        if(isStorageBlock){
            EnergyComponent.setEnergyComponent(this.energyStack, new EnergyComponent(energyType.getRemainSB(), energyType));
            this.ingredientKey = energyType.getMaterialSB();
        }else{
            this.energyStack.set(MiniumModComponent.REMAIN_ENERGY, new EnergyComponent(1, energyType));
            this.ingredientKey = energyType.getMaterial();
        }
    }
    @Override
    public List<EntryIngredient> getInputEntries() {
        List<EntryIngredient> list = new ArrayList<>();
        list.add(EntryIngredients.of(MiniumItem.ENERGY_GUN));
        Iterable<RegistryEntry<Item>> entryList = Registries.ITEM.iterateEntries(getIngredientKey());//エネルギーとして使われてるなら
        for (RegistryEntry<Item> entry : entryList) {
            Item item = entry.value();
            list.add(EntryIngredients.of(item));
        }
        return list;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of(EntryIngredients.of(MiniumItem.ENERGY_GUN));
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return REICategoryIdentifiers.ENERGY_GUN;
    }
    public ItemStack getEnergyStack(){
        return this.energyStack;
    }
    public TagKey<Item> getIngredientKey(){
        return this.ingredientKey;
    }
}
