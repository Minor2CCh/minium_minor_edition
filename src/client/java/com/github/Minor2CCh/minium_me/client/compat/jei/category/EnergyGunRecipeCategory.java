package com.github.Minor2CCh.minium_me.client.compat.jei.category;

import com.github.Minor2CCh.minium_me.Minium_me;
import com.github.Minor2CCh.minium_me.client.compat.jei.resource.EnergyGunConvert;
import com.github.Minor2CCh.minium_me.component.EnergyComponent;
import com.github.Minor2CCh.minium_me.component.MiniumModComponent;
import com.github.Minor2CCh.minium_me.item.MiniumItem;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.annotation.MethodsReturnNonnullByDefault;

import java.util.ArrayList;
import java.util.List;

@MethodsReturnNonnullByDefault
public class EnergyGunRecipeCategory implements IRecipeCategory<EnergyGunConvert> {
    public static final RecipeType<EnergyGunConvert> TYPE =
            new RecipeType<>(Minium_me.of("energy_gun"), EnergyGunConvert.class);
    private static final int slotSize = 22;

    private final Text title;
    private final IDrawable background;
    private final IDrawable slotIcon;
    private final IDrawable icon;
    public EnergyGunRecipeCategory(IGuiHelper helper) {
        title = Text.translatable("minium_me.jei.energy_convert");
        Identifier backgroundImage = Minium_me.of("textures/gui/jei/energy_gun_convert.png");
        background = helper.createDrawable(backgroundImage, 0, 0, this.getWidth(), this.getHeight());
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(MiniumItem.ENERGY_GUN));
        slotIcon = helper.createDrawable(backgroundImage, 119, 0, slotSize, slotSize);
    }

    @Override
    public RecipeType<EnergyGunConvert> getRecipeType() {
        return TYPE;
    }

    @Override
    public Text getTitle() {
        return this.title;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }
    @Override
    public int getWidth() {
        return 118;
    }
    @Override
    public int getHeight() {
        return 80;
    }


    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, EnergyGunConvert recipe, IFocusGroup focusGroup) {
        boolean isStorageBlock = recipe.sb();
        ItemStack gunStack = new ItemStack(MiniumItem.ENERGY_GUN);
        TagKey<Item> ingredientKey;
        EnergyComponent.EnergyType energyType = recipe.material();
        List<ItemStack> materialList = new ArrayList<>();
        if(isStorageBlock){
            gunStack.set(MiniumModComponent.REMAIN_ENERGY, new EnergyComponent(energyType.getRemainSB(), energyType));
            ingredientKey = energyType.getMaterialSB();
        }else{
            gunStack.set(MiniumModComponent.REMAIN_ENERGY, new EnergyComponent(1, energyType));
            ingredientKey = energyType.getMaterial();
        }
        if(ingredientKey != null){
            Iterable<RegistryEntry<Item>> entryList = Registries.ITEM.iterateEntries(ingredientKey);//エネルギーとして使われてるなら
            for (RegistryEntry<Item> entry : entryList) {
                Item item = entry.value();
                materialList.add(item.getDefaultStack());
            }
        }
        builder.addSlot(RecipeIngredientRole.INPUT, 9, 26).addItemStack(new ItemStack(MiniumItem.ENERGY_GUN));


        builder.addSlot(RecipeIngredientRole.OUTPUT, 93, 26).addItemStack(gunStack);
        renderIngredient(builder, ingredientKey, materialList);
    }
    private void renderIngredient(IRecipeLayoutBuilder builder, TagKey<Item> ingredientKey, List<ItemStack> materialList){
        if(materialList.isEmpty()){
            ItemStack emptyStack = new ItemStack(Items.BARRIER);
            emptyStack.set(DataComponentTypes.ITEM_NAME, Text.translatable("minium_me.jei.energy_unused", ingredientKey.id().toString()).formatted(Formatting.RED));
            builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 52, 54).addItemStack(emptyStack);
        }else{
            builder.addSlot(RecipeIngredientRole.INPUT, 52, 54).addItemStacks(materialList);
        }
    }

    @Override
    public void draw(EnergyGunConvert recipe, IRecipeSlotsView recipeSlotsView, DrawContext guiGraphics, double mouseX, double mouseY) {
        this.background.draw(guiGraphics, 0, 0);
        this.slotIcon.draw(guiGraphics, 51, 53);
    }
}
