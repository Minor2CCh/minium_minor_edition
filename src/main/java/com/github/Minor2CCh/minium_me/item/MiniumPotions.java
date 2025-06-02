package com.github.Minor2CCh.minium_me.item;

import com.github.Minor2CCh.minium_me.Minium_me;
import com.github.Minor2CCh.minium_me.block.MiniumBlock;
import com.github.Minor2CCh.minium_me.statuseffect.MiniumStatusEffects;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MiniumPotions {
    public static final Potion POISON_HEAL = Registry.register(Registries.POTION,
            Identifier.of(Minium_me.MOD_ID, "poison_heal"),
            new Potion(new StatusEffectInstance(MiniumStatusEffects.POISON_HEAL, 3600)));
    public static final Potion LONG_POISON_HEAL = Registry.register(Registries.POTION,
            Identifier.of(Minium_me.MOD_ID, "long_poison_heal"),
            new Potion(new StatusEffectInstance(MiniumStatusEffects.POISON_HEAL, 9600)));

    public static void initialize() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
        builder.registerPotionRecipe(
                Potions.AWKWARD,
                MiniumBlock.MINIUM_ARTIFICIAL_FLOWER.asItem(),
                Registries.POTION.getEntry(POISON_HEAL));
        builder.registerPotionRecipe(
                Potions.WATER,
                MiniumBlock.MINIUM_ARTIFICIAL_FLOWER.asItem(),
                Potions.MUNDANE);
        builder.registerPotionRecipe(
                Registries.POTION.getEntry(POISON_HEAL),
                Items.REDSTONE,
                Registries.POTION.getEntry(LONG_POISON_HEAL));
    });
    }
}
