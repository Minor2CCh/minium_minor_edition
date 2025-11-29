package com.github.Minor2CCh.minium_me.client.compat.rei.categories;

import com.github.Minor2CCh.minium_me.Minium_me;
import com.github.Minor2CCh.minium_me.client.compat.REICategoryIdentifiers;
import com.github.Minor2CCh.minium_me.client.compat.rei.display.EnergyGunDisplay;
import com.github.Minor2CCh.minium_me.item.MiniumItem;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class EnergyGunDisplayCategory implements DisplayCategory<EnergyGunDisplay> {
    private static final Identifier BACKGROUND = Minium_me.of("textures/gui/jei/energy_gun_convert.png");
    @Override
    public CategoryIdentifier<? extends EnergyGunDisplay> getCategoryIdentifier() {
        return REICategoryIdentifiers.ENERGY_GUN;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("minium_me.jei.energy_convert");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MiniumItem.ENERGY_GUN);
    }

    @Override
    public int getDisplayHeight() {
        return 102;
    }
    @SuppressWarnings("all")
    @Override
    public int getDisplayWidth(EnergyGunDisplay display) {
        return 150;
    }

    @Override
    public List<Widget> setupDisplay(EnergyGunDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        Point startPoint = new Point(bounds.getCenterX() - 59, bounds.getCenterY() - 40);

        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createTexturedWidget(BACKGROUND, startPoint.x, startPoint.y, 0, 0, 118, 80));
        widgets.add(Widgets.createSlot(new Point( startPoint.x + 9, startPoint.y + 26)).entry(EntryStacks.of(MiniumItem.ENERGY_GUN)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 93, startPoint.y + 26)).entry(EntryStacks.of(display.getEnergyStack())));
        previewTagKey(display, widgets, startPoint);
        //widgets.add(Widgets.createSlot(new Point(startPoint.x + 64, startPoint.y + 54)).entries(EntryIngredients.ofItemTag(previewIngredientList(tickTime))));


        return widgets;
    }

    private static void previewTagKey(EnergyGunDisplay display, List<Widget> widgets, Point startPoint) {
        var matchingItems = Registries.ITEM.iterateEntries(display.getIngredientKey());
        if(!matchingItems.iterator().hasNext()){
            ItemStack emptyStack = new ItemStack(Items.BARRIER);
            emptyStack.set(DataComponentTypes.ITEM_NAME, Text.translatable("minium_me.jei.energy_unused", display.getIngredientKey().id().toString()).formatted(Formatting.RED));
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 52, startPoint.y + 54)).entry(EntryStacks.of(emptyStack)));
        }else{
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 52, startPoint.y + 54)).entries(EntryIngredients.ofItemTag(display.getIngredientKey())));
        }
    }

    @Override
    public int getFixedDisplaysPerPage() {
        return 1;
    }
}
