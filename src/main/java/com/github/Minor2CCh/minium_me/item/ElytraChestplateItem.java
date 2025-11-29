package com.github.Minor2CCh.minium_me.item;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.event.GameEvent;
import org.apache.commons.lang3.mutable.MutableFloat;


public class ElytraChestplateItem extends ArmorItem implements FabricElytraItem {

    public ElytraChestplateItem(RegistryEntry<ArmorMaterial> material, ArmorItem.Type type, Item.Settings settings) {
        super(material, type, settings);
    }
    /*
    public static boolean isUsable(ItemStack stack) {
        return stack.getDamage() < stack.getMaxDamage() - 1;
    }
     */
    @Override
    public void doVanillaElytraTick(LivingEntity entity, ItemStack chestStack) {
        int nextRoll = entity.getFallFlyingTicks() + 1;
        if (!entity.getWorld().isClient && nextRoll % 10 == 0) {
            Consumer consumer = (enchantment, level) -> enchantment.value().modifyItemDamage((ServerWorld) entity.getWorld(), level, chestStack, new MutableFloat(1.0F));
            int lv = forEachEnchantment(chestStack, consumer);
            //System.out.println(lv);
            //耐久力エンチャントを他のツールと同じ仕様に変換する
            if ((nextRoll / 10) % 2 == 0 && entity.getWorld().getRandom().nextDouble() < (1.0 / (1 + lv)) / (0.4 / (lv + 1) + 0.6)) {
                chestStack.damage(1, entity, EquipmentSlot.CHEST);
            }

            entity.emitGameEvent(GameEvent.ELYTRA_GLIDE);
        }
    }

    private static int forEachEnchantment(ItemStack stack, Consumer consumer) {
        ItemEnchantmentsComponent itemEnchantmentsComponent = stack.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT);
        int getLevel = 0;
        for (Object2IntMap.Entry<RegistryEntry<Enchantment>> entry : itemEnchantmentsComponent.getEnchantmentEntries()) {
            consumer.accept(entry.getKey(), entry.getIntValue());
            getLevel = entry.getIntValue();
        }
        return getLevel;
    }
    @FunctionalInterface
    interface Consumer {
        void accept(RegistryEntry<Enchantment> enchantment, int level);
    }
}
