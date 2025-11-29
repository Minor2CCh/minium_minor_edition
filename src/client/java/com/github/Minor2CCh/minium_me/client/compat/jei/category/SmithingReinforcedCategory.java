package com.github.Minor2CCh.minium_me.client.compat.jei.category;

import com.github.Minor2CCh.minium_me.Minium_me;
import com.github.Minor2CCh.minium_me.component.MiniumModComponent;
import com.github.Minor2CCh.minium_me.recipe.SmithingArmorReinforcedRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

public class SmithingReinforcedCategory implements IRecipeCategory<SmithingRecipe> {
    public static final RecipeType<SmithingRecipe> SMITHING_REINFORCED = RecipeType.create(Minium_me.MOD_ID, "smithing_reinforced", SmithingArmorReinforcedRecipe.class);
    private final IDrawable icon;

    public SmithingReinforcedCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(Items.SMITHING_TABLE));
        //guiHelper.drawableBuilder(RECIPE_GUI_VANILLA, 0, 168, 125, 18).addPadding(0, 16, 0, 0).build();
    }

    @Override
    public @NotNull RecipeType<SmithingRecipe> getRecipeType() {
        return SMITHING_REINFORCED;
    }

    @Override
    public @NotNull Text getTitle() {
        return Text.translatable("minium_me.jei.smithing_reinforced");
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }
    @Override
    public int getWidth() {
        return 108;
    }
    @Override
    public int getHeight() {
        return 28;
    }

    // ★ここでスロットの配置と動的出力を設定する
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder,
                          SmithingRecipe recipe,
                          IFocusGroup focuses) {

        SmithingArmorReinforcedRecipe reinforcedRecipe = (SmithingArmorReinforcedRecipe) recipe;
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 6)
                .addIngredients(reinforcedRecipe.template).setStandardSlotBackground();

        // Base
        builder.addSlot(RecipeIngredientRole.INPUT, 19, 6)
                .addIngredients(reinforcedRecipe.base).setStandardSlotBackground();

        // Addition
        builder.addSlot(RecipeIngredientRole.INPUT, 37, 6)
                .addIngredients(reinforcedRecipe.addition).setStandardSlotBackground();

        // ★ 動的 Output：自作する
        ItemStack baseExample = reinforcedRecipe.base.getMatchingStacks()[0].copy();
        ItemStack result = baseExample.copyWithCount(1);
        result.set(MiniumModComponent.ARMOR_REINFORCED, reinforcedRecipe.reinforced);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 91, 6)
                .addItemStack(result).setStandardSlotBackground();

    }
    @Override
    public void createRecipeExtras(mezz.jei.api.gui.widgets.IRecipeExtrasBuilder builder, SmithingRecipe recipe, mezz.jei.api.recipe.IFocusGroup focuses){
        builder.addRecipeArrow().setPosition(61, 6);
    }
}
