package com.github.Minor2CCh.minium_me.enchantment;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class MiniumEnchantmentTags {
    public static final TagKey<Enchantment> TRANSFORMER_STONE = Modof("transformer_stone");

    private static TagKey<Enchantment> Modof(String id) {
        return TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(Minium_me.MOD_ID, id));
    }
    private static TagKey<Enchantment> Conventionalof(String id) {
        return TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.of("c", id));
    }
    private static TagKey<Enchantment> Vanillaof(String id) {
        return TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.ofVanilla(id));
    }
    private static TagKey<Enchantment> OtherModof(String Modid, String id) {
        return TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(Modid, id));
    }
}
