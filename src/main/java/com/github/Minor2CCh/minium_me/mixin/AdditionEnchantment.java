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
        if(stack.isIn(MiniumItemTag.SPEARS) || stack.isIn(MiniumItemTag.ENERGY_GUNS)){
            Enchantment enchantment = (Enchantment) (Object) this;
            if(!enchantment.getEffect(EnchantmentEffectComponentTypes.EQUIPMENT_DROPS).isEmpty()){//ドロップ数が増加するエンチャント
                if(isAcceptableItem(Items.WOODEN_SWORD.getDefaultStack())){//木の剣が使用できるエンチャント
                    return true;//ドロップ数増加を追加
                }
            }
        }
        return original;
    }
    @ModifyReturnValue(method = "isPrimaryItem(Lnet/minecraft/item/ItemStack;)Z", at = @At("RETURN"))
    private boolean PrimaryEnchantmentInject(boolean original, ItemStack stack){
        if(stack.isIn(MiniumItemTag.SPEARS)) {
            if (isPrimaryItem(Items.WOODEN_SWORD.getDefaultStack()) && isAcceptableItem(MiniumItem.MINIUM_SPEAR.getDefaultStack())) {
                return true;
            }
        }
        return original;
    }//剣で付与される攻撃系のエンチャントがエンチャントテーブルから出るように、(ただし、Sinytra Connectorを使用してNeoForgeで使用すると失敗する)
}
