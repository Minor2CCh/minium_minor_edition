package com.github.Minor2CCh.minium_me.registry;
import com.github.Minor2CCh.minium_me.Minium_me;
import com.github.Minor2CCh.minium_me.recipe.SmithingArmorReinforcedRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

@SuppressWarnings("all")
public class MiniumRecipeTypes {
    public static final RecipeType<SmithingArmorReinforcedRecipe> SMITHING_REINFORCED =
            register("smithing_reinforced");
    private static <T extends Recipe<?>> RecipeType<T> register(String id){
        return Registry.register(Registries.RECIPE_TYPE, Minium_me.of(id), new RecipeType<T>() {
            public String toString() {
                return id;
            }
        });
    }
    public static void initialize() {

    }
}
