package com.github.Minor2CCh.minium_me.client.compat.jei;

import com.github.Minor2CCh.minium_me.client.compat.jei.category.SmithingReinforcedCategory;
import com.github.Minor2CCh.minium_me.recipe.SmithingArmorReinforcedRecipe;
import com.github.Minor2CCh.minium_me.recipe.SmithingToolReinforcedRecipe;
import com.google.common.collect.Lists;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.recipe.*;

import java.util.List;

public class JEIRecipes {
    private final RecipeManager recipeManager;

    public JEIRecipes() {
        MinecraftClient minecraft = MinecraftClient.getInstance();
        ClientWorld level = minecraft.world;

        if (level != null) {
            this.recipeManager = level.getRecipeManager();
        } else {
            throw new NullPointerException("minecraft world must not be null.");
        }
    }

    public void setSmithingReinforcedRecipe(IRecipeRegistration registration) {
        List<RecipeEntry<SmithingRecipe>> specialRecipe = recipeManager.listAllOfType(RecipeType.SMITHING);
        List<SmithingRecipe> recipes = Lists.newArrayList();
        specialRecipe.forEach((recipe) -> {
            if(recipe.value() instanceof SmithingArmorReinforcedRecipe || recipe.value() instanceof SmithingToolReinforcedRecipe){
                //System.out.println(recipe);
                recipes.add(recipe.value());
            }
        });

        registration.addRecipes(SmithingReinforcedCategory.SMITHING_REINFORCED, recipes);
    }
}
