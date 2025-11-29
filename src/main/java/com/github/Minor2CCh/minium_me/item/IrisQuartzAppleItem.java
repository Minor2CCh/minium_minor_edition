package com.github.Minor2CCh.minium_me.item;

import com.github.Minor2CCh.minium_me.config.MiniumConfigLoader;
import com.github.Minor2CCh.minium_me.registry.MiniumTrackDatas;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class IrisQuartzAppleItem extends Item {
    public IrisQuartzAppleItem(Settings settings) {
        super(settings);
    }
    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if(!world.isClient()){
            EntityAttributeInstance instance = user.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            if(instance != null){
                int healthBoost = user.getDataTracker().get(MiniumTrackDatas.getMaxHealthBoostTracker());
                final int boostLimit = MiniumConfigLoader.getConfig().getHealthBoostLimit();
                if(healthBoost < boostLimit){
                    user.getDataTracker().set(MiniumTrackDatas.getMaxHealthBoostTracker(), Math.min(healthBoost + MiniumConfigLoader.getConfig().getHealthBoostValue(), boostLimit));
                }else {
                    user.setHealth(user.getMaxHealth());
                    if(user instanceof PlayerEntity player){
                        player.sendMessage(Text.translatable("item.minium_me.iris_quartz_apple.max_health"));
                    }
                }

            }
        }
        return super.finishUsing(stack, world, user);
    }
}
