package com.github.Minor2CCh.minium_me.item;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MiniumSpecialRecipes {
    public static RecipeSerializer<MiniumShieldDecorationRecipe> MINIUM_SHIELD_DECORATION = register(
            "crafting_special_miniumshielddecoration", new SpecialRecipeSerializer<>(MiniumShieldDecorationRecipe::new)
    );
    public static void initialize() {
    }
    @SuppressWarnings("all")
    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        Identifier recipeId = Minium_me.of(id);
        return Registry.register(Registries.RECIPE_SERIALIZER, recipeId, serializer);
    }
}
