package com.github.Minor2CCh.minium_me.item;

import net.fabricmc.fabric.api.entity.event.v1.FabricElytraItem;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.*;

public class IrisQuartzElytraItem extends ElytraItem implements FabricElytraItem {
    public IrisQuartzElytraItem(Item.Settings settings) {
        super(settings);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
    }

    public static boolean isUsable(ItemStack stack) {
        return stack.getDamage() < stack.getMaxDamage() - 1;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.isIn(MiniumItemTag.IRIS_QUARTZ_INGOT);
    }
}
