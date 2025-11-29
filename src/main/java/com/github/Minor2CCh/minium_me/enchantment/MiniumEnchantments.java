package com.github.Minor2CCh.minium_me.enchantment;

import com.github.Minor2CCh.minium_me.Minium_me;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

@SuppressWarnings("unused")
public class MiniumEnchantments {
    public static final RegistryKey<Enchantment> STONE_ALCHEMY = of("stone_alchemy");
    public static final RegistryKey<Enchantment> ENERGY_BOOST = of("energy_boost");
    private static RegistryKey<Enchantment> of(String id) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, Minium_me.of(id));
    }
    public static void initialize() {

    }
    public static void giveCustomEnchantment(ServerPlayerEntity player, ItemStack stack, int level, RegistryKey<Enchantment> enchantmentKey) {
        if(player.getWorld().isClient()){
            return;
        }
        RegistryEntry<Enchantment> entry = player.getWorld()
                .getRegistryManager()
                .get(RegistryKeys.ENCHANTMENT)
                .getEntry(enchantmentKey)
                .orElseThrow();
        //System.out.println(entry);
        stack.addEnchantment(entry, level);
    }
    public static void giveCustomEnchantment(ServerWorld world, ItemStack stack, int level, RegistryKey<Enchantment> enchantmentKey) {
        RegistryEntry<Enchantment> entry = world
                .getRegistryManager()
                .get(RegistryKeys.ENCHANTMENT)
                .getEntry(enchantmentKey)
                .orElseThrow();
        //System.out.println(entry);
        stack.addEnchantment(entry, level);
    }
    public static int getEnchantmentLevel(Entity entity, ItemStack stack, RegistryKey<Enchantment> enchantmentKey) {
        if(entity.getWorld().isClient()){
            return 0;
        }
        return getEnchantmentLevel((ServerWorld) entity.getWorld(), stack, enchantmentKey);
    }
    public static int getEnchantmentLevel(ServerWorld world, ItemStack stack, RegistryKey<Enchantment> enchantmentKey) {
        if(world.isClient()){
            return 0;
        }
        RegistryEntry<Enchantment> entry = world
                .getRegistryManager()
                .get(RegistryKeys.ENCHANTMENT)
                .getEntry(enchantmentKey)
                .orElseThrow();
        return EnchantmentHelper.getLevel(entry, stack);
    }
    public static boolean getAcceptEnchantment(Entity entity, ItemStack stack, RegistryKey<Enchantment> enchantmentKey) {
        if(entity.getWorld().isClient()){
            return false;
        }
        RegistryEntry<Enchantment> entry = entity.getWorld()
                .getRegistryManager()
                .get(RegistryKeys.ENCHANTMENT)
                .getEntry(enchantmentKey)
                .orElseThrow();
        Enchantment enchantment = entry.value();
        return enchantment.isAcceptableItem(stack);
    }
}
