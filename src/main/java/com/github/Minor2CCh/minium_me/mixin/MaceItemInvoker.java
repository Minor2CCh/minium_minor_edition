package com.github.Minor2CCh.minium_me.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.MaceItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MaceItem.class)
public interface MaceItemInvoker {
    @Invoker("knockbackNearbyEntities")
    static void invokeKnockbackNearbyEntities(World world, PlayerEntity player, Entity attacked) {
        throw new AssertionError();
    }
    //@Inject(method = "postHit", at = @At("HEAD"))
}
