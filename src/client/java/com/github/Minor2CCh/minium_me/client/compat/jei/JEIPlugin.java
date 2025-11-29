package com.github.Minor2CCh.minium_me.client.compat.jei;

import com.github.Minor2CCh.minium_me.Minium_me;
import com.github.Minor2CCh.minium_me.block.MiniumBlock;
import com.github.Minor2CCh.minium_me.client.compat.jei.category.EnergyGunRecipeCategory;
import com.github.Minor2CCh.minium_me.client.compat.jei.category.SmithingReinforcedCategory;
import com.github.Minor2CCh.minium_me.client.compat.jei.resource.EnergyGunConvert;
import com.github.Minor2CCh.minium_me.component.EnergyComponent;
import com.github.Minor2CCh.minium_me.item.MiniumItem;
import com.google.common.collect.ImmutableList;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.annotation.MethodsReturnNonnullByDefault;

@JeiPlugin
@MethodsReturnNonnullByDefault
@SuppressWarnings("unused")
public class JEIPlugin implements IModPlugin {
    private static final Identifier ID = Minium_me.of("jei_plugin");
    @Override
    public Identifier getPluginUid() {
        return ID;
    }
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new EnergyGunRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new SmithingReinforcedCategory(registry.getJeiHelpers().getGuiHelper()));
    }
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        for (EnergyComponent.EnergyType forType : EnergyComponent.EnergyType.values()) {
            if(forType.equals(EnergyComponent.EnergyType.ENERGY_EMPTY))continue;
            registration.addRecipes(EnergyGunRecipeCategory.TYPE, ImmutableList.of(new EnergyGunConvert(forType, false)));
            if(forType.getMaterialSB() != null){
                registration.addRecipes(EnergyGunRecipeCategory.TYPE, ImmutableList.of(new EnergyGunConvert(forType, true)));
            }

        }
        JEIRecipes modRecipes = new JEIRecipes();
        modRecipes.setSmithingReinforcedRecipe(registration);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(MiniumBlock.MINIUM_ANVIL), mezz.jei.api.constants.RecipeTypes.ANVIL);
        // EMIは壊れかけた金床も対象なので
        if(FabricLoader.getInstance().isModLoaded("emi")){
            registration.addRecipeCatalyst(new ItemStack(MiniumBlock.CHIPPED_MINIUM_ANVIL), mezz.jei.api.constants.RecipeTypes.ANVIL);
            registration.addRecipeCatalyst(new ItemStack(MiniumBlock.DAMAGED_MINIUM_ANVIL), mezz.jei.api.constants.RecipeTypes.ANVIL);
        }
        registration.addRecipeCatalyst(new ItemStack(MiniumItem.ENERGY_GUN), EnergyGunRecipeCategory.TYPE);
        registration.addRecipeCatalyst(new ItemStack(Items.SMITHING_TABLE), SmithingReinforcedCategory.SMITHING_REINFORCED);
    }


}
