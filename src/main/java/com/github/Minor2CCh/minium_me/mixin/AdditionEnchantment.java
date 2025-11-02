package com.github.Minor2CCh.minium_me.mixin;

import com.github.Minor2CCh.minium_me.item.MiniumItem;
import com.github.Minor2CCh.minium_me.item.MiniumItemTag;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Enchantment.class)
public abstract class AdditionEnchantment {
    @Shadow public abstract boolean isPrimaryItem(ItemStack stack);

    @Shadow public abstract boolean isAcceptableItem(ItemStack stack);

    @ModifyReturnValue(method = "isAcceptableItem(Lnet/minecraft/item/ItemStack;)Z", at = @At("RETURN"))
    private boolean EnchantmentInject(boolean original, ItemStack stack){
        if(stack.isIn(MiniumItemTag.SPEARS) || stack.isIn(MiniumItemTag.ENERGY_GUNS) || stack.isOf(MiniumItem.IRIS_QUARTZ_MACE)){
            Enchantment enchantment = (Enchantment) (Object) this;

            if(!enchantment.getEffect(EnchantmentEffectComponentTypes.EQUIPMENT_DROPS).isEmpty()){//ドロップ数が増加するエンチャント
                if(isAcceptableItem(Items.WOODEN_SWORD.getDefaultStack())){//木の剣が使用できるエンチャント
                    return true;//ドロップ数増加を追加
                }
            }
        }else if(stack.isOf(MiniumItem.TEMPORALLY_BLOCK_PLACER)){
            Enchantment enchantment = (Enchantment) (Object) this;
            if(!enchantment.getEffect(EnchantmentEffectComponentTypes.ITEM_DAMAGE).isEmpty()){
                if(isAcceptableItem(Items.WOODEN_PICKAXE.getDefaultStack())){//木のツルハシが使用できるエンチャント
                    return true;
                }

            }
        }
        return original;
    }
}
