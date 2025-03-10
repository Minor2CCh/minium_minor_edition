package com.github.Minor2CCh.minium_me.enchantment;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class MiniumEnchantments {
    public static final RegistryKey<Enchantment> STONE_ALCHEMY = of("stone_alchemy");
    private static RegistryKey<Enchantment> of(String id) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(Minium_me.MOD_ID, id));
    }
    public static void initialize() {
    }
}
