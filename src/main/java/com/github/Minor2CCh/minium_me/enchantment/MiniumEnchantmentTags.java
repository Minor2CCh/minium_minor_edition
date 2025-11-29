package com.github.Minor2CCh.minium_me.enchantment;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

@SuppressWarnings("all")
public class MiniumEnchantmentTags {
    public static final TagKey<Enchantment> TRANSFORMER_STONE = modOf("transformer_stone");
    public static final TagKey<Enchantment> FREEZABLE_TEMPORALLY_BLOCK = modOf("freezable_temporally_block");

    private static TagKey<Enchantment> modOf(String id) {
        return TagKey.of(RegistryKeys.ENCHANTMENT, Minium_me.of(id));
    }
    private static TagKey<Enchantment> conventionalOf(String id) {
        return TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.of("c", id));
    }
    private static TagKey<Enchantment> vanillaOf(String id) {
        return TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.ofVanilla(id));
    }
    private static TagKey<Enchantment> otherModOf(String Modid, String id) {
        return TagKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(Modid, id));
    }
}
