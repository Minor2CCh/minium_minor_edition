package com.github.Minor2CCh.minium_me.recipe;

import com.github.Minor2CCh.minium_me.component.MiniumModComponent;
import com.github.Minor2CCh.minium_me.component.ToolReinforcedComponent;
import com.github.Minor2CCh.minium_me.item.MiniumItem;
import com.github.Minor2CCh.minium_me.registry.MiniumSpecialRecipes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.recipe.input.SmithingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public class SmithingToolReinforcedRecipe implements SmithingRecipe {
    public final Ingredient template;
    public final Ingredient base;
    public final Ingredient addition;
    public final ToolReinforcedComponent reinforced;

    public SmithingToolReinforcedRecipe(Ingredient template, Ingredient base, Ingredient addition, ToolReinforcedComponent reinforced) {
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.reinforced = reinforced;
    }
    @Override
    public ItemStack craft(SmithingRecipeInput smithingRecipeInput, RegistryWrapper.WrapperLookup wrapperLookup) {
        ItemStack itemStack = smithingRecipeInput.base();
        if (this.base.test(itemStack) && this.template.test(smithingRecipeInput.template())) {
            ToolReinforcedComponent inputComponent = reinforced;
            ToolReinforcedComponent baseReinforced = itemStack.get(MiniumModComponent.TOOL_REINFORCED);
            if (baseReinforced != null && baseReinforced.equals(inputComponent)) {
                return ItemStack.EMPTY;
            }

            ItemStack itemStack2 = itemStack.copyWithCount(1);
            itemStack2.set(MiniumModComponent.TOOL_REINFORCED, inputComponent);
            return itemStack2;

        }

        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        ItemStack itemStack = new ItemStack(MiniumItem.MINIUM_PICKAXE);
        itemStack.set(MiniumModComponent.TOOL_REINFORCED, ToolReinforcedComponent.EMPTY);

        return itemStack;
    }

    @Override
    public boolean matches(SmithingRecipeInput smithingRecipeInput, World world) {
        return this.template.test(smithingRecipeInput.template()) && this.base.test(smithingRecipeInput.base()) && this.addition.test(smithingRecipeInput.addition());
    }
    @Override
    public boolean testTemplate(ItemStack stack) {
        return this.template.test(stack);
    }

    @Override
    public boolean testBase(ItemStack stack) {
        return this.base.test(stack);
    }

    @Override
    public boolean testAddition(ItemStack stack) {
        return this.addition.test(stack);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MiniumSpecialRecipes.SMITHING_TOOL_REINFORCED;
    }

    public static class Serializer implements RecipeSerializer<SmithingToolReinforcedRecipe> {
        private static final MapCodec<SmithingToolReinforcedRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Ingredient.ALLOW_EMPTY_CODEC.fieldOf("template").forGetter(recipe -> recipe.template),
                                Ingredient.ALLOW_EMPTY_CODEC.fieldOf("base").forGetter(recipe -> recipe.base),
                                Ingredient.ALLOW_EMPTY_CODEC.fieldOf("addition").forGetter(recipe -> recipe.addition),
                                ToolReinforcedComponent.CODEC.optionalFieldOf("reinforced", ToolReinforcedComponent.EMPTY).forGetter(recipe -> recipe.reinforced)
                        )
                        .apply(instance, SmithingToolReinforcedRecipe::new)
        );
        public static final PacketCodec<RegistryByteBuf, SmithingToolReinforcedRecipe> PACKET_CODEC = PacketCodec.ofStatic(
                SmithingToolReinforcedRecipe.Serializer::write, SmithingToolReinforcedRecipe.Serializer::read
        );

        @Override
        public MapCodec<SmithingToolReinforcedRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, SmithingToolReinforcedRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static SmithingToolReinforcedRecipe read(RegistryByteBuf buf) {
            Ingredient ingredient = Ingredient.PACKET_CODEC.decode(buf);
            Ingredient ingredient2 = Ingredient.PACKET_CODEC.decode(buf);
            Ingredient ingredient3 = Ingredient.PACKET_CODEC.decode(buf);
            ToolReinforcedComponent reinforced = ToolReinforcedComponent.PACKET_CODEC.decode(buf);
            return new SmithingToolReinforcedRecipe(ingredient, ingredient2, ingredient3, reinforced);
        }

        private static void write(RegistryByteBuf buf, SmithingToolReinforcedRecipe recipe) {
            Ingredient.PACKET_CODEC.encode(buf, recipe.template);
            Ingredient.PACKET_CODEC.encode(buf, recipe.base);
            Ingredient.PACKET_CODEC.encode(buf, recipe.addition);
            ToolReinforcedComponent.PACKET_CODEC.encode(buf, recipe.reinforced);
        }
    }
}
