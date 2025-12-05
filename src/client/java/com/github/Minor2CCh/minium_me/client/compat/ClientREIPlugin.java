package com.github.Minor2CCh.minium_me.client.compat;

import com.github.Minor2CCh.minium_me.block.MiniumBlock;
import com.github.Minor2CCh.minium_me.client.compat.rei.categories.EnergyGunDisplayCategory;
import com.github.Minor2CCh.minium_me.client.compat.rei.display.EnergyGunDisplay;
import com.github.Minor2CCh.minium_me.client.compat.rei.display.SmithingReinforcedDisplay;
import com.github.Minor2CCh.minium_me.client.compat.rei.display.SmithingReinforcedToolDisplay;
import com.github.Minor2CCh.minium_me.component.EnergyComponent;
import com.github.Minor2CCh.minium_me.item.MiniumItem;
import com.github.Minor2CCh.minium_me.recipe.SmithingArmorReinforcedRecipe;
import com.github.Minor2CCh.minium_me.recipe.SmithingToolReinforcedRecipe;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.recipe.RecipeType;

@Environment(EnvType.CLIENT)
public class ClientREIPlugin implements REIClientPlugin {
    @Override
    public void registerDisplays(DisplayRegistry registry) {
        for (EnergyComponent.EnergyType forType : EnergyComponent.EnergyType.values()) {
            if(forType.equals(EnergyComponent.EnergyType.ENERGY_EMPTY))continue;
            registry.add(new EnergyGunDisplay(forType, false));
            if(forType.getMaterialSB() != null){
                registry.add(new EnergyGunDisplay(forType, true));
            }
        }
        registry.registerRecipeFiller(
                SmithingArmorReinforcedRecipe.class,
                RecipeType.SMITHING,
                SmithingReinforcedDisplay::new
        );
        registry.registerRecipeFiller(
                SmithingToolReinforcedRecipe.class,
                RecipeType.SMITHING,
                SmithingReinforcedToolDisplay::new
        );

    }
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(
                new EnergyGunDisplayCategory()
        );
        registry.addWorkstations(REICategoryIdentifiers.ENERGY_GUN, EntryIngredients.of(MiniumItem.ENERGY_GUN));
        registry.addWorkstations(BuiltinPlugin.ANVIL, EntryIngredients.of(MiniumBlock.MINIUM_ANVIL));
    }
}
