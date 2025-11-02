package com.github.Minor2CCh.minium_me.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
    @Accessor("playerHitTimer")
    int playerHitTimer();
    @Accessor("playerHitTimer")
    void setPlayerHitTimer(int newPlayerHitTimer);
    @Accessor("activeItemStack")
    ItemStack getActiveItemStack();

}