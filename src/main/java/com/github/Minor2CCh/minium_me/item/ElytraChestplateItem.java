package com.github.Minor2CCh.minium_me.item;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.fabricmc.fabric.api.item.v1.EnchantmentSource;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.List;
import java.util.function.Supplier;

public class ElytraChestplateItem extends ArmorItem implements FabricElytraItem {
    public static final DispenserBehavior DISPENSER_BEHAVIOR = new ItemDispenserBehavior() {
        @Override
        protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            return ArmorItem.dispenseArmor(pointer, stack) ? stack : super.dispenseSilently(pointer, stack);
        }
    };
    protected final ArmorItem.Type type;
    protected final RegistryEntry<ArmorMaterial> material;
    private final Supplier<AttributeModifiersComponent> attributeModifiers;

    public ElytraChestplateItem(RegistryEntry<ArmorMaterial> material, ArmorItem.Type type, Item.Settings settings) {
        super(material, type, settings);
        this.material = material;
        this.type = type;
        DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOR);
        this.attributeModifiers = Suppliers.memoize(
                () -> {
                    int i = material.value().getProtection(type);
                    float f = material.value().toughness();
                    AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
                    AttributeModifierSlot attributeModifierSlot = AttributeModifierSlot.forEquipmentSlot(type.getEquipmentSlot());
                    Identifier identifier = Identifier.ofVanilla("armor." + type.getName());
                    builder.add(
                            EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(identifier, (double)i, EntityAttributeModifier.Operation.ADD_VALUE), attributeModifierSlot
                    );
                    builder.add(
                            EntityAttributes.GENERIC_ARMOR_TOUGHNESS,
                            new EntityAttributeModifier(identifier, (double)f, EntityAttributeModifier.Operation.ADD_VALUE),
                            attributeModifierSlot
                    );
                    float g = material.value().knockbackResistance();
                    if (g > 0.0F) {
                        builder.add(
                                EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE,
                                new EntityAttributeModifier(identifier, (double)g, EntityAttributeModifier.Operation.ADD_VALUE),
                                attributeModifierSlot
                        );
                    }

                    return builder.build();
                }
        );
    }
    /*
    public static boolean isUsable(ItemStack stack) {
        return stack.getDamage() < stack.getMaxDamage() - 1;
    }
     */

    public ArmorItem.Type getType() {
        return ArmorItem.Type.CHESTPLATE;
    }

    public int getProtection() {
        return this.material.value().getProtection(ArmorItem.Type.CHESTPLATE);
    }

    public float getToughness() {
        return this.material.value().toughness();
    }

    public EquipmentSlot getSlotType() {
        return EquipmentSlot.CHEST;
    }
    /*
    public RegistryEntry<SoundEvent> getEquipSound() {
        return this.getMaterial().value().equipSound();
    }*/
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
            consumer.accept((RegistryEntry<Enchantment>)entry.getKey(), entry.getIntValue());
            getLevel = entry.getIntValue();
        }
        return getLevel;
    }
    @FunctionalInterface
    interface Consumer {
        void accept(RegistryEntry<Enchantment> enchantment, int level);
    }
}
