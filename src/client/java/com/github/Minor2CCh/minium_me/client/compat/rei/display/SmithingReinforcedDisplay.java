package com.github.Minor2CCh.minium_me.client.compat.rei.display;

import com.github.Minor2CCh.minium_me.component.MiniumModComponent;
import com.github.Minor2CCh.minium_me.recipe.SmithingArmorReinforcedRecipe;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.common.displays.DefaultSmithingDisplay;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;

import java.util.ArrayList;
import java.util.List;

public class SmithingReinforcedDisplay extends DefaultSmithingDisplay {

    private final SmithingArmorReinforcedRecipe recipe;

    public SmithingReinforcedDisplay(RecipeEntry<SmithingArmorReinforcedRecipe> entry) {
        super(entry.value(), entry.id(), List.of(
                EntryIngredients.ofIngredient(entry.value().template),
                EntryIngredients.ofIngredient(entry.value().base),
                EntryIngredients.ofIngredient(entry.value().addition)
        ));
        this.recipe = entry.value();
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return List.of(
                EntryIngredients.ofIngredient(recipe.template),
                EntryIngredients.ofIngredient(recipe.base),
                EntryIngredients.ofIngredient(recipe.addition)
        );
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        List<EntryIngredient> stackList = new ArrayList<>();
        for(ItemStack stack : recipe.base.getMatchingStacks()){
            ItemStack result = stack.copyWithCount(1);
            result.set(MiniumModComponent.ARMOR_REINFORCED, recipe.reinforced);
            stackList.add(EntryIngredients.of(result));
        }
        return stackList;
    }
}
